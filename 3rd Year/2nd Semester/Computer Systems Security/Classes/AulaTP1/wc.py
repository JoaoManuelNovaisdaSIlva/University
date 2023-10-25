
def mywc(file):
    linhas, palavras, caracteres = 0,0,0
    f = open(file,"r")
    lines = f.readlines()
    linhas = len(lines)
    for l in lines:
        caracteres += len(l)
        for w in l:
            if w == ' ':
                palavras += 1
    return linhas,palavras,caracteres
