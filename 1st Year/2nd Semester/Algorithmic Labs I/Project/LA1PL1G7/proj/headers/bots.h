/**
 * @file bots.h
 */
#pragma once

#include <time.h>
#include <stdio.h>
#include <math.h>

#include "../headers/board.h"
#include "../headers/linkedlist.h"
#include "../headers/logic.h"
/**
 * tipo de dados de uma célula
 */
typedef struct cell
{
    float distance;
    VEC2D position;
}CELL;
/**
 * \brief comando que efetua uma jogada possivel aleatória
 * @param b apontador do estado do tabuleiro
 */
void jogrand (BOARD *b);
/**
 * \brief comando que efetua uma jogada posssivel através da distância baseada no método FloodFill
 * @param b apontador do estado do tabuleiro
 */
void floodFill(BOARD *b);
