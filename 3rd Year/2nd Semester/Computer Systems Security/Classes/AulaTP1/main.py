
import re, string, random
import cesar
from wc import mywc
from purify import purify

def main():
    txtA = """
    As armas e os barões assinalados,
    Que da ocidental praia Lusitana,
    Por mares nunca de antes navegados,
    Passaram ainda além da Taprobana,
    Em perigos e guerras esforçados,
    Mais do que prometia a força humana,
    E entre gente remota edificaram
    Novo Reino, que tanto sublimaram;
    """

    txt2 = purify(txtA)
    print("\n----Função Purify: ")
    print(txt2)

    # wc esta apenas a funcionar com ler de ficheiros
    linhas, palavras, caracteres = mywc("texto.txt")
    print("\n-----Função wc: ")
    print(linhas)
    print(palavras)
    print(caracteres)

    mensagem = "CartagoEstaNoPapo"
    msg = purify(mensagem)
    r = cesar.cesar("enc", "G", msg)
    print("\n-----Função césar: ")
    print("Encriptando: " + r)

    r2 = cesar.cesar("dec", "G", r)
    print("Desencriptando: " + r2)

    msgEncriptada = "PEVCGBTENSVNRFRTHENAPNQRERQRF"
    r3 = cesar.cesarCrackBruteForce(msgEncriptada)
    print("\n-----Função para desincriptar mensagem de cesar usando brute force: ")
    print(r3)

main()

