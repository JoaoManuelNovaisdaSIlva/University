from ply import yacc

from abin_lex import tokens

def p_abin_vazia(p):
    'ABin : NULL'
    #print("Encontrei uma ABin vazia")
    p[0] = "{}"

def p_abin(p):
    'ABin : Raiz OPENPAREN ABin ABin CLOSEPAREN'
    #print("Encontei uma ABin")
    p[0] = "{" + "root : " + p[1] + "," + "left : " + p[3] + ", right : " + p[4] + "}"

def p_raiz(p):
    'Raiz : NUM'
    #print("Encontrei uma raiz")
    p[0] = p[1]

def p_error(p):
    print("Syntax error in input!",p)
    parser.success=False

parser = yacc.yacc()
parser.success=True

source = ""
#for linha in sys.stdin:
#	source += linha
f = open("abin.txt",encoding="utf-8")
for linha in f:
	source += linha

parser.parse(source)
#print(source)

json = parser.parse(source)
print(json)
if parser.success:
   print('Parsing completed!')
else:
   print('Parsing failed!')
