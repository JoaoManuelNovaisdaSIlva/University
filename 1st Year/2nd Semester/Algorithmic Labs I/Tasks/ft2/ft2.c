#include <stdlib.h>
#include <stdio.h>
#include <time.h>

char *listabem [3] = {"Otimo","Belo trabalho","Continua assim"};
char *listamal [3] = {"Errado,tenta novamente","Nao desista","Nao tente mais uma vez"};

int geraquestao (int *x,int *y,int dificuldade)
{
	srand(time(NULL));
	*x = rand () %10 * dificuldade + 1;
	*y = rand () %10 * dificuldade + 1;
	int tipo_questao;

	printf("escolha o tipo de questao:\n0)soma\n1)subtracao\n2)multiplicacao\n3)divisao\n");
	scanf("%d",&tipo_questao);
	if (tipo_questao == 0) 
	{
		printf("quanto e %d mais %d\n",*x,*y);
	}	
	else if (tipo_questao == 1)
	{
		printf("quanto e %d menos %d\n",*x,*y);
	}
	else if (tipo_questao == 2)
	{
		printf("quanto e %d vezes %d\n",*x,*y);
	}
	else if (tipo_questao == 3)
	{
		printf("quanto e %d a dividir por %d\n",*x,*y);
	}
	else (printf("selecionou uma opcao errada\n") && geraquestao (x,y,dificuldade));

	return tipo_questao;
}

int main ()
{
	int x,y,resposta,tipo_questao;
	int contador = 0;
	int dificuldade = 1;
	tipo_questao = geraquestao (&x,&y,dificuldade);
	
	while (1)
	{
		if (contador == 5)
		{ 
			contador = 0;
			dificuldade ++;
		}
		if (tipo_questao == 0)
		{
			scanf ("%d",&resposta);
			if ( resposta == x + y)
			{
				printf("%s\n",listabem[rand() %3]);
				tipo_questao = geraquestao (&x,&y,dificuldade);
				contador ++;
			}
			else printf ("%s \n%d mais %d\n",listamal[rand() %3],x,y);
		}
		if (tipo_questao == 1)
		{
			scanf ("%d",&resposta);
			if ( resposta == x - y)
			{
				printf("%s\n",listabem[rand() %3]);
				tipo_questao = geraquestao (&x,&y,dificuldade);
				contador ++;
			}
			else printf ("%s \n%d menos %d\n",listamal[rand() %3],x,y);
		}
		if (tipo_questao == 2)
		{
			scanf ("%d",&resposta);
			if ( resposta == x * y)
			{
				printf("%s\n",listabem[rand() %3]);
				tipo_questao = geraquestao (&x,&y,dificuldade);
				contador ++;
			}
			else printf ("%s \n%d vezes %d\n",listamal[rand() %3],x,y);
		}
		if (tipo_questao == 3)
		{
			scanf ("%d",&resposta);
			if (resposta ==  x / y)
			{
				printf("%s\n",listabem[rand() %3]);
				tipo_questao = geraquestao (&x,&y,dificuldade);
				contador ++;
			}
			else printf ("%s \n%d a dividir por %d\n",listamal[rand() %3],x,y);
		}
	}
}