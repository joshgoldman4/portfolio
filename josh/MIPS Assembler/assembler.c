#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "src/utils.h"
#include "src/tables.h"
#include "src/translate_utils.h"
#include "src/translate.h"
#include "assembler.h"

const int MAX_ARGS = 3;
const int BUF_SIZE = 1024;
const char* IGNORE_CHARS = " \f\n\r\t\v,()";



static void raise_label_error(uint32_t input_line, const char* label) {
    write_to_log("Error - invalid label at line %d: %s\n", input_line, label);
}


static void raise_extra_arg_error(uint32_t input_line, const char* extra_arg) {
    write_to_log("Error - extra argument at line %d: %s\n", input_line, extra_arg);
}


static void raise_inst_error(uint32_t input_line, const char* name, char** args,
    int num_args) {
    
    write_to_log("Error - invalid instruction at line %d: ", input_line);
    log_inst(name, args, num_args);
}

/* Truncates the string at the first occurrence of the '#' character. */
static void skip_comment(char* str) {
    char* comment_start = strchr(str, '#');
    if (comment_start) {
        *comment_start = '\0';
    }
}

/* Reads STR and determines whether it is a label (ends in ':'), and if so,
   whether it is a valid label, and then tries to add it to the symbol table.

   INPUT_LINE is which line of the input file we are currently processing. Note
   that the first line is line 1 and that empty lines are included in this count.

   BYTE_OFFSET is the offset of the NEXT instruction (should it exist). 

   Four scenarios can happen:
    1. STR is not a label (does not end in ':'). Returns 0.
    2. STR ends in ':', but is not a valid label. Returns -1.
    3a. STR ends in ':' and is a valid label. Addition to symbol table fails.
        Returns -1.
    3b. STR ends in ':' and is a valid label. Addition to symbol table succeeds.
        Returns 1.
 */
static int add_if_label(uint32_t input_line, char* str, uint32_t byte_offset,
    SymbolTable* symtbl) {
    
    size_t len = strlen(str);
    if (str[len - 1] == ':') {
        str[len - 1] = '\0';
        if (is_valid_label(str)) {
            if (add_to_table(symtbl, str, byte_offset) == 0) {
                return 1;
            } else {
                return -1;
            }
        } else {
            raise_label_error(input_line, str);
            return -1;
        }
    } else {
        return 0;
    }
}



/*  A helpful helper function that parses instruction arguments. It raises an error
    if too many arguments have been passed into the instruction.
*/
static int parse_args(uint32_t input_line, char** args, int* num_args) {
    char* token;
    while ((token = strtok(NULL, IGNORE_CHARS))) {
        if (*num_args < MAX_ARGS) {
            args[*num_args] = token;
            (*num_args)++;
        } else {
            raise_extra_arg_error(input_line, token);
            return -1;
        }
    }
    return 0;
}


int pass_one(FILE* input, FILE* output, SymbolTable* symtbl) {

    if (!input) {             //if there is no input file, return -1
        return -1;
    }

    char buf[BUF_SIZE];
    uint32_t input_line = 0, byte_offset = 0;
    int ret_code = 0;


     // Read lines and add to instructions
    while(fgets(buf, BUF_SIZE, input)) {
        input_line++;

        // Ignore comments
        skip_comment(buf);

        // Scan for the instruction name
    	char* token = strtok(buf, IGNORE_CHARS);
        if (!token) {         //if token is empty space, continue to next line
            continue;
        }
        int label;
        if ((label = add_if_label(input_line, token, byte_offset, symtbl)) == -1) {    //if addition to symtbl errors or token is not valid label, continue to next line
            ret_code = -1;
            continue;
        } else if (label == 1) {
	    char* ptr = strtok(NULL, IGNORE_CHARS);       //if label is valid but it is the only input on the line, continue to the next line
	    if (!ptr) {
		continue;
	    }
	    token = ptr;         //otherwise, assign this next input to token (it is the name of an instruction)
	} 
        
        // Scan for arguments
        char* args[MAX_ARGS];
        int num_args = 0;
        
        if (parse_args(input_line, args, &num_args) == -1) {
            ret_code = -1;
        }

    	// Checks to see if there were any errors when writing instructions
        unsigned int lines_written = write_pass_one(output, token, args, num_args);
        if (lines_written == 0) {
            raise_inst_error(input_line, token, args, num_args);
            ret_code = -1;
        } 
        byte_offset += lines_written * 4;
    }       
    return ret_code;
}


int pass_two(FILE *input, FILE* output, SymbolTable* symtbl, SymbolTable* reltbl) {
    

    if (!input) {           //if there is no input file, return -1
        return -1;
    }

    /* Since we pass this buffer to strtok(), the characters in this buffer will
       GET CLOBBERED. */
    char buf[BUF_SIZE];
    /* Store input line number / byte offset. When should each be incremented? */
    uint32_t input_line = 0; 
    uint32_t byte_offset = 0;
    int ret_code = 0;

    /* First, read the next line into a buffer. */
    while (fgets(buf, BUF_SIZE, input)) {
        input_line++;   //increment line 

        /* Next, use strtok() to scan for next character.*/
        char* name = strtok(buf, IGNORE_CHARS);  //set name
        
        // Error checking?

        
        char* args[MAX_ARGS];
        int num_args = 0;
        if (parse_args(input_line, args, &num_args) == -1) {
            ret_code = -1;
        }
        
        

        if (translate_inst(output, name, args, num_args, byte_offset, symtbl, reltbl) == -1) {
            raise_inst_error(input_line, name, args, num_args);
            ret_code = -1;
        }

        byte_offset += 4;  //increment byte offset

        
    }
    /* Repeat until no more characters are left */

    return ret_code;
}



static int open_files(FILE** input, FILE** output, const char* input_name, 
    const char* output_name) {
    
    *input = fopen(input_name, "r");
    if (!*input) {
        write_to_log("Error: unable to open input file: %s\n", input_name);
        return -1;
    }
    *output = fopen(output_name, "w");
    if (!*output) {
        write_to_log("Error: unable to open output file: %s\n", output_name);
        fclose(*input);
        return -1;
    }
    return 0;
}

static void close_files(FILE* input, FILE* output) {
    fclose(input);
    fclose(output);
}

/* Runs the two-pass assembler. Most of the actual work is done in pass_one()
   and pass_two().
 */
int assemble(const char* in_name, const char* tmp_name, const char* out_name) {
    FILE *src, *dst;
    int err = 0;
    SymbolTable* symtbl = create_table(SYMTBL_UNIQUE_NAME);
    SymbolTable* reltbl = create_table(SYMTBL_NON_UNIQUE);

    if (in_name) {
        printf("Running pass one: %s -> %s\n", in_name, tmp_name);
        if (open_files(&src, &dst, in_name, tmp_name) != 0) {
            free_table(symtbl);
            free_table(reltbl);
            exit(1);
        }

        if (pass_one(src, dst, symtbl) != 0) {
            err = 1;
        }
        close_files(src, dst);
    }

    if (out_name) {
        printf("Running pass two: %s -> %s\n", tmp_name, out_name);
        if (open_files(&src, &dst, tmp_name, out_name) != 0) {
            free_table(symtbl);
            free_table(reltbl);
            exit(1);
        }

        fprintf(dst, ".text\n");
        if (pass_two(src, dst, symtbl, reltbl) != 0) {
            err = 1;
        }
        
        fprintf(dst, "\n.symbol\n");
        write_table(symtbl, dst);

        fprintf(dst, "\n.relocation\n");
        write_table(reltbl, dst);

        close_files(src, dst);
    }
    
    free_table(symtbl);
    free_table(reltbl);
    return err;
}

static void print_usage_and_exit() {
    printf("Usage:\n");
    printf("  Runs both passes: assembler <input file> <intermediate file> <output file>\n");
    printf("  Run pass #1:      assembler -p1 <input file> <intermediate file>\n");
    printf("  Run pass #2:      assembler -p2 <intermediate file> <output file>\n");
    printf("Append -log <file name> after any option to save log files to a text file.\n");
    exit(0);
}

int main(int argc, char **argv) {
    if (argc != 4 && argc != 6) {
        print_usage_and_exit();
    }

    int mode = 0;
    if (strcmp(argv[1], "-p1") == 0) {
        mode = 1;
    } else if (strcmp(argv[1], "-p2") == 0) {
        mode = 2;
    }

    char *input, *inter, *output;
    if (mode == 1) {
        input = argv[2];
        inter = argv[3];
        output = NULL;
    } else if (mode == 2) {
        input = NULL;
        inter = argv[2];
        output = argv[3];
    } else {
        input = argv[1];
        inter = argv[2];
        output = argv[3];
    }

    if (argc == 6) {
        if (strcmp(argv[4], "-log") == 0) {
            set_log_file(argv[5]);
        } else {
            print_usage_and_exit();
        }
    }

    int err = assemble(input, inter, output);

    if (err) {
        write_to_log("One or more errors encountered during assembly operation.\n");
    } else {
        write_to_log("Assembly operation completed successfully.\n");
    }

    if (is_log_file_set()) {
        printf("Results saved to %s\n", argv[5]);
    }

    return err;
}
