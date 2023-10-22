module Exemplo4_v1 where

import LI12223

-- Não é preciso distinguir os casos em que abs n é maior ou igual ao comprimento da lista -> usamos mod em ambos os casos
--
moverObstaculos :: Int -> [Obstaculo] -> [Obstaculo]
moverObstaculos n l
  | n > 0 = drop (length l - (n `mod` length l)) l ++ take (length l - (n `mod` length l)) l
  | n < 0 = drop ((abs n) `mod` length l) l ++ take ((abs n) `mod` length l) l 
  | otherwise = l


