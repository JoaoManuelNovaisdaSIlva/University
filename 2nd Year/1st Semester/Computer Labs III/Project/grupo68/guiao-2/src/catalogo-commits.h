#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>
#include <glib.h>


//int loadCommitsG2();
//int buildCommitG2(char* line);

typedef struct commits* COMMITS;

int getCommitter(COMMITS c);

int getAuthor(COMMITS c);

int getRepos(COMMITS c);

struct tm getDate(COMMITS c);

float numOfCommitters(GTree *commits, GTree *repos);

void buildCommits(char* line, int lineN, GTree *t);

void loadCommits(char* filename, GTree *t);