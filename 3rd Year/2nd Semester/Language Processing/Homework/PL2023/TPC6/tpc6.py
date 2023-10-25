import ply.lex as lex

tokens = (
    "PLUS",
    "MINUS",
    "MULTIPLY",
    "DEVIDE",
    "GREATER",
    "LESSER",
    "DOUBLE_EQUALS",
    "EQUALS",
    "SEMI_COLLON",
    "COMA",
    "BLOCK_START",
    "BLOCK_END",
    "TYPE",
    "OPERATION",
    "NUM",
    "COMMENT",
    "MULTI_COMMENT",
    "FUNCTION_NAME",
    "VARIABLE_NAME",
    "BLOCK_NAME",
    "LIST",
    "PARENTHESES_OPEN",
    "PARENTHESES_CLOSE"
)

t_PLUS = r"\+"
t_MINUS = r"\-"
t_MULTIPLY = r"\*"
t_DEVIDE = r"\/"
t_GREATER = r"\>"
t_LESSER = r"\<"
t_DOUBLE_EQUALS = r"\=="
t_EQUALS = r"\="
t_SEMI_COLLON = r"\;"
t_COMA = r"\,"
t_BLOCK_START = r"\{"
t_BLOCK_END = r"\}"


t_COMMENT = r"//+"
t_MULTI_COMMENT = r"\/\*\s*\w+\s*\*\/"
t_FUNCTION_NAME = r" \w+(?=[\{\(])"
t_VARIABLE_NAME = r" \w+(?=\[)*"
t_BLOCK_NAME = r"program|function"
t_LIST = r"\[(\d+(\.\.\d+)?)?\]"
t_PARENTHESES_OPEN = r"\(+"
t_PARENTHESES_CLOSE = r"\)+"

t_ignore = " \n\t"

def t_NUM(t):
    r'\d+'
    t.value = int(t.value)
    return t

def t_TYPE(t):
    r"int|float|char|double|long|bool"
    return t

def t_OPERATION(t):
    r"if|in|or|else|elif|(else if)|while|for"
    return t

def t_error(t):
    print("Illegal character '%s'" % t.value[0])
    t.lexer.skip(1)

lexer = lex.lex()

test = '''
/* factorial.p
-- 2023-03-20 
-- by jcr
*/

int i;

// Função que calcula o factorial dum número n
function fact(n){
  int res = 1;
  while res > 1 {
    res = res * n;
    res = res - 1;
  }
}

// Programa principal
program myFact{
  for i in [1..10]{
    print(i, fact(i));
  }
}
'''

lexer.input(test)

for tok in lexer:
    print(tok)
    print(tok.type, tok.value, tok.lineno, tok.lexpos)


