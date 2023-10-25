module Exemplo4_v3 where

import LI12223
import Data.List

-- Versão que usa o cycle, não necessariamente melhor do que a outra
--
moverObstaculos :: Int -> [Obstaculo] -> [Obstaculo]
moverObstaculos v | v < 0 = deslocaEsquerda (-v) 
                  | otherwise = reverse . deslocaEsquerda v . reverse

deslocaEsquerda :: Int -> [a] -> [a]
deslocaEsquerda n l = take (length l) $ drop n $ cycle l
