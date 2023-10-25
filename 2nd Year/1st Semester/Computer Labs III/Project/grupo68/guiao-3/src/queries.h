#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>
#include <glib.h>
#include "catalogo.h"

//Queries
typedef struct query* QUERY;

char* getMessage(QUERY q);

int getRepoID(QUERY c);

void freeQuery(QUERY c);


gint inteiros(gconstpointer a, gconstpointer b);

float commitsPerUser(GTree *commitTree, GTree *userTree);

char* numTypes(CATALOGO cat);

char* quantidadeReposWithBots(GTree* users, GTree* repos);

GList* topNlanguage_language(GTree *users, GTree *commits, GTree *repos, int nTop, char* lang);

GList* numOfUsersActive(GTree *treeCommits, GTree *users, int nTop, char* dateStrat, char* dateEnd);

float averageNumOfCollab(GTree *t, GTree *repos);

GList* inactiveRepos(GTree *commits, GTree *repos, char* dateStart);

GList* topNlanguage_afterTime (GTree *repos, GTree *commits, char *date, int N);

