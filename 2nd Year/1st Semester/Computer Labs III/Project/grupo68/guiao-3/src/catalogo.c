#include <stdio.h>
#include <string.h>
#include <sys/ioctl.h>
#include <unistd.h>
#include <stdlib.h>
#include <glib.h>
#include "catalogo.h"

struct catalogo
{
    GTree* users;
    GTree* commits;
    GTree* repos;
    int numOfBots;
    int numOfOrganizations;
    int numOfUsers;
    float averageContributors;
    int numOfReposWithBots;
    int averageCommitsPerUser;
};

CATALOGO iniciarCatalogo(){
    CATALOGO c = malloc(sizeof(struct catalogo));
    c->numOfUsers = 0;
    c->numOfBots = 0;
    c->numOfOrganizations = 0;
    c->numOfReposWithBots = 0;
    c->averageCommitsPerUser = 0;
    c->commits = NULL;
    c->users = NULL;
    c->repos = NULL;
    return c;
}

GTree* getUsers(CATALOGO cat){
    return cat->users;
}
GTree* getCommits(CATALOGO cat){
    return cat->commits;
}
GTree* getRep(CATALOGO cat){
    return cat->repos;
}

void setUsers(CATALOGO cat, GTree* t){
    cat->users = t;
}
void setCommits(CATALOGO cat, GTree* t){
    cat->commits = t;
}
void setRep(CATALOGO cat, GTree* t){
    cat->repos = t;
}

int getNumBots(CATALOGO cat){
    return cat->numOfBots;
}

int getNumOrganizations(CATALOGO cat){
    return cat->numOfOrganizations;
}

int getNumUsers(CATALOGO cat){
    return cat->numOfUsers;
}

float getAverageContributors(CATALOGO cat){
    return cat->averageContributors;
}

int getnumofreposwithbots(CATALOGO cat){
    return cat->numOfReposWithBots;
}

int getaveragecommits(CATALOGO cat){
    return cat->averageCommitsPerUser;
}


void setNumBots(CATALOGO cat, int n){
    cat->numOfBots = n;
}

void setNumUsers(CATALOGO cat, int n){
    cat->numOfUsers = n;
}
void setNumOrganizations(CATALOGO cat, int n){
    cat->numOfOrganizations = n;
}