module Exemplo1_v1 where

import LI12223

-- Dar nomes esclarecedores às funções
obstaculosValidos :: Mapa -> Bool
obstaculosValidos (Mapa lr []) = True
obstaculosValidos (Mapa lr ((Relva,lo):t)) 
        |(elem Carro lo || elem Tronco lo) = False  
        |otherwise = obstaculosValidos (Mapa lr t)
obstaculosValidos (Mapa lr (((Rio vr),lo):t))
        |(elem Carro lo || elem Arvore lo) = False 
        |otherwise = obstaculosValidos (Mapa lr t)
obstaculosValidos (Mapa lr (((Estrada ve),lo):t))
        |(elem Arvore lo || elem Tronco lo) = False
        |otherwise = obstaculosValidos (Mapa lr t)
