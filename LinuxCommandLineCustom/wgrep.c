/*Breanna Burd
  Homework 1
  wgrep.c
  Reads through a file and prints out each line
  that has a specific character sting.
*/

#include <stdio.h>
#include <stdlib.h>
#include<string.h>

int main(int argc, char** argv) {
  //If there aren't enough command-line arguments
  if(argc < 2) {
    printf("wgrep: searchterm [file ...]\n");
    exit(1);
  }
  char *word = argv[1];

  //Reads from a file if they are listed
  if(argc > 2){
    FILE* file;
    for(int i = 2; i < argc; i++){
      file = fopen(argv[i], "r");
    
      if(file == NULL){
        printf("wgrep: cannot open file\n");
        exit(1);
      }
    
      char *line = NULL;
      size_t len = 0;
      
      while(getline(&line, &len, file) != -1){
        if(strstr(line, word))
          printf("%s", line); 
      }
    
      fclose(file);
    }
  }

  //Reads from standard input otherwise
  else{
    int SIZE = 1024;
    char line[SIZE];
    while(fgets(line, SIZE , stdin) != NULL)
    {
      if(strstr(line, word))
        printf("%s", line);
    }
  }
  return 0;
}
