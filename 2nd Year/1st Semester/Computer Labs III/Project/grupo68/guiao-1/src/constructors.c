#define _XOPEN_SOURCE
#define _DEFAULT_SOURCE
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>
#include <sys/resource.h>
#include <assert.h>
#include <stdbool.h>


#include "./constructors.h"

const int INVALID_FILES = 0;

struct returnArray {
	int size;
	int * array;
};

typedef struct returnArray* RETURN_ARRAY;


int getInvalid()
{
    return INVALID_FILES;
}

int verifyInt(char* str){
	int value = -1;

	sscanf(str, "%d", &value);
    if(value < 0)
    {
        value = -1;
        return value;
    }
	return value;
}

char* verifyStr(char* str)
{
    if(strcmp(str,"")==0) return "ERROR";
    return str;
}

void freeAbin(struct node* aBin)
{

    if(aBin==NULL)
        return;

    freeAbin(aBin->right);
    freeAbin(aBin->left);

    free(aBin);

}

struct tm verifyTime (char* str)
{
	//printf("%s", str);
    time_t rawtime;
    struct tm * c;
	struct tm tempo = {0};
	strptime(str, "%Y-%m-%d %H:%M:%S", &tempo);

    if(strlen(str) != 19) tempo.tm_mday = 0;

	tempo.tm_year = tempo.tm_year+1900;
    tempo.tm_mon = tempo.tm_mon+1;
    

    time ( &rawtime );
    c = localtime ( &rawtime );
    c->tm_year = c->tm_year+1900;

    //printf("%d-%d-%d %d:%d:%d\n", c->tm_year, c->tm_mon, c->tm_mday, c->tm_hour, c->tm_min, c->tm_sec);

    if(tempo.tm_year == 2005 && tempo.tm_mon < 4) tempo.tm_mday = 0;
    if(tempo.tm_year == 2005 && tempo.tm_mon == 4 && tempo.tm_mday < 7) tempo.tm_mday = 0;
    if(tempo.tm_year > c->tm_year || tempo.tm_year < 2005) tempo.tm_mday = 0;
    if(tempo.tm_year == c->tm_year && tempo.tm_mon > c->tm_mon) tempo.tm_mday = 0;
    if(tempo.tm_year == c->tm_year && tempo.tm_mon == c->tm_mon && tempo.tm_mday > c->tm_mday) tempo.tm_mday = 0;
    if(tempo.tm_year == c->tm_year && tempo.tm_mon == c->tm_mon && tempo.tm_mday == c->tm_mday 
        && tempo.tm_hour > c->tm_hour) tempo.tm_mday = 0;
    if(tempo.tm_year == c->tm_year && tempo.tm_mon == c->tm_mon && tempo.tm_mday == c->tm_mday 
        && tempo.tm_hour == c->tm_hour && tempo.tm_min > c->tm_min) tempo.tm_mday = 0;



    return tempo;
	
}

void removeChar(char* s, char c){
 
    int j, n = strlen(s);
    for (int i = j = 0; i < n; i++)
        if (s[i] != c)
            s[j++] = s[i];
 
    s[j] = '\0';
}

int countOccurrences(char *s, char c)
{
    int i,count=0;
     for(i=0;s[i];i++)  
    {
        if(s[i]==c)
        {
          count++;
        }
    }
    return count;      
}

void printMemory()
{
    struct rusage r_usage;
    getrusage(RUSAGE_SELF,&r_usage);
    printf("Memory usage: %ld kilobytes\n",r_usage.ru_maxrss);
}

void saveToFile(char* fileName, char* write[], int iWrite, int firstTime, char* firstLine, int debugFile) {
    if(debugFile==1 && getInvalid()==0)
    {
        for(int i = 0; i<iWrite; i++)
            free(write[i]); //copia
        return;
    }
    FILE *f = fopen(fileName, "a");
    if(firstTime == 1) f = fopen(fileName, "w");

    if (f == NULL)
    {
        printf("Nao foi possivel abrir o ficheiro: %s\n", fileName);
        return;
    }

    if(firstTime == 1) fprintf(f, "%s\n", firstLine);

    for(int i = 0; i<iWrite; i++)
    {
        fprintf(f, "%s", write[i]);
        free(write[i]); //copia
    }
    fclose(f);

    printf("%s alterado com sucesso !\n", fileName);
}



RETURN_ARRAY verifyArr(char* str)
{
    RETURN_ARRAY ret = malloc(sizeof(struct returnArray));
    int i = 0;

    if(strcmp(str,"")==0){i=-100;}
    if(countOccurrences(str, '[')==0){i=-100;}
    if(countOccurrences(str, ']')==0){i=-100;}

    removeChar(str, '[');
    removeChar(str, ']');


    int occ = countOccurrences(str, ',');
    //printf("Num: %d", occ);
    int arr[occ];

    char *r, *tok;

    r = str;
    assert(r != NULL);

    while ((tok = strsep(&r, ",")) != NULL) {
        int ver = verifyInt(tok);
        if(ver != -1)
        {
            arr[i] = ver;
            i++;

        }
    }


    ret->size = i;
    ret->array = (int*) malloc(i*sizeof(int));


    for(int fi = 0; fi<i; fi++){
        ret->array[fi] = arr[fi];
    }


    return ret;

}

int verifyArrInt(char* str, int * arr, int validSize)
{
    int i = 0;
    if(strcmp(str,"")==0){i=-100;}
    if(countOccurrences(str, '[')==0){i=-100;}
    if(countOccurrences(str, ']')==0){i=-100;}

    removeChar(str, '[');
    removeChar(str, ']');


    char *r, *tok;

    r = str;
    assert(r != NULL);

    while ((tok = strsep(&r, ",")) != NULL) {
        int ver = verifyInt(tok);
        if(ver != -1)
        {
            arr[i++] = ver;
        }
    }

    if(i != validSize)
    {
        return -1;
    }

    return 1;


}


struct node *newNode(int item) {
    struct node *temp = (struct node *)malloc(sizeof(struct node));
    temp->val = item;
    temp->left = temp->right = NULL;
    return temp;
}


struct node *insert(struct node *node, int key) {
    // Return a new node if the tree is empty
    if (node == NULL) return newNode(key);

    // Traverse to the right place and insert the node
    if (key < node->val)
        node->left = insert(node->left, key);
    if (key > node->val)
        node->right = insert(node->right, key);

    return node;
}

int contains(struct node *node, int key) {
    if(node == NULL) return 0;
    if(node->val == key) return 1;
    if(key < node->val)
        return contains(node->left, key);
    if(key > node->val)
        return contains(node->right, key);

    return 0;
}

void inorder(struct node *root) {
    if (root != NULL) {
        // Traverse left
        inorder(root->left);

        // Traverse root
        printf("%d -> ", root->val);

        // Traverse right
        inorder(root->right);
    }
}

