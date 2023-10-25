module Exemplo1 where

import LI12223

-- Função que testa se existem linhas do mapa com obstáculos impróprios
mapaValido1 :: Mapa -> Bool
mapaValido1 (Mapa lr []) = True
mapaValido1 (Mapa lr ((Relva,lo):t)) 
        |(elem Carro lo || elem Tronco lo) = False  
        |otherwise = mapaValido1 (Mapa lr t)
 
mapaValido1 (Mapa lr (((Rio vr),lo):t))
        |(elem Carro lo || elem Arvore lo) = False 
        |otherwise = mapaValido1 (Mapa lr t)
        
mapaValido1 (Mapa lr (((Estrada ve),lo):t))
        |(elem Arvore lo || elem Tronco lo) = False
        |otherwise = mapaValido1 (Mapa lr t)


