#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>


typedef struct gh_commit* GH_COMMIT;


char* printCommit(GH_COMMIT u);

void saveCommits(GH_COMMIT arrayCommits[], int numCommits, int delete);

GH_COMMIT build_commit(char* line);

void loadCommits(char* fileName);

struct node* loadReposWCommits(char *string);

void removeInvalidCommits(char* fileName, struct node* userIDs, struct node* reposIDs);

