//
// Created by admin on 31/01/22.
//

#ifndef GUIAO_3_MAIN_H
#define GUIAO_3_MAIN_H

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/resource.h>
#include <glib.h>
#include <ctype.h>

#include "catalogo-users.h"
#include "catalogo-commits.h"
#include "queries.h"
#include "catalogo-repos.h"
#include "constructors.h"
#include "interpretador.h"
#include "catalogo.h"

char* getNomeFicheiroUsers();
char* getNomeFicheiroCommits();
char* getNomeFicheiroRepos();
void executeQueries(char* line, CATALOGO* cat, int query);

#endif //GUIAO_3_MAIN_H
