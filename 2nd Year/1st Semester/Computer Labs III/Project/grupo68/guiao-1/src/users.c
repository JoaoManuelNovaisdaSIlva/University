#define _XOPEN_SOURCE
#define _DEFAULT_SOURCE
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>


#include "./users.h"
#include "./constructors.h"

struct gh_user{
	int public_repos;
	int id;
	int followers;
	int * follower_list;
	enum {User,Organization,Bot} type;
	int * following_list;
	int public_gists;
	struct tm created_at;
	int following;
	char *login;
    int error;
};

typedef struct gh_user* GH_USER;

char* firstLine = "id;login;type;created_at;followers;follower_list;following;following_list;public_gists;public_repos";

char* printUser(GH_USER u)
{
	char prefix[200] = "";
	struct tm data = u->created_at;
	char dataString[100] = "";
	char* type = "User";
	if (u->type == Bot) type = "Bot";
	if(u->type == Organization) type="Organization";



	char* following_list;
	following_list = "[]";

	snprintf(dataString, sizeof(dataString), "%d-%d-%d %d:%d:%d", data.tm_year, data.tm_mon, data.tm_mday, data.tm_hour, data.tm_min, data.tm_sec);

	snprintf( prefix, sizeof(prefix), "%d;%s;%s;%s;%d;%s;%d;%s;%d;%d\n", u->id, u->login, type,
					dataString, u->followers, following_list, u->following, following_list, 
					u->public_gists, u->public_repos);


	return strdup(prefix);
}

void saveUser(GH_USER arrayUsers[], int numUsers, int delete)
{
	FILE *f = fopen("../saida/users-ok.csv", "w");
	if (f == NULL)
	{
	    printf("Nao foi possivel abrir o ficheiro!\n");
	    exit(1);
	}

	fprintf(f, "id;login;type;created_at;followers;follower_list;following;following_list;public_gists;public_repos\n");
	for(int i = 0; i<numUsers; i++)
	{
		char* write = printUser(arrayUsers[i]);
		//printf("%s", write);
		fprintf(f, "%s", write	);
		if(delete==1){
			free(arrayUsers[i]->follower_list);
			free(arrayUsers[i]->following_list);
			free(arrayUsers[i]->login);
		}
	}
	fclose(f);
	printf("Users gravados com sucesso !\n");

}


GH_USER build_user(char* line)
{
	GH_USER u = malloc(sizeof(struct gh_user));

    u->error = 0;
	char* buff2 = line;
	int temp;
	char* temp1;

    u->login = malloc(sizeof(char)*strlen(line));
    u->follower_list = malloc(sizeof(int)*strlen(line));
    u->following_list = malloc(sizeof(int)*strlen(line));

    temp = verifyInt(strsep(&buff2, ";\n"));
	if(temp == -1) {
        u->error = 1;
        return u;
    }
	u->id = temp;

    temp1 = verifyStr(strsep(&buff2, ";\n"));
    if (strcmp(temp1, "ERROR")==0){
        u->error = 1;
        return u;
    }
    
    strcpy(u->login, temp1);

    temp1 = verifyStr(strsep(&buff2, ";\n"));
    if (strcmp(temp1, "User")==0){ u->type = User; }
    if(strcmp(temp1,"Organization")==0) { u->type = Organization; }
	if(strcmp(temp1, "Bot")==0) { u->type = Bot; }
	if(strcmp(temp1, "ERROR")==0){
        u->error = 1;
        return u;
    }

	
	struct tm temp2 = verifyTime(strsep(&buff2, ";\n"));
	if(temp2.tm_mday == 0){
        u->error = 1;
        return u;
    }
	u->created_at = temp2;
	


    temp = verifyInt(strsep(&buff2, ";\n"));
	if(temp == -1) {
        u->error = 1;
        return u;
    }
	u->followers = temp;

    temp = verifyArrInt(strsep(&buff2, ";\n"), u->follower_list, u->followers);
    if(temp == -1) {
        u->error = 1;
        return u;
    }



    temp = verifyInt(strsep(&buff2, ";\n"));
	if(temp == -1){
        u->error = 1;
        return u;
    }
	u->following = temp;


    temp = verifyArrInt(strsep(&buff2, ";\n"), u->following_list, u->following);
    if(temp == -1) {
        u->error = 1;
        return u;
    }
    
    temp = verifyInt(strsep(&buff2, ";\n"));
    if(temp == -1){
        u->error = 1;
        return u;
    }
	u->public_gists = temp;

	temp = verifyInt(strsep(&buff2, ";\n"));
    if(temp == -1){
        u->error = 1;
        return u;
    }
	u->public_repos = temp;

	return u;
}


void  loadUsers(char* fileName)
{
	int max_len = 500000;
	char buff[max_len];

	FILE *file = fopen(fileName, "r");
	if(file == NULL)
	{
		printf("Ficheiro não encontrado: %s\n", fileName);
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
        GH_USER temp = build_user(buff);

        free(temp->follower_list);
        free(temp->following_list);
        free(temp->login);

		if(temp->error == 0){
            free(temp);
            arrayStringsWrite[iArrWrite++] = copia;
            if(iArrWrite == 200000) {
                saveToFile("./saida/users-ok.csv", arrayStringsWrite, iArrWrite, firstTime, firstLine, 0);
                firstTime = 0;
                iArrWrite = 0;
            }

		}else{
            free(temp);
            arrayInvalid[invalidWrite++] = copia;
            if(invalidWrite == 10000){
                saveToFile("./saida/users-invalid-2.csv", arrayInvalid, invalidWrite, firstTimei, firstLine, 1);
                firstTimei = 0;
                invalidWrite = 0;
            }
        }


		ln++;
	}
	saveToFile("./saida/users-ok.csv", arrayStringsWrite, iArrWrite, firstTime, firstLine, 0);
    saveToFile("./saida/users-invalid-2.csv", arrayInvalid, invalidWrite, firstTimei, firstLine, 1);

    fclose(file);
	//saveUser(array, i, 1);
    

}

void saveUsersToFinal(char* fileName)
{
    int max_len = 500000;
    char buff[max_len];

    FILE *file = fopen(fileName, "r");
    if(file == NULL)
    {
        printf("Ficheiro não encontrado: %s\n", fileName);
        return;
    }

    char* arrayStringsWrite[200000];
    int iArrWrite = 0, firstTime = 1;

    int ln = 1;
    fgets(buff, max_len, file);
    while(fgets(buff, max_len, file))
    {
        char *copia = malloc(sizeof(char)*500000);
        strcpy(copia, buff);

        arrayStringsWrite[iArrWrite++] = copia;
        if(iArrWrite == 200000) {
            saveToFile("./saida/users-final.csv", arrayStringsWrite, iArrWrite, firstTime, firstLine, 0);
            firstTime = 0;
            iArrWrite = 0;
        }


        ln++;
    }
    saveToFile("./saida/users-final.csv", arrayStringsWrite, iArrWrite, firstTime, firstLine, 0);

    fclose(file);
}

struct node* loadUsersBin(const char *string) {
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