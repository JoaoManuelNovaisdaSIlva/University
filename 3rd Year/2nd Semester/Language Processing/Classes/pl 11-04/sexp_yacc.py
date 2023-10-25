import ply.yacc as yacc
import sys

from sexp_lex import tokens

## incio da GIC

def p_lisp(p):
    "lisp : sexp"
    print('Parsing completed succesfully! ')


def p_sexp_pal(p):
    "sexp : PAL"
    print('Reconheci o atomo ', p[1])


def p_sexp_num(p):
    "sexp : NUM"
    print('Reconheci um numero ', p[1])

def p_sexp_sexplist(p):
    "sexp : LPAREN sexplist RPAREN"
    print('Reconheci uma lista completa', p[1], p[2], p[3])

def p_sexplist_sexp(p):
    "sexplist : sexp sexplist"
    print('Reconheci uma cabeça e uma cauda ')


def p_sexplist_empty(p):
    "sexplist : "
    print('Reconheci uma lista vazia ')


def p_error(p):
    parser.success = False
    print('Syntax error!')
    exit()

###inicio do parsing
parser = yacc.yacc()
parser.success = True
    
for linha in sys.stdin:
    parser.parse(linha)
  

###inicio do parsing
#parser = yacc.yacc()
#parser.success = True
#fonte = ""
#for line in sys.stdin:
#    fonte += line   
#parser.parse(fonte)

