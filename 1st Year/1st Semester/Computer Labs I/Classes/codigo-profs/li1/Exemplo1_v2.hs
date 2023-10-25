module Exemplo1_v2 where

import LI12223

-- Separar código em funções mais pequenas que fazem coisas mais específicas:
-- Uma função que testa se uma linha contém obstáculos impróprios;
-- Outra função que verifica todas as linhas de um mapa, usando a primeira função

obstaculosValidos :: Mapa -> Bool
obstaculosValidos (Mapa _ []) = True
obstaculosValidos (Mapa lr (l:ls)) | obstaculosValidosLinha l = obstaculosValidos (Mapa lr ls)
                                   | otherwise = False

obstaculosValidosLinha :: (Terreno, [Obstaculo]) -> Bool
obstaculosValidosLinha (Relva,lo) 
        | elem Carro lo || elem Tronco lo = False  
        | otherwise = True
obstaculosValidosLinha (Rio vr,lo)
        | elem Carro lo || elem Arvore lo = False 
        | otherwise = True
obstaculosValidosLinha (Estrada ve,lo)
        | elem Arvore lo || elem Tronco lo = False
        |otherwise = True
