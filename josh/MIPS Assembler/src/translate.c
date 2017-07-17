#include <stdio.h>
#include <string.h>
#include <stdlib.h>

#include "tables.h"
#include "translate_utils.h"
#include "translate.h"


const int TWO_POW_SEVENTEEN = 131072;    // 2^17


unsigned write_pass_one(FILE* output, const char* name, char** args, int num_args) {
    if (!output || !name || !args || num_args < 0) {
      return 0;
    }
    if (strcmp(name, "li") == 0) {
        if (num_args != 2) {
            return 0;
        }
    
        long int up;
    	if (translate_num(&up, args[1], INT32_MIN, UINT32_MAX) == -1) {  //if the immediate does not fit in 32 bits, return 0
	    return 0;	
    	}

        if (up >= INT16_MIN && up <= INT16_MAX) {       //if the immediate fits in 16 bits, use addiu to load it
	    fprintf(output, "addiu %s $0 %ld\n", args[0], up);
   	    return 1;
    	}
 
        uint16_t pos = (unsigned) up >> 16;      //shift immediate right 16 to remove bottom 16 bits and store top 16 bits 
        fprintf(output, "lui $at %d\n", pos);

        uint16_t down = (unsigned) up << 16 >> 16;    //shift immediate left 16 and then right 16 to remove top 16 bits and store bottom 16 bits 
        fprintf(output, "ori %s $at %d\n", args[0], down); 

        return 2;  
    } else if (strcmp(name, "push") == 0) {
	
  if (num_args != 1) {
	    return 0;
	}
	
	
	fprintf(output, "%s %s %s %d\n", "addiu", "$sp", "$sp", -4);         //check number of args for each command and expand pseudo instructions
  fprintf(output, "%s %s %d%s\n", "sw", args[0], 0, "($sp)");
  return 2;
    
    } else if (strcmp(name, "pop") == 0) {
        if (num_args != 1) {
      return 0;
    }
    fprintf(output, "%s %s %d%s\n", "lw", args[0], 0, "($sp)");
    fprintf(output, "%s %s %s %d\n", "addiu", "$sp", "$sp", 4);
    return 2;

  } else if (strcmp(name, "mod") == 0) {
        if (num_args != 3) {
          return 0;
        }
        fprintf(output, "div %s %s\n", args[1], args[2]);
        fprintf(output, "mfhi %s\n", args[0]);
        return 2;
    

    } else if (strcmp(name, "subu") == 0) {
        if (num_args != 3) {
          return 0;
    }


        fprintf(output, "%s %s %s %d\n", "addiu", "$at", "$0", -1);
        fprintf(output, "%s %s %s %s\n", "xor", "$at", "$at", args[2]);
        fprintf(output, "%s %s %s %d\n", "addiu", "$at", "$at", 1);
        fprintf(output, "%s %s %s %s\n", "addu", args[0], args[1], "$at");
        return 4;
    }
    write_inst_string(output, name, args, num_args);
    return 1;

}


int translate_inst(FILE* output, const char* name, char** args, size_t num_args, uint32_t addr,
    SymbolTable* symtbl, SymbolTable* reltbl) {
  if (!name) {
    return -1;
  }
    if (strcmp(name, "addu") == 0)       return write_rtype(0x21, output, args, num_args);              //returns what to write for each valid command
    else if (strcmp(name, "or") == 0)    return write_rtype(0x25, output, args, num_args);
    else if (strcmp(name, "slt") == 0)   return write_rtype(0x2a, output, args, num_args);
    else if (strcmp(name, "sltu") == 0)  return write_rtype(0x2b, output, args, num_args);
    else if (strcmp(name, "sll") == 0)   return write_shift(0x00, output, args, num_args);
    
    else if (strcmp(name, "xor") == 0) 	 return write_rtype(0x26, output, args, num_args);
    else if (strcmp(name, "jr") == 0) 	 return write_jr(0x08, output, args, num_args);
    else if (strcmp(name, "addiu") == 0) {
	 return write_addiu(0x9, output, args, num_args);
    }
    else if (strcmp(name, "ori") == 0)   return write_ori(0xd, output, args, num_args);
    else if (strcmp(name, "lui") == 0)   return write_lui(0xf, output, args, num_args);
    else if (strcmp(name, "lb") == 0)  	 return write_mem(0x20, output, args, num_args);
    else if (strcmp(name, "lbu") == 0)   return write_mem(0x24, output, args, num_args);
    else if (strcmp(name, "lw") == 0)    return write_mem(0x23, output, args, num_args);
    else if (strcmp(name, "sb") == 0)    return write_mem(0x28, output, args, num_args);
    else if (strcmp(name, "sw") == 0)    return write_mem(0x2b, output, args, num_args);
    else if (strcmp(name, "beq") == 0){   
      return write_branch(0x4, output, args, num_args, addr, symtbl);
    }
    else if (strcmp(name, "bne") == 0){   
      return write_branch(0x5, output, args, num_args, addr, symtbl);
    }
    else if (strcmp(name, "j") == 0)     return write_jump(0x2, output, args, num_args, addr, reltbl);
    else if (strcmp(name, "jal") == 0)   return write_jump(0x3, output, args, num_args, addr, reltbl);
    else if (strcmp(name, "mult") == 0)  return write_mult_div(0x18, output, args, num_args);
    else if (strcmp(name, "div") == 0)   return write_mult_div(0x1a, output, args, num_args);
    else if (strcmp(name, "mfhi") == 0)  return write_mfhi_mflo(0x10, output, args, num_args);
    else if (strcmp(name, "mflo") == 0)  return write_mfhi_mflo(0x12, output, args, num_args);
    else                                 return -1;
}

/* A helper function for writing most R-type instructions. You should use
   translate_reg() to parse registers and write_inst_hex() to write to 
   OUTPUT. Both are defined in translate_utils.h.

   This function is INCOMPLETE. Complete the implementation below. You will
   find bitwise operations to be the cleanest way to complete this function.
 */
int write_rtype(uint8_t funct, FILE* output, char** args, size_t num_args) {
    
    if (funct > 63 || num_args != 3 || !output || !args) {               //check if funct fits in 6 bits, correct args, null check
	return -1;
    }
    int rd = translate_reg(args[0]);
    int rs = translate_reg(args[1]);
    int rt = translate_reg(args[2]);
    if (rd == -1 || rs == -1 || rt == -1) {   //check validity of values
	return -1;
    }
    unsigned urd = (unsigned) rd;             // cast rs rt rd to unsigned values
    unsigned urs = (unsigned) rs;
    unsigned urt = (unsigned) rt;

    uint32_t instruction = 0;

    instruction = instruction | (urd << 11);           //shift values to correct locations of instruction
    instruction = instruction | (urs << 21);
    instruction = instruction | (urt << 16);
    instruction = instruction | funct;
    write_inst_hex(output, instruction);
    return 0;
}


int write_shift(uint8_t funct, FILE* output, char** args, size_t num_args) {
	// Perhaps perform some error checking?

  if (funct > 63 || num_args != 3 || !output || !args) {
  return -1;
    }

    long int shamt;
    int rd = translate_reg(args[0]);
    int rt = translate_reg(args[1]);
    int err = translate_num(&shamt, args[2], 0, 31);  //check that shift fits in 5 bits
    if (rd == -1 || rt == -1 || err == -1) {
  return -1;
    }

    unsigned urd = (unsigned) rd;
    unsigned uShamt = (unsigned) shamt;
    unsigned urt = (unsigned) rt;

    uint32_t instruction = 0;
    instruction = instruction | (urd << 11);        //shift values to correct locations of instruction
    instruction = instruction | (urt << 16);
    instruction = instruction | (uShamt << 6);
    instruction = instruction | funct;
    write_inst_hex(output, instruction);
    return 0;
}



int write_jr(uint8_t funct, FILE* output, char** args, size_t num_args) {
   

  if (funct > 63 || num_args != 1 || !output || !args) {
  return -1;
    }

    int rs = translate_reg(args[0]);

    if (rs == -1) {
  return -1;      //check validity of rs
    }

    unsigned urs = (unsigned) rs;

    uint32_t instruction = 0;                     //shift values to correct locations of instruction
    instruction = instruction | (urs << 21);
    instruction = instruction | funct;
    write_inst_hex(output, instruction);
    return 0;
}

int write_addiu(uint8_t opcode, FILE* output, char** args, size_t num_args) {
   

  if (opcode > 63 || num_args != 3 || !output || !args) {
  return -1;
    }
    
    long int imm;
    int rt = translate_reg(args[0]);
    int rs = translate_reg(args[1]);
    int err = translate_num(&imm, args[2], INT16_MIN, INT16_MAX);    //make sure immediate fits in 16 bits

    if (rs == -1 || rt == -1 || err == -1) {
  return -1;
    }

    unsigned urs = (unsigned) rs;
    unsigned urt = (unsigned) rt;
    imm = (unsigned) 65535 & imm;        //make sure imm does not sign extend by zeroing out top 16 bits of 32 bit imm

    uint32_t instruction = 0;

    instruction = instruction | (urt << 16);      //shift values to correct locations of instruction
    instruction = instruction | (urs << 21);
    instruction = instruction | imm;
    instruction = instruction | (opcode << 26);
    write_inst_hex(output, instruction);
    return 0;
}

int write_ori(uint8_t opcode, FILE* output, char** args, size_t num_args) {
    

  if (opcode > 63 || num_args != 3 || !output || !args) {
  return -1;
    }
    
    long int imm;
    int rt = translate_reg(args[0]);
    int rs = translate_reg(args[1]);
    int err = translate_num(&imm, args[2], 0, UINT16_MAX);          //make sure imm is unsigned and fits in 16 bits

    if (rs == -1 || rt == -1 || err == -1) {
  return -1;
    }

    unsigned urs = (unsigned) rs;
    unsigned uImm = (unsigned) imm;
    unsigned urt = (unsigned) rt;

    uint32_t instruction = 0;
    instruction = instruction | (urt << 16);             //shift values to correct locations of instruction
    instruction = instruction | (urs << 21);
    instruction = instruction | uImm;
    instruction = instruction | (opcode << 26);
    write_inst_hex(output, instruction);
    return 0;
}

int write_mult_div(uint8_t funct, FILE* output, char** args, size_t num_args) {
   
  if (funct > 63 || num_args != 2 || !output || !args) {
  return -1;
    }

	int rs = translate_reg(args[0]);
	int rt = translate_reg(args[1]);
		        
	if (rs < 0 || rt < 0) {
		return -1;
	}

    unsigned urs = (unsigned) rs;
    unsigned urt = (unsigned) rt;

			    
	uint32_t instruction = 0;                   //shift values to correct locations of instruction
  instruction = instruction | (urt << 16);
  instruction = instruction | (urs << 21);
  instruction = instruction | funct;

	write_inst_hex(output, instruction);
	return 0;
}

int write_mfhi_mflo(uint8_t funct, FILE* output, char** args, size_t num_args) {
    	

  if (funct > 63 || num_args != 1 || !output || !args) {
  return -1;
    }

	int rd = translate_reg(args[0]);
	if (rd < 0) {
		return -1;
	}

  unsigned urd = (unsigned) rd;

	uint32_t instruction = 0;                //shift values to correct locations of instruction
  instruction = instruction | (urd << 11);
  instruction = instruction | funct;

	write_inst_hex(output, instruction);
	return 0;
}

int write_lui(uint8_t opcode, FILE* output, char** args, size_t num_args) {
    
    
  if (opcode > 63 || num_args != 2 || !output || !args) {
  return -1;
    }

    long int imm;
    int rt = translate_reg(args[0]);
    int err = translate_num(&imm, args[1], 0, UINT16_MAX);      //make sure imm is unsigned and fits in 16 bits

    if (rt == -1 || err == -1) {
  return -1;
    }

    unsigned uImm = (unsigned) imm;
    unsigned urt = (unsigned) rt;

    uint32_t instruction = 0;
    instruction = instruction | uImm;       //shift values to correct locations of instruction
    instruction = instruction | (urt << 16);
    instruction = instruction | (opcode << 26);

    write_inst_hex(output, instruction);
    return 0;
}

int write_mem(uint8_t opcode, FILE* output, char** args, size_t num_args) {
 

  if (opcode > 63 || num_args != 3 || !output || !args) {
  return -1;
    }
    
    long int imm;
    int rt = translate_reg(args[0]);
    int rs = translate_reg(args[2]);
    int err = translate_num(&imm, args[1], INT16_MIN, INT16_MAX);    //make sure imm fits in 16 bits

    if (rt == -1 || err == -1 || rs == -1) {
  return -1;
    }
    unsigned urt = (unsigned) rt;
    unsigned urs = (unsigned) rs;

    uint32_t uImm = (unsigned) 65535 & imm;   //make sure imm does not sign extend by zeroing out top 16 bits of 32 bit imm

    uint32_t instruction = 0;
    instruction = instruction | uImm;
    instruction = instruction | (urt << 16);
    instruction = instruction | (urs << 21);     //shift values to correct locations of instruction
    instruction = instruction | (opcode << 26);
    write_inst_hex(output, instruction);
    return 0;
}

/*  A helper function to determine if a destination address
    can be branched to
*/
static int can_branch_to(uint32_t src_addr, uint32_t dest_addr) {
    int32_t diff = dest_addr - src_addr;
    return (diff >= 0 && diff <= TWO_POW_SEVENTEEN) || (diff < 0 && diff >= -(TWO_POW_SEVENTEEN - 4));
}


int write_branch(uint8_t opcode, FILE* output, char** args, size_t num_args, uint32_t addr, SymbolTable* symtbl) {
    
    if (opcode > 63 || !output || !args || num_args != 3 || !symtbl) {
	return -1;
    }
    int rs = translate_reg(args[0]);
    int rt = translate_reg(args[1]);
    int label_addr = get_addr_for_symbol(symtbl, args[2]);    //get label_addr for label from symtbl

    if (rs == -1 || rt == -1 || label_addr == -1) {
	return -1;
    }
  
    
    if (!can_branch_to(addr, label_addr)) {
	return -1;
    }
    
    int16_t offset = (label_addr) / 4 - addr / 4 - 1;    //label_addr and addr are byte offsets, must divide by 4 to calculate difference in # of lines
    uint32_t off = (unsigned) 65535 & offset;       //make sure imm does not sign extend by zeroing out top 16 bits of 32 bit imm
    uint32_t instruction = 0;
    instruction = instruction | (opcode << 26);      //shift values to correct locations of instruction
    instruction = instruction| (rs << 21); 
    instruction = instruction| (rt << 16); 
    instruction = instruction | off;
    write_inst_hex(output, instruction);        
    return 0;
}

int write_jump(uint8_t opcode, FILE* output, char** args, size_t num_args, uint32_t addr, SymbolTable* reltbl) {
  

  if (opcode > 63 || !output || !args || num_args != 1 || !reltbl) {
  return -1;
    }

    uint32_t instruction = 0;
    instruction = instruction | (opcode << 26); //shift opcode to correct locations of instruction, leave addr in instruction as 0
    
    add_to_table(reltbl, args[0], addr);   //add label and byte offset to reltbl
    write_inst_hex(output, instruction);
    return 0;
}
