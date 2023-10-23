import re
import json

def parserFilter():
    linhas_filtradas = {}
    acc = 0
    ex = r'(?P<Pasta>\d+)::(?P<Data>(?P<Ano>\d+)-(?P<Mes>\d+)-(?P<Dia>\d+))::(?P<Nome>[\w\s]+)::(?P<Pai>[\w\s]+)::(?P<Mae>[\w\s]+)::(?P<Observacoes>[^:]*)::'
    pattern = re.compile(ex)
    with open("processos.txt", 'r') as file:
        lines = file.readlines()

    for line in lines:
        if pattern.match(line):
            dic = {}
            dic['Pasta'] = pattern.match(line).group('Pasta')
            dic['Ano'] = pattern.match(line).group('Ano')
            dic['Mes'] = pattern.match(line).group('Mes')
            dic['Dia'] = pattern.match(line).group('Dia')
            dic['Nome'] = pattern.match(line).group('Nome')
            dic['Pai'] = pattern.match(line).group('Pai')
            dic['Mae'] = pattern.match(line).group('Mae')
            dic['Observacoes'] = pattern.match(line).group('Observacoes')

        linhas_filtradas[acc] = dic
        acc += 1

    return linhas_filtradas

def frequencia(linhas_filtradas):
    dic = {}

    for key in linhas_filtradas:
        ano = linhas_filtradas[key]['Ano']
        if ano in dic:
            dic[ano] += 1
        else:
            dic[ano] = 1

    return dic


def findOldCentury(linhas_filtradas):
    temp = int(linhas_filtradas[0]['Ano'])
    for key in linhas_filtradas:
        ano = int(linhas_filtradas[key]['Ano'])
        if ano <= temp:
            temp = ano

    return temp // 100 + 1

def findNewCentury(linhas_filtradas):
    temp = int(linhas_filtradas[0]['Ano'])
    for key in linhas_filtradas:
        ano = int(linhas_filtradas[key]['Ano'])
        if ano >= temp:
            temp = ano

    return temp // 100 + 1


def frequenciaDeNomes(linhas_filtradas):
    dic = {}
    oldCentury = findOldCentury(linhas_filtradas)
    newCentury = findNewCentury(linhas_filtradas)

    for i in range(oldCentury, newCentury+1):
        dic[i] = {'Primeiro Nome' : {},
                  'Ultimo Nome' : {}}

    for key in linhas_filtradas:
        nomes = linhas_filtradas[key]['Nome'].split(" ")
        seculo = int(linhas_filtradas[key]['Ano']) // 100 + 1
        if nomes[0] in dic[seculo]['Primeiro Nome']:
            dic[seculo]['Primeiro Nome'][nomes[0]] += 1
        else:
            dic[seculo]['Primeiro Nome'][nomes[0]] = 1

        if nomes[-1] in dic[seculo]['Ultimo Nome']:
            dic[seculo]['Ultimo Nome'][nomes[-1]] += 1
        else:
            dic[seculo]['Ultimo Nome'][nomes[-1]] = 1

    last5PrimeiroNome = {}
    last5UltimosNome = {}
    for key in dic:
        tempPrimeiroNomeOrdenado = dict(sorted(dic[key]['Primeiro Nome'].items(), key=lambda item: item[1]))
        tempSegundoNomeOrdenado = dict(sorted(dic[key]['Ultimo Nome'].items(), key=lambda item: item[1]))
        last5PrimeiroNome[key] = list(tempPrimeiroNomeOrdenado.items())[-5:]
        last5UltimosNome[key] = list(tempSegundoNomeOrdenado.items())[-5:]

    return last5PrimeiroNome, last5UltimosNome


def frequenciaDeRelacoes(data):
    relacoes = dict()
    exp = re.compile(r"[a-zA-Z ]*,([a-zA-Z\s]*)\.[ ]*Proc\.\d+\.")

    for key in data:
        obs = data[key]["Observacoes"]
        matches = exp.finditer(obs)

        for match in matches:
            if match.group(1) not in relacoes:
                relacoes[match.group(1)] = 0
            relacoes[match.group(1)] += 1

    return relacoes


def converJson(linhas_filtradas):
    vintePrimeiros = {}
    for i in range(1, 21):
        vintePrimeiros[i] = linhas_filtradas[i]

    with open("output.json", "w") as outfile:
        json.dump(vintePrimeiros, outfile)


r = parserFilter()
#print(r)
r1 = frequencia(r)
print(r1)

r2 = findOldCentury(r)
print(r2)
r3 = findNewCentury(r)
print(r3)

r4 = frequenciaDeNomes(r)
print(r4)

r5 = frequenciaDeRelacoes(r)
print(r5)

converJson(r)

