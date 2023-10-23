{- |
Module      : Tarefa4_2022li1g093
Description : Determinar se o jogo terminou
Copyright   : João Manuel Novais da Silva <a91671@alunos.uminho.pt>
              Pedro António Pires Correia Leite Sequeira <a91660@alunos.uminho.pt>

Módulo para a realização da Tarefa 4 do projeto de LI1 em 2022/23.
-}
module Tarefa4_2022li1g093 where

import LI12223
import Tarefa3_2022li1g093

{- |
A função @jogoTerminou@ , verifica se o jogador se encontra na água de um rio, na mesma posição de um carro, ou até na mesma posição de uma árvore. 
Para isto é usada a função @encontraLinha@ que indo linha a linha do mapa procura qual é a linha em que o jogador se encontra. Dependendo do tipo de terreno
foram feitas várias funções auxiliares, @encontraColuna_Rio@, @encontraColune_Estrada@ e @encontraColuna_Relva@ que verificam se o jogador se encontra em alguma
posição irregular. Também verifica se o jogador se encontra numa coordenada inválida, i.e. fora do mapa, usando a função @verificarCoordenadas@ da tarefa 3. 
-}
jogoTerminou :: Jogo -> Bool
jogoTerminou (Jogo (Jogador (x,y)) m) = (encontraLinha m (x,y) 0) || verificarCoordenadas (Jogo (Jogador (x,y)) m)

encontraLinha :: Mapa -> (Int,Int) -> Int -> Bool
encontraLinha (Mapa l []) _ _ = False
encontraLinha (Mapa l ((Rio v,obs):t)) (x,y) acc = if y == acc then encontraColuna_Rio obs (x,y) 0 else encontraLinha (Mapa l t) (x,y) (acc+1)
encontraLinha (Mapa l ((Estrada v, obs):t)) (x,y) acc = if y == acc then encontraColuna_Estrada obs (x,y) 0 else encontraLinha (Mapa l t) (x,y) (acc+1)
encontraLinha (Mapa l ((Relva, obs):t)) (x,y) acc = if y == acc then encontraColuna_Relva obs (x,y) 0 else encontraLinha (Mapa l t) (x,y) (acc+1)

encontraColuna_Rio :: [Obstaculo] -> (Int,Int) -> Int -> Bool
encontraColuna_Rio [] _ _ = False
encontraColuna_Rio (ob:obs) (x,y) acc = if x == acc && ob == Nenhum then True else encontraColuna_Rio obs (x,y) (acc+1)

encontraColuna_Estrada :: [Obstaculo] -> (Int,Int) -> Int -> Bool
encontraColuna_Estrada [] _ _ = False
encontraColuna_Estrada (ob:obs) (x,y) acc = if x == acc && ob == Carro then True else encontraColuna_Estrada obs (x,y) (acc+1)

encontraColuna_Relva :: [Obstaculo] -> (Int,Int) -> Int -> Bool
encontraColuna_Relva [] _ _ = False
encontraColuna_Relva (ob:obs) (x,y) acc = if x == acc && ob == Arvore then True else encontraColuna_Relva obs (x,y) (acc+1)
