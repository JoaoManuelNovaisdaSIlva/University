import ply.lex as lex


tokens=(
    'OPENPAREN',
    'CLOSEPAREN',
    'NULL',
    'NUM'
)

t_OPENPAREN = r'\('
t_CLOSEPAREN = r'\)'
t_NUM = r'\d+'
t_NULL = r'NULL'

t_ignore = " \n\t"

def t_error(t):
    print("Illegal character '%s'" % t.value[0])
    t.lexer.skip(1)


lexer = lex.lex()

test="1 ( 2 (() ()) ())"

lexer.input(test)

for tok in lexer:
    print(tok)
    print(tok.type, tok.value, tok.lineno, tok.lexpos)
