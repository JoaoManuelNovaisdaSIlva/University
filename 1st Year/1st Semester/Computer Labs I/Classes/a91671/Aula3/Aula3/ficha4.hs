{-|
Module: ficha4
Description: Módulo Haskell resolução de exercicio da ficha 3
Copyright: a916712alunos.uminho.pt
-}

module Ficha4 where

{-| 
A função (!!) recebe uma lista de "a's" e um inteiro devolvendo o elemento do array na posição indicada
pelo segundo argumento


== Exemplos de Utilização

>>> (!!) [1,2,3] 2
3

>>> (!!) [1,2,3] 0
1

-}

myPos :: [a] -> Int -> a
myPos [] _ = error "Invalid variables"
myPos (h:t) x
    | x < 0 = error "Invalid variables"
    | x > 0 = myPos t (x-1)
    | otherwise = h


