module Exemplo1_v3 where

import LI12223

-- Eliminar guardas desnecessárias:
-- f | condicao = True
--   | otherwise = False
-- é o mesmo que:
-- f = condicao

obstaculosValidos :: Mapa -> Bool
obstaculosValidos (Mapa _ []) = True
obstaculosValidos (Mapa lr (l:ls)) | obstaculosValidosLinha l = obstaculosValidos (Mapa lr ls)
                                   | otherwise = False

obstaculosValidosLinha :: (Terreno, [Obstaculo]) -> Bool
obstaculosValidosLinha (Relva,lo) = not $ elem Carro lo || elem Tronco lo 
obstaculosValidosLinha ((Rio vr),lo)= not $ elem Carro lo || elem Arvore lo 
obstaculosValidosLinha ((Estrada ve),lo)= not $ elem Arvore lo || elem Tronco lo 

