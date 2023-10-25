#include <stdio.h>
#include <stdlib.h>


int func(int x) {
  int r = 0;
  while (x > 0) {
    r += 2;
    x = x - r;
  }
  return r;
}

int main1()
{
    int x,y;
    for(y=0;y<8;y++)
    {
        for(x=0;x<8;x++)
        {
            if(x + y < 8) putchar('#');
            else putchar('.');
        }
        putchar('\n');
    }
}



int main(int argc, char const *argv[]) {
  for(int i = 0; i < 100; i++){
    printf("Value: %d | Index: %d\n", func(i), i);
  }
  return 0;
}