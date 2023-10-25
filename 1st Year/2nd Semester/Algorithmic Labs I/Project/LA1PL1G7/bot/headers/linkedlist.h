#pragma once

#include <stdlib.h>
#include <stdio.h>

typedef struct list
{
    void *value;
    struct list *next_location;
}*LIST, NODE;

LIST createList();
LIST insertHead(LIST l, void *value);
LIST removeHead(LIST l);
LIST next(LIST l);

void *getHead(LIST l);
int isListEmpty(LIST l);
