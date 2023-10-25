module Exempo2_v1 where

import LI12223

-- Para validar os terrenos não precisamos da largura, nem mesmo das listas de obstáculos
-- Andar sempre com o tipo Mapa torna as funções mais difíceis de ler 
-- Não precisamos de dar nomes a argumentos que não usamos

terrenoValida :: Mapa -> Bool 
terrenoValida (Mapa l []) = True
terrenoValida (Mapa l xs) = (contaRios ts 0 0 <= 4) && 
                            (contaEstradas ts 0 0 <= 5) && 
                            (contaRelva ts 0 0 <= 5) 
                            where
                            ts = map fst xs

contaRios :: [Terreno] -> Int -> Int -> Int
contaRios [] _ max = max
contaRios (Rio _ : ys) i max = contaRios ys (i+1) (if (i+1) > max then (i+1) else max)
contaRios (Estrada _ : ys) _ max = contaRios ys 0 max
contaRios (Relva : ys) _ max = contaRios ys 0 max

contaEstradas :: [Terreno] -> Int -> Int -> Int
contaEstradas [] _ max = max
contaEstradas (Estrada _: ys) i max = contaEstradas ys (i+1) (if (i+1) > max then (i+1) else max)
contaEstradas (Rio _ : ys) _ max = contaEstradas ys 0 max
contaEstradas (Relva : ys) _ max = contaEstradas ys 0 max

contaRelva :: [Terreno] -> Int -> Int -> Int
contaRelva [] _ max = max
contaRelva (Relva : ys) i max = contaRelva ys (i+1) (if (i+1) > max then (i+1) else max)
contaRelva (Rio _ : ys) i max = contaRelva ys 0 max
contaRelva (Estrada _ : ys) i max = contaRelva ys 0 max
