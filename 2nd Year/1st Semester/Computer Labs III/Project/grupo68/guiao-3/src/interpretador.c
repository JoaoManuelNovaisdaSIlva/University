#include <stdio.h>
#include <string.h>
#include <sys/ioctl.h>
#include <unistd.h>
#include <stdlib.h>
#include <glib.h>
#include "queries.h"
#include "constructors.h"
#include "interpretador.h"
#include "catalogo-repos.h"


void interpretador( CATALOGO cat, int writeToFile){
    int userInput, terminado = 0;

    struct winsize w;
    ioctl(STDOUT_FILENO, TIOCGWINSZ, &w);


    char* imprimir_grande = ""
           "----------------------------------------------------------------------------------------\n"
           "|1| Quantidade de bots, organizações e utilizadores                                     |\n"
           "----------------------------------------------------------------------------------------\n"
           "|2| Número médio de colaboradores por repositório                                       |\n"
           "----------------------------------------------------------------------------------------\n"
           "|3| Quantidade de repositórios com bots                                                 |\n"
           "----------------------------------------------------------------------------------------\n"
           "|4| Quantidade média de commits por utilizador                                          |\n"
           "----------------------------------------------------------------------------------------\n"
           "|5| Top N de utilizadores mais ativos num determinado intervalo de datas                |\n"
           "----------------------------------------------------------------------------------------\n"
           "|6| Top N de utilizadores com mais commits em repositórios de uma determinada linguagem |\n"
           "----------------------------------------------------------------------------------------\n"
           "|7| Repositórios inativos a partir de uma determinada data                              |\n"
           "----------------------------------------------------------------------------------------\n"
           "|8| Top N de linguagens mais utilizadas a partir de uma determinada data                |\n"
           "----------------------------------------------------------------------------------------\n"
           "|9| Top N de utilizadores com mais commits em repositórios cujo owner é um amigo seu    |\n"
           "----------------------------------------------------------------------------------------\n"
           "|10| Top N de utilizadores com as maiores mensagens de commit por repositório           |\n"
           "----------------------------------------------------------------------------------------\n";

    char* imprimir_peq = ""
                     "--------------------------------------------------------------------\n"
                     "|1| Quantidade de bots, organizações e utilizadores                 |\n"
                     "--------------------------------------------------------------------\n"
                     "|2| Número médio de colaboradores por repositório                   |\n"
                     "--------------------------------------------------------------------\n"
                     "|3| Quantidade de repositórios com bots                             |\n"
                     "--------------------------------------------------------------------\n"
                     "|4| Quantidade média de commits por utilizador                      |\n"
                     "--------------------------------------------------------------------\n"
                     "|5| Top N de utilizadores mais ativos num determinado intervalo de  |\n"
                     "datas                                                               |\n"
                     "--------------------------------------------------------------------\n"
                     "|6| Top N de utilizadores com mais commits em repositórios de uma   |\n"
                     "determinada linguagem                                               |\n"
                     "--------------------------------------------------------------------\n"
                     "|7| Repositórios inativos a partir de uma determinada data          |\n"
                     "--------------------------------------------------------------------\n"
                     "|8| Top N de linguagens mais utilizadas a partir de uma determinada |\n"
                     "data                                                                |\n"
                     "--------------------------------------------------------------------\n"
                     "|9| Top N de utilizadores com mais commits em repositórios cujo     |\n"
                     "owner é um amigo seu                                                |\n"
                     "--------------------------------------------------------------------\n"
                     "|10| Top N de utilizadores com as maiores mensagens de commit por   |\n"
                     "repositório                                                         |\n"
                     "--------------------------------------------------------------------\n";




    while (terminado == 0){
        system("clear");
        char arg1[300000];
        //char arg2[300000];
        int argInt1 = 0;
        int argInt2 = 0;
        char arg3[300000], arg4[300000];


        if(w.ws_col < 80) printf("%s", imprimir_peq);
        else printf("%s", imprimir_grande);

        printf("Introduz uma opção: ");
        scanf("%d", &userInput);


        if(userInput > 0 && userInput <= 10)
        {
            if(userInput == 5 || userInput == 6 || userInput == 9 || userInput == 10)
            {
                printf("Introduza o numero de Users: ");
                scanf("%s", &arg1);
                argInt1 = atoi(arg1);
            }
            if(userInput == 8)
            {
                printf("Introduza o numero de Linguagens: ");
                scanf("%s", &arg1);
                argInt1 = atoi(arg1);
            }
            if(userInput == 5)
            {
                printf("Data Inicial: ");
                scanf("%s", &arg3);
                printf("Data Final: ");
                scanf("%s", &arg4);
            }
            if(userInput == 6)
            {
                printf("Linguagem: ");
                scanf("%s", &arg3);
                toUppercase(arg3);
            }
            if(userInput == 7 || userInput == 8)
            {
                printf("Data: ");
                scanf("%s", &arg3);
            }
            //printf("Argumentos: %s, %s\n", arg3, arg4);
            char* char1 = strdup(arg3);
            char* char2 = strdup(arg4);
            printf("%s", executa(cat, userInput, argInt1, argInt2, char1, char2));
            free(char1);
            free(char2);
            /**printf("\nPressione 1 e enter para continuar.");
            scanf("%s", &char1);**/
        }else if(userInput == 99){
            terminado = 1;
        }


    }


}

void executaComandos(CATALOGO cat, char* line){
    GTree* repos = getRep(cat);
    GTree* commits = getCommits(cat);
    GTree* users = getUsers(cat);
    char* id = strsep(&line, " ");
    char* buff = &line;
    int query = atoi(id);

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
    char* leng;


    //int nTop5;
    char *dateStart;
    char *dateEnd;
    int nTop8;
    int nTop5;
    char* retornoChar;
    int inteiro;
    float float_;
    int firstTime = 0;
    GList* output = NULL;
    strcat(fileName,"./saida/command");
    sprintf(queryToString, "%d", query);
    switch(query){
        case 1:
            retornoChar = numTypes(cat);
            printf("%s\n", retornoChar);
            break;
        case 2:
            float_ = averageNumOfCollab(commits, repos);
            printf("%.2f\n", float_);
            //return writeToFile2;
            break;
        case 3:
            retornoChar = quantidadeReposWithBots(users,commits);
            printf("%s\n", retornoChar);
            break;
        case 4:
            float_ = commitsPerUser(commits, users);
            printf("%.2f\n", float_);
            //return writeToFile4;
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
            inactiveRepos(commits,repos, dateStart);
            break;
        case 8:
            nTop8 =  strdup(strsep(&line," \n"));
            dateStart =  strdup(strsep(&line," \n"));
            output = topNlanguage_afterTime(repos, commits, dateStart, nTop8);
            //return queryCharOut;
            //saveToFile(fileName, queryCharOut, firstTime);
    }

    int numOflines = g_list_length(output);
    int numOfPages = (numOflines/5) + 1;
    char char1[30000];
    GList* atual = output;
    GList* inicioFor = output;
    for(int j = 1; j <= numOfPages && atual != NULL; j++){

        QUERY q = atual->data;
        line = getMessage(q);
        if(query == 7)
        {
            strcat(line, getDescrionFromREPOID(repos, getRepoID(q)));
        }
        atual = atual->next;
        saveToFile("", line, firstTime);
        freeQuery(q);
    }



    g_list_free(output);
    free(writeToFile4);
    free(writeToFile2);
    free(writeToFile3);
    free(leng);

    free(line);
}



void interpretadorComandos(CATALOGO cat, char* filename){
    int max_len = 200000;
    char buff[max_len];

    FILE *f = fopen(filename, "r");
    if(f == NULL){
        printf("Ficheiro não encontrado: %s\n", filename);
        return;
    }
    fgets(buff, max_len, f);
    int line = 0;
    while (fgets(buff, max_len, f)){
        executaComandos(cat, buff);
        line++;
    }
    fclose(f);
}


char* executa(CATALOGO cat, int query, int arg1, int arg2, char* arg3, char* arg4){
    GTree* repos = getRep(cat);
    GTree* commits = getCommits(cat);
    GTree* users = getUsers(cat);
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

    //int nTop6;
    char* leng = malloc(sizeof(char)*200);

    //int nTop5;
    char *dateStart;
    int nTop8;
    char* retornoChar;
    int inteiro;
    float float_;
    GList* output = NULL;
    sprintf(queryToString, "%d", query);
    switch(query){
        case 1:
            retornoChar = numTypes(cat);
            printf("%s\n", retornoChar);
            break;
        case 2:
            float_ = averageNumOfCollab(commits, repos);
            printf("%.2f\n", float_);
            //return writeToFile2;
            break;
        case 3:
            retornoChar = quantidadeReposWithBots(users,commits);
            printf("%s\n", retornoChar);
            break;
        case 4:
            float_ = commitsPerUser(commits, users);
            printf("%.2f\n", float_);
            //return writeToFile4;
            break;

        case 5:
            output = numOfUsersActive(commits,users,arg1,arg3 ,arg4);
            //return queryCharOut;
            break;
        case 6:
            output = topNlanguage_language(users,commits,repos,arg1, arg3);
            //return queryCharOut;
            break;
        case 7:
            dateStart = arg3;

            output = inactiveRepos(commits,repos, arg3);
            //return queryCharOut;
            strcat(fileName, queryToString);
            strcat(fileName,"_output.txt");
            break;
            //saveToFile(fileName, queryCharOut, firstTime);
        case 8:
            nTop8 = arg1;
            dateStart = arg3;
            output = topNlanguage_afterTime(repos, commits, dateStart, nTop8);
            //return queryCharOut;
            //saveToFile(fileName, queryCharOut, firstTime);
    }

    char* line = malloc(sizeof(char)*300000);
    int numOflines = g_list_length(output);
    int numOfPages = (numOflines/5) + 1;
    char char1[30000];
    GList* atual = output;
    GList* inicioFor = output;
    for(int j = 1; j <= numOfPages && atual != NULL; j++){
        system("clear");
        printf("A sua query produziu o seguinte resultado: \n\n");

        inicioFor = atual;
        for(int i=0; i < 5 && atual != NULL; i++){
            QUERY q = atual->data;
            line = getMessage(q);
            if(query == 7)
            {
                strcat(line, getDescrionFromREPOID(repos, getRepoID(q)));
            }
            atual = atual->next;
            printf("%s\n",line);
        }
        printf("\n------Pagina %d de %d------\n",j,numOfPages);
        printf("P     -> Proxima\n");
        printf("A     -> Anterior\n");
        printf("S     -> Saltar para página\n");
        printf("1     -> Continuar\n");
        printf("Selecione a opção: ");
        scanf("%s", &char1);
        toUppercase(&char1);
        if(strcmp(char1, "P") == 0 && j == numOfPages ){
            j=1;
        }
        if(strcmp(char1, "1") == 0)
        {
            return NULL; //mudar
        }
        if(strcmp(char1, "A") == 0 && j > 1){
            j-=2;
            for(int z=0;z<5;z++)
            {
                inicioFor = inicioFor->prev;
            }
            atual = inicioFor;

        }
        if(strcmp(char1, "S") == 0)
        {

            int novaPagina;
            printf("Pagina: ");
            scanf("%d", &novaPagina);
            GList* posicaoLista = output;
            for(int i = 1; i<novaPagina; i++){
                for(int z = 0; z<5; z++){
                    posicaoLista = posicaoLista->next;
                }
            }
            atual = posicaoLista;
            j=novaPagina-1;
        }
        //return line;
    }

    if(output == NULL){
        int oul;
        printf("Opções:\n"
                      "1     -> Continuar\n");
        printf("Selecione a opção: ");
        scanf("%d", &oul);
    }


    g_list_free(output);
    free(writeToFile4);
    free(writeToFile2);
    free(writeToFile3);
    free(leng);

    free(line);

    return ";";

}



/**
char* printLinesForPages(GList* a){
    char* line = malloc(sizeof(char)*300000);
    int numOflines = g_list_length(a);

    for(int j = 0; j < numOflines; j++){
        for(int i=0; i < 10; i++){
            line = a->data;
            a->next;
        }
        return line;
    }
}
**/