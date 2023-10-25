from cesar import cesar


def vigenere(operacao, key, mensagem):
    temp = key
    i = 0
    resultado = ""
    for letra in mensagem:
        if i == len(key):
            i = 0
        resultado += cesar(operacao, temp[i], letra)
        i += 1

    return resultado

# FALTA VER NÃO SÓ A LETRA MAIS COMUM POR POSIÇÃO E TAMBÉM ASSUMIR QUE ESSAS PODEM SER 'O' OU 'E'
def vigenereCrack(mensagem, size):
    msgSplit = []
    for i in range(0, len(mensagem), size):
        msgSplit.append(mensagem[i:i + size])

    listOcurrencias = letraMaisComun(msgSplit)
    letrasPort = ['a', 'e', 'o']

    listaOcurrenciasOrdenada = {}
    for key in listOcurrencias:
        listaOcurrenciasOrdenada[key] = dict(sorted(listOcurrencias[key].items(), key=lambda item: item[1]))

    letras = []
    for key in listaOcurrenciasOrdenada:
        letras.append(list(listaOcurrenciasOrdenada[key].keys())[-1:])

    a = letras[1][0]
    msgDecrypt = ""
    for group in msgSplit:
        j = 0
        for letra in group:
            msgDecrypt += cesar("dec", letras[j][0], letra)
            j += 1

    return msgDecrypt

def letraMaisComun(msgList):
    listaOcurrencias = {1: {},
                        2: {},
                        3: {},
                        4: {},
                        5: {}}
    for group in msgList:
        ocurrencias = {}
        i = 1
        for letra in group:
            if letra in ocurrencias:
                ocurrencias[letra] += 1
            else:
                ocurrencias[letra] = 1

        for key, valor in ocurrencias.items():
            if key in listaOcurrencias[i].keys():
                listaOcurrencias[i][key] += valor
            else:
                listaOcurrencias[i][key] = valor
            i += 1

    return listaOcurrencias


texto = "TJIXATJMUGURZUSLRAYWGRTGRHJYASWRWIWWVVZOEGZGWTCCYWMRVGDHIUGFXJVABVRLKOGKMYBTMMMO WFAVOLJIXOFRQTRTRTKAWRBGDKFJGBTVUVSKZOUGXXCKFKRAKGYFZIOWFASOBJLUENVXXCFVBOOTWWXQ TYCSOGRMKBMIMMSGKMXSFFBGSWZNOQTIISBHMWXSBEWWIXKITHHJCHZBDIXOFTMYGXDLUGTSQUUKVOUS WFBXCBRVUOLEIBSZRKUSLXZGBWVAWIXWQFSKRUIOEVAKRXRTKLTELXCXUMZFTAITCTWISOWRABWMFZOO LHCKHBMMXOFHCKSNTITHHFXKWMFQRILKZKZNJQZOGFIWIXDVKDMLVUSFRZZSHSMJSVVZGAVVAYSMLLUC JLMGANJIGBMZOAOVRVZOJLMUIMIWBOEFZSOBJIRHHJMGZXMITHT"
print(vigenereCrack(texto, 5))
