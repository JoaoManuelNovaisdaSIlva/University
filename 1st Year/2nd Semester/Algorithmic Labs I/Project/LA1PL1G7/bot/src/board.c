#include "../headers/board.h"

const char symbols[] = {'.', '#', '*', '1', '2'};
const char *letters = "abcdefgh";

void initBoard(BOARD *b)
{
    for(int y = 0; y < BOARD_SIZE; y++)
    {
        for(int x = 0; x < BOARD_SIZE; x++)
        {
            b->board_arr[y][x] = 0;
        }
    }

    b->board_arr[BOARD_SIZE-1][0] = PLAYER1_HOUSE;
    b->board_arr[0][BOARD_SIZE-1] = PLAYER2_HOUSE;
    b->board_arr[3][3] = WHITE;
    b->last_play.x = 3, b->last_play.y = 3;
    b->current_player = PLAYER_1;
    b->n_plays = 0;
    b->all_plays[0].x = 3;
    b->all_plays[0].y = 3; 
}

void setStautus(BOARD *b,VEC2D pos, int status)
{
    b->board_arr[pos.y][pos.x] = status;
}

int getStatus(const BOARD b, VEC2D pos)
{
    return b.board_arr[pos.y][pos.x];
}

void addMove (BOARD *b, VEC2D pos)
{
    b->last_play.x = pos.x;
    b->last_play.y = pos.y;
}

VEC2D getLastPlay(const BOARD b)
{
    return b.last_play;
}

void setLastPlay(BOARD *b, VEC2D pos)
{
    b->last_play.x = pos.x;
    b->last_play.y = pos.y;
}

void setCurrentPlayer(BOARD *b, int player)
{
    b->current_player = player;
}

int getCurrentPlayer(const BOARD b)
{
    return b.current_player;
}

void setNPlays(BOARD *b, int num)
{
    b->n_plays = num;
}

int getNPlays(const BOARD b)
{
    return b.n_plays;
}

void savePlay (VEC2D coords, BOARD *b)
{
    b->all_plays[b->n_plays] = coords;
}

int isInBoard(const VEC2D coords){
    if(coords.x > -1 && coords.x < 8 && coords.y > -1 && coords.y < 8)
        return 1;
    return 0;
}

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

VEC2D strToVec(const char* str)
{
    VEC2D final = { .x = (int)str[0] - 97, .y = (int)abs(8 - (str[1] - 48))};
    return final;
}

unsigned int distance(VEC2D pos1, VEC2D pos2)
{
    unsigned int x_diference = abs(pos1.x - pos2.x);
    unsigned int y_diference = abs(pos1.y - pos2.y);

    if(x_diference > 1 || y_diference > 1)
        return 0;
    return 1;
}

VEC2D getPlay(const BOARD b, int index)
{
    return b.all_plays[index];
}

void *generateMovsString(const BOARD b, FILE *fp)
{
    char buffer[BUFFER_SIZE] = {0};

    for(int i = 1; i <= getNPlays(b); i+=2)
    {
        if(getPlay(b, i+1).x != -1)
            fprintf(fp, "%d%d: %c%d %c%d\n", i / 10, i/2,  getPlay(b, i).x + 'a', 8 - getPlay(b,i).y, getPlay(b, i+1).x + 'a', 8 - getPlay(b,i+1).y);
        else
            fprintf(fp, "%d%d: %c%d\n", i / 10, i/2,  getPlay(b, i).x + 'a', 8 - getPlay(b,i).y);
    }
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

BOARD loadBoard(const char *path)
{
    FILE *fp;
    BOARD b;
    char buffer[BUFFER_SIZE] = {0};
    initBoard(&b);
    b.board_arr[3][3] = BLACK;
    fp = fopen(path, "r");

    char ignore [3];
    char c1, c2;
    int d1, d2;
    
    
    for (int i = 0; i < 9; i++)
    {
        fgets(buffer, BUFFER_SIZE, fp);
    }


    while(!feof(fp)){
        fgets(buffer, BUFFER_SIZE, fp);

        for (int i = 4; i < strlen(buffer); i++)
        {
            if(sscanf (buffer, "%s %c%d %c%d", ignore, &c1, &d1, &c2, &d2) == 5){
                c1 -= 97;
                c2 -= 97;
                d1 = 8 - d1;
                d2 = 8 - d2;

                VEC2D c1d1 = {.x = c1, .y = d1};
                VEC2D c2d2 = {.x = c2, .y = d2};

                b.n_plays += 2;

                b.all_plays[b.n_plays] = c2d2;
                b.all_plays[b.n_plays - 1] = c1d1;

                setStautus(&b, c1d1, BLACK);
                setStautus(&b, c2d2, BLACK);


            }else
            {
                c1 -= 97;
                d1 = 8 - d1;

                VEC2D c1d1 = {.x = c1, .y = d1};

                b.n_plays++;
                b.all_plays[b.n_plays]  = c1d1;

                setStautus(&b, c1d1, BLACK);

            }
        }
    }

    setStautus(&b, b.all_plays[b.n_plays], WHITE);
    b.last_play = b.all_plays[b.n_plays];
    fclose(fp);
    return b;
}


int saveBoard(const BOARD b, const char* path)
{
    FILE *fp;
    int i = 0;

    char buffer[BUFFER_SIZE] = {0};
    const char symbols[] = {'.', '#', '*', '1', '2'};

    fp = fopen(path, "w");
    VEC2D temp_pos = {.x = 0, .y = 0};
   
    for(int y = 0; y < BOARD_SIZE; y++)
    {
        for(int x = 0; x <= BOARD_SIZE; x++)
        {
            if(x < BOARD_SIZE){
                VEC2D pos = {.x = x, .y = y};
                buffer[i] = symbols[getStatus(b, pos)];
            }else
                buffer[i] = '\n';
            
            i++;
        }
    }
    
    if(fputs(buffer, fp) < 0)
        perror("Failed to save file: ");

    fputc('\n', fp);
    generateMovsString(b, fp);
    fclose(fp);
    return 0;
}
