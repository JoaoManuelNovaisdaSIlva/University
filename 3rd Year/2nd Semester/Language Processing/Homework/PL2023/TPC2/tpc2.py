import sys

def tpc2():
    print("\n--- BEM VINDO À RESOLUÇÃO DO TPC2 DE PL2023 ---")
    print("Introduza a sequência de digitos:")
    txt = ""
    digitos,i, offOn = 0,0,0

    try:
        while True:
            linha = sys.stdin.readline().strip("\n")
            linha = linha.upper()
            txt = txt+linha
            if not linha:
                break
            for caracter in linha:
                if caracter == 'O':
                    if txt[i:i+3] == "OFF":
                        offOn = 1
                    if txt[i:i+2] == "ON":
                        offOn = 0
                if caracter == "=":
                    print(digitos)
                if offOn == 0:
                    if 48 <= ord(caracter) <= 57:
                        digitos += 1
                i += 1
    except KeyboardInterrupt:
        print("Programa Parado.")
        return digitos

tpc2()
