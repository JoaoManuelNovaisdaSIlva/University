#include<stdio.h>
#include<stdlib.h>

typedef struct lligada{
    int valor;
    struct lligada *prox;
}*LInt;

void imprimeL(LInt l){
    while(l != NULL){
        printf("%d\n", l->valor);
        l = l->prox;
    }
}


void swap(int *xp, int *xt){
    int temp = *xp;
    *xp = *xt;
    *xt = temp;
}

int nesimo(int a[], int N, int i){
    int x, j, min_idx;
 
    for (x = 0; x < N-1; x++){
        min_idx = x;
        for (j = x+1; j < N; j++)
            if (a[j] < a[min_idx]) min_idx = j;
 
        if(min_idx != x) swap(&a[min_idx], &a[x]);
    }
    return a[i];
}

LInt removeMaiores(LInt l, int x){
    LInt temp = l->prox;
    LInt prev = l;
    if(l->valor > x) l = l->prox;
    while(temp){
        if(temp->valor > x){
            prev->prox = temp->prox;
        }
        prev = temp;
        temp = temp->prox;
    }
    return l;
}

int main(){
    LInt head = NULL;
    LInt node1 = NULL;
    LInt node2 = NULL;
    LInt node3 = NULL;
    LInt node4 = NULL;

    head = malloc(sizeof(struct lligada));
    node1 = malloc(sizeof(struct lligada));
    node2 = malloc(sizeof(struct lligada));
    node3 = malloc(sizeof(struct lligada));
    node4 = malloc(sizeof(struct lligada));

    head->valor = 1;
    head->prox = node1;

    node1->valor = 3;
    node1->prox = node2;

    node2->valor = 5;
    node2->prox = node3;

    node3->valor = 10;
    node3->prox = node4;

    node4->valor = 3;
    node4->prox = NULL;

    LInt copia = removeMaiores(head, 4);
    imprimeL(copia);


    return 0;
}