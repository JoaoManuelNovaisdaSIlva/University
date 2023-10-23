from ply import yacc

from bibtex_lex import tokens


def p_bibtex(p):
    "bibtex : referencias"
    p[0] = p[1]
    print("Encontrei uma bibtex: ", p[0])

def p_referencias_lista(p):
    "referencias : referencias referencia"
    print("Encontrei uma lista de referencias: ", p[0], p[1])
    p[0] = p[1] + 1

def p_referencias_vazia(p):
    "referencias : "
    print("Encontrei uma lista de referencias vazia")
    p[0] = 0

def p_referencia(p):
    "referencia : TIPOreg '{' chaveCitacao ',' campos '}'"
    print("Encontrei uma referencia")
    p[0] = [p[1]] + [p[2]] + [p[3]] + [p[4]] + [p[5]] + [p[6]]

def p_chaveCitacao(p):
    "chaveCitacao : PAL"
    print("Encontrei uma chave de citação")
    p[0] = [1]

def p_campos_lista(p):
    "campos : campos ',' campo"
    print("Encontrei uma lista de campos")
    p[0] = p[1] + p[2] + p[3]

def p_campos_vazia(p):
    "campos : campo"
    print("Encontrei uma lista vazia de campos")
    p[0] = p[1]

def p_campo(p):
    "campo : nomeCampo SEP valorCampo"
    print("Encontrei um campo")
    p[0] = p[1] + p[2] + p[3]

def p_nomeCampo(p):
    "nomeCampo : PAL"
    print("Encontrei um nome de campo")
    p[0] = p[1]

def p_valorCampo(p):
    "valorCampo : TEXTO"
    print("Encontrei o valor do campo")
    p[0] = p[1]

def p_error(p):
    print("Syntax error in input!",p)
    parser.success=False

parser = yacc.yacc()
parser.success=True

source = ""
#for linha in sys.stdin:
#	source += linha
f = open("bibtex.txt",encoding="utf-8")
for linha in f:
	source += linha

parser.parse(source)
#print(source)
if parser.success:
   print('Parsing completed!')
else:
   print('Parsing failed!')
