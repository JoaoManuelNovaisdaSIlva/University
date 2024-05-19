% Definindo as bibliotecas
biblioteca(1, 'Geral Uminho', 'Braga').
biblioteca(2, 'Lúcio Craveiro', 'Braga').
biblioteca(3, 'Municipal', 'Porto').
biblioteca(4, 'Pública', 'Viana').
biblioteca(5, 'Ajuda', 'Lisboa').
biblioteca(6, 'Cidade', 'Coimbra').

% Definindo os leitores
leitores(1, 'Pedro', 'masculino').
leitores(2, 'João', 'masculino').
leitores(3, 'Lúcia', 'feminino').
leitores(4, 'Sofia', 'feminino').
leitores(5, 'Patrícia','feminino').
leitores(6, 'Diana', 'feminino').

% Definindo as requisições
requisicao(1, 2, 3, '17/05/2022').
requisicao(2, 1, 2, '10/07/2022').
requisicao(3, 1, 3, '02/11/2021').
requisicao(4, 1, 4, '01/02/2022').
requisicao(5, 5, 3, '23/04/2022').
requisicao(6, 4, 2, '09/03/2021').
requisicao(7, 4, 1, '05/05/2022').
requisicao(8, 2, 6, '18/07/2021').
requisicao(9, 5, 7, '12/04/2022').

% Definindo os livros
livro(1, 'Game of Thrones', 1).
livro(2, 'Código da Vinci', 2).
livro(3, 'Sétimo Selo', 1).
livro(4, 'Fire and Blood', 4).
livro(5, 'Harry Potter', 6).
livro(6, 'Senhor dos Anéis', 7).
livro(7, 'O Algoritmo Mestre', 9).

% Definindo as devoluções
devolucao(2, '26/07/2022').
devolucao(4, '04/02/2022').
devolucao(5, '13/06/2022').
devolucao(1, '23/05/2022').
devolucao(6, '09/04/2022').

b1(N) :- 
    findall(Person, leitores(_, Person, 'feminino'), Bag),
    length(Bag, N).

b2(list) :-
    setof(Livro, (requisicao(_, _, IDLivro, _), livro(IDLivro, Livro, IDBiblioteca), biblioteca(IDBiblioteca, _, 'Braga')), list).
