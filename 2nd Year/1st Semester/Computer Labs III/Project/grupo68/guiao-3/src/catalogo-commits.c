#define _XOPEN_SOURCE
#define _DEFAULT_SOURCE
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>
#include <glib.h>

#include "catalogo-commits.h"
#include "queries.h"
#include "catalogo-users.h"
#include "catalogo.h"
#include "constructors.h"


struct commits{
    int committer_id;
    int repos_id;
    int author_id;
    struct tm date;
    int sizeMessage;
};


int getCommitter(COMMITS c){
    return c->committer_id;
}

int getAuthor(COMMITS c){
    return c->author_id;
}

int getRepos(COMMITS c){
    return c->repos_id;
}

struct tm getDate(COMMITS c){
    return c->date;
}


void buildCommits(char* line, int lineNumber, CATALOGO cat) {
    GTree* t = NULL;
    t = getCommits(cat);
    char* buff2 = line;
    COMMITS temp = malloc(sizeof(struct commits));
    //struct tm* date = malloc(sizeof(struct tm));
    //char* lineN = malloc(sizeof(char)*20);
    //strcpy(lineN, itoa(lineNumber));

    int reposId = atoi(strsep(&buff2, ";\n"));
    char* author_id = strsep(&buff2, ";\n");
    char* commiter_id = strsep(&buff2,";\n");
    char* data = strsep(&buff2,";\n");
    struct tm data_commit;
    strptime(data, "%Y-%m-%d %H:%M:%S", &data_commit);
    data_commit.tm_year = data_commit.tm_year+1900;
    data_commit.tm_mon = data_commit.tm_mon+1;
    char* message = strsep(&buff2, ";/n");
    //int* commitID_key = malloc(sizeof(commiter_id));
    temp->date = data_commit;



    temp->sizeMessage = sizeof(message);
    temp->repos_id = reposId;
    temp->committer_id = atoi(commiter_id);
    temp->author_id = atoi(author_id);

    int* ln = toIntAsterix(lineNumber);

    //printf("%s;", author_key);
    g_tree_insert(t, ln, temp);



}



void loadCommits(char* filename, CATALOGO cat){
    int max_len = 200000;
    char buff[max_len];
    //GTree *coms = g_new_tree((GCompareFunc)g_ascii_strcasecmp);

    FILE *f = fopen(filename, "r");
    if(f == NULL){
        printf("Ficheiro não encontrado: %s\n", filename);
        return;
    }
    fgets(buff, max_len, f);
    int line = 0;
    while (fgets(buff, max_len, f)){
        buildCommits(buff,line,cat);
        line++;
    }
    fclose(f);
}

/**
gboolean iter_all_Commits(gpointer key, gpointer value, gpointer data) {
    int* index = (int*) key;
    int* obter = (int*) data;
    int key_act = *index;
    int key_get = *obter;
    if(key_act == key_get)
        printf("Pretendiamos: %d\nTemos: %d\n, %s\n", key_get, key_act, "");
    return FALSE;
}

COMMITS procuraCommits(int id, GTree* t){
    //return (GH_USER)  g_tree_lookup(t, &id);

    g_tree_foreach(t, iter_all_Commits, &id);
    //return g_tree_search(t, (GCompareFunc) procura_user_com_nome, &id);
}
**/