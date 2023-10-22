module Exemplo3_v1 where

import LI12223

-- Usar pattern matching para juntar num só caso os últimos três casos anteriores

validaPassagemEntreArvores:: [Obstaculo] -> [Obstaculo] -> Bool
validaPassagemEntreArvores [] [] = False 
validaPassagemEntreArvores (Nenhum:_) (Nenhum:_) = True
validaPassagemEntreArvores (_:xs) (_:ys) = False || validaPassagemEntreArvores xs ys
