import base64
import os
import random
import string
import sys

# REFAZER O PARSER ENCODE E DECODE

def setup(nBytes, ficheiro):
    random_bytes = os.urandom(nBytes)
    with open(ficheiro, 'wb') as f:
        f.write(base64.b64encode(random_bytes))

def encrypt(mensagemClean, ficheiro):
    with open(ficheiro, 'rb') as f:
        key = base64.b64decode(f.read())
    message_bytes = mensagemClean.encode('utf-8')
    encrypted_bytes = bytes([message_byte ^ key_byte for message_byte, key_byte in zip(message_bytes, key)])
    print(base64.b64encode(encrypted_bytes).decode('utf-8'))

def decrypt(mensagemCifrada, ficheiro):
    with open(ficheiro, 'rb') as f:
        key = base64.b64decode(f.read())
    encrypted_bytes = base64.b64decode(mensagemCifrada)
    decrypted_bytes = bytes([encrypted_byte ^ key_byte for encrypted_byte, key_byte in zip(encrypted_bytes, key)])
    print(decrypted_bytes.decode('utf-8'))

if __name__ == "__main__":

    tipo = sys.argv[1]
    segundo = sys.argv[2]
    ficheiro = sys.argv[3]

    if tipo.lower() == "setup":
        setup(int(segundo), ficheiro)
    elif tipo.lower() == "enc":
        encrypt(segundo, ficheiro)
    elif tipo.lower() == "dec":
        decrypt(segundo, ficheiro)
    else:
        print(f"Comando desconhecido {tipo}")
