import ply.yacc as yacc
from abin_lex import tokens

def p_grammar(p):
    """
    ABin    : Raiz OPENPAREN ABin ABin CLOSEPAREN
            | NULL
    Raiz    : NUM
    """

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
if parser.success:
   print('Parsing completed!')
else:
   print('Parsing failed!')

#result = parser.parse(source)
#print(result)
