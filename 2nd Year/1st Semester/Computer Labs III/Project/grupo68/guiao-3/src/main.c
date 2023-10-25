#define _XOPEN_SOURCE
#define _DEFAULT_SOURCE

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/resource.h>
#include <glib.h>
#include <ctype.h>

#include "catalogo-users.h"
#include "catalogo-commits.h"
#include "queries.h"

#include "catalogo-repos.h"
#include "catalogo.h"
#include "constructors.h"
#include "interpretador.h"
#include "tests.h"

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


char* ficheiroUsers;
char* ficheiroCommits;
char* ficheiroRepos;

char* getNomeFicheiroUsers(){
    return ficheiroUsers;
}

char* getNomeFicheiroRepos(){
    return ficheiroRepos;
}

char* getNomeFicheiroCommits(){
    return ficheiroCommits;
}


int main(int argc, char const *argv[])
{
    CATALOGO cat = iniciarCatalogo();



    setUsers(cat, g_tree_new((GCompareFunc)g_ascii_strcasecmp));
    setCommits(cat, g_tree_new(inteiros));
    setRep(cat, g_tree_new(inteiros));

    char* ficheiroComandos = "./entrada/commands.txt";

    ficheiroUsers = "./entrada/users-g3.csv";
    ficheiroCommits = "./entrada/commits-g3.csv";
    ficheiroRepos = "./entrada/repos-g3.csv";

    loadUsers(ficheiroUsers, cat);
    loadCommits(ficheiroCommits, cat);
    loadRepos(ficheiroRepos, cat);

    if(argc == 2){
        interpretadorComandos(cat, argv[1]);
    }

    if(strcmp(argv[0],"./testes")==0){
        printf("A iniciar testes......\n");
        if(testQuerie1(cat) != 1 && testQuerie1(cat) > 5) printf("Querie 1 invalida, não executa em tempo útil");
            else printf("Querie 1 invalida, outputs invalidos");
        if(testQuerie1(cat) == 1) printf("Querie 1 valida!");

        if(testQuerie1(cat) != 1 && testQuerie1(cat) > 5) printf("Querie 2 invalida, não executa em tempo útil");
            else printf("Querie 2 invalida, outputs invalidos");
        if(testQuerie2(cat) == 1) printf("Querie 2 valida!");

        if(testQuerie1(cat) != 1 && testQuerie1(cat) > 5) printf("Querie 3 invalida, não executa em tempo útil");
            else printf("Querie 3 invalida, outputs invalidos");
        if(testQuerie3(cat) == 1) printf("Querie 3 valida!");

        if(testQuerie1(cat) != 1 && testQuerie1(cat) > 5) printf("Querie 4 invalida, não executa em tempo útil");
            else printf("Querie 4 invalida, outputs invalidos");
        if(testQuerie4(cat) == 1) printf("Querie 4 valida!");

        if(testQuerie1(cat) != 1 && testQuerie1(cat) > 5) printf("Querie 5 invalida, não executa em tempo útil");
            else printf("Querie 5 invalida, outputs invalidos");
        if(testQuerie5(cat) == 1) printf("Querie 5 valida!");

        if(testQuerie1(cat) != 1 && testQuerie1(cat) > 5) printf("Querie 6 invalida, não executa em tempo útil");
            else printf("Querie 6 invalida, outputs invalidos");
        if(testQuerie6(cat) == 1) printf("Querie 6 valida!");

        if(testQuerie1(cat) != 1 && testQuerie1(cat) > 5) printf("Querie 7 invalida, não executa em tempo útil");
            else printf("Querie 7 invalida, outputs invalidos");
        if(testQuerie7(cat) == 1) printf("Querie 7 valida!");

    }

    if(argc == 1 && strcmp(argv[0],"./testes")!=0){
        interpretador(cat, 0);
    }

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

    FILE *file = fopen(ficheiroComandos,"r");
    if(file == NULL){
        printf("Ficheiro não encotrado, tem que ser: ./entrada/commands.txt\n");
        return 0;
    }

    fclose(file);

    g_tree_destroy(getUsers(cat));
    g_tree_destroy(getCommits(cat));
    g_tree_destroy(getRep(cat));
    free(cat);

    return 1;
}
