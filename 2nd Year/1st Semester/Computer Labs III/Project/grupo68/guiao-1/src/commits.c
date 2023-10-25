#define _XOPEN_SOURCE
#define _DEFAULT_SOURCE
#include <stdlib.h>
#include <string.h>
#include <time.h>

#include "./constructors.h"
#include "./commits.h"

struct gh_commit{
	int repo_id;
	int author_id;
	int committer_id;
	struct tm commit_at;
	char *message;

};
typedef struct gh_commit* GH_COMMIT;


char * firstLineCommits = "repo_id;author_id;committer_id;commit_at;message";
char* printCommit(GH_COMMIT u)
{
    char prefix[400000] = "";

	struct tm data = u->commit_at;
	char dataString[20];


	sprintf(dataString, "%04d-%02d-%02d %02d:%02d:%02d", data.tm_year, data.tm_mon, data.tm_mday, data.tm_hour, data.tm_min, data.tm_sec);

	snprintf(prefix, 400000, "%d;%d;%d;%s;%s\n", u->repo_id, u->author_id, u->committer_id, 
					dataString, u->message);

    return strdup(prefix);
}

void saveCommits(GH_COMMIT arrayCommits[], int numCommits, int delete)
{
	FILE *f = fopen("../saida/commits-ok.csv", "w");
	if (f == NULL)
	{
	    printf("Nao foi possivel abrir o ficheiro!\n");
	    exit(1);
	}

	fprintf(f, "repo_id;author_id;committer_id;commit_at;message\n");
	for(int i = 0; i<numCommits; i++)
	{
		char* write = printCommit(arrayCommits[i]);
		//printf("%s", write);
		fprintf(f, "%s", write	);
		if(delete==1)
		{
			free(arrayCommits[i]->message);
            free(write);
		}
		
	}
	fclose(f);
	printf("Commits gravados com sucesso !\n");
}


GH_COMMIT build_commit(char* line)
{
	GH_COMMIT u = malloc(sizeof(struct gh_commit));
	char* buff2 = line;
	int temp;

    temp = verifyInt(strsep(&buff2, ";\n"));
	if(temp == -1){
        free(u);
        return NULL;
    }
	u->repo_id = temp;

	temp = verifyInt(strsep(&buff2, ";\n"));
	if(temp == -1){
        free(u);
        return NULL;
    }
	u->author_id = temp;

	temp = verifyInt(strsep(&buff2, ";\n"));
	if(temp == -1){
        free(u);
        return NULL;
    }
	u->committer_id = temp;

    struct tm temp2 = verifyTime(strsep(&buff2, ";\n"));
	if(temp2.tm_mday == 0){
        free(u);
        return NULL;
    }
	u->commit_at = temp2;


    u->message = strdup(strsep(&buff2, ";\n"));


	return u;
}



void loadCommits(char* fileName)
{
	int max_len = 500000;
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
    int ln = 1;
	fgets(buff, max_len, file);
	while(fgets(buff, max_len, file))
	{
        char * copia = malloc(sizeof(char)*sizeof(buff));
		strcpy(copia, buff);

        GH_COMMIT temp = build_commit(buff);

		if(temp != NULL){

			free(temp->message);
			free(temp);
            arrayStringsWrite[iArrWrite++] = copia;
            if(iArrWrite == 200000) {
                saveToFile("./saida/commits-ok.csv", arrayStringsWrite, iArrWrite, firstTime, firstLineCommits, 0);
                firstTime = 0;
                iArrWrite = 0;
            }

		}else{
            arrayInvalid[invalidWrite++] = copia;

            if(invalidWrite == 10000){
                saveToFile("./saida/commits-invalid-2.csv", arrayInvalid, invalidWrite, firstTimei, firstLineCommits, 1);
                firstTimei = 0;
                invalidWrite = 0;
            }
        }
		ln++;
	}
	saveToFile("./saida/commits-ok.csv", arrayStringsWrite, iArrWrite, firstTime, firstLineCommits, 0);
    saveToFile("./saida/commits-invalid-2.csv", arrayInvalid, invalidWrite, firstTimei, firstLineCommits, 1);


    fclose(file);
}

struct node* loadReposWCommits(char *string) {
    FILE *f = fopen(string, "r");
    int max_len = 500000;
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
        int reposID = verifyInt(strsep(&buff2, ";\n"));
        pNode = insert(pNode, reposID);
    }
    return pNode;
}


void removeInvalidCommits(char* fileName, struct node* userIDs, struct node* reposIDs)
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

        int repos_id = verifyInt(strsep(&buff2, ";\n"));
        int author_id = verifyInt(strsep(&buff2, ";\n"));
        int commiter_id = verifyInt(strsep(&buff2, ";\n"));

        if(commiter_id != author_id) {
            if(contains(userIDs, commiter_id) && contains(userIDs, author_id) && contains(reposIDs, repos_id))
            {
                arrayStringsWrite[iArrWrite++] = copia;
            }else{
                arrayInvalid[invalidWrite++] = copia;
            }
        }else{
            if(contains(userIDs, commiter_id) && contains(reposIDs, repos_id))
            {
                arrayStringsWrite[iArrWrite++] = copia;
            }else{
                arrayInvalid[invalidWrite++] = copia;
            }
        }
        if(invalidWrite == 10000){
            saveToFile("./saida/commits-invalid-2.csv", arrayInvalid, invalidWrite, firstTimei, firstLineCommits, 1);
            firstTimei = 0;
            invalidWrite = 0;
        }
        if(iArrWrite == 200000) {
            saveToFile("./saida/commits-final.csv", arrayStringsWrite, iArrWrite, firstTime, firstLineCommits, 0);
            firstTime = 0;
            iArrWrite = 0;

        }
        ln++;

    }
    saveToFile("./saida/commits-invalid-2.csv", arrayInvalid, invalidWrite, firstTimei, firstLineCommits,1);
    saveToFile("./saida/commits-final.csv", arrayStringsWrite, iArrWrite, firstTime, firstLineCommits, 0);
    fclose(file);


}
