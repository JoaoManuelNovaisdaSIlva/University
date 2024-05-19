filho(joao, jose).
filho(jose, manuel).
filho(carlos, jose).
filho(filipe, ines).

pai(paulo, filipe).
pai(paulo, maria).

pai(pai(antonio, nadia)).

filho(filho(nuno, ana)).

sexo(joao, masculino).
sexo(jose, masculino).
sexo(maria, feminino).
sexo(joana, feminino).


a(P,F) :- filho(F,P),pai(P,F).
b(A,N) :- filho(N,X),pai(A,X).
c(X,Y) :- filho(X,P),filho(P,Y).