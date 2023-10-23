#include <stdio.h>

#include "../headers/board.h"
#include "../headers/bot.h"
#include "../headers/astar.h"



int main(int argc, char *argv[]) 
{
    if(argc == 3)
    {
        BOARD b = loadBoard (argv [0]);
        drawBoard (b);
        VEC2D finalPlay = astar (b);
        printf ("final: %d%d",finalPlay.x, finalPlay.y);
        play (&b, finalPlay);
        saveBoard (b, argv [1]);
    }  
    return 0;
}


/**int main(){
    BOARD b;
    initBoard(&b);
    VEC2D test = astar(b);
    printf("%d %d", test.x, test.y);
    return 0;

}
**/