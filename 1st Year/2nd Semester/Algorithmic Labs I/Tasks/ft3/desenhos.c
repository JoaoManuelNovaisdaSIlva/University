#include "desenhos.h"

int triangulo (int numero_linhas)
{
	int linha,espaco_coluna,letra_coluna;
	for (linha = 1; linha <= numero_linhas; linha ++)
	{
		for (espaco_coluna = numero_linhas - linha; espaco_coluna >= 1; espaco_coluna --)
		{
			printf(" ");
		}
		for (letra_coluna = 1; letra_coluna <= linha;letra_coluna ++)
		{			
			printf("%c",'A' + letra_coluna - 1);
		}
		for (letra_coluna = linha*2 -2;letra_coluna >= linha;letra_coluna --)
		{
			printf("%c",'A' + letra_coluna - linha);
		}
		printf("\n");
	}
	return 0;
}

int losango (int numero_linhas)
{
	int linha,espaco_coluna,letra_coluna;
	for (linha = 1; linha <= numero_linhas; linha ++)
	{
		for (espaco_coluna = numero_linhas - linha; espaco_coluna >= 1; espaco_coluna --)
		{
			printf(" ");
		}
		for (letra_coluna = 1; letra_coluna <= linha;letra_coluna ++)
		{			
			printf("%c",'A' + letra_coluna - 1);
		}
		for (letra_coluna = linha*2 -2;letra_coluna >= linha;letra_coluna --)
		{
			printf("%c",'A' + letra_coluna - linha);
		}
		printf("\n");
	}
	for (linha = numero_linhas - 1; linha >= 1; linha --)
	{
		for (espaco_coluna = numero_linhas - linha; espaco_coluna >= 1; espaco_coluna --)
		{
			printf(" ");
		}
		for (letra_coluna = 1; letra_coluna <= linha;letra_coluna ++)
		{			
			printf("%c",'A' + letra_coluna - 1);
		}
		for (letra_coluna = linha*2 -2;letra_coluna >= linha;letra_coluna --)
		{
			printf("%c",'A' + letra_coluna - linha);
		}
		printf("\n");
	}
	return 0;
}

int hexagono (int tamanho_lado)
{
	int j = 0, k, tamanho_linha;

	for (int linha = 1,k = tamanho_lado, tamanho_linha = 2 * tamanho_lado - 1; linha < tamanho_lado; linha ++, k --, tamanho_linha ++)
    {
    	for(j = 0; j < 3 * tamanho_lado; j ++)
        {
        	if (j == k || j == tamanho_linha || (linha == 1 && j >= k && j <= tamanho_linha))
            {
                printf("#");       
            }
            else
            {
                printf(" ");
            }
        }
        printf("\n");       
    }
              
    for (int linha = 0, k = 1, tamanho_linha = 3 * tamanho_lado - 2; linha < tamanho_lado; linha ++, k ++, tamanho_linha --)
    {
        for (j = 0; j < 3 * tamanho_lado; j ++)
        {
            if (j == k || j == tamanho_linha)
            {
            	printf("#");
            }
           	else
            {
               	if (linha == tamanho_lado - 1 && (j >= k && j <= tamanho_linha))
              	{
               		printf("#");
                }
               	else 
               	{
               		printf(" ");
               	}
            }
        }
        printf("\n");
    }
    return 0; 
}