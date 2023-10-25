import sys

def mywc():
    linhas, palavras, caracteres = 0,0,0
    nLinhasSTDIN = 0
    for line in sys.stdin:
        nLinhasSTDIN+=1

        caracteres += len(line)
        if line[:1].isalnum():
            palavras += 1
        for w in line:
            if w == ' ':
                palavras += 1

    return nLinhasSTDIN,palavras,caracteres

