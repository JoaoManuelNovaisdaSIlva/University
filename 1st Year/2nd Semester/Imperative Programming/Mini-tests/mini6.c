#include <stdlib.h>
#include <stdio.h>

typedef struct slist
{
    int valor;
    struct slist *prox;
}LInt;

int calcula(LInt l) {
    int tamanho = 73, soma = 0, i, contador = 0;
    LInt temp = l;
    for(i = 0; i < tamanho; i++)
    {
        if (contador % 2 != 0)
        {
            soma += temp->valor;
        }
        contador++;
        temp = temp->prox;
    }
    return soma;
}



    int calcula(LInt l) {
    int i=0, counter=0, soma=0;
    LInt temp = l;
    
    while(i < 73)
    {
        if(counter % 2 != 0)
        {
             soma += temp->valor;
             i++;
        }
        counter++;
        temp = temp->prox;
    }
    return soma;
}