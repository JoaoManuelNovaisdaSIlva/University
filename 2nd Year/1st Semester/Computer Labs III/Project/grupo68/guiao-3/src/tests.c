#define _XOPEN_SOURCE
#define _DEFAULT_SOURCE

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/resource.h>
#include <glib.h>
#include <ctype.h>
#include <time.h>

#include "catalogo.h"
#include "queries.h"
#include "constructors.h"

int compareToValid(char* filenameValid, char* filenameTested){
    FILE* fileValid = fopen(filenameValid, "r");
    FILE* fileTested = fopen(filenameTested, "r");
    char* buffTested = malloc(sizeof(char)*20000);
    char* buffValid = malloc(sizeof(char)*20000);
    int max_size = 20000;

    if(filenameValid == NULL || filenameTested == NULL) return 0;


    while(filenameTested != NULL || filenameValid == NULL){
        fgets(buffTested, max_size, filenameTested);
        fgets(buffValid, max_size, filenameValid);

        if(strcmp(buffValid, buffTested)==0){
        }
        else return 0;

    }
    return 1;

}

int testQuerie1(CATALOGO cat){
    clock_t start, end;
    double cpu_time_used;

    start = clock();
    char* res = intToString(numTypes(cat));
    saveToFile ("command1_output.txt", res, 1);
    end = clock();

    cpu_time_used = ((double) (end-start));
    if(cpu_time_used > 5 && compareToValid("./tests/test-querie1.txt", "./saida/command1_output.txt") == 1){
        return 1;
    }
    return cpu_time_used;
}

int testQuerie2(CATALOGO cat){
    clock_t start, end;
    double cpu_time_used;

    start = clock();
    char* write = malloc(sizeof(char)*100);
    float res = averageNumOfCollab(getCommits(cat),getRep(cat));
    gcvt(res, 6, write);
    saveToFile ("command2_output.txt", write, 1);
    end = clock();

    cpu_time_used = ((double) (end-start));
    if(cpu_time_used > 5 && compareToValid("./tests/test-querie2.txt", "./saida/command2_output.txt") == 1){
        return 1;
    }
    return cpu_time_used;
}

int testQuerie3(CATALOGO cat){
    clock_t start, end;
    double cpu_time_used;

    start = clock();
    char* res = intToString(numTypes(cat));
    saveToFile ("command3_output.txt", res, 1);
    end = clock();

    cpu_time_used = ((double) (end-start));
    if(cpu_time_used > 5 && compareToValid("./tests/test-querie3.txt", "./saida/command3_output.txt") == 1){
        return 1;
    }
    return cpu_time_used;
}

int testQuerie4(CATALOGO cat){
    clock_t start, end;
    double cpu_time_used;

    start = clock();
    char* write = malloc(sizeof(char)*100);
    float res = averageNumOfCollab(getCommits(cat),getRep(cat));
    gcvt(res, 6, write);
    saveToFile ("command4_output.txt", write, 1);
    end = clock();

    cpu_time_used = ((double) (end-start));
    if(cpu_time_used > 5 && compareToValid("./tests/test-querie4.txt", "./saida/command4_output.txt") == 1){
        return 1;
    }
    return cpu_time_used;
}

int testQuerie5(CATALOGO cat){
    clock_t start, end;
    double cpu_time_used;
    char* write = malloc(sizeof(char)*1000);

    start = clock();

    GList* res = numOfUsersActive(getCommits(cat), getUsers(cat), 5, "2008-01-01", "2015-01-01");
    write = res->data;
    res->next;
    saveToFile("command5_output.txt", write, 1);
    for(int i=0; i < g_list_length(res); i++)
    {
        write = res->data;
        saveToFile("command5_output.txt", write, 0);
        res->next;
    }
    
    end = clock();

    cpu_time_used = ((double) (end-start));
    if(cpu_time_used > 5 && compareToValid("./tests/test-querie5.txt", "./saida/command5_output.txt") == 1){
        return 1;
    }
    return cpu_time_used;
}

int testQuerie6(CATALOGO cat){
    clock_t start, end;
    double cpu_time_used;
    char* write = malloc(sizeof(char)*1000);

    start = clock();

    GList* res = topNlanguage_language(getUsers(cat), getCommits(cat), getRep(cat), 5, "HTML");
    write = res->data;
    res->next;
    saveToFile("command6_output.txt", write, 1);
    for(int i=0; i < g_list_length(res); i++)
    {
        write = res->data;
        saveToFile("command6_output.txt", write, 0);
        res->next;
    }
    
    end = clock();

    cpu_time_used = ((double) (end-start));
    if(cpu_time_used > 5 && compareToValid("./tests/test-querie6.txt", "./saida/command6_output.txt") == 1){
        return 1;
    }
    return cpu_time_used;
}

int testQuerie7(CATALOGO cat){
    clock_t start, end;
    double cpu_time_used;
    char* write = malloc(sizeof(char)*1000);

    start = clock();

    GList* res = inactiveRepos(getCommits(cat), getRep(cat), "2015-01-01");
    write = res->data;
    res->next;
    saveToFile("command7_output.txt", write, 1);
    for(int i=0; i < g_list_length(res); i++)
    {
        write = res->data;
        saveToFile("command7_output.txt", write, 0);
        res->next;
    }
    
    end = clock();

    cpu_time_used = ((double) (end-start));
    if(cpu_time_used > 5 && compareToValid("./tests/test-querie7.txt", "./saida/command7_output.txt") == 1){
        return 1;
    }
    return cpu_time_used;
}

int testQuerie8(CATALOGO cat){
    clock_t start, end;
    double cpu_time_used;
    char* write = malloc(sizeof(char)*1000);

    start = clock();

    GList* res = topNlanguage_afterTime(getRep(cat), getCommits(cat), "2015-01-01", 7);
    write = res->data;
    res->next;
    saveToFile("command8_output.txt", write, 1);
    for(int i=0; i < g_list_length(res); i++)
    {
        write = res->data;
        saveToFile("command8_output.txt", write, 0);
        res->next;
    }
    
    end = clock();

    cpu_time_used = ((double) (end-start));
    if(cpu_time_used > 5 && compareToValid("./tests/test-querie8.txt", "./saida/command8_output.txt") == 1){
        return 1;
    }
    return cpu_time_used;
}