#include "../headers/astar.h"

void printNodes(AS_NODE *nodes){
    int counter = 0;
    for(int y = 0; y < 8; y++){
        for(int x = 0; x < 8; x++){
            printf("%.2f   ", nodes[counter].g_cost);
            counter++;
        }
        printf("\n\n");
    }
}

void printList(NODE *l){
    while(!isListEmpty(l)){
        AS_NODE *temp = (AS_NODE *)getHead(l);
        printf("X: %dY: %d", temp->x, temp->y);
        l = next(l);
    }
}

void printNeighbours(AS_NODE *nodes){
    int counter = 0;
    for(int y = 0; y < 3; y++){
        for(int x = 0; x < 3; x++){
            printf("%.2f   ", nodes[counter].g_cost);
            counter++;
        }
        printf("\n\n");
    }
}

AS_NODE * genNodes(VEC2D cp, const BOARD b, int current_player){
    AS_NODE *final = malloc(sizeof(AS_NODE) * 64);
    int counter = 0;

    for(int y = 0; y < 8; y++){
        for(int x = 0; x < 8; x++){
            VEC2D temp = {.x = x, .y = y};
            final[counter].x = x;
            final[counter].y = y;
            if(current_player == PLAYER_2){
                final[counter].g_cost = pow(final[counter].x - cp.x ,2 ) + pow(final[counter].y - cp.y, 2);
                final[counter].h_cost = pow(final[counter].x - 7, 2) + pow(final[counter].y, 2); //NEEDS TO BE ADAPTED FOR BOTH PLAYERS
                final[counter].f_cost = final[counter].g_cost + final[counter].h_cost;
            }else{
                final[counter].g_cost = pow(final[counter].x - cp.x ,2 ) + pow(final[counter].y - cp.y, 2);
                final[counter].h_cost = pow(final[counter].x, 2) + pow(final[counter].y - 7, 2); //NEEDS TO BE ADAPTED FOR BOTH PLAYERS
                final[counter].f_cost = final[counter].g_cost + final[counter].h_cost;
            }
            if(x == cp.x && y == cp.y)
                final[counter].is_used = 1;
            else
                final[counter].is_used = 0;
            if(getStatus(b, temp) != WHITE && getStatus(b, temp) != BLACK)
                final[counter].is_obstacle = 1;
            else
                final[counter].is_obstacle = 0;

            counter++;
        }
    }
    return final;
}

AS_NODE findNode(int gx, int gy, AS_NODE * nodes){
    int counter = 0;

    for(int y = 0; y < 8; y++){
        for(int x = 0; x < 8; x++){
            if(x == gx && y == gy){
                return nodes[counter];
            }
            counter ++;
        }
    }
}

AS_NODE * getNeighbours(VEC2D cp, AS_NODE * nodes){
    AS_NODE * final = malloc(sizeof(AS_NODE) * 9);
    int counter = 0;
    for(int y = 0; y < 8; y++){
        for(int x = 0; x < 8; x++){
            if(x >= cp.x - 1 && x <= cp.x + 1 && y >= cp.y - 1 && y <= cp.y + 1){
                final[counter] = findNode(x, y, nodes);
                counter++;
            }
        }
    }
    return final;
}

float minFCost(AS_NODE * neighbours){
    float final = neighbours[0].f_cost;
    
    for(int i = 0; i < 9; i++){
        if(neighbours[i].is_used == 0 && neighbours[i].f_cost < final && neighbours[i].is_obstacle == 0){
            final = neighbours[i].f_cost;
        }
    }
    return final;
}

NODE * findNextSteps(AS_NODE * neighbours, AS_NODE * nodes){

    NODE * final = createList();
    float min = minFCost(neighbours);

    for(int i = 0; i < 9; i++){
        if(neighbours[i].is_used == 0 && neighbours[i].f_cost == min){
            neighbours[i].is_used = 1;
            final = insertHead(final, (void *)&neighbours[i]);
        }
    }

    return final;
}

VEC2D astar(const BOARD b){
    VEC2D cp = getLastPlay(b);
    VEC2D final;
    AS_NODE * all_nodes = genNodes(cp, b, getCurrentPlayer(b));
    AS_NODE * neighbours = getNeighbours(cp, all_nodes);

    NODE *ns = findNextSteps(neighbours, all_nodes);

    if(!isListEmpty(ns)){
        AS_NODE * final_play = (AS_NODE*)getHead(ns);
        final.x = final_play->x;
        final.y = final_play->y;
    }    

    free(all_nodes);
    free(neighbours);

    return final;
} 