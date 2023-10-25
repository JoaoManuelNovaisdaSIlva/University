#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>
#include <glib.h>
#include "catalogo.h"

typedef struct repo* REPO;

int getReposid(REPO c);

int getOwnerid(REPO c);

struct tm getUploadedDate(REPO c);

char* getLanguage(REPO c);

char* getDescription(REPO c);

int contaRepos (GTree *t);

void loadRepos(char* filename, CATALOGO cat);

char* getLanguageFromREPOID(GTree* t, int repo_id);

char* getDescrionFromREPOID(GTree* t, int repo_id);