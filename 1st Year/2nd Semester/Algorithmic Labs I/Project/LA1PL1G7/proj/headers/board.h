/**
@file board.h

*/
#pragma once

#define EMPTY 0
#define BLACK 1
#define WHITE 2
#define PLAYER1_HOUSE 3
#define PLAYER2_HOUSE 4

#define PLAYER_1 0
#define PLAYER_2 1

#define BOARD_SIZE 8

#define BUFFER_SIZE 1024

#include <stdio.h>
#include <string.h>

/**
 \brief vetor que recebe inteiros x e y
 */
typedef struct 
{
    int x,y;
}VEC2D;

/**
\brief Board que recebe um tabuleiro 8 por 8, um vetor com a ultima jogada, a atual jogada, o número de jogadas possiveis e o número total de jogadas
*/
typedef struct
{   
    int board_arr[BOARD_SIZE][BOARD_SIZE];
    VEC2D last_play;
    int current_player;
    int n_plays;
    VEC2D all_plays [64]; 
}BOARD;
/**
 * \brief inicializa o tabuleiro
 * @param b apontador do estado do tabuleiro
 */
void initBoard(BOARD *b);
/**
 * \brief atualiza o estado da celula
 * @param b apontador do estado do tabuleiro
 * @param pos vetor posição
 * @param status inteiro
 */
void setStautus(BOARD *b,VEC2D pos, int status);
/**
 * \brief insere a última jogada
 * @param b apontador do estado do tabuleiro
 * @param pos vetor posição
 */
void setLastPlay(BOARD *b, VEC2D pos);
/**
 * \brief define o jogador atual
 * @param b apontador do estado do tabuleiro
 * @param player inteiro para o jogador (1 ou 2)
 */
void setCurrentPlayer(BOARD *b, int player);
/**
 * \brief define o número de jogadas efetuadas
 * @param b apontador do estado do tabuleiro
 * @param num inteiro com o numero de jogadas efetuadas
 */
void setNPlays(BOARD *b, int num);
/**
 * \brief vetor x e y  das coordenadas da última jogada
 * @param b estado do tabuleiro
 * @return retorna as coordenadas da última jogada
 */
VEC2D getLastPlay(const BOARD b);
/**
 * \brief revela o jogador atual (1 ou 2)
 * @param b estado do tabuleiro
 * @return o jogador atual
 */
int getCurrentPlayer(const BOARD b);
/**
 * \brief
 * @param b estado do tabuleiro
 * @param pos vetor posição
 * @return o estado atual do tabuleiro
 */
int getStatus(const BOARD b, VEC2D pos);
/**
 * \brief
 * @param b estado do tabuleiro
 * @return o número de jogadas efetuadas
 */
int getNPlays(const BOARD b);
/**
 * \brief guarda as atualizações efetuadas no tabuleiro
 * @param b estado do tabuleiro
 * @param path apontador char que permite a leitura e escrita no ficheiro do tabuleiro
 * @return 0
 */
int saveBoard(const BOARD b, const char* path);
/**
 * \brief carrega a ultima atualização guardada no tabuleiro
 * @param path apontador char
 * @return o tabuleiro com a ultima atualização
 */
BOARD loadBoard(const char *path);
/**
 * \brief guarda as coordenadas da ultima jogada efetuada
 * @param b apontador do estado do tabuleiro
 */
void savePlay (VEC2D, BOARD *b);
/**
 * \brief devolve as coordenadas das index últimas jogadas efetuadas
 * @param b estado do tabuleiro
 * @param index inteiro número de jogadas
 * @return
 */
VEC2D getPlay (const BOARD b, int index);
/**
 * \brief identifica se as coordenadas inseridas são ou não válidas dentro do tabuleiro
 * @param coords vetor coordenadas
 * @return 1 caso sejam válidas ou 2 caso sejam inválidas
 */
int isInBoard(const VEC2D coords);