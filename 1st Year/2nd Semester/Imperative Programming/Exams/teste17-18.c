#include <stdio.h>
#include <stdlib.h>
#include <string.h>

// DECORAR A FUNÇÃO APPEND

// Part A

typedef struct slist
{
    int valor;
    struct slist *prox;
} *LInt;

typedef struct nodo
{
    int valor;
    struct nodo *esq, *dir;
} ABin;

// Pergunta 1

int retiraNeg(int v[], int N)
{
    int i = 0, counter = 0;
    while(i<N)
    {
        if(v[i] >= 0)
        {
            counter++;
            i++;
        }
        else if(v[i] < 0)
        {
            for (int a = i; a < N-1; a++)
            {
                v[a] = v[a+1];
            }
            N--;
        }
    }
    for(int i = 0; i < counter; i++)
    {
        printf("%d\n",v[i]);
    }
    printf("counter: %d\n",counter);
}

// Pergunta 2

/**int difConsecutivos_aux(char s[])
{
    int counterOfWords = 1, sizeOfWords = 1;
    for (int i = 0; i < sizeof(s); i++)
    {
        if (s[i] != s[i+1])
        {
            sizeOfWords++;
        }
        else if (s[i] == s[i+1])
        {
            counterOfWords++;
        }
    }
    printf("%d",counterOfWords);
    return counterOfWords;
}

int difConsecutivos1(char s[])
{
    int *arr = malloc(sizeof (int) * difConsecutivos_aux(s));
    int counter = 1, a = 0, max = arr[0];
    for (int i = 0; i < strlen(s); i++)
    {
        if (s[i] != s[i+1])
        {
            counter++;
        }
        else if(s[i] == s[i+1])
        {
            arr[a] = counter;
            counter = 1;
            a++;
        }
    }
    for (int i = 0; i < sizeof(arr)/4; i++)
    {
        printf("lista= %d\n",arr[i]);
        if (arr[i] > max)
        {
            max = arr[i];
        }
    }
    printf("tamanho:%ld\n",sizeof(arr)/4);
    printf("%d\n",max);
}
**/

int difConsecutivos(char s[])
{
    char last = *s;
    int f = 0, max = 0;
    while (*s != '\0')
    {
        s++;
        f++;
        if (last == *s)
        {
            if (f > max)
            {
                max = f;
                f = 0;
            }
        }
    }
    return f;
}

// Pergunta 3

int maximo (LInt l)
{
    int max = 0;
    for(int i = 0; i < sizeof(l)/4; i++)
    {
        if(l->valor > max)
        {
            max = l->valor;
        }
        l->prox;
    }
    printf("%d\n",max);
}

// Pergunta 4

int removeALL (LInt *l, int a)
{
    int counter = 0;
    while(*l)
    {
        if (a == (*l)->valor)
        {
            LInt temp = (*l);
            (*l) = (*l)->prox;
            free(temp);
            counter++;
        }
        else l = &((*l)->prox);
    }
    printf("%d\n",counter);
}

// Pergunta 5

LInt arrayToList (int v[], int N)
{
    LInt l = NULL;
    for(int i = 0; i < N; i++)
    {
        l = append(l,v[i]);
        printf("%d\n",l->valor);
    }
}

LInt append(LInt l, int valor)
{
    LInt f = (LInt)malloc(sizeof(struct slist));
    f->prox = l;
    f->valor = valor;
    return f;
}

// Parte B

// Pergunta 1

/**
int minheapOK (ABin a)
{
    int cond = 0;
    if(a.valor == NULL)
    {
        return 0;
    }
    if (a.esq != NULL && a.esq->valor < a.valor)
    {
        return 1;
    }
    cond = minheapOK(a.esq);
    if(!cond)
    {
        if(a.dir != NULL && a.dir->valor < a.valor)
        {
            return 1;
        }
        cond = minheapOK(a.dir);
    }
    return cond;
}
**/

// Pergunta 2

int maxHeap(ABin a)
{
    int max = a.valor;
    if(a.esq != NULL)
    {
        int x = maxHeap(a->esq);
    }
}

int main()
{
    int a[5] = {1,2,3,4,5};
    arrayToList(a,5);   
}



