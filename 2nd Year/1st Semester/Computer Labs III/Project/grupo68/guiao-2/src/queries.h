#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>
#include <glib.h>


gint inteiros(gconstpointer a, gconstpointer b);

float commitsPerUser(GTree *commitTree, GTree *userTree);

char* numTypes(GTree *t);

int quantidadeReposWithBots(GTree* users, GTree* repos);

char* topNlanguage_language(GTree *users, GTree *commits, GTree *repos, int nTop, char* lang);

char* numOfUsersActive(GTree *treeCommits, GTree *users, int nTop, char* dateStrat, char* dateEnd);

float averageNumOfCollab(GTree *t, GTree *repos);

char* inactiveRepos(GTree *commits, GTree *repos, char* dateStart, char* fileName);


