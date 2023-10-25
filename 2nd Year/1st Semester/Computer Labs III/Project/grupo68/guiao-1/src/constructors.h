
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>


struct node {
    struct node* left;
    struct node* right;
    int val;
};

typedef struct aBin* A_BIN;
typedef struct returnArray* RETURN_ARRAY;

int getInvalid();

void printMemory();

int verifyInt(char* str);

char* verifyStr(char* str);

struct tm verifyTime (char* str);

RETURN_ARRAY verifyArr(char* str);

int verifyArrInt(char* str, int * arr, int validSize);

int contains(struct node *node, int key);

void saveToFile(char* fileName, char* write[], int iWrite, int firstTime, char* firstLine, int debugFile);

void inorder(struct node *root);

void freeAbin(struct node* aBin);

struct node *insert(struct node *node, int key);

struct node *newNode(int item);