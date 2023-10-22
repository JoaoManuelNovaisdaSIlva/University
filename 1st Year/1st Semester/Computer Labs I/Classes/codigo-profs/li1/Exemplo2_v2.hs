module Exempo2_v2 where

import LI12223

-- Identificar padrões. 
-- Juntar numa única função a lógica que era usada nas funções auxiliares

terrenoValida :: Mapa -> Bool 
terrenoValida (Mapa l xs) = (contaRios ts 0 0 <= 4) && 
                            (contaEstradas ts 0 0 <= 5) && 
                            (contaRelva ts 0 0 <= 5) 
                            where
                            ts = map fst xs

maximoSeguido :: (a -> Bool) -> [a] -> Int -> Int -> Int
maximoSeguido _ [] _ m = m
maximoSeguido f (x:xs) i m | f x = maximoSeguido f xs (i+1) (max (i+1) m)
                           | otherwise = maximoSeguido f xs 0 m

isRio :: Terreno -> Bool
isRio (Rio _) = True
isRio _ = False

isEstrada :: Terreno -> Bool
isEstrada (Estrada _) = True
isEstrada _ = False

isRelva :: Terreno -> Bool
isRelva Relva = True
isRelva _ = False

contaRios :: [Terreno] -> Int -> Int -> Int
contaRios = maximoSeguido isRio

contaEstradas :: [Terreno] -> Int -> Int -> Int
contaEstradas = maximoSeguido isEstrada

contaRelva :: [Terreno] -> Int -> Int -> Int
contaRelva = maximoSeguido isRelva
