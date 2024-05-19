xpto([X], X).
xpto([_|T], X) :- xpto(T,X).