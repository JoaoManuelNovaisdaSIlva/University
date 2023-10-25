import ply.lex as lex

tokens = (
    'VIRGULA',
    'PONTO',
    'PARENTESERETOABERTO',
    'PARENTESERETOFECHO',
    'EQUALS',
    'CHAVETAABERTO',
    'CHAVETAFECHO',
    'INT',
    'FLOAT',
    'HEXADECIMAL',
    'OCTAL',
    'BINARY',
    'EXPONENTE',
    'INFINITO',
    'NAN',
    'STRING',
    'MULTILINESTRING',
    'TIME',
    'DATE',
    'DATETIME',
    'OFFSETDATETIME',
    'BOOLEAN',
    'END',
    'KEY'
)

t_VIRGULA = r'\,'
t_PONTO = r'\.'
t_PARENTESERETOABERTO = r'\['
t_PARENTESERETOFECHO = r'\]'
t_EQUALS = r'\='
t_CHAVETAABERTO = r'\{'
t_CHAVETAFECHO = r'\}'


t_ignore_comment = r'\#.*'
t_ignore = " \t\r\n"

def t_OFFSETDATETIME(t):
    r'\d{4}\-\d{2}\-\d{2}[T ](\d{2}\:\d{2}:\d{2})\.?[0-9]*(Z|[+-]\d{2}:\d{2})'
    return t

def t_DATETIME(t):
    r'[0-9]{4}\-[0-9]{2}\-[0-9]{2}T[0-9]{2}\:[0-9]{2}\:[0-9]{2}\.?[0-9]*'
    return t

def t_TIME(t):
    r'[0-9]{2}\:[0-9]{2}\:[0-9]{2}\.?[0-9]*'
    return t

def t_DATE(t):
    r'[0-9]{4}\-[0-9]{2}\-[0-9]{2}'
    #t.type = 'DATE'
    return t

def t_EXPONENTE(t):
    r'[\-|\+]?\d+\.?\d*[e|E][\-|\+]?\d+'
    t.value = float(t.value)
    return t

def t_HEXADECIMAL(t):
    r'0x(_?\d|_?[a-fA-F])+'
    t.value = int(t.value, 16)
    return t

def t_OCTAL(t):
    r'0o[0-7][0-7|_0-7]*'
    t.value = int(t.value, 8)
    return t

def t_BINARY(t):
    r'0b[01]([01]|_[01])*'
    t.value = int(t.value, 2)
    return t

def t_FLOAT(t):
    r'[\-|\+]?\d[\d|_\d]*\.\d[\d|_\d]*'
    t.value = float(t.value)
    return t

def t_INT(t):
    r'[\-|\+]?\d[\d|_\d]*'
    t.value = int(t.value)
    return t


def t_INFINITO(t):
    r'[\-|\+]?inf'
    t.value = float(t.value)
    return t

def t_NAN(t):
    r'[\-|\+]?nan'
    t.type = 'NAN'
    return t

def t_MULTILINESTRING(t):
    r'"""([^\\]|(\\(.|\n))|\n)*?"""'
    t.value = t.value[3:-3]
    return t

def t_STRING(t):
    r'[\"|\'].*[\"|\']'
    t.value = t.value[1:-1]
    return t


def t_BOOLEAN(t):
    r'true|false'
    if t.value == 'true': t.value = True
    elif t.value == 'false': t.value = False
    return t

def t_KEY(t):
    #r'([a-zA-Z0-9_-]|[^\u0000-\u007F])+'
    r'[a-zA-Z]\w*\-*\w*'
    #t.value = t.value[0:len(t.value)-1]
    return t

def t_END(t):
    r'\s+$'
    t.value = t.value.strip()
    return t

def t_error(t):
    print("Illegal character '%s'" % t.value[0])
    t.lexer.skip(1)


lexer = lex.lex()
