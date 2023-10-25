/**
@file interface.h

*/
#pragma once
#include "../headers/board.h"
#include "../headers/logic.h"
#include "../headers/bots.h"

#include <stdio.h>
#include <string.h>

void drawBoard(const BOARD b);
/**
 * \brief permite a utilização de outros comandos (movs, gr,...)
 * @param b apontador do estado do tabuleiro
 * @return 0
 */
int promptCommand(BOARD *b);
void movs (BOARD *e);