#include "../headers/linkedlist.h"

LIST createList()
{
    return NULL;
}

LIST insertHead(LIST l, void *value)
{
    LIST head = (LIST)malloc(sizeof(LIST));
    head->next_location = l;
    head->value = value;
    return head;
}

void *getHead(LIST l)
{
    return l->value;
}

int isListEmpty(LIST l)
{
    return l == NULL;
}

LIST next(LIST l)
{
    return l->next_location;
}

LIST removeHead(LIST l){
    LIST final = l->next_location;
    free(l);
    return final;   
}