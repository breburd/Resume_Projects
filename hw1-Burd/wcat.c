/*
	Homework 1
	2/22/2022
	wcat.c
	
	Reads from a file(s) and outputs the contents to standard output
	
	Author: Bre Burd
*/

#include <stdio.h>
#include <stdlib.h>

int main(int argc, char *argv[]) {

	if(argc < 2){
		exit(1);
	}
	
	//Starts with the first file and loops through all of them
	for(int i = 1; i < argc; i++) {
		//Opens the file listed in the command line
		FILE* file = fopen(argv[i], "r");
		
		//Check to see if the file was opened successfully
		if(file == NULL){
			printf("wcat: cannot open file\n");
			exit(1);
		}
		
		int buf_size = 1024;
		char buffer[buf_size];
		
		//Read from the file and print the contents
		while(fgets(buffer, buf_size, file) != NULL){
			printf("%s", buffer);
		}
		
		fclose(file);
	}
		
	
	return 0;
}
