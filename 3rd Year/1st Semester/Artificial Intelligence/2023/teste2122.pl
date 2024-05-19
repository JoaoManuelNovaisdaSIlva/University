% a) 

aviao("B737").
aviao("A330").
aviao("A320").
aviao("A319").
aviao("A340").
aviao("A321").
aviao("E190").

companhia("Ryanair").
companhia("Tap").
companhia("EasyJet").
companhia("Emirates").
companhia("Azul").
% acho que é suposto também escrever tudo o que esta na segunda tabela, individualmente para a a) e em extenssão de predicado para a b) (não tenho tempo nem paciência para isso)
% Viagens (destino, origem, custo, data, hora, aviao)
viagem("Paris", "Berlim", "30€", "22/02/2020", "8h00", "A320").

% b)

aviaoCompanhia("B737", "Ryanair").
aviaoCompanhia("A330", "Tap").
aviaoCompanhia("A320", "EasyJet").
aviaoCompanhia("A319", "EasyJet").
aviaoCompanhia("A340", "Tap").
aviaoCompanhia("A340", "Ryanair").
aviaoCompanhia("E190", "Azul").

% c)

:- dynamic aviao/2.

novo_aviao(Nome, Companhia) :-
    \+ aviao(Nome, Companhia), % Verifica se o avião não existe
    assertz(aviao(Nome, Companhia)).

remover_aviao(Nome) :-
    (viagem(_, _, _, _, _, Nome)
     -> fail
     ; retract(aviao(Nome, _))
     ).

% Grupo 2 última V/F

xpto([X], X).
xpto([_|T], X) :- xpto(T,X).