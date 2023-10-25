#pragma once 

#include <math.h>

#include "../headers/board.h"
#include "../headers/linkedlist.h"

typedef struct node{
    int x, y;
    float g_cost, h_cost, f_cost;
    int null_set, id;
    int is_obstacle, is_used;
    NODE *parent;
}AS_NODE;

void printNodes(AS_NODE *nodes);
VEC2D astar(const BOARD b);