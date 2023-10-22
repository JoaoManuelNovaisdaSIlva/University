//calcula a media

int main ()
{
	int soma = 0, k = 0, y[n];

	for (int i = 0; i < n; i ++)
	{
		soma = soma + x[i];
	}
	float media = soma / n;

	for (int j = 0; j < n; j ++)
	{
		if (x[j] > media)
		{
			y[k] =x[j];
		}
		k ++;
	}
}