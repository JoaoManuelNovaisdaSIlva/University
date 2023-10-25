/**
@file interface.c
Camada de interface responsável pelo desenho do tabuleiro
*/
#include "../headers/interface.h"

const char symbols[] = {'.', '#', '*', '1', '2'};
const char *letters = "abcdefgh";

/**
\brief Convertor do string introduzido na promp que representa a jogada
para vetor, para ser utilizado nas outras funções 
*/
VEC2D strToVec(const char* str)
{
    VEC2D final = { .x = (int)str[0] - 97, .y = (int)abs(8 - (str[1] - 48))};
    return final;
}
/**
\brief Desenha o tabuleiro com as peças e as casas
*/
void drawBoard(const BOARD b)
{
    char num = '8';

    for(int y = 0; y < BOARD_SIZE; y++)
    {
        printf("%c ",num);
        for(int x = 0; x < BOARD_SIZE; x++)
        {
            VEC2D pos = {.x = x, .y = y};
            putchar(symbols[getStatus(b, pos)]);
        }
        putchar('\n');
        num--;
    }
    printf("  %s\n", letters);
}
/**
\brief Apresenta as jogadas anteriores 
*/
void printMoves(const BOARD b)
{
   for(int i = 1; i <= getNPlays(b); i+=2)
    {
        if(getPlay(b, i+1).x != -1)
            printf("%d%d: %c%d %c%d\n", i / 10, i/2,  getPlay(b, i).x + 'a', 8 - getPlay(b,i).y, getPlay(b, i+1).x + 'a', 8 - getPlay(b,i+1).y);
        else
            printf("%d%d: %c%d\n", i / 10, i/2,  getPlay(b, i).x + 'a', 8 - getPlay(b,i).y);
    }
}
/**
\brief Apresenta a promp que incorpora a ronda o jogador e a jogada
*/

void boardReset(BOARD *b, unsigned int play_num)
{
    for (int i = play_num + 1; i < 64; i++)
    {
        setStautus(b, getPlay(*b, i), EMPTY);
    }
    setStautus(b, getPlay(*b, play_num), WHITE);
    setLastPlay(b, getPlay(*b, play_num));
    setNPlays(b, play_num);
}

int promptCommand(BOARD *b)
{
    const char *players[] = {"PL1", "PL2"};

    char *linha = malloc(128);
    char *cmd = malloc(8);
    char *path = malloc(16);

    printf("# %d %s (%d)>", getNPlays(*b) + 1, players[getCurrentPlayer(*b)], getNPlays(*b) / 2);
    fgets(linha, 128, stdin);

    if(sscanf(linha, "%[a-h]%[1-9]", &cmd[0], &cmd[1]) == 2)
    {
        play(b, strToVec(cmd));
        drawBoard(*b);
    }
    else{
        sscanf(linha, "%s %s", cmd, path);
        if(!strcmp(cmd, "gr"))
            saveBoard(*b, path);
        
        else if(!strcmp(cmd, "ler")){
            *b = loadBoard(path);
            drawBoard(*b);
        }
        else if(!strcmp(cmd, "movs"))
        {
            printMoves(*b);
        }
        else if(!strcmp(cmd, "pos"))
        {
            boardReset(b, atoi(path) * 2);
            drawBoard(*b);
        }
        else if (!strcmp(cmd,"jogrand"))
        {
            jogrand (b);
            drawBoard(*b);
        }else if (!strcmp(cmd, "jog2")){
            floodFill(b);
            drawBoard(*b);
        }
        else
            printf("Invalid Input\n");
        }

    free(linha);
    free(cmd);
    free(path);
    return 0;
}