
#include <stdio.h>
#include <string.h>
#include <stdlib.h>

#include "utils.h"
#include "tables.h"

const int SYMTBL_NON_UNIQUE = 0;
const int SYMTBL_UNIQUE_NAME = 1;

#define INITIAL_SIZE 5
#define SCALING_FACTOR 2

/*******************************
 * Helper Functions
 *******************************/

void allocation_failed() {
    write_to_log("Error: allocation failed\n");
    exit(1);
}

void addr_alignment_incorrect() {
    write_to_log("Error: address is not a multiple of 4.\n");
}

void name_already_exists(const char* name) {
    write_to_log("Error: name '%s' already exists in table.\n", name);
}

void write_symbol(FILE* output, uint32_t addr, const char* name) {
    fprintf(output, "%u\t%s\n", addr, name);
}

/*******************************
 * Symbol Table Functions
 *******************************/


SymbolTable* create_table(int mode) {
    SymbolTable *table = (SymbolTable *) calloc(1, sizeof(SymbolTable));
    if (!table) {
	allocation_failed();
    }
    Symbol* lst = (Symbol*) malloc(sizeof(Symbol) * INITIAL_SIZE); 	//create a Symbol list of initial size for table.
    if (!lst) {
	free(table); 		//free table if unable to allocate space for symbol list
	allocation_failed();
    }
    table->len = 0; 		//current length of empty table
    table->cap = INITIAL_SIZE;  //maximum capacity of this table
    table->mode = mode;		//either UNIQUE or NON_UNIQUE
    table->tbl = lst;		//assign the allocated list to field
    return table;
}

/* Frees the given SymbolTable and all associated memory. */
void free_table(SymbolTable* table) {
    if (!table) { 				//null check input
	return;
    }
    Symbol *ptr = table->tbl;			//assign pointer to symbol list so as not to lose pointer to original address
    for (int i = 0; i < table->len; i += 1) {	//only free pointer fields of symbols actually in the list
    	Symbol *del = ptr; 			//point to the current symbol in the lsit 
	ptr++;					//increment ptr to the next symbol in the list
	free(del->name);			//free the char allocated space for the symbol name
    }
    free(table->tbl);				//free the symbol list, since all the pointers within have been freed
    free(table);				//free the SymbolTable since all the pointer within it have been freed
}


static char* create_copy_of_str(const char* str) {
    size_t len = strlen(str) + 1;
    char *buf = (char *) malloc(len);
    if (!buf) {
       allocation_failed();
    }
    strncpy(buf, str, len); 
    return buf;
}

/* Resized the TABLE by a scale of FACTOR * current length. 
	
   Creates a bigger list of size scale by geometrically
   increasing it to optimize performance. Copies each element 
   from original list in TABLE to the new list, copying both
   the addr and pointer to name. Frees the original list in 
   TABLE. Assigns bigger list to table and updates maximum 
   capacity of the table.
*/
void resize_table(SymbolTable *table, int factor) {
    int scale = table->cap * factor;
    Symbol *big = (Symbol *) malloc(sizeof(Symbol) * scale);
    if (!big) {
	allocation_failed();
    }
    Symbol *orig = table->tbl;
    Symbol *ptr = big;
    for (int i = 0; i < table->len; i += 1) {
	ptr[i].name = orig[i].name;
	ptr[i].addr = orig[i].addr;
    }
    free(table->tbl);
    table->tbl = big;
    table->cap = scale;
}


int add_to_table(SymbolTable* table, const char* name, uint32_t addr) {
    if (addr % 4 != 0) {
	addr_alignment_incorrect(); 
        return -1;
    }
    if (!table || !name) {
	return -1;
    }
    if (table->mode == SYMTBL_UNIQUE_NAME) {
	Symbol *ptr = table->tbl;
	for (int i = 0; i < table->len; i += 1) {
	    if (strcmp(ptr[i].name, name) == 0) {
		name_already_exists(name);
		return -1;
	    }
	}
    } 
    if (table->len >= table->cap) {
	resize_table(table, SCALING_FACTOR);
    }
    char *str = create_copy_of_str(name);
    int next = table->len;
    table->tbl[next].name = str;
    table->tbl[next].addr = addr;
    table->len += 1;
    return 0;
}

/* Returns the address (byte offset) of the given symbol. If a symbol with name
   NAME is not present in TABLE, return -1.


   Null check the pointer inputs. Check every item in the length of the list
   and compare names. Return the address of the first match. Otherwise -1. 
 */
int64_t get_addr_for_symbol(SymbolTable* table, const char* name) {
    if (!table || !name) {
	return -1;
    }
    Symbol *ptr = table->tbl;
    for (int i = 0; i < table->len; i += 1) {
	if (strcmp(ptr[i].name, name) == 0) {
	    return ptr[i].addr;
	}
    }
    return -1;   
}

/* Writes the SymbolTable TABLE to OUTPUT. You should use write_symbol() to
   perform the write. Do not print any additional whitespace or characters.
 */
void write_table(SymbolTable* table, FILE* output) {
    if (!table || !output) {
	return;
    }
    Symbol *ptr = table->tbl;
    for (int i = 0; i < table->len; i += 1) {
	write_symbol(output, ptr[i].addr, ptr[i].name);
    }
}
