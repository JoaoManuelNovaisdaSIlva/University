# import js2pyn
import ply.yacc as yacc
import json

# importar os tokens
from lex import tokens


def p_codigo_empty(p):
    'codigo : '
    p[0] = dict()


def p_codigo_ending(p):
    'codigo : codigo END'
    p[0] = p[1]


def p_codigo_start(p):
    'codigo : codigo bloco'
    p[1].update(p[2])
    p[0] = p[1]


def p_conteudo(p):
    '''conteudo : String
                | TIME
                | DATE
                | OFFSETDATETIME
                | DATETIME
                | BOOLEAN
                | BINARY
                | OCTAL
                | HEXADECIMAL
                | INT
                | FLOAT
                | EXPONENTE
                | INFINITO
                | NAN
                | lista
                | inline_table'''
    p[0] = p[1]


def p_String(p):
    '''String : STRING
              | MULTILINESTRING'''
    p[0] = p[1]

def p_lista(p):
    '''lista : PARENTESERETOABERTO elems PARENTESERETOFECHO
             | PARENTESERETOABERTO PARENTESERETOFECHO'''
    if len(p) == 4:
        p[0] = p[2]
    else:
        p[0] = []


def p_elems(p):
    '''elems : elems VIRGULA conteudo
           | conteudo'''
    if len(p) == 4:
        p[1].append(p[3])
        p[0] = p[1]
    else:
        p[0] = [p[1]]



def p_codigo_newobject(p):
    '''codigo : codigo PARENTESERETOABERTO chaves PARENTESERETOFECHO bloco
              | codigo PARENTESERETOABERTO chaves PARENTESERETOFECHO'''
    if len(p) == 6:
        keys = p[3].split(".")
        if(keys[0] in p[1]):
            p[1][keys[0]].update({keys[1]:p[5]})
        else: p[1][p[3]] = p[5]
        #print("CODIGO- p5: ", p[5])
    else:
        p[1][p[3]] = {}
    p[0] = p[1]
    #print("RECURSIVIDADE", p[0])



def p_chaves(p):
    '''chaves : KEY PONTO KEY
              | KEY'''
    if len(p) == 4:
        p[0] = p[1] + p[2] + p[3]
    else:
        p[0] = p[1]
    #print("CHAVES:", p[0])


def p_bloco_conteudo(p):
    '''bloco : bloco KEY EQUALS conteudo
             | KEY EQUALS conteudo'''
    if len(p) > 4:
        aux = {p[2]: p[4]}
        p[1].update(aux)
        p[0] = p[1]
    else:
        aux = {p[1]: p[3]}
        p[0] = aux
    #print("AUX", aux)


def p_inline_table(p):
    '''inline_table :  CHAVETAABERTO insideTable CHAVETAFECHO'''
    p[0] = p[2]
    #print("P2", p[2])

def p_insideTable(p):
    '''insideTable : insideTable VIRGULA bloco
                   | bloco'''
    if(len(p) == 4):
        p[1].append(p[3])
        p[0] = p[1]
    else:
        p[0] = p[1]

def p_error(simb):
    print("Erro sintático, token inesperado: ", simb)


parser = yacc.yacc()

parser.stack = []

parser.vars = {}  # dicionario de variaveis
