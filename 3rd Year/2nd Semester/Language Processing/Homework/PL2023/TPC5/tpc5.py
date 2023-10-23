import re


def tpc5():
    er_levantar = re.compile(r"^LEVANTAR\b")
    er_pousar = re.compile(r"^POUSAR\b")
    er_moeda = re.compile(r"^MOEDA\b")
    er_numero = re.compile(r"^T=\b")
    er_abortar = re.compile(r"^ABORTAR\b")

    levantado = False

    moedas = re.compile(r"(\d+[ce])")
    moedas_aceites = ["5c", "10c", "20c", "50c", "1e", "2e"]
    saldo = 0.0

    numeros = re.compile(r"T=(\d+)")

    while True:
        user_input = input()

        if er_levantar.match(user_input):
            levantado = True
            print("maq: Introduza moedas. Moedas aceites: 5c, 10c, 20c, 50c, 1e, 2e")
        elif er_moeda.match(user_input):
            if levantado:
                if moedas.findall(user_input):
                    introduzidas = moedas.findall(user_input)
                    for m in introduzidas:
                        if m in moedas_aceites:
                            if m[-1] == "c":
                                saldo += int(m[:-1]) / 100.0
                            elif m[-1] == "e":
                                saldo += int(m[:-1])
                        else:
                            print(f"maq: {m} moeda inválida")
                    print("maq: Saldo = " + str(saldo))
            else:
                print("maq: Tem que levantar o telefone primeiro!")
        elif er_numero.match(user_input):
            if levantado:
                numero = numeros.search(user_input).group(1)
                if re.search(r"(\d{2})", numero).group(1) == "00":
                    if saldo < 1.5:
                        print("maq: Não tem saldo sufeciente para ligar a números no estrangeiro!")
                    else:
                        saldo -= 1.5
                        print("maq: Saldo = " + str(saldo))
                elif len(numero) != 9:
                    print("maq: Introduziu um número inválido!")
                elif re.search(r"(\d{3})", numero).group(1) in ["601", "641"]:
                    print("maq: Esse número está bloqueado! Digite outro número")
                elif re.search(r"(\d{3})", numero).group(1) == "800":
                    print("maq: Saldo = " + str(saldo))
                elif re.search(r"(\d{3})", numero).group(1) == "808":
                    if saldo >= 0.1:
                        saldo -= 0.1
                        print("maq: Saldo = " + str(saldo))
                    else:
                        print("maq: Não tem saldo suficiente!")
                elif re.search(r"(\d{1})", numero).group(1) == "2":
                    if saldo >= 0.25:
                        saldo -= 0.25
                        print("maq: Saldo = " + str(saldo))
                    else:
                        print("maq: Não tem saldo suficiente!")
            else:
                print("maq: Tem que levantar o telefone primeiro!")
        elif er_pousar.match(user_input):
            if levantado:
                levantado = False
                trocoList = troco(saldo)
                trocoStr = ", ".join(trocoList)
                print("maq: Troco = " + trocoStr)
            else:
                print("maq: O telefone já está pousado!")
        elif er_abortar.match(user_input):
            print("maq: Troco = " + str(saldo))
            break
        else:
            print("maq: Comando Inválido")


def troco(saldo):
    arredondado = round(saldo * 100)
    moedas = {"2e": 0,
              "1e": 0,
              "50c": 0,
              "20c": 0,
              "10c": 0,
              "5c": 0}
    while (arredondado > 0):
        if arredondado >= 200:
            arredondado -= 200
            moedas["2e"] += 1
        elif arredondado >= 100:
            arredondado -= 100
            moedas["1e"] += 1
        elif arredondado >= 50:
            arredondado -= 50
            moedas["50c"] += 1
        elif arredondado >= 20:
            arredondado -= 20
            moedas["20c"] += 1
        elif arredondado >= 10:
            arredondado -= 10
            moedas["10c"] += 1
        elif arredondado >= 5:
            arredondado -= 5
            moedas["5c"] += 1
    return [str(value) + "x" + str(key) for key,value in moedas.items() if value > 0]

tpc5()
