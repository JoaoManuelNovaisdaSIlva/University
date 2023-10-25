

def cesar(operacao, key, mensagem):
    novaMsg = ""
    desvio = ord(key) - ord('A')
    for letra in mensagem:
        letraNova = ord(letra)
        if operacao == "enc":
            letraNova += desvio
            if letraNova > 90:
                letraNova -= 26
        elif operacao == "dec":
            letraNova -= desvio
            if letraNova < 65:
                letraNova += 26

        novaMsg += chr(letraNova)
    return novaMsg

def cesarCrackBruteForce(mensagem):
    lista = []
    for i in range(65,90): # valores de ascii de A até Z
        lista.append(cesar("dec", chr(i), mensagem))
    return lista
