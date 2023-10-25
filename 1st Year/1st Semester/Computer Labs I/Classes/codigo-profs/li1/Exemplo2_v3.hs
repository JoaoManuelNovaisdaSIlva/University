module Exempo2_v3 where

import LI12223
import Data.List

-- Usando ordem superior e funções pre-definidas

terrenoValida :: Mapa -> Bool 
terrenoValida (Mapa larg linhas) = maximoSeguido isRio ts <= 4 &&
                                   maximoSeguido isRelva ts <= 5 &&
                                   maximoSeguido isEstrada ts <= 4
                                   where
                                   ts = map fst linhas 

-- na verdade também não precisamos da lista de obstáculos
maximoSeguido :: (a -> Bool) -> [a] -> Int
maximoSeguido f = maximum . map length . filter (f . head) . groupBy (\x1 x2 -> f x1 == f x2)

isRio :: Terreno -> Bool
isRio (Rio _) = True
isRio _ = False

isEstrada :: Terreno -> Bool
isEstrada (Estrada _) = True
isEstrada _ = False

isRelva :: Terreno -> Bool
isRelva Relva = True
isRelva _ = False
