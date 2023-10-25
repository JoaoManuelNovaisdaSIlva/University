#include <stdio.h>
#include <string.h>
#include <sys/ioctl.h>
#include <unistd.h>
#include <stdlib.h>
#include <glib.h>

#ifndef GUIAO_3_INTERPRETADOR_H
#define GUIAO_3_INTERPRETADOR_H

void interpretador(CATALOGO cat, int writeToFile);
char* executa(CATALOGO cat, int query, int arg1, int arg2, char* arg3, char* arg4);
char* printLinesForPages(GList* a);
void interpretadorComandos(CATALOGO cat, char* filename);

#endif //GUIAO_3_INTERPRETADOR_H
