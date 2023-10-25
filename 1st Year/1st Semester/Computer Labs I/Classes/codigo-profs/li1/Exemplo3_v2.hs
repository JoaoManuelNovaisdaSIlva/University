module Exemplo3_v2 where

import LI12223

-- False || ... é redundante

validaPassagemEntreArvores:: [Obstaculo] -> [Obstaculo] -> Bool
validaPassagemEntreArvores [] [] = False 
validaPassagemEntreArvores (Nenhum:_) (Nenhum:_) = True
validaPassagemEntreArvores (_:xs) (_:ys) = validaPassagemEntreArvores xs ys
