module Exemplo4_v2 where

import LI12223

-- Identificar padrão (drop qualquer coisa ++ take essa mesma coisa), evitar repetir código
-- Não precisamos de um caso especial para quando n é 0
--
moverObstaculos :: Int -> [Obstaculo] -> [Obstaculo]
moverObstaculos n l = drop chunk l ++ take chunk l
    where chunk = if n > 0 
                  then length l - (n `mod` length l)
                  else abs n `mod` length l


