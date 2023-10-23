import json
import re
import sys


def parser(ficheiro):
    text = []
    with open(ficheiro, "r") as file:
        first_line = file.readline().strip()

        pattern = r',(?![^{}]*\})'
        fields = re.split(pattern, first_line)

        for line in file:
            lines = line.strip().split(",")
            text.append(lines)

        filtered = [s for s in fields if s != ""]
    return filtered, text


def tpc4(first_line, lines):
    regex = r"^(.+)\{(\d+)(?:,(\d+))?\}(?:::(sum|media))?$"
    r = re.compile(regex)

    i, l = 0, len(first_line)
    for campos in first_line:
        match = r.match(campos)  # fazer compile fora do loop para ser mais eficiente

        if match:
            prefixo = match.group(1)
            min_val = int(match.group(2))
            max_val = int(match.group(3)) if match.group(3) else None
            operacao = match.group(4)

            if max_val == None:
                tudo("lista", first_line, lines, prefixo, min_val, max_val)  # uma lista com tamanho fixo
            elif operacao == None:
                tudo("intervalo", first_line, lines, prefixo, min_val, max_val)  # uma lista de intervalos possíveis
            elif operacao == "sum":
                tudo("sum", first_line, lines, prefixo, min_val, max_val)
            elif operacao == "media":
                tudo("media", first_line, lines, prefixo, min_val, max_val)
        if i == l - 1 and not match:
            tudo("nenhum", first_line, lines, first_line[-1], 0, 0)

        i += 1
    i = 0

def tudo(type, first_line, lines, prefixo, min_val, max_val): # tipe é o tipo de transformacao a fazer
    static_fields = []
    for campo in first_line[:-1]:
        static_fields.append((campo, ""))
    static_fields.append((prefixo, ""))

    resultado = []
    i, l = 0, len(lines[0])
    for list in lines:
        dic = dict(static_fields)
        for elemento in list:
            coluna, valor = static_fields[i]
            dic[coluna] = elemento
            if max_val != None:
                if i == l - max_val:
                    if type == "intervalo":
                        dic[coluna] = [int(x) for x in list[i:i + max_val] if x != ""]
                        break
                    elif type == "sum":
                        valores = [int(x) for x in list[i:i + max_val] if x != ""]
                        dic[coluna] = sum(valores)
                        break
                    elif type == "media":
                        valores = [int(x) for x in list[i:i + max_val] if x != ""]
                        dic[coluna] = sum(valores) / len(valores)
                        break
            if i == l - min_val and type != "nenhum":
                if type == "lista":
                    dic[coluna] = [int(x) for x in list[-min_val:]]
                    break
            i += 1
        i = 0
        resultado.append(dic)

    with open("alunos.json", "w") as f:
        json.dump(resultado, f, indent=3)

if __name__ == "__main__":
    ficheiro = sys.argv[1]

    fst, lines = parser(ficheiro)
    tpc4(fst, lines)
