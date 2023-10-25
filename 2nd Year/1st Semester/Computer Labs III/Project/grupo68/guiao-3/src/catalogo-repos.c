#define _XOPEN_SOURCE
#define _DEFAULT_SOURCE
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>
#include <glib.h>

#include "constructors.h"
#include "catalogo-repos.h"
#include "main.h"

REPO loadFromLineNumber(char* filename, int lineNumber);

struct repo {
    int reposid;
    int owner_id;
    char* language;
    char* description;
    struct tm uploaded_date;
    int sizeDescription;
    int linhaNoFicheiro;
};

struct intermedio {
    int repoid;
    char* lang;
};

typedef struct intermedio* INTERMEDIO;



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

    char* repos = getNomeFicheiroRepos();
    REPO r = loadFromLineNumber(repos, c->linhaNoFicheiro);
    char* copiada = malloc(sizeof(char)*400000);
    strcpy(copiada, r->description);
    free(r->description);
    free(r->language);
    return copiada;

}

int contaRepos (GTree *t){
  return ( g_tree_nnodes (t));
}

gboolean findLanguage (gpointer key, gpointer value, gpointer data){
    INTERMEDIO inter = (INTERMEDIO) data;
    int* chave = key;
    int find = *chave;
    if(find == inter->repoid){
        REPO repo = (REPO) value;
        inter->lang = strdup(repo->language);
        return TRUE;
    }

    return FALSE;
}

char* getDescrionFromREPOID(GTree* t, int repo_id){
    REPO r = g_tree_lookup(t, GINT_TO_POINTER(repo_id));
    return getDescription(r);

}

char* getLanguageFromREPOID(GTree* t, int repo_id){
    REPO r = g_tree_lookup(t, GINT_TO_POINTER(repo_id));
    return getLanguage(r);
}

void buildRepos (char* line, CATALOGO cat, int nLinha) {
    GTree* t = NULL;
    t = getRep(cat);
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




    temp->language = malloc(sizeof(char)*strlen(lang)+4);

    temp->sizeDescription = sizeof(descricao);
    temp->linhaNoFicheiro = nLinha;

    strcpy(temp->language, lang);

    temp->reposid = id;
    temp->owner_id = owner;
    temp->uploaded_date = date;

    if(id == 0 || id == NULL)
        return;

    g_tree_insert(t, GINT_TO_POINTER(id), temp);
}

REPO buildReposWithoutAdd (char* line, int nLinha) {
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
    temp->linhaNoFicheiro = nLinha;
    temp->sizeDescription = strlen(descricao);


    strcpy(temp->language, lang);
    strcpy(temp->description, descricao);

    temp->reposid = id;
    temp->owner_id = owner;
    temp->uploaded_date = date;

    return temp;
}

REPO loadFromLineNumber(char* filename, int lineNumber){
    int max_len = 400000;
    int count = 0;
    char buff[max_len];

    FILE *f = fopen(filename, "r");
    if(f == NULL){
        printf("Ficheiro não encontrado: %s\n", filename);
        return NULL;
    }
    char line[400000]; /* or other suitable maximum line size */
    while (fgets(line, sizeof line, f) != NULL) /* read a line */
    {
        if (count == lineNumber-1)
        {
            fclose(f);
            return buildReposWithoutAdd(line, lineNumber-1);
        }
        else
        {
            count++;
        }
    }
    fclose(f);
}

void loadRepos(char* filename, CATALOGO cat){
    int max_len = 400000;
    char buff[max_len];

    FILE *f = fopen(filename, "r");
    if(f == NULL){
        printf("Ficheiro não encontrado: %s\n", filename);
        return;
    }
    int nLinha = 2;
    fgets(buff, max_len, f);
    while (fgets(buff, max_len, f)){
        buildRepos(buff,cat, nLinha++);
    }
    fclose(f);
}