#include <stdio.h>

void soma_elemento (int *arr, int dim, int idx)
{	
	int valor = arr [idx];
	printf("\n\n\n\n\n\n");


	for (int elemento = 0; elemento < dim; elemento ++)
	{
		arr [elemento] += valor;
	}
	for (int i = 0; i < dim; i ++)
	{
		printf("%d\n",arr[i]);
	}
}

void roda_esq (int *arr,int dim, int shifter)
{
	printf("\n\n\n\n\n\n");

	for(int x = 0; x < shifter; x ++)
	{
		int last = arr[0];

		for ( int i = 1; i < dim; i ++)
		{
			arr[i-1] = arr[i];
		}
		arr[dim-1] = last;
	}

	for (int i = 0; i < dim; i ++)
	{
		printf("%d\n",arr[i]);
	}
}


int remove_menores(int *arr, int dim, int valor)
{
	int menores[dim];
	int maiores[dim];

	printf("\n\n\n\n\n\n");


	int tamanho_menores = 0, tamanho_maiores = 0;

	for ( int i = 0; i < dim; i ++)
	{
		if (arr[i] < valor)
		{
			menores[tamanho_menores] = arr[i];
			tamanho_menores ++;
		}
		else
		{
			maiores[tamanho_maiores] = arr[i];
			tamanho_maiores++;
		}
	}

	for ( int i = 0; i < tamanho_maiores; i ++)
	{
		arr[i] = maiores[i];
	}
	for ( int i = 0; i < tamanho_menores; i ++)
	{
		arr[i+tamanho_maiores] = menores[i];
	}

	for ( int i = 0; i < dim; i ++)
	{
		printf("%d\n",arr[i]);
	}
} 

int main ()
{
	int n,tamanho, valor;

	printf("selecione a funcao:\n 1) soma_elemento\n 2) roda_esq\n 3) remove_menores\n");
	scanf("%d",&n);

	printf("selecione o tamanho:\n");
	scanf ("%d",&tamanho);
	int arra[tamanho];

	printf("selecione o valor:\n");
	scanf ("%d",&valor);

	printf("preencha a lista:\n");

	for (int i = 0;i < tamanho; i++)
	{
		scanf ("%d",&arra[i]);
	}

	if (n == 1)
	{
		soma_elemento (arra, tamanho, valor);
	}
	else if (n == 2)
	{
		roda_esq (arra, tamanho, valor);
	}
	else if (n == 3)
	{
		remove_menores(arra, tamanho,  valor);
	}
}