/*Breanna Burd
  Homework 1
  wuzip.c
  Reads through a file and prints out each line
  that has a specific character sting.
*/

#include <stdio.h>
#include <stdlib.h>


int main(int argc, char** argv){
  //If no file is listed
  if(argc < 2){
    printf("wunzip: file1 [file2 ...]\n");
    exit(1);
  }
  //Output compression to stdout
  if(*argv[argc-1] != '>'){
    for(int i = 1; i < argc; i++) {
      FILE *file;
      file = fopen(argv[i], "rb");
      if(file == NULL)
        exit(1);
      int count; 
      char ch;
      while(!feof(file)){
      	fread(&count, sizeof(count), 1, file);
        fread(&ch, sizeof(ch), 1, file);
        for(int i = 0; i < count; i++){
          printf("%c", ch);
        }
      }
      fclose(file);
    }
  }

  //Output to a file
  else{
  	printf("Else statement");
    FILE *unzip_file;
    unzip_file = fopen(argv[argc], "w");
    if(unzip_file == NULL){
    	printf("Did not open unzip file");
      exit(1);
     }
    for(int i = 1; i < argc-1; i++) {
      FILE *file;
      file = fopen(argv[i], "rb");
      if(file == NULL){
      	printf("File did not open (2)\n");
        exit(1);
      }
      int count; 
      char ch;
      printf("Before reading in the count");
      while(!feof(file)){
      	fread(&count, sizeof(count), 1, file);
        fread(&ch, sizeof(ch), 1, file);
        for(int i = 0; i < count; i++){
          fprintf(unzip_file, "%s", &ch);
        }
      }
      fclose(file);
    }
    fclose(unzip_file);
  }
  
  return 0;
}
