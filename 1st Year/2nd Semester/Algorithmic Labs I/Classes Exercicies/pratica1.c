#include <stdio.h>


int main ()
{
	int array [5];

	for (int i = 0; i < 5; i ++)
	{
		scanf ("%d",&array [i]);
	}
	for (int j = 4; j > -1; j --)
	{
		printf("%d",array [j]);
	}
}