#define _XOPEN_SOURCE
#define _DEFAULT_SOURCE
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>


struct gh_user{
	int public_repos;
	int id;
	int followers;
	char *follower_list;
	enum {User,Organization} type;
	char *following_list;
	int public_gists;
	struct tm created_at;
	int following;
	char *login;

};

typedef struct gh_user* GH_USER;
//int errorCount = 0;

int verificarInteiro(char* str){
	int inteiro = -1;

	sscanf(str, "%d", &inteiro);
    if(inteiro == -1)
        return inteiro;
	return inteiro;
}

char* verifyStr(char* str)
{
    if(strcmp(str,"")==0) return "ERROR";
    return str;
}

struct tm verifyTime (char* str)
{
	//printf("%s", str);
	struct tm time = {0};
	strptime(str, "%Y-%m-%d %H:%M:%S", &time);
	time.tm_year = time.tm_year+1900;
    time.tm_mon = time.tm_mon+1;
    //fazer a partir de data cancro

    return time;
	
}

char *verifyArr(char* str)
{
    //char* strng = str;
    if(strcmp(&str[0], "[") != 0 && strcmp(&str[-1], "]")) return str;
    return "ERROR";
}

GH_USER build_user(char* line)
{
	GH_USER u = malloc(sizeof(struct gh_user));
	char* buff2 = line;
    
    int temp = verificarInteiro(strsep(&buff2, ";\n"));
	if(temp == -1){
         printf("Linha %d", __LINE__); return NULL;
        }
	u->id = temp;

    char* tempStr = strdup(verifyStr(strsep(&buff2, ";\n")));
    if (strcmp(tempStr, "ERROR")==0){printf("user: %d", u->id); return NULL;}
    u->login = tempStr;

    char *temp1 = strsep(&buff2, ";\n");
    if (strcmp(temp1, "User")) u->type = User;
    else if(strcmp(temp1,"Organization")) u->type = Organization;
    else { printf("user: %d", u->id); return NULL; }

	struct tm temp2 = verifyTime(strsep(&buff2, ";\n"));
	if(temp2.tm_mday == 0){printf("user: %d", u->id);return NULL;}
	u->created_at = temp2;


    temp = verificarInteiro(strsep(&buff2, ";\n"));
	if(temp == -1) {printf("user: %d", u->id);return NULL;}
	u->followers = temp;


    temp1 = strdup(verifyArr((strsep(&buff2, ";\n"))));
    if(strcmp(temp1,"ERROR") == 0) return NULL;
    u->follower_list = temp1;

    temp = verificarInteiro(strsep(&buff2, ";\n"));
	if(temp == -1){printf("user: %d", u->id);return NULL;}
	u->following = temp;

    temp1 = strdup(verifyArr((strsep(&buff2, ";\n"))));
    if(strcmp(temp1,"ERROR") == 0) return NULL;
    u->following_list = temp1;
    
    temp = verificarInteiro(strsep(&buff2, ";\n"));
	if(temp == -1){printf("user: %d", u->id);return NULL;}
	u->public_gists = temp;

	temp = verificarInteiro(strsep(&buff2, ";\n"));
	if(temp == -1){printf("user: %d", u->id);return NULL;}
	u->public_repos = temp;

	return u;
}




char* printUser(GH_USER u)
{
	char prefix[200] = "";
	struct tm data = u->created_at;
	char dataString[100] = "";
	char* type = "User";
	if(u->type == Organization) type="Organization";

	snprintf(dataString, sizeof(dataString), "%d-%d-%d %d:%d:%d", data.tm_year, data.tm_mon, data.tm_mday, data.tm_hour, data.tm_min, data.tm_sec);

	snprintf( prefix, sizeof(prefix), "%d;%s;%s;%s;%d;%s;%d;%s;%d;%d\n", u->id, u->login, type, 
					dataString, u->followers, u->follower_list, u->following, u->following_list, 
					u->public_gists, u->public_repos);


	char* pref = malloc(200 * sizeof(char));
	strcpy(pref, prefix);
	return pref;
}

void gravar_ficheiro(GH_USER arrayUsers[], int numUsers)
{
	FILE *f = fopen("users-ok.csv", "w");
	if (f == NULL)
	{
	    printf("Nao foi possivel abrir o ficheiro!\n");
	    exit(1);
	}

	fprintf(f, "id;login;type;created_at;followers;follower_list;following;following_list;public_gists;public_repos\n");
	for(int i = 0; i<numUsers; i++)
	{
		char* escrever = printUser(arrayUsers[i]);
		printf("%s", escrever);
		fprintf(f, "%s", escrever	);
	}
	fclose(f);

	printf("Ficheiro gravado com sucesso !");

}

void print_user(GH_USER u)
{
    struct tm data = u->created_at;
	printf("id: %d | pub_repos: %d | data: %d-%d-%d %d:%d:%d\n", u->id, u->public_repos, data.tm_year, data.tm_mon, data.tm_mday, data.tm_hour, data.tm_min, data.tm_sec);
}

int main(int argc, char const *argv[])
{
	int max_len = 400000;
	char buff[max_len];
	char *buff2;

	GH_USER array[400000];


	FILE *file = fopen(argv[1], "r");
	if(file == NULL)
		return -1;

	int i = 0, ln = 1;
	fgets(buff, max_len, file);
	while(fgets(buff, max_len, file))
	{	
		GH_USER temp = build_user(buff);

		if(temp != NULL){
			array[i] = temp;
			i++;
		}else{
			printf("Erro encontrado na linha %d\n", ln+1);
		}
		ln++;
			
	}

	print_user(array[1]);
	gravar_ficheiro(array, i);


	fclose(file);


	return 0;
}