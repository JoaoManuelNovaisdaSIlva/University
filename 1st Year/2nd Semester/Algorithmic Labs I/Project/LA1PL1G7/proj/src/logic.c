/**
@file logic.c
Camada de lógica
*/
#include "../headers/logic.h"

/**
 * \brief determina a distância entre duas posições
 * @param pos1
 * @param pos2
 * @return o resultado entre a diferença de uma posição inicial e final
 */
unsigned int distance(VEC2D pos1, VEC2D pos2)
{
    unsigned int x_diference = abs(pos1.x - pos2.x);
    unsigned int y_diference = abs(pos1.y - pos2.y);

    if(x_diference > 1 || y_diference > 1)
        return 0;
    return 1;
}
/**
 * \brief verifica se a peça branca tem espaços livres pa jogar ou se está rodeada de peças pretas
 * @param b estado do tabuleiro
 * @return 0 se houver uma casa livre na posição onde vai jogar ou 1 caso esteja uma peça preta nessa posição
 */
int isSurrounded(const BOARD b)
{
    int counter = 0;

    VEC2D lp = getLastPlay(b);
    VEC2D neighbours[8] = {0};

    for(int y = -1; y < 2; y++)
    {
        for(int x = -1; x < 2; x++){
            if(x != 0 || y != 0){
                VEC2D temp = {.x = lp.x + x, .y = lp.y + y};
                if( temp.x < 0 || temp.x > 7 || temp.y < 0 || temp.y > 7){
                    neighbours[counter] = getPlay(b, 0);
                }else
                    neighbours[counter] = temp;
                counter++;
            }
        }
    }

    for(int i = 0; i < 8; i++){
        if(getStatus(b, neighbours[i]) != BLACK){
            return 0;
        }
    }
    return 1;
}

int isGameOver(const BOARD b) 
{ 
    VEC2D endp1 = {.x = 7, .y = 0};
    VEC2D endp2 = {.x = 0, .y = 7};

    if(getLastPlay(b).x == endp1.x && getLastPlay(b).y == endp1.y || 
       getLastPlay(b).x == endp2.x && getLastPlay(b).y == endp2.y){
        return 1;
    }

    return isSurrounded(b);
}


int play(BOARD *b, VEC2D pos)
{
    if(distance(pos, getLastPlay(*b)) && getStatus(*b, pos) != BLACK && getStatus(*b, pos) != WHITE
       && pos.x > -1 && pos.x < 8 && pos.y > -1 && pos.y < 8)
    {
        setStautus(b, pos, WHITE);
        setStautus(b, getLastPlay(*b), BLACK);
        setLastPlay(b, pos);
        setNPlays(b, getNPlays(*b) + 1);
        setCurrentPlayer(b, abs(getCurrentPlayer(*b) - 1));
        savePlay(pos, b);
        return VALID;
    }

    return INVALID;
}