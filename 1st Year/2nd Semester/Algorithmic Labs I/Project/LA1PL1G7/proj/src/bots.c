/**
 * @file bots.c
 * camada de comandos de jogadas artificiais
 */
#include "../headers/bots.h"

// bot 1

/**
 * \brief apresenta uma lista de jogadas efetuadas
 */
void printList(NODE *list){
    while (!isListEmpty(list))
    {
        CELL *temp = (CELL*)getHead(list);
        printf("X: %d, Y: %d\n", temp->position.x, temp->position.y);
        list = next(list);
    }
    
}
/**
 * \brief avalia as possiveis jogadas a ser efetuadas em torno da peça branca
 * @param b estado do tsbuleiro
 * @param neighbours apontador de um nodo
 * @param size apontador de um inteiro size
 * @return  uma lista de jogadas
 */
NODE *validPlays(BOARD b, NODE *neighbours, int *size){

    NODE* vp = createList();
    int counter = 0;

    for(int i = 0; i < 9; i++){
        if(getStatus(b, *(VEC2D*)getHead(neighbours)) != BLACK 
        && getStatus(b, *(VEC2D*)getHead(neighbours)) != WHITE
        && isInBoard(*(VEC2D*)getHead(neighbours))){
            vp = insertHead(vp, getHead(neighbours));
            counter++;
        }
        neighbours = next(neighbours);
    }

    *size = counter;
    return vp;
}

NODE* generateNeighbours(VEC2D localPosition, VEC2D *temp){
    NODE *vectors = createList();
    int counter = 0;

    for(int y = -1; y < 2; y++){
        for(int x = -1; x < 2; x++){
            temp[counter].x = localPosition.x + x;
            temp[counter].y = localPosition.y + y;

            vectors = insertHead(vectors, (void*)&temp[counter]);
            counter++;
            
        }
    }

    return vectors;
}
/**
 * \brief faz uma seleção aleatória de posiçôes possiveis a ser jogadas
 */
VEC2D generateRandomVec(NODE *list, int size){
    for(int i = 0; i < rand() % size; i++){
        list = next(list);
    }

    return *(VEC2D*)getHead(list);
}

void jogrand (BOARD *b)
{
    srand(time(NULL));
    int size;
    VEC2D lpos = getLastPlay(*b);

    VEC2D *temp = malloc(sizeof(VEC2D) * 9);
    NODE *neighbours = generateNeighbours(lpos, temp);
    NODE *valid = validPlays(*b, neighbours, &size);

    VEC2D final = generateRandomVec(valid, size);

    play(b, final);

    free(temp);
    free(valid);
    free(neighbours);
}
// bot 2
/**
 * calcula a distância entre duas posiçôes dependendo do jogador atual
 */
float generateDistance(int player, VEC2D currentPosition){
    float final;
    if(player == PLAYER_2){
        final = sqrt(pow(currentPosition.y - 7, 2) + pow(currentPosition.x, 2));
    }else{
        final = sqrt(pow(currentPosition.y, 2) + pow(currentPosition.x - 7, 2));
    }
    //printf ("final: %f \n", final);

    return final;
}
/**
 * \brief
 */
void generateMatrix(CELL matrix[8][8], const BOARD b, int player){
    for(int y = 7; y > -1; y--){
        for(int x = 0; x < 8; x++){
            VEC2D temp = {.x = x, .y = y};
            matrix[y][x].position = temp;
            //printf ("x : %d y: %d\n", temp.x , temp.y);
            matrix[y][x].distance = generateDistance(player, temp);
        }
    }
}
/**
 *\brief  retorna a posição atual da peça branca
 */
CELL getWhitePosition (VEC2D lastPlay, CELL matrix [8][8])
{
     CELL whitePosition;

    for (int y = 0; y < 8; y++)
    {
        for (int x = 0; x < 8; x++)
        {
            if (matrix [y][x].position.x == lastPlay.x && matrix [y][x].position.y == lastPlay.y)
            {
                whitePosition.position.x = lastPlay.x;
                whitePosition.position.y = lastPlay.y;
                whitePosition.distance = matrix [y][x].distance;
            }
        }
    }
    return whitePosition;
}
/**
 *\brief gera uma celula de listas vizinhas
 */
CELL *generateNeighbourCell (VEC2D lastPlay, CELL matrix [8][8])
{
    CELL whitePosition = getWhitePosition (lastPlay, matrix);
    int counter = 0;
    CELL *final = malloc (sizeof (CELL) * 9);

     for(int y = -1; y < 2; y++)
     {
        for(int x = -1; x < 2; x++)
        {

            final[counter].position.x = whitePosition.position.x + x;
            final[counter].position.y = whitePosition.position.y + y;

            float distance = matrix[whitePosition.position.x + x][whitePosition.position.y + y].distance;

            final[counter].distance = distance; 
            counter++;         
        }
    }
    return final;
}
/**
 * \brief gera as celulas validas pra jogar
 */
NODE *generateValidCell (const BOARD b ,CELL *neighbours, int *size)
{
    NODE *vp = createList();
    int counter = 0;
    for (int i = 0; i < 9; i++)
    {
        if(getStatus(b, neighbours[i].position) != BLACK 
        && getStatus(b, neighbours[i].position) != WHITE
        && isInBoard(neighbours[i].position)){
            vp = insertHead(vp, &neighbours[i]);
            counter++;
        }  
    }

    *size = counter;
    return vp;
}

/**
 * Encontra a celula com a menor distancia a casa pretendida
 */
CELL min(NODE *list){
    CELL *final = (CELL *)getHead(list);

    while (!isListEmpty(list))
    {
        CELL *currentCell = (CELL *)getHead(list);
        //printf("FINAL: %d, CURRENT: %d\n", final->distance, currentCell->distance);
        if(currentCell->distance < final->distance){
            final->position = currentCell->position;
            final->distance = currentCell->distance;
        }
        list = next(list);
    }
    return *final;
}

void floodFill(BOARD *b)
{
    int current_player = getCurrentPlayer(*b);
    CELL matrix[8][8];
    int size;

    generateMatrix(matrix, *b, current_player);
    CELL *neighbours = generateNeighbourCell (getLastPlay(*b), matrix);

    NODE *valid_cells = generateValidCell(*b, neighbours, &size);

    //printList(valid_cells);
    CELL cell_to_play = min(valid_cells);

    //printf("X: %d Y: %d D: %.02f", cell_to_play.position.x, cell_to_play.position.y, cell_to_play.distance);

    play(b, cell_to_play.position);

    free (neighbours);
    free (valid_cells);
}