#define _XOPEN_SOURCE
#define _DEFAULT_SOURCE

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/resource.h>
#include <glib.h>
#include <ctype.h>

#include "users.h"
#include "catalogo-commits.h"
#include "queries.h"
#include "catalogo-repos.h"
#include "constructors.h"

void executeQueries(char* line, GTree *users, GTree *commits, GTree *repos, int query);
/*
gboolean g_tree_foreach_func(gpointer key,
                             gpointer value,
                             gpointer data){
    char* chave = (char*) key;
    GH_USER valuee = (GH_USER*) value;
    char* info = (char*) data;
    printf("%s\n",getLogin(valuee));
    if(strcmp(getLogin(valuee), data) == 0)
    {
        printf("USER encontrado");
        return TRUE;
    }
    return FALSE;
    printf("key:%s   (%s)", chave, info);
}*/

// MAIN DEVE TER COMO ARGUMENTO O NOME DO FICHEIRO DE COMANDOS
int main(int argc, char const *argv[])
{
    GTree* users = g_tree_new((GCompareFunc)g_ascii_strcasecmp);
    GTree* commits = g_tree_new(inteiros);
    GTree* repos = g_tree_new(inteiros);

    char* ficheiroComandos = "./entrada/commands.txt";

    loadUsers("./entrada/users.csv", users);
    loadCommits("./entrada/commits.csv", commits);
    loadRepos("./entrada/repos.csv", repos);
    //inactiveRepos(commits, repos, "2015-01-01");
    //topNlanguage_language(users, commits, repos, 17, "Haxe");
    /*
     * int nRepos = g_tree_nnodes(repos);
    int nNodos = g_tree_nnodes(commits);
    int n = commitsPerUser(commits);

     //numOfUsersActive(commits, users,2, "2001-01-01", "2008-05-01");
    /*float avg = averageNumOfCollab(commits, repos);
    printf("Media de Colaboradores/Repositorio = %f\n", avg);
    int* procurar = malloc(sizeof(int));
    printf("Nodos: %d\n", g_tree_nnodes(users));
    USER  u = procuraUser(5662201, users);*/

    //printf("%s", getLogin(u));*/
    int max_len = 500;
    char* buff = malloc(sizeof(char)*500);
    int query = 1;
    FILE *file = fopen(ficheiroComandos,"r");
    if(file == NULL){
        printf("Ficheiro não encotrado, tem que ser: ./entrada/commands.txt\n");
        return 0;
    }
    while (fgets(buff, max_len, file)){
        executeQueries(buff, users, commits, repos, query);
        query++;
    }
    fclose(file);

    g_tree_destroy(users);
    g_tree_destroy(commits);
    g_tree_destroy(repos);

    return 1;
}

void executeQueries(char* line, GTree *users, GTree *commits, GTree *repos, int query){
    char* id = strsep(&line, " ");
    char* buff = &line;
    int idInt = atoi(id);
    int firstTime = 1;
    char queryToString[30] = "";
    char fileName[2000] = "";
    char* queryCharOut = malloc(sizeof(char)*40000);

    float query2Out;
    char* writeToFile2 = malloc(sizeof(char)*200);

    int query3Out;
    char* writeToFile3 = malloc(sizeof(char)*200);

    float query4Out;
    char* writeToFile4 = malloc(sizeof(char)*200);

    int nTop6;
    char* leng = malloc(sizeof(char)*200);

    int nTop5;
    char *dateStart;
    char *dateEnd;
    strcat(fileName,"./saida/command");
    sprintf(queryToString, "%d", query);
    switch(idInt){
        case 1:
            queryCharOut = numTypes(users);
            //char* writeToFile1 = malloc(sizeof(char)*200);
            //sprintf(writeToFile1, "%d", queryCharOut);
            strcat(fileName, queryToString);
            strcat(fileName,"_output.txt");
            saveToFile(fileName, queryCharOut, firstTime);
            break;
        case 2:
            query2Out = averageNumOfCollab(commits, repos);
            sprintf(writeToFile2, "%.2f", query2Out);
            strcat(fileName, queryToString);
            strcat(fileName,"_output.txt");
            saveToFile(fileName, writeToFile2, firstTime);
            break;
        case 3:
            query3Out = quantidadeReposWithBots(users,commits);
            sprintf(writeToFile3,"%d",query3Out);
            strcat(fileName, queryToString);
            strcat(fileName,"_output.txt");
            saveToFile(fileName, writeToFile3, firstTime);
            break;
        case 4:
            query4Out = commitsPerUser(commits, users);
            sprintf(writeToFile4,"%1.02f",query4Out);
            strcat(fileName, queryToString);
            strcat(fileName,"_output.txt");
            saveToFile(fileName, writeToFile4, firstTime);
            break;

        case 5:
            nTop5 = atoi(strsep(&line," "));
            dateStart = strdup(strsep(&line, " "));
            dateEnd = strdup(strsep(&line, " "));
            queryCharOut = numOfUsersActive(commits,users,nTop5,dateStart,dateEnd);
            strcat(fileName, queryToString);
            strcat(fileName,"_output.txt");
            saveToFile(fileName, queryCharOut, firstTime);
            break;
        case 6:
            nTop6 = atoi(strsep(&line," "));
            leng = strdup(strsep(&line," \n"));
            toUppercase(leng);

            queryCharOut = topNlanguage_language(users,commits,repos,nTop6,leng);
            strcat(fileName, queryToString);
            strcat(fileName,"_output.txt");
            saveToFile(fileName, queryCharOut, firstTime);
            break;
        case 7:
            dateStart = strdup(strsep(&line," \n"));
            strcat(fileName, queryToString);
            strcat(fileName,"_output.txt");
            inactiveRepos(commits,repos, dateStart,fileName);
            //saveToFile(fileName, queryCharOut, firstTime);


    }
    free(queryCharOut);
    free(writeToFile4);
    free(writeToFile2);
    free(writeToFile3);
    free(leng);

}