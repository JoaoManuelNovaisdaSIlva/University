#include <stdio.h>
#include <stdlib.h>
/**
int sumhtpo (int n)
{
    int r = 0;
    while (n != 1) 
    {
        r += n;
        if (n%2 == 0) n = n/2;
        else n = 1+(3*n);
    }
    return r;
}
**/
int Nperfeito(int n) {
    int i, soma = 0;
    
    for(i = 1; i < n; i++) 
    {
        if (n % i == 0) soma += i;
    }
    
    return n-soma;
}

int sumhtpo (int n){
  int r = 0;
  while (n != 1) {
    printf("%d ->", n);
    if (!Nperfeito(n)) r++;
    if (n%2 == 0) n = n/2;
    else {
        n = 1+(3*n);
    }
  }
  printf("end\n");
  return r;
}

int main()
{
    //int r = Nperfeito(37);
    printf("%d\n",sumhtpo(37));
}