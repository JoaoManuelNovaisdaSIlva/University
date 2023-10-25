from Utente import Utente
import matplotlib.pyplot as plt
from prettytable import PrettyTable


def main():
    utentes = parser("myheart.csv")
    opcao = 0

    while(opcao != 9):
        print("\n--- BEM VINDO À RESOLUÇÂO DO TPC1 DE PL2023---")
        print("Foi feito o parser do ficheiro myheart.csv")
        print("Selecione uma das opções: ")
        print("1: Distribuição da doença por sexo ")
        print("2: Distribuição da doença por escalão etária ")
        print("3: Distribuição da doença por níveis de colestrol ")
        print("9: Sair ")
        print("Opção: ")
        opcao = input()
        try:
            opcao = int(opcao)
        except ValueError:
            print("Introduza um valor válido!")

        if opcao == 1:
            print("DISTRIBUIÇÃO DA DOENÇA POR SEXO")
            dist = doencaPorSexo(utentes)
            desenhaTabelasSexo(dist)

            grafico = input("Pretende obter o gráfico? (Y/N)")

            if grafico == "Y":
                count = list(dist.values())
                plt.bar(range(len(dist)), count, width=0.5, align='center')
                plt.xticks(range(len(dist)), ["Masculino", "Feminino"], fontsize=7)
                plt.yticks(fontsize=7)
                plt.xlabel("Sexo")
                plt.ylabel("Número de doentes")
                plt.show()
        elif opcao == 2:
            print("DISTRIBUIÇÃO DA DOENÇA POR ESCALÃO ETÁRIO")
            dist = doencaPorEscaloes(utentes)
            desenhaTableasEscaloesEtarios(dist)

            grafico = input("Pretende obter o gráfico? (Y/N)")
            if grafico == "Y":
                count = list(dist.values())
                plt.bar(range(len(dist)), count, width=0.5, align='center')
                plt.xticks(range(len(dist)), [f"[{inf},{sup}]" for inf,sup in dist.keys()], fontsize=7)
                plt.yticks(fontsize=7)
                plt.xlabel("Intervalos Etários")
                plt.ylabel("Número de doentes")
                plt.show()
        elif opcao == 3:
            print("DISTRIBUIÇÃO DA DOENÇA POR NÍVEIS DE COLESTROL")
            dist = doencaPorColestrol(utentes)
            desenhaTableasColestrol(dist)

            grafico = input("Pretende obter o gráfico? (Y/N)")
            if grafico == "Y":
                count = list(dist.values())
                plt.bar(range(len(dist)), count, width=0.5, align='center')
                plt.xticks(range(len(dist)), [f"[{inf},{sup}]" for inf, sup in dist.keys()], fontsize=7)
                plt.yticks(fontsize=7)
                plt.xlabel("Intervalos de Colestrol")
                plt.ylabel("Número de doentes")
                plt.show()
        elif opcao == 9:
            break;



def parser(filepath):
    utentes = []
    linha = 2
    with open(filepath, "r") as file:
        next(file)  # passar a primeira linha que apenas possúi os nomes das colunas

        for line in file:
            colunas = line.strip().split(',')
            if len(colunas) != 6: return print(f"A linha: {linha} não tem colunas suficientes!")
            else:
                idade = colunas[0]
                sexo = colunas[1]
                tensao = colunas[2]
                colestrol = colunas[3]
                batimento = colunas[4]
                temDoenca = colunas[5]
                utente = Utente(idade, sexo,tensao, colestrol, batimento, temDoenca)
                utentes.append(utente)
                linha += 1

    return utentes


def doencaPorSexo(utentes):
    dist = {"Masculino" : 0,
            "Feminino" : 0}
    for utente in utentes:
        if utente.getTemDoenca() == "1":
            if utente.getSexo() == "M":
                dist["Masculino"] += 1
            elif utente.getSexo() == "F":
                dist["Feminino"] += 1

    return dist


def doencaPorEscaloes(utentes):
    novo, velho = maisNovoEmaisVelho(utentes)
    limiteInf = (novo // 5) * 5
    limiteSup = ((velho // 5) + 1) * 5
    dist = {}
    for i in range(limiteInf, limiteSup, 5):
        dist[(i, i+4)] = 0

    for utente in utentes:
        if utente.getTemDoenca() == "1":
            for inf,sup in dist:
                if inf <= int(utente.getIdade()) <= sup:
                    dist[(inf,sup)] += 1

    return dist


def maisNovoEmaisVelho(utentes):
    tempNovo = int(utentes[0].getIdade())
    tempVelho = int(utentes[0].getIdade())
    for utente in utentes:
        if int(utente.getIdade()) <= tempNovo:
            tempNovo = int(utente.getIdade())
        if int(utente.getIdade()) >= tempVelho:
            tempVelho = int(utente.getIdade())

    return tempNovo, tempVelho

def doencaPorColestrol(utentes):
    menor,maior = menorEmaiorColestrO(utentes)
    limiteInf = (menor // 10) * 10
    limiteSup = ((maior // 10) + 1) * 10
    dist = {}
    for i in range(limiteInf, limiteSup, 10):
        dist[(i, i+9)] = 0

    for utente in utentes:
        if utente.getTemDoenca() == "1":
            for inf,sup in dist:
                if inf <= int(utente.getColestrol()) <= sup:
                    dist[(inf,sup)] += 1
    return dist

def menorEmaiorColestrO(utentes):
    tempInf = int(utentes[0].getColestrol())
    tempSup = int(utentes[0].getColestrol())
    for utente in utentes:
        if int(utente.getColestrol()) <= tempInf:
            tempInf = int(utente.getColestrol())
        if int(utente.getColestrol()) >= tempSup:
            tempSup = int(utente.getColestrol())
    return tempInf,tempSup


def desenhaTabelasSexo(dictionary):
    table = PrettyTable()
    table.field_names = ["Masculino", "Feminino"]
    table.add_row([dictionary["Masculino"], dictionary["Feminino"]])
    print(table)

def desenhaTableasEscaloesEtarios(dictionary):
    table = PrettyTable()
    table.field_names = [f"[{inf},{sup}]" for inf, sup in dictionary.keys()]
    table.add_row([f"{valor}" for valor in dictionary.values()])
    print(table)

def desenhaTableasColestrol(dictionary):
    table = PrettyTable()
    table.field_names = [f"[{inf},{sup}]" for inf, sup in dictionary.keys()]
    table.add_row([f"{valor}" for valor in dictionary.values()])
    print(table)

main()

