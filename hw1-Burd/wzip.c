/*Breanna Burd
  Homework 1
  wzip.c
  Reads through a file and prints out each line
  that has a specific character sting.
*/

#include <stdio.h>
#include <stdlib.h>


int main(int argc, char** argv){
  //If no file is listed
  if(argc < 2){
    printf("wzip: file1 [file2 ...]\n");
    exit(1);
  }
  //Output compression to stdout
  if(*argv[argc-1] != '>'){
    for(int i = 1; i < argc; i++) {
      FILE *file;
      file = fopen(argv[i], "r");
      if(file == NULL)
        exit(1);

      int count = 1; 
      char ch = fgetc(file);
      char previous = ch;
      while(ch != EOF){
      	ch = fgetc(file);
        //If they are not the same char, put it into binaries.
        if(previous != ch){
          fwrite(&count, sizeof(count), 1, stdout);
          fwrite(&previous, sizeof(char), 1, stdout);
          count = 0;
          previous = ch;
        }
        count++;
      }
    }
    
    
  }
  //Output to a file
  else{
    FILE *zip_file;
    zip_file = fopen(argv[argc], "wb");
    if(zip_file == NULL)
      exit(1);
    for(int i = 1; i < argc-1; i++) {
      FILE *file;
      file = fopen(argv[i], "r");
      if(file == NULL)
        exit(1);
      
      int count = 1; 
      char ch = fgetc(file);
      char previous = ch;
      while(ch != EOF){
        ch = fgetc(file);
        //If they are not the same char, put it into binaries.
        if(previous != ch){
          fwrite(&count, sizeof(count), 1, zip_file);
          fprintf(zip_file, "%s", &previous);
          count = 1;
          previous = ch;
        }
         count++;
      }
      fclose(file);
    }
    fclose(zip_file);
  }
  return 0;
}
