/**
@file main.c
Ficheiro main que executa o programa
*/
#include <stdio.h>
#include "../headers/board.h"
#include "../headers/interface.h"
#include "../headers/logic.h"

/**
\brief
*/
int main(int argc, char const *argv[])
{
    BOARD b;
    initBoard(&b);
    drawBoard(b);
    while (!isGameOver(b))
    {
        promptCommand(&b);
    }
    return 0;
}
