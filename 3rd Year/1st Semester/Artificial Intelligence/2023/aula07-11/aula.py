import numpy
import numpy.random as npr

def objetivo(x):
    return x[0] ** 2.0

def hillClimber(objetivo, limites, num, tamanho):
    ponto_atual = limites[:, 0] + npr.rand(len(limites)) * (limites[:, 1] - limites[:, 0])

    ponto_atual_avaliacao = objetivo(ponto_atual)
    resultados = list()
    resultados.append(ponto_atual)

    for i in range(num_iteracoes):
        candidato = ponto_atual + npr.randn(len(limites)) * tamanho_passo

        candidato_avaliacao = objetivo(candidato)

        if candidato_avaliacao <= ponto_atual_avaliacao:
            ponto_atual, ponto_atual_avaliacao = candidato, candidato_avaliacao

            resultados.append(ponto_atual)
            print("Iteracao: >%d, f(%s) = %.5f" % (i, ponto_atual, ponto_atual_avaliacao))
    return [ponto_atual, ponto_atual_avaliacao, resultados]


npr.seed(1)
limites = np.array([[-5.0,5.0]])
num_iteracoes = 500
tamanho_passo = 0.1

melhor, avaliacao, resultados = hillClimber(objetivo, limites, num_iteracoes, tamanho_passo)
print("Fim!")