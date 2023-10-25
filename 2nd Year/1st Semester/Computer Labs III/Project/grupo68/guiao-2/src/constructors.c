//
// Created by admin on 13/12/21.
//

#include <malloc.h>
#include <sys/resource.h>

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/resource.h>
#include <glib.h>
#include <ctype.h>

char* intToString(int i){
    char* buff = malloc(sizeof(char)*20000);
    sprintf(buff, "%d", i);
    return buff;
}

char* toUppercase(char* buff){
    char * name;
    name = strtok(buff,"");

    // Convert to upper case
    char *s = name;
    while (*s) {
        *s = toupper((unsigned char) *s);
        s++;
    }
}

int asterixToInt(int* i){
    return *i;
}

int* toIntAsterix(int i){
    int* ret = malloc(sizeof(int));
    *ret = i;
    return ret;
}

void saveToFile(char* fileName, char* write, int firstTime) {
    FILE *f = fopen(fileName, "a");
    if(firstTime == 1) f = fopen(fileName, "w");

    if (f == NULL)
    {
        printf("Nao foi possivel abrir o ficheiro: %s\n", fileName);
        return;
    }

    //if(firstTime == 1) fprintf(f, "%s\n", firstLine);

    fprintf(f, "%s", write);

    fclose(f);

    printf("%s alterado com sucesso !\n", fileName);
}

void writeLineToFile(char* fileName, char* write, int firstTime) {
    /**if(debugFile==1 && getInvalid()==0)
    {
        for(int i = 0; i<iWrite; i++)
            free(write[i]); //copia
        return;
    }**/
    FILE *f = fopen(fileName, "a");
    if(firstTime == 1) f = fopen(fileName, "w");

    if (f == NULL)
    {
        printf("Nao foi possivel abrir o ficheiro: %s\n", fileName);
        return;
    }

    //if(firstTime == 1) fprintf(f, "%s\n", firstLine);

    fprintf(f, "%s", write);
    fclose(f);

    //printf("%s alterado com sucesso !\n", fileName);
}
