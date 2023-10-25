#define _XOPEN_SOURCE
#define _DEFAULT_SOURCE
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>
#include <glib.h>

#include "constructors.h"
#include "catalogo.h"
#include "main.h"

USER loadCompleteUSER(char* filename, int id_user);
char* getUserLogin_Internal(USER c);

struct user {
	int id;
	char *login;
    int commits;
    int typeUser;
    struct tm created_at;
};

char * getLogin(USER u){
	return getUserLogin_Internal(u);
}

int getUserId(USER u){
    return u->id;
}

int getTypeUser(USER u){
    if(u == NULL) return -1;
    return u->typeUser;
}



int getNumCommits(USER u){
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
    USER u = g_tree_lookup(t, &id);
    if(u != NULL)
    {
        return u;
    }

    return NULL;
}

//int isInFriendList()

char* getLoginFromID(GTree* t, int id)
{
    USER u = getUserFromID(t, id);
    if(u != NULL)
        return getLogin(u);
    return "";
}

char* getUserLogin_Internal(USER c){

    char* user = getNomeFicheiroUsers();
    USER u = loadCompleteUSER(user, c->id);
    char* copiada = malloc(sizeof(char)*400000);
    strcpy(copiada, u->login);
    //free(u->login);
    return copiada;

}



/* Privadas */

int genTypeUser(char* type){
    if(strcmp(type, "User") == 0)
        return 1;
    if(strcmp(type, "Bot") == 0)
        return 2;
    if(strcmp(type, "Organization") == 0)
        return 3;
    return -1;
}

USER buildUserWithoutTree(char* line, int id_user) {
    char* buff2 = line;
    char* id = strsep(&buff2, ";\n");
    int id_actual = atoi(id);

    if(id_user != id_actual){
        return NULL;
    }

    USER temp = malloc(sizeof(struct user));

    char* name = strsep(&buff2, ";\n");
    temp->login = malloc(sizeof(char)*strlen(name));
    temp->typeUser = genTypeUser(strsep(&buff2, ";\n"));

    strcpy(temp->login, name);
    temp->id = id_user;

    return temp;
}


void buildUsers(char* line, CATALOGO cat) {
    GTree* t = NULL;
    t = getUsers(cat);

    char* buff2 = line;
    USER temp = malloc(sizeof(struct user));

    char* id = strsep(&buff2, ";\n");
    char* name = strsep(&buff2, ";\n");
    int id_user = atoi(id);
    //int* id_key = GINT_TO_POINTER(id_user);




    //temp->login = malloc(sizeof(char)*strlen(name));

    temp->typeUser = genTypeUser(strsep(&buff2, ";\n"));

    //strcpy(temp->login, name);
    temp->id = id_user;


    struct tm data = verifyTime(strsep(&buff2, ";\n"));
    temp->created_at = data;
    if(id_user < 0 || temp->typeUser == -1 || data.tm_mday == 0){
        free(temp);
        return;
    }

    if(temp->typeUser == 1)
        setNumUsers(cat, getNumUsers(cat)+1);
    if(temp->typeUser == 2)
        setNumBots(cat, getNumBots(cat)+1);
    if(temp->typeUser == 3)
        setNumOrganizations(cat, getNumOrganizations(cat)+1);


    g_tree_insert(t, toIntAsterix(id_user), temp);
}


USER procuraUser(int id, GTree* t){
    //return (GH_USER)  g_tree_lookup(t, &id);
    //int* conta = numTypes(t);

    //printf("Temos %d organizations !", conta[0]);
    //return g_tree_search(t, (GCompareFunc) procura_user_com_nome, &id);
}

USER loadCompleteUSER(char* filename, int id_user){
    int max_len = 400000;
    int count = 0;
    char buff[max_len];

    FILE *f = fopen(filename, "r");
    if(f == NULL){
        printf("Ficheiro não encontrado: %s\n", filename);
        return NULL;
    }
    char line[400000]; /* or other suitable maximum line size */
    fgets(line, sizeof line, f);
    while (fgets(line, sizeof line, f) != NULL) /* read a line */
    {
        USER u = buildUserWithoutTree(line, id_user);
        if(u != NULL)
        {
            fclose(f);
            return u;
        }
    }
    fclose(f);
}

void loadUsers(char *fileName, CATALOGO cat) {
    int max_len = 400000;
    char buff[max_len];

    FILE *file = fopen(fileName, "r");
    if(file == NULL)
    {
        printf("Ficheiro não encontrado: %s\n", fileName);
        return;
    }

    fgets(buff, max_len, file);
    while(fgets(buff, max_len, file))
    {
        buildUsers(buff, cat);
    }

    fclose(file);


    //saveUser(array, i, 1);


}
