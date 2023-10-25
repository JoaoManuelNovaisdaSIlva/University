#include <stdio.h>
#include <stdlib.h>

// Pergunta 1

int sumhtpo_aux(int n)
{
    int r = 0, counter = 0;
    while(n != 1)
    {
        r += n;
        counter++;
        if(n%2 == 0) n = n/2; else n = 1+(3*n);
    }
    return counter;
}

int sumhtpo(int n)
{
    int r = 0, i = 0;
    int arr [sumhtpo_aux(n)];
    if(sumhtpo_aux(n) > 100)
    {
        while(n != 1)
        {
            r += n;
            arr[i] = n;
            if(n%2 == 0) n = n/2; else n = 1+(3*n);
            i++;
        }
        for(int i = 0; i < sumhtpo_aux(n); i++)
        {
            int temp;
            if(arr[0] > arr[i])
            {
                temp = arr[0];
                arr[0] = arr[i];
                arr[i] = temp;
            }
        }
        return arr[99];
    }
    else return -1;
    printf("%d",arr[99]);
}

// Exercicio 2

int moda(int v[], int N, int *m)
{
    int temp;
    int arr[N];
    for(int a = 0; a < N; a++)
    {
        arr[a] = 0;
    }
    for(int i = 0; i < N; i++)
    {
        for(int j = 0; j < N; j++)
        {
            if(v[i] == v[j])
            {
                arr[i]++;
            }
        }
    }
}

// Exercicio 3

typedef struct slist
{
    int valor;
    struct slist *prox;
}*LInt;

int procura(LInt *l, int x)
{
    LInt temp = l;
    while(temp->valor != NULL)
    {
        if(x == temp->valor)
        {
            
            return 1;
        }
    }
}

int main()
{
    int arr[10];
    for(int i = 0; i < 10; i++)
    {
        printf("%d",arr[i]);
    }
   //sumhtpo(1391);
}