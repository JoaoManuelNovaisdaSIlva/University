
import re, string, random
import cesar
from wc import mywc
from purify import purify
import vigenere


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

    #mywc()
    #print("\n-----Função wc: ")


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

    mensagem1 = "CifraIndecifravel"
    msg1 = purify(mensagem1)
    r4 = vigenere.vigenere("enc", "BACO", msg1)
    print("\n-----Função vigenere:  ")
    print("Encriptando: " + r4)

    r5 = vigenere.vigenere("dec", "BACO", r4)
    print("Desencriptando: " + r5)

    print(vigenere.letraMaisComun([["ABCDE"],["AEDCB"], ["BECAB"]]))


main()

