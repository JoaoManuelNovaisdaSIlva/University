#define _XOPEN_SOURCE
#define _DEFAULT_SOURCE
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>
#include <glib.h>

#include "constructors.h"
#include "catalogo-repos.h"


struct repo {
    int reposid;
    int owner_id;
    char* language;
    char* description;
    struct tm uploaded_date;
};


int getReposid(REPO c){
    return c->reposid;
}

int getOwnerid(REPO c){
    return c->owner_id;
}

struct tm getUploadedDate(REPO c){
    return c->uploaded_date;
}

char* getLanguage(REPO c){
  return strdup(c->language);
}

char* getDescription(REPO c){
  return strdup(c->description);
}

int contaRepos (GTree *t){
  return ( g_tree_nnodes (t));
}

void buildRepos (char* line, GTree *t) {
    char* buff2 = line;
    REPO temp = malloc(sizeof(struct repo));
    struct tm date = {0};


    int id = atoi(strsep(&buff2, ";\n"));
    int owner = atoi(strsep(&buff2, ";\n"));
    strsep(&buff2,";\n");
    strsep(&buff2,";\n");
    strsep(&buff2,";\n");
    char* descricao = strsep(&buff2, ";\n");
    char* lang = strsep(&buff2, ";\n");
    strsep(&buff2, ";\n");
    strsep(&buff2, ";\n");
    char* data = strsep(&buff2,";\n");
    strptime(data, "%Y-%m-%d %H:%M:%S", &date);
    date.tm_year = date.tm_year+1900;
    date.tm_mon = date.tm_mon+1;
    int* id_key = malloc(sizeof(int));
    *id_key = id;

    temp->language = malloc(sizeof(char)*4000);
    temp->description = malloc(sizeof(char)*400000);

    strcpy(temp->language, lang);
    strcpy(temp->description, descricao);

    temp->reposid = id;
    temp->owner_id = owner;
    temp->uploaded_date = date;

    g_tree_insert(t, id_key, temp);
}


void loadRepos(char* filename, GTree *t){
    int max_len = 400000;
    char buff[max_len];

    FILE *f = fopen(filename, "r");
    if(f == NULL){
        printf("Ficheiro não encontrado: %s\n", filename);
        return;
    }
    fgets(buff, max_len, f);
    while (fgets(buff, max_len, f)){
        buildRepos(buff,t);
    }
    fclose(f);
}