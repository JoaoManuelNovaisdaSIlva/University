{- |
Module      : Tarefa5_2022li1g093
Description : Deslize do Mapa
Copyright   : João Manuel Novais da Silva <a91671@alunos.uminho.pt>
              Pedro António Pires Correia Leite Sequeira <a91660@alunos.uminho.pt>

Módulo para a realização da Tarefa 5 do projeto de LI1 em 2022/23.
-}

module Tarefa5_2022li1g093 where

import LI12223
import Tarefa2_2022li1g093

{- |
A função @deslizaJogo@ é a função que irá fazer o efeito de movimento do mapa gerando uma linha nova no topo do mapa usando a função @estendeMapa@ 
já definida na tarefa 2 e removendo a última linha do mesmo
-}

deslizaJogo :: Int -> Jogo -> Jogo
deslizaJogo r (Jogo (Jogador (x,y)) mapa) = (Jogo (Jogador (x,y+1)) (removeUltima(estendeMapa mapa r)))

-- | Função que remove a última linha do mapa
removeUltima :: Mapa -> Mapa
removeUltima (Mapa l m) = (Mapa l (removeUltima_aux m))

-- | Função auxiliar da @removeUltima@
removeUltima_aux :: [(Terreno,[Obstaculo])] -> [(Terreno,[Obstaculo])]
removeUltima_aux ((terr,obs):t)
    | t == [] = []
    | otherwise = (terr,obs) : removeUltima_aux t