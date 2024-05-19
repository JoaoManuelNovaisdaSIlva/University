p1p1(A,B,C, Soma) :-
Soma is A + B + C.

p1p2([],0).
p1p2([H|T], S) :- p1p2(T,G),S is H+G.

p1p3(A, B, Maior) :-
A > B -> Maior is A ; Maior is B.

p1p4([A], A).
p1p4([H|T], M) :- p1p4(T,G), M is max(H, G).

p1p42([A], A).
p1p42([H|T], M) :- 
    p1p42(T, G),
     ( H > G
       -> M = H
        ; M = G
     ).

soma_lista([],0).
soma_lista([H|T], Soma) :-
    soma_lista(T, G), Soma is H + G.

media(Valores, Media) :-
    soma_lista(Valores, Soma),
    length(Valores, NElem),
    Media is Soma / NElem.

inserir(El, [], [El]).
inserir(El, [H|T], [El, H|T]) :-
    El =< H.
inserir(El, [H|T], [H|TOrd]) :-
    El > H,
    inserir(El, T, TOrd).

ordenar([],[]).
ordenar([H|T], ListaOrdenada) :-
    ordenar(T, CaudaParcOrd),
    inserir(H, CaudaParcOrd, ListaOrdenada).

par([],[]).
par([H|T], Pares) :-
    par(T,G),
    ( H // 2 == 0
      -> append(G, H, Pares)
      ; fail
    ).

pertence(X, [H|T]).
pertence(X, [H|T]) :-
    X \= Y,
    pertence(X,L).

comprimento([], 0).
comprimento([H|T], Comp) :-
    comprimento(T,G), Comp is G+1.

diferentes([], 0).
diferentes([H|T], Dif) :-
    pertence(H,T),
    