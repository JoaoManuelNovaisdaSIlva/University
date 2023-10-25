#include <stdio.h>
#include <stdlib.h>

// Parte A

// Exercicio 1

char *strcpy(char *dest, char source[])
{
    int i = 0;
    while(source[i] != '\0')
    {
        dest[i] = source[i];
        i++;
    }
    dest[i] = '\0';
    return dest;
}

// Exercicio 2

void strnoV(char s[])
{
    for(int i = 0; s[i] != '\0'; i++)
    {
        if (s[i] == 'a' || s[i] == 'e' || s[i] == 'i' 
        || s[i] == 'o' || s[i] == 'u' || s[i] == 'A' 
        || s[i] == 'E' || s[i] == 'i' || s[i] == 'O'
        || s[i] == 'U')

        s[i] = s[i+1];
        printf("%c",s[i]);
    }
}

// Exercicio 3

typedef struct nodo
{
    int valor;
    struct nodo *esq, *dir;
}*ABin;


int dempAbin(ABin a, int v[], int N)
{
    if(a == NULL) return 0;
    if(N == 0) return 0;
    
    int e = 0, d = 0;

    e = dumpAbin(a->esq,v,N);
    if(e == N) return N;
    v[e] = a->valor;

    d = dumpAbin(a->dir,v,N-e);
    return e + d;
}

// Exercicio 4

int lookupAB (ABin a, int x)
{
    while(a != NULL && x != a->valor)
    {
        if(x < a->valor)
        {
            a->esq;
        }
        else if (x > a->valor)
        {
            a->dir;
        }
    return 1;
    }
}

// Parte B

typedef struct listaP
{
    char *pal;
    int cont;
    struct listaP *prox;
}Nodo, *Hist;


// Exercicio 1


// Exercicio 2

char *remMiasFreq(Hist *h, int *count)
{
    char palTemp = (*h)->pal;
    int contTemp = (*h)->cont;
    while(h != NULL)
    {
        if((*h)->cont > contTemp)
        {
            palTemp = (*h)->pal;
            contTemp = (*h)->cont;
            (*h)->prox;
        }
        else (*h)->prox;
    }
    *count = contTemp;
    return palTemp;
}

// Exercicio 3



int main()
{
    char s[10] = "pi e merda";
}