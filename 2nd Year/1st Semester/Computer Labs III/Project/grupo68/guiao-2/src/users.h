#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>


typedef struct user* USER;

char * getLogin();
int getCommits();

USER procuraUser(int id, GTree* t);

int getUserId(USER u);

void  loadUsers(char* fileName, GTree* t);

char* getLoginFromID(GTree* t, int id);

USER getUserFromID(GTree*t, int id);
int isBot(USER u);
int isUser(USER u);

int getTypeUser(USER u);

//char* numOfUsersActive(GTree *treeCommits, int nTop, char* dateStrat, char* dateEnd);


