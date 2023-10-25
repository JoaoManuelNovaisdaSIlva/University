module Exemplo4 where

import LI12223

-- Função que desliza obstáculos com base na velocidade
--
moverObstaculos :: Int -> [Obstaculo] -> [Obstaculo]
moverObstaculos n l
  | n > 0 && n < length l = drop (length l - (abs n)) l ++ take (length l - (abs n)) l
  | n > 0 && n > length l = drop (length l - (mod n (length l))) l ++ take (length l - (mod n (length l))) l
  | n < 0 && (abs n) < length l = drop (abs n) l ++ take (abs n) l
  | n < 0 && (abs n) > length l = drop (length l - (mod n (length l))) l ++ take (length l - (mod n (length l))) l
  | otherwise = l


