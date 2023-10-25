#include <stdio.h>
#include <stdlib.h>

/** 
 * Travecias de arvores binárias:
 * 
 *                  23
 *      15                      34
 *  6        18            29        45
 *        16                  31
 *
 * 
 * INORDER : 6 15 16 18 23 29 31 34 45

void inorder(ABin a)
{
    if(a)
    {
        inorder(a->esq);
        printf("%d", a->valor);
        inorder(a->dir);
    }
}

 * POSORDER : 6 16 18 15 31 29 45 34 23

void posorder(ABin a)
{
    if(a)
    {
        posorder(a->esq);
        posorder(a->dir);
        printf("%d", a->valor);
    }
}

* PREORDER : 23 15 6 18 16 34 29 31 45

void preorder(ABin a)
{
    if(a)
    {
        printf("%d", a->valor);
        preorder(a->esq);
        preorder(a->dir);
    }
}

**/


// Exercicio 1

int paresImpares(int v[], int N)
{
    int temp, a = 0;
    for (int i = 0; i < N; i++)
    {
        if(v[i] % 2 == 0)
        {
            temp = v[a];
            v[a] = v[i];
            v[i] = temp;
            a++;
        }
    }
}

// Exercicio 2

typedef struct slist
{
    int valor;
    struct slist *prox;
} *LInt;


void merge(LInt *r, LInt a, LInt b)
{
    (*r) = a;
    (*r)->prox;
    while(!b->prox)
    {
        (*r)->valor = b->valor;
        b->prox;
        (*r)->prox;
    }
}

// Exercicio 3

void latino(int N, int m[N][N])
{
    int num = 0, b = 1;
    for(int i = 0; i < N; i++)
    {
        for(int j = 0; j < N; j++)
        {
            if (num < N && j != 0)
            {
                m[i][j] = num + 1;
                num++;
            }
            else if(j == 0)
            {
                num = b;
                m[i][j] = num;
                b++;
            }
            else if(num >= N)
            {
                num = 1;
                m[i][j] = 1;  
            }
        }
    }
}

// Exercicio 4

typedef struct nodo
{
    int valor;
    struct nodo *pai, *esq, *dir;
}*ABin;

ABin next(ABin a)
{
    a->dir;
    if(a->esq == NULL) return NULL;
    else next_aux(a->esq);
}

ABin next_aux(ABin a)
{
    while(a->esq != NULL)
    {
        next(a->esq);
    }
    return a;
}

// Exercicio 5

void rodaEsquerda(ABin *a)
{
    ABin b = (*a)->dir;
    (*a)->dir = b->esq;
    b->esq = (*a);
    *a = b;
}

void rodaDireita(ABin *a)
{
    ABin b = (*a)->esq;
    (*a)->esq = b->dir;
    b->dir = *a;
    *a = b;
}


typedef struct palavras
{
    char *palavras;
    int nOcorr;
    struct palavras *esq, *dir;
}*Palavras;

// NAO FAÇO A MINIMA SE FUNCIONA, FALTA CASO pal NAO PERTENCE A p
int acrescentaPal(Palavras *p, char *pal)
{
    if(*p)
    {
        if((*p)->palavras != (*pal))
        {
            acrescentaPal(&((*p)->esq), pal);
            acrescentaPal(&((*p)->dir), pal);
        }
        else (*p)->nOcorr++;  
    }
    rodaDireita(p);
    if((*p)->nOcorr > (*p)->dir->nOcorr)
        return p;
    else rodaEsquerda(p);
}


int main()
{
    int m[5][5];
    int a = 5;
    latino(a,m);
}