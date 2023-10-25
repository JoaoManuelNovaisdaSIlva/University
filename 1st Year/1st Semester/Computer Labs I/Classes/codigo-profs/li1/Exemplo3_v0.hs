module Exemplo3 where

import LI12223

-- Função que testa se há passagem para o jogador entre duas linhas de Relva

validaPassagemEntreArvores:: [Obstaculo] -> [Obstaculo] -> Bool
validaPassagemEntreArvores [] [] = False 
validaPassagemEntreArvores (x:xs) (y:ys)
  | (x == Nenhum && y == Nenhum) = True
  | (x == Arvore && y == Arvore) = False || (validaPassagemEntreArvores xs ys)
  | (x == Nenhum && y == Arvore) = False || (validaPassagemEntreArvores xs ys)
  | (x == Arvore && y == Nenhum) = False || (validaPassagemEntreArvores xs ys)

