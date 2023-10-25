#include <stdio.h>

int main ()
{
	int numero_elementos = 5,tamanho_array,d,array_introduzida[5],array_invertida[5];

	printf("introduza os numeros inteiros da array:\n");
	
	for (tamanho_array = 0;  tamanho_array < numero_elementos; tamanho_array++)
	{
		scanf ("%d",&array_introduzida[tamanho_array]);
	}
	for (tamanho_array = numero_elementos - 1, d = 0; tamanho_array >= 0; tamanho_array--, d++)
	{
		array_invertida[d] = array_introduzida[tamanho_array];
	}

	printf("a array invertida é a seguinte:\n");

	for (tamanho_array = 0; tamanho_array < numero_elementos; tamanho_array++)
	{
		printf("%d\n",array_invertida[tamanho_array]);
	}
	return 0;
}