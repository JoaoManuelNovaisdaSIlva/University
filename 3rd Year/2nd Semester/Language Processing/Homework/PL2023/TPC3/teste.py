import re

def main():
    ex = r'(?P<Pasta>\d+)::(?P<Data>(?P<Ano>\d+)-(?P<Mes>\d+)-(?P<Dia>\d+))::(?P<Nome>[\w\s]+)::(?P<Pai>[\w\s]+)::(?P<Mae>[\w\s]+)::(?P<Observacoes>[^:]*)::'
    pattern = re.compile(ex)
    print(pattern.match("575::1894-11-08::Aarao Pereira Silva::Antonio Pereira Silva::Francisca Campos Silva::::"))
    print(pattern.match("575::1894-11-08::Aarao Pereira Silva::Antonio Pereira Silva::Francisca Campos Silva::::").group(0))
    print(pattern.match("575::1894-11-08::Aarao Pereira Silva::Antonio Pereira Silva::Francisca Campos Silva::::").group(1))
    print(pattern.match("575::1894-11-08::Aarao Pereira Silva::Antonio Pereira Silva::Francisca Campos Silva::::").group('Ano'))



main()
