module Exemplo3_v3 where

import LI12223

-- Usar zip para transformar função numa one-liner :)

validaPassagemEntreArvores:: [Obstaculo] -> [Obstaculo] -> Bool
validaPassagemEntreArvores xs ys = (Nenhum, Nenhum) `elem` zip xs ys 
-- validaPassagemEntreArvores = elem (Nenhum, Nenhum) . zip -- ainda mais lindo :)
