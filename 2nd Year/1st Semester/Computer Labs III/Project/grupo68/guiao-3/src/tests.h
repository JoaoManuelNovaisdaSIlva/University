#define _XOPEN_SOURCE
#define _DEFAULT_SOURCE

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/resource.h>
#include <glib.h>
#include <ctype.h>
#include <time.h>

#include "catalogo.h"
#include "queries.h"
#include "constructors.h"

int compareToValid(char* filenameValid, char* filenameTested);

int testQuerie1(CATALOGO cat);

int testQuerie2(CATALOGO cat);

int testQuerie3(CATALOGO cat);

int testQuerie4(CATALOGO cat);

int testQuerie5(CATALOGO cat);

int testQuerie6(CATALOGO cat);

int testQuerie7(CATALOGO cat);

int testQuerie8(CATALOGO cat);