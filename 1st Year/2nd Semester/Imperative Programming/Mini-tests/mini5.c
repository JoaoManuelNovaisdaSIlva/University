#include <stdlib.h>
#include <stdio.h>

typedef struct slist {

  int valor;
  struct slist * prox;
} *LInt;


LInt newLInt (int x, LInt xs) {
  LInt r = malloc (sizeof(struct slist));
  if (r!=NULL) {
    r->valor = x;
    r->prox = xs;
  }
  return r;
}

int printList(LInt *l)
{
    while((*l)->prox)
    {
        printf("%d", (*l)->valor);
        (*l) = (*l)->prox;
    }
}

LInt clone (LInt l) {
  LInt r,a,b;
  r = a = b = NULL;
  while (l) {
    a = newLInt(l->valor,NULL);
     		
    if (b) b->prox = a; b = a; if (!r) r = b;

    l = l->prox;
  }
  return r;
}

int main ()
{
    LInt teste = NULL;
    for (int i = 0; i < 10; i++)
    {
        teste = newLInt(i,teste);
    }
    teste = clone(teste);
     while(teste != NULL){
        printf("%d\n", teste->valor);
        teste = teste->prox;
    }
}
