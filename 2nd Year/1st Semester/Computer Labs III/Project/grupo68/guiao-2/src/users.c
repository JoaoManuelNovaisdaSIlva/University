#define _XOPEN_SOURCE
#define _DEFAULT_SOURCE
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>
#include <glib.h>

#include "./users.h"
#include "constructors.h"

struct user {
	int id;
	char *login;
    int commits;
    int typeUser;
};

char * getLogin(USER u){
	return strdup(u->login);
}

int getUserId(USER u){
    return u->id;
}

int getTypeUser(USER u){
    if(u == NULL) return -1;
    return u->typeUser;
}



int getCommits(USER u){
    return u->commits;
}

int isBot(USER u){
    if(u == NULL) return 0;
    if(u->typeUser == 2)
        return 1;
    return 0;
}

struct retornaUser {
    USER u;
    int* id_procurar;
};

typedef struct retornaUser* RET_USER;

gboolean g_count (gpointer key, gpointer value, gpointer data){
    RET_USER id = data;
    USER valor = value;
    int* chave = key;

    if(valor->id == id->id_procurar)
    {
        id->u = valor;
        return TRUE;
    }


    return FALSE;
}

int count(int i){
    int count = 0;
    do
    {
        /* Increment digit count */
        count++;

        /* Remove last digit of 'num' */
        i /= 10;
    } while(i != 0);
    return count;
}

gint finder(gpointer key, gpointer user_data) {
    int* chave = (int*)key;
    int* need = (int*)user_data;
    if (*chave == *need) {
        return 0;
    }
    return (count(*chave)< count(*need)) ? 1 : -1;
    //return (*chave < *need) ? -1 : 1;
}

USER getUserFromID(GTree*t, int id)
{
    int nn = g_tree_nnodes(t);
    char* id_search = intToString(id);
    USER u = g_tree_lookup(t, id_search);
    if(u != NULL)
    {
        return u;
    }

    return NULL;
}
char* getLoginFromID(GTree* t, int id)
{
    USER u = getUserFromID(t, id);
    if(u != NULL)
        return getLogin(u);
    return "";
}



/* Privadas */

int genTypeUser(char* type){
    if(strcmp(type, "User") == 0)
        return 1;
    if(strcmp(type, "Bot") == 0)
        return 2;
    if(strcmp(type, "Organization") == 0)
        return 3;

}

void buildUsers(char* line, GTree *t) {
    char* buff2 = line;
    USER temp = malloc(sizeof(struct user));
    temp->login = malloc(sizeof(char)*200);
    char* id = strsep(&buff2, ";\n");
    char* name = strsep(&buff2, ";\n");
    char* id_key = malloc(sizeof(char)*strlen(name));
    strcpy(id_key, id);



    temp->typeUser = genTypeUser(strsep(&buff2, ";\n"));



    strcpy(temp->login, name);
    temp->id = atoi(id);

    g_tree_insert(t, id_key, temp);
}


USER procuraUser(int id, GTree* t){
    //return (GH_USER)  g_tree_lookup(t, &id);
    //int* conta = numTypes(t);

    //printf("Temos %d organizations !", conta[0]);
    //return g_tree_search(t, (GCompareFunc) procura_user_com_nome, &id);
}

void loadUsers(char *fileName, GTree *t) {
    int max_len = 400000;
    char buff[max_len];

    FILE *file = fopen(fileName, "r");
    if(file == NULL)
    {
        printf("Ficheiro não encontrado: %s\n", fileName);
        return;
    }

    char* copia;

    fgets(buff, max_len, file);
    while(fgets(buff, max_len, file))
    {
        buildUsers(buff, t);
    }

    fclose(file);


    //saveUser(array, i, 1);


}
