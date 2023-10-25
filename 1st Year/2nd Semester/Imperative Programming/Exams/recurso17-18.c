#include <stdio.h>
#include <stdlib.h>
#include <string.h>

// Parte A

typedef struct pisicao
{
    int x,y;
}Posicao;

typedef struct slist
{
    int valor;
    struct slist *prox;
}*LInt;

typedef struct nodo
{
    int valor;
    struct nodo *esq,*dir;
}*ABin;

// Exercicio 1  VOLTAR A RESOLVER
/**
char *strstr(char s1[], char s2[])
{
    int sizeS2 = sizeof(s2), sizeS1 = sizeof(s1), ocorrencia = 0;

    for(int i = 0; i < sizeS1; i++)
    {
        for(int a = 0; a >= sizeS2; a++)
        {
            if(s1[i] == s2[a])
            {
                ocorrencia = i;
                i++;
            }
            else a = 0;
            if(a == sizeS2)
            {
                ocorrencia =- sizeS2;
            }
        }
    }
    for(int i = 0; i<3;i++)
    {
        printf("%d",s1[i]);
    }
    printf("%d",sizeS1);
}
**/
// Exercicio 2

void truncW (char t[], int n) {
    int i = 0, l = 0;
    while(t[i]) 
    {
        if(t[i] != ' ') 
        {
            l++;
        }
        else l = 0;

        if(l > n) 
        {
            for(int j = i; t[j]; j++) 
            {
                t[j] = t[j+1];
            }
        }
        else i++;
    }
    for (int i = 0; t[i] != '\0'; i++)
    {
        printf("%c",t[i]);
    }
}

// Exercicio 3

int maisCentral(Posicao pos[], int N)
{
    int tempx = pos[0].x, tempy = pos[0].y, p = 0;
    for(int i = 0; i < N; i++)
    {
        if(pos[i].x < 0)
        {
            pos[i].x += pos[i].x*(-2);
        }
        if(pos[i].y < 0)
        {
            pos[i].y += pos[i].y*(-2);
        }
    }
    for(int i = 0; i < N; i++)
    {
        if(pos[i].x <= tempx && pos[i].y <= tempy)
        {
            tempx = pos[i].x;
            tempy = pos[i].y;
            p = i;
        }
        else if (pos[i].x < tempx && pos[i].y <= tempy || pos[i].y < tempy && pos[i].x <= tempx)
        {
            tempx = pos[i].x;
            tempy = pos[i].y;
            p = i;
        }
    }
    printf("%d\n",p);
}

// Exercicio 4

LInt somasAcL(LInt l)
{
    int temp = 0;
    LInt new = malloc(sizeof(struct slist));
    new->valor = l->valor;
    temp = new->valor;
    new = new->prox;
    while(l != NULL)
    {
        new->valor = temp;
        l = l->prox;
        new->valor += l->valor;
        temp = new->valor;
        new->prox;
    }
}

// Exercicio 5



// Parte B

typedef struct celula
{
    char *palavra;
    int comp;
    struct celula *prox;
}*Palavras;

// Exercicio 1

int daPalavra (char *s, int *e) 
{
	if (s[0] == '\0') return 0;

	for (*e = 0; isspace(s[*e]); (*e)++);

	if (s[*e] == '\0') return 0;

	int size = 0;
	for (size = *e; !isspace(s[size]) && s[size] != '\0';size++);

    return size-*e;
}
// Exercicio 2

Palavras words(char *texto)
{
    int e = 0, deslocamento = 0;
    Palavras new = malloc(sizeof(struct celula));
    new->palavra = texto + e;
    new->comp = daPalavra(texto,&e);
    new->prox = NULL;
    deslocamento = new->comp + e;

    if(new->comp == 0)
    {
        free(new);
        return NULL;
    }
    Palavras ant = new;
    while(texto[deslocamento] != '\0')
    {
        Palavras temp = malloc(sizeof(struct celula));
        temp->comp = daPalavra(texto + deslocamento,&e);
        temp->prox = NULL;
        temp->palavra = texto + deslocamento + e;
        deslocamento += temp->comp + e;

        if(temp->comp == 0)
        {
            free(temp);
            break;
        }
        ant->prox = temp;
        ant = temp;
    }
    return ant;
    
}

int main()
{
    LInt l = malloc(sizeof(struct slist));
    for(int i = 1; i < 5; i++)
    {
        l->valor = i;
        l = l->prox;
    }
    somasAcL(l);
}
