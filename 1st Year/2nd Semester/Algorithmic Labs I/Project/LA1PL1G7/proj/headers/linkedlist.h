/**
 * @file linkedlist.h
 */
#pragma once

#include <stdlib.h>
#include <stdio.h>
/**
 * \brief tipo de dados para uma lista nodo
 */
typedef struct list
{
    void *value;
    struct list *next_location;
}*LIST, NODE;
/**
 * \brief cria uma lista vazia
 * @return NULL
 */
LIST createList();
/**
 * \brief insere um pointer value e uma nova localização vazia na cabeça da lista
 */
LIST insertHead(LIST l, void *value);
/**
 * \brief remove o pointer value da cabeça da lista
 * @return final
 */
LIST removeHead(LIST l);
/**
 *  \brief retorna a proxima localização  na lista
 */
LIST next(LIST l);
/**
 * \brief retorna o pointer value na cabeça da lista
 */
void *getHead(LIST l);
/**
 * retorna uma lista l caso esta esteja vazia
 */
int isListEmpty(LIST l);
