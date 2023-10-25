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

#define VALID 1
#define INVALID 0

#include "../headers/bot.h"

#include <stdio.h>
#include <string.h>
#include <stdlib.h>

typedef struct
{
    int x,y;
}VEC2D;

typedef struct
{   
    int board_arr[BOARD_SIZE][BOARD_SIZE];
    VEC2D last_play;
    int current_player;
    int n_plays;
    VEC2D all_plays [64]; 
}BOARD;

void initBoard(BOARD *b);

void setStautus(BOARD *b,VEC2D pos, int status);
int getStatus(const BOARD b, VEC2D pos);

void addMove (BOARD *b, VEC2D pos);
VEC2D getLastPlay(const BOARD b);

void setCurrentPlayer(BOARD *b, int player);
int getCurrentPlayer(const BOARD b);

void setNPlays(BOARD *b, int num);
int getNPlays(const BOARD b);
void savePlay (VEC2D coords, BOARD *b);

int isInBoard(const VEC2D coords);

VEC2D strToVec(const char* str);
void *generateMovsString(const BOARD b, FILE *fp);

int saveBoard(const BOARD b, const char* path);
BOARD loadBoard(const char *path);

VEC2D getPlay(const BOARD b, int index);

int play(BOARD *b, VEC2D pos);

void drawBoard(const BOARD b);