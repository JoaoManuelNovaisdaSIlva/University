/**
@file logic.h

*/
#pragma once
#include "../headers/board.h"
#include <stdlib.h>

#define VALID 1
#define INVALID 0
/**
 * permite a movimentação da peça branca ao longo do tabuleiro dentro das jogadas possiveis
 * @param b apontador do estado do tabuleiro
 * @param pos vetor com coordenadas x e y
 * @return valid caso haja casas livres possiveis pra jogar ou invalid caso estejam todas ocupadas por fichas pretas
 */
int play(BOARD *b, VEC2D pos);
/**
 * a função avalia o estado do jogo e ,caso não haja casas vazias em torno da peça branca retorna o fim do jogo,
 * senão retorna 1 ou 2 caso a peça branca alcance o vetor (0,0) ou (7,7), respetivamente.
 * @param b estado do tabuleiro
 * @return 1 ou a função isSurrounded
 */
int isGameOver(const BOARD b);
