#include <stdbool.h>

#define _XOPEN_SOURCE
#define _DEFAULT_SOURCE


#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>

#include "./repos.h"
#include "./constructors.h"

struct gh_repos{
	int id;
	int owner_id;
	char* full_name;
	char* license;
	int has_wiki;
	char* description;
	char* language;
	char* default_branch;
	struct tm created_at;
	struct tm uploaded_at;
	int forks_count;
	int open_issues;
	int stargazer_count;
	int size;
	
};

typedef struct gh_repos* GH_REPOS;

char* firstLineRepos = "id;owner_id;full_name;license;has_wiki;description;language;default_branch;created_at;updated_at;forks_count;open_issues;stargazers_count;size";

char* printRepos(GH_REPOS u)
{
    char prefix[400000] = "";
	struct tm created_at = u->created_at;
	char createdString[20];
    struct tm uploaded_at = u->uploaded_at;
    char uploadedString[20];
    char* has_wiki = "True";
    if(u->has_wiki == 0) has_wiki = "False";

    sprintf(createdString, "%04d-%02d-%02d %02d:%02d:%02d", created_at.tm_year, created_at.tm_mon, created_at.tm_mday, 
    	created_at.tm_hour, created_at.tm_min, created_at.tm_sec);

	sprintf(uploadedString, "%04d-%02d-%02d %02d:%02d:%02d", uploaded_at.tm_year, uploaded_at.tm_mon, 
		uploaded_at.tm_mday, uploaded_at.tm_hour, uploaded_at.tm_min, uploaded_at.tm_sec);

	snprintf(prefix, 400000, "%d;%d;%s;%s;%s;%s;%s;%s;%s;%s;%d;%d;%d;%d\n", u-> id, u->owner_id, u->full_name, u->license, 
		has_wiki, u->description, u->language,
     u->default_branch, createdString, uploadedString, u->forks_count, u->open_issues, u->stargazer_count, u->size);


	return strdup(prefix);
}

void saveRepos(GH_REPOS arrayRepos[], int numRepos, int delete)
{
	FILE *f = fopen("../saida/repos-ok.csv", "w");
	if (f == NULL)
	{
	    printf("Nao foi possivel abrir o ficheiro!\n");
	    exit(1);
	}

	fprintf(f, "id;owner_id;full_name;license;has_wiki;description;language;default_branch;created_at;updated_at;forks_count;open_issues;stargazers_count;size\n");
	for(int i = 0; i<numRepos; i++)
	{
		char* write = printRepos(arrayRepos[i]);
		//printf("%s", write);
		fprintf(f, "%s", write	);
		if(delete==1)
		{
			free(arrayRepos[i]->full_name);
			free(arrayRepos[i]->description);
			free(arrayRepos[i]->language);
			free(arrayRepos[i]->license);
			free(arrayRepos[i]->default_branch);
			free(arrayRepos[i]);
			free(write);
		}
	}
	fclose(f);

	printf("Repositórios gravados com sucesso !\n");

}

GH_REPOS build_repos(char* line)
{
	GH_REPOS u = malloc(sizeof(struct gh_repos));
	char* buff2 = line;
	int temp;
	char* temp1 = "ERROR";
    
    temp = verifyInt(strsep(&buff2, ";\n"));
	if(temp == -1){ return NULL; }
	u->id = temp;

	temp = verifyInt(strsep(&buff2, ";\n"));
	if(temp == -1){ return NULL; }
	//u->owner_id = temp;

    temp1 = verifyStr(strsep(&buff2, ";\n"));
    if (strcmp(temp1, "ERROR")==0){return NULL;}
    //u->full_name = malloc(sizeof(char)*1000);
    //strcpy(u->full_name, temp1);

    temp1 = verifyStr(strsep(&buff2, ";\n"));
    if (strcmp(temp1, "ERROR")==0){return NULL;}
    //u->license = malloc(sizeof(char)*1000);
    //strcpy(u->license, temp1);

    temp1 = verifyStr(strsep(&buff2, ";\n"));
    if (strcmp(temp1, "True")==0) u->has_wiki = 1;
    else if(strcmp(temp1,"False")==0) u->has_wiki = 0;
	else return NULL;

    temp1 = strsep(&buff2, ";\n");
    //u->description = malloc(sizeof(char)*400000);
    //strcpy(u->description, temp1);

    temp1 = verifyStr(strsep(&buff2, ";\n"));
    if (strcmp(temp1, "ERROR")==0){return NULL;}
    //u->language = malloc(sizeof(char)*1000);
    //strcpy(u->language, temp1);

    temp1 = verifyStr(strsep(&buff2, ";\n"));
    if (strcmp(temp1, "ERROR")==0){return NULL;}
    //u->default_branch = malloc(sizeof(char)*1000);
    //strcpy(u->default_branch, temp1);

	struct tm temp2 = verifyTime(strsep(&buff2, ";\n"));
	if(temp2.tm_mday == 0){return NULL;}
	u->created_at = temp2;

	temp2 = verifyTime(strsep(&buff2, ";\n"));
	if(temp2.tm_mday == 0){return NULL;}
	u->uploaded_at = temp2;

    temp = verifyInt(strsep(&buff2, ";\n"));
	if(temp == -1){ return NULL; }
	u->forks_count = temp;    

    temp = verifyInt(strsep(&buff2, ";\n"));
	if(temp == -1){ return NULL; }
	u->open_issues = temp;

    temp = verifyInt(strsep(&buff2, ";\n"));
	if(temp == -1){ return NULL; }
	u->stargazer_count = temp;

    temp = verifyInt(strsep(&buff2, ";\n"));
	if(temp == -1){ return NULL; }
	u->size = temp;

	return u;
}

void loadRepos(char* fileName)
{
	int max_len = 400000;
	char buff[max_len];


	FILE *file = fopen(fileName, "r");
	if(file == NULL)
	{
		printf("Ficheiro não encontrado: %s", fileName);
        return;
	}

    char* arrayStringsWrite[200000];
    char* arrayInvalid[100000];

    int iArrWrite = 0, firstTime = 1, invalidWrite = 0, firstTimei = 1;
    int ln=1;
    fgets(buff, max_len, file);
	while(fgets(buff, max_len, file))
	{
        char *copia = malloc(sizeof(char)*sizeof(buff));
		strcpy(copia, buff);

        GH_REPOS temp = build_repos(buff);

		if(temp != NULL){
			/*if(i%20000 == 0 && i != 0) array = realloc(array, sizeof(struct gh_repos)*i+20000*sizeof(struct gh_repos));
			array[i] = temp; OLD
			 i++;
			 */
			free(temp);
            arrayStringsWrite[iArrWrite++] = copia;
            if(iArrWrite == 200000) {
                saveToFile("./saida/repos-ok.csv", arrayStringsWrite, iArrWrite, firstTime, firstLineRepos, 0);
                firstTime = 0;
                iArrWrite = 0;
            }
		}else{
            arrayInvalid[invalidWrite++] = copia;

            if(invalidWrite == 10000){
                saveToFile("./saida/repos-invalid-2.csv", arrayInvalid, invalidWrite, firstTimei, firstLineRepos, 1);
                firstTimei = 0;
                invalidWrite = 0;
            }
        }
		ln++;
	}
    saveToFile("./saida/repos-ok.csv", arrayStringsWrite, iArrWrite, firstTime, firstLineRepos, 0);
    saveToFile("./saida/repos-invalid-2.csv", arrayInvalid, invalidWrite, firstTimei, firstLineRepos, 1);


    fclose(file);


}

struct node* loadReposBin(const char *string) {
    FILE *f = fopen(string, "r");
    int max_len = 400000;
    char buff[max_len];

    if (f == NULL)
    {
        printf("Nao foi possivel abrir o ficheiro!\n");
        return NULL;
    }
    struct node* pNode = NULL;
    fgets(buff, max_len, f);
    while(fgets(buff, max_len, f))
    {
        char* buff2 = buff;
        int id = atoi(strsep(&buff2, ";\n"));
        //printf("%d", id);
        pNode = insert(pNode, id);
    }
    return pNode;
}

void removeInvalidRepos(char* fileName, struct node* userIDs, struct node* reposWCommits)
{
    int max_len = 400000;
    char buff[max_len];

    FILE *file = fopen(fileName, "r");
    if(file == NULL)
    {
        printf("Ficheiro não encontrado: %s", fileName);
        return;
    }

    char* arrayStringsWrite[200000];
    char* arrayInvalid[10000];

    int iArrWrite = 0, firstTime = 1, invalidWrite = 0, firstTimei = 1;
    int ln = 1;
    fgets(buff, max_len, file);
    while(fgets(buff, max_len, file))
    {
        char *copia = malloc(sizeof(char)*500000);
        strcpy(copia, buff);

        char* buff2 = buff;
        int repo_id = verifyInt(strsep(&buff2, ";\n"));
        int owner_id = verifyInt(strsep(&buff2, ";\n"));

        if(contains(userIDs, owner_id) && contains(reposWCommits, repo_id)){
            arrayStringsWrite[iArrWrite++] = copia;
        }else{
            arrayInvalid[invalidWrite++] = copia;
        }

        if(invalidWrite == 10000){
            saveToFile("./saida/repos-invalid-2.csv", arrayInvalid, invalidWrite, firstTimei, firstLineRepos, 1);
            firstTimei = 0;
            invalidWrite = 0;
        }
        if(iArrWrite == 200000) {
            saveToFile("./saida/repos-final.csv", arrayStringsWrite, iArrWrite, firstTime, firstLineRepos, 0);
            firstTime = 0;
            iArrWrite = 0;

        }
        ln++;

    }
    saveToFile("./saida/repos-invalid-2.csv", arrayInvalid, invalidWrite, firstTimei, firstLineRepos, 1);
    saveToFile("./saida/repos-final.csv", arrayStringsWrite, iArrWrite, firstTime, firstLineRepos, 0);
    fclose(file);


}

