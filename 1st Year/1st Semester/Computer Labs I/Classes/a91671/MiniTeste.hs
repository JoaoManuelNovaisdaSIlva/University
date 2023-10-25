{-|
Module : MiniTeste
Description : Módulo Haskell contendo miniteste
Copyright : João Silva <a91671@alunos.uminho.pt;
Este módulo contém a definição da função para avaliação do mini-teste de 
li1
-}

module MiniTeste where

{-|
Como mod 91671 5 = 1 então foi defenida a função 1
Esta função recebe uma lista de pares e decrementa o segundo elemento de cada par
removendo, no caso deste decremento ser igual a 0, o par em questão

== Exemplos de utilização 

>> f [(2,1),(1,5),(4,6)]
>> [(1,4),(4,5)]

>> f [(2,4),(1,10),(10,-1)]
>> [(2,3),(1,9)]

-}

f :: [(Int,Int)] -> [(Int,Int)]
f [] = []
f ((a,b):t)
    | b == 1 = f t 
    | b < 0 = f t 
    | otherwise = (a,b-1) : f t