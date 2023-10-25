#include <stdio.h>
#include <string.h>
#include <sys/ioctl.h>
#include <unistd.h>
#include <stdlib.h>
#include <glib.h>



typedef struct catalogo *CATALOGO;

int getNumBots(CATALOGO cat);
int getNumOrganizations(CATALOGO cat);
int getNumUsers(CATALOGO cat);
float getAverageContributors(CATALOGO cat);
int getnumofreposwithbots(CATALOGO cat);
int getaveragecommits(CATALOGO cat);
void setNumBots(CATALOGO cat, int n);
void setNumUsers(CATALOGO cat, int n);
void setNumOrganizations(CATALOGO cat, int n);
GTree* getUsers(CATALOGO cat);
GTree* getCommits(CATALOGO cat);
GTree* getRep(CATALOGO cat);
void setUsers(CATALOGO cat, GTree* t);
void setCommits(CATALOGO cat, GTree* t);
void setRep(CATALOGO cat, GTree* t);
CATALOGO iniciarCatalogo();