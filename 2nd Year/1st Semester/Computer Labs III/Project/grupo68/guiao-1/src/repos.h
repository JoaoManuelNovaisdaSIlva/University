#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>


typedef struct gh_repos* GH_REPOS;


char* printRepos(GH_REPOS u);

void saveRepos(GH_REPOS arrayRepos[], int numRepos, int delete);

GH_REPOS build_repos(char* line);

void loadRepos(char* fileName);

struct node* loadReposBin(const char *string);

void removeInvalidRepos(char* fileName, struct node* userIDs, struct node* reposWCommits);

