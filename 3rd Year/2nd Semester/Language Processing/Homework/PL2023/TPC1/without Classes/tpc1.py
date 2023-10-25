from matplotlib import pyplot as plt
from prettytable import PrettyTable


def main():
    utentes = parser("myheart.csv")
    opcao = 0

    while (opcao != 9):
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
            dist = doecaPorEscaloesEtarios(utentes)
            desenhaTableasEscaloesEtarios(dist)

            grafico = input("Pretende obter o gráfico? (Y/N)")
            if grafico == "Y":
                count = list(dist.values())
                plt.bar(range(len(dist)), count, width=0.5, align='center')
                plt.xticks(range(len(dist)), [f"[{inf},{sup}]" for inf, sup in dist.keys()], fontsize=7)
                plt.yticks(fontsize=7)
                plt.xlabel("Intervalos Etários")
                plt.ylabel("Número de doentes")
                plt.show()
        elif opcao == 3:
            print("DISTRIBUIÇÃO DA DOENÇA POR NÍVEIS DE COLESTROL")
            dist = doecaPorNiveisColestrol(utentes)
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

def parser(path):
    utentes = {}  # dicionário que vai ter como keys o nome da propriedade e value um dicionário com keys um id que é um acumulador e value o valor da propriedade
    linha = 2
    id = 0
    idade = {}
    sexo = {}
    tensao = {}
    colestrol = {}
    batimento = {}
    temDoenca = {}

    with open(path, 'r') as file:
        next(file)

        for line in file:
            colunas = line.strip().split(',')
            if len(colunas) != 6: return print(f"A linha: {linha} não tem colunas suficientes!")
            else:
                idade[id] = colunas[0]
                sexo[id] = colunas[1]
                tensao[id] = colunas[2]
                colestrol[id] = colunas[3]
                batimento[id] = colunas[4]
                temDoenca[id] = colunas[5]
                id += 1
                linha += 1

    utentes["IDADE"] = idade
    utentes["SEXO"] = sexo
    utentes["TENSAO"] = tensao
    utentes["COLESTROL"] = colestrol
    utentes["BATIMENTO"] = batimento
    utentes["TEMDOENCA"] = temDoenca

    return utentes

def doencaPorSexo(utentes):
    dist = {"Masculino" : 0,
            "Feminino" : 0}

    for id,valor in utentes["SEXO"].items():
        if utentes["TEMDOENCA"][id] == "1":
            if valor == "M":
                dist["Masculino"] += 1
            elif valor == "F":
                dist["Feminino"] += 1
    return dist

def doecaPorEscaloesEtarios(utentes):
    dist = {}
    for i in range(30, 90, 5):
        dist[(i, i+4)] = 0

    for id,valor in utentes["IDADE"].items():
        if utentes["TEMDOENCA"][id] == "1":
            for inf,sup in dist:
                if inf <= int(valor) <= sup:
                    dist[(inf,sup)] += 1

    return dist


def doecaPorNiveisColestrol(utentes):
    dist = {}
    inf,sup = menorEmaiorColestrol(utentes)
    for i in range(inf, sup, 10):
        dist[(i, i+9)] = 0

    for id,valor in utentes["COLESTROL"].items():
        if utentes["TEMDOENCA"][id] == "1":
            for inf1,sup1 in dist:
                if inf1 <= int(valor) <= sup1:
                    dist[(inf1,sup1)] += 1

    return dist


def menorEmaiorColestrol(utentes):
    tempMenor = int(utentes["COLESTROL"][0])
    tempMaior = int(utentes["COLESTROL"][0])

    for id,valor in utentes["COLESTROL"].items():
        if int(valor) <= tempMenor:
            tempMenor = int(valor)
        elif int(valor) >= tempMaior:
            tempMaior = int(valor)

    return tempMenor,tempMaior


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
