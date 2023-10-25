#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>


typedef struct gh_user* GH_USER;


char* printUser(GH_USER u);

void saveUser(GH_USER arrayUsers[], int numUsers, int delete);

GH_USER build_user(char* line);

void  loadUsers(char* fileName);

void saveUsersToFinal(char* fileName);

struct node* loadUsersBin(const char *string);