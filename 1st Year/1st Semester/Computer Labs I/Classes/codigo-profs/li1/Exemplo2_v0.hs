module Exemplo2 where

import LI12223

-- Função que valida se o mapa cumpre as regras relativas ao número de terrenos iguais contíguos
terrenoValida :: Mapa -> Bool
terrenoValida (Mapa l []) = True
terrenoValida  (Mapa l xs) 
  | ((contaRios (Mapa l xs) 0 0) <= 4) && 
    ((contaEstradas (Mapa l xs) 0 0) <= 5) && 
    ((contaRelva (Mapa l xs) 0 0) <= 5)  = True 
  | otherwise = False

contaRios :: Mapa -> Int -> Int -> Int
contaRios (Mapa l []) i max = (if i > max then i else max)
contaRios (Mapa l ((Rio v1, (o1)):(Rio v2, (o2)): ys)) i max = contaRios (Mapa l ((Rio v2, (o2)): ys)) (i+1) max
contaRios (Mapa l ((Rio v, (o)): ys)) i max = contaRios (Mapa l ys) (i+1) (if (i+1) > max then (i+1) else max)
contaRios (Mapa l ((Estrada v, (o)): ys)) i max = contaRios (Mapa l ys) 0 max
contaRios (Mapa l ((Relva, (o)): ys)) i max = contaRios (Mapa l ys) 0 max

contaEstradas :: Mapa -> Int -> Int -> Int
contaEstradas (Mapa l []) i max = (if i > max then i else max)
contaEstradas (Mapa l ((Estrada v1, (o1)):(Estrada v2, (o2)): ys)) i max = contaEstradas (Mapa l ((Estrada v2, (o2)): ys)) (i+1) max
contaEstradas (Mapa l ((Estrada v, (o)): ys)) i max = contaEstradas (Mapa l ys) (i+1) (if (i+1) > max then (i+1) else max)
contaEstradas (Mapa l ((Rio v, (o)): ys)) i max = contaEstradas (Mapa l ys) 0 max
contaEstradas (Mapa l ((Relva, (o)): ys)) i max = contaEstradas (Mapa l ys) 0 max

contaRelva :: Mapa -> Int -> Int -> Int
contaRelva (Mapa l []) i max = (if i > max then i else max)
contaRelva (Mapa l ((Relva, (o1)):(Relva, (o2)): ys)) i max = contaRelva (Mapa l ((Relva, (o2)): ys)) (i+1) max
contaRelva (Mapa l ((Relva, (o)): ys)) i max = contaRelva (Mapa l ys) (i+1) (if (i+1) > max then (i+1) else max)
contaRelva (Mapa l ((Rio v, (o)): ys)) i max = contaRelva (Mapa l ys) 0 max
contaRelva (Mapa l ((Estrada v, (o)): ys)) i max = contaRelva (Mapa l ys) 0 max

