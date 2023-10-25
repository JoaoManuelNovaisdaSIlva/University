{- |
Module      : Tarefa1_2022li1g093
Description : Validação de um mapa
Copyright   : João Manuel Novais da Silva <a91671@alunos.uminho.pt>
              Pedro António Pires Correia Leite Sequeira <a91660@alunos.uminho.pt>

Módulo para a realização da Tarefa 1 do projeto de LI1 em 2022/23.
-}
module Tarefa1_2022li1g093 where

import LI12223

mapaValido :: Mapa -> Bool
mapaValido (Mapa 0 l) = False
mapaValido (Mapa x []) = False
mapaValido mapa = validaObstaculos mapa && verificaDirecoesRios mapa && validaTronco mapa && validaCarro mapa && existeNenhumMapa mapa && validaComprimentoMapa mapa && validaContigualidade mapa 

{- |
Exercício 1:
A função validaObstaculos, verifica se existem obstáculos em sítios que não devem estar,
por exemplo, árvores em rios, troncos em estradas, etc. É utilizada uma função auxiliar
para ser mais fácil percorrer a lista de obstáculos dependendo do tipo de terreno, e pelo
facto de 2 dos 3 terrenos serem "tipados" como um conjunto de 2 propriendades, o tipo de terreno
e a velocidade dos obstáculos
-}

validaObstaculos :: Mapa -> Bool
validaObstaculos (Mapa x ((terr,o):t)) = percorreOstaculos terr o


percorreOstaculos :: Terreno -> [Obstaculo] -> Bool
percorreOstaculos terr [] = True
percorreOstaculos (Rio x) (h:t) = if h == Tronco || h == Nenhum then percorreOstaculos (Rio x) t else False
percorreOstaculos (Estrada x) (h:t) = if h == Carro || h == Nenhum then percorreOstaculos (Estrada x ) t else False
percorreOstaculos Relva (h:t) = if h == Arvore || h == Nenhum then percorreOstaculos Relva t else False

{- |
Exercício 2:
A função @verificaDirecoesRios@ , verifica se, todos os rios contíguos tem velociadas para direções diferentes, ou seja,
se um rio tem velocidade de 2 o próximo rio contíguo tem que ter velocidade negativa.
-}

verificaDirecoesRios :: Mapa -> Bool
verificaDirecoesRios (Mapa l ((terr,o):t)) = verificaDirecoesRios_aux ((terr,o):t) 0

verificaDirecoesRios_aux :: [(Terreno, [Obstaculo])] -> Int -> Bool
verificaDirecoesRios_aux [] _ = True
verificaDirecoesRios_aux ((Relva,o):t) v = verificaDirecoesRios_aux t 0
verificaDirecoesRios_aux (((Estrada x),o):t) v = verificaDirecoesRios_aux t 0
verificaDirecoesRios_aux ((Rio x, (ob:obs)):t) v
    | v == 0 = verificaDirecoesRios_aux t x
    | v > 0 && x > 0 = False
    | v < 0 && x < 0 = False
    | otherwise = verificaDirecoesRios_aux t x   

{- |
Exercício 3:
A função @validaTronco@ , verifica se, para as linhas de Rio, apenas existem troncos com tamanho
menor que 5. Caso contrário devolve False invalidando o mapa. Foi necessário usar um acumulador
(__acc__) para contar o número de vezes que o obstáculo tronco ocorre na linha. Foi, mais tarde, pensado como 
o programa deveria reagir se existi-se uma lista de obstáculos que, devido á regra de todos os objetos que saem
dum lado da lista voltam a entrar do outro lado, representasse um tronco com tamanho maior que 5 separado nos cantos
da lista, ou seja, a lista: [Tronco, Tronco, Tronco, Tronco, Tronco, Nenhum, Tronco] apresenta um tronco de tamanho 6
que está dividido no final da lista.
-}

validaTronco :: Mapa -> Bool
validaTronco (Mapa x ((terr,(ob:obs)):t)) = validaTronco_aux ((terr,(ob:obs)):t) 0 && validaTroncosSeparados (Mapa x ((terr,(ob:obs)):t))

validaTronco_aux :: [(Terreno,[Obstaculo])] -> Int -> Bool
validaTronco_aux [] _ = True 
validaTronco_aux ((Relva,o):t) acc = validaTronco_aux t acc
validaTronco_aux (((Estrada x),o):t) acc = validaTronco_aux t acc
validaTronco_aux (((Rio x),[]):t) acc = if acc > 5 then False else validaTronco_aux t 0
validaTronco_aux (((Rio x),(ob:obs)):t) acc
    | acc > 5 = False
    | ob == Tronco = validaTronco_aux (((Rio x),obs):t) (acc+1)
    | otherwise = validaTronco_aux (((Rio x),obs):t) 0

validaTroncosSeparados :: Mapa -> Bool
validaTroncosSeparados (Mapa l []) = True
validaTroncosSeparados (Mapa l ((Relva, (obs)):t)) = validaTroncosSeparados (Mapa l t)
validaTroncosSeparados (Mapa l ((Estrada x,(obs)):t)) = validaTroncosSeparados (Mapa l t)
validaTroncosSeparados (Mapa l ((Rio x, obs):t))
    | (contaTroncosSeguidosEsquerda obs 0) + (contaTroncosSeguidosDireita (reverse(obs)) 0)> 5 = False
    | otherwise = validaTroncosSeparados (Mapa l t)

contaTroncosSeguidosEsquerda :: [Obstaculo] -> Int -> Int
contaTroncosSeguidosEsquerda (ob:obs) acc
    | ob == Tronco = contaTroncosSeguidosEsquerda obs (acc+1)
    | otherwise = acc 

contaTroncosSeguidosDireita :: [Obstaculo] -> Int -> Int
contaTroncosSeguidosDireita (ob:obs) acc
    | ob == Tronco = contaTroncosSeguidosDireita obs (acc+1)
    | otherwise = acc

{- |
Exercício 4:
A função @validaCarro@ , verifica se, para as linhas de Estrada, apenas existem carros com tamanho
menor ou igual a 3. Caso contrário devolve False invalidando o mapa. Foi necessário usar um acumulador
(__acc__) para contar o número de vezes que o obstáculo carro ocorre na linha. Foi, mais tarde, pensado como 
o programa deveria reagir se existi-se uma lista de obstáculos que, devido á regra de todos os objetos que saem
dum lado da lista voltam a entrar do outro lado, representasse um carro com tamanho maior que 3 separado nos cantos
da lista, ou seja, a lista: [Carro, Carro, Carro, Nenhum, Carro] apresenta um carro de tamanho 4 que está dividido 
no final da lista.
-}

validaCarro :: Mapa -> Bool
validaCarro (Mapa x ((terr,(ob:obs)):t)) = validaCarro_aux ((terr,(ob:obs)):t) 0 && validaCarrosSeparados (Mapa x ((terr,(ob:obs)):t))

validaCarro_aux :: [(Terreno,[Obstaculo])] -> Int -> Bool
validaCarro_aux [] _ = True 
validaCarro_aux ((Relva,o):t) acc = validaCarro_aux t acc
validaCarro_aux (((Rio x),o):t) acc = validaCarro_aux t acc
validaCarro_aux (((Estrada x),[]):t) acc = if acc > 3 then False else validaCarro_aux t 0
validaCarro_aux (((Estrada x),(ob:obs)):t) acc
    | acc > 3 = False
    | ob == Carro = validaCarro_aux (((Estrada x),obs):t) (acc+1)
    | otherwise = validaCarro_aux (((Estrada x),obs):t) 0

validaCarrosSeparados :: Mapa -> Bool
validaCarrosSeparados (Mapa l []) = True
validaCarrosSeparados (Mapa l ((Relva, (obs)):t)) = validaCarrosSeparados (Mapa l t)
validaCarrosSeparados (Mapa l ((Rio x,(obs)):t)) = validaCarrosSeparados (Mapa l t)
validaCarrosSeparados (Mapa l ((Estrada x, obs):t))
    | (contaCarrosSeguidosEsquerda obs 0) + (contaCarrosSeguidosDireita (reverse(obs)) 0)> 3 = False
    | otherwise = validaCarrosSeparados (Mapa l t)

contaCarrosSeguidosEsquerda :: [Obstaculo] -> Int -> Int
contaCarrosSeguidosEsquerda (ob:obs) acc
    | ob == Carro = contaCarrosSeguidosEsquerda obs (acc+1)
    | otherwise = acc 

contaCarrosSeguidosDireita :: [Obstaculo] -> Int -> Int
contaCarrosSeguidosDireita (ob:obs) acc
    | ob == Carro = contaCarrosSeguidosDireita obs (acc+1)
    | otherwise = acc

{- |
Exercício 5:
A função @existeNenhumMapa@ , verifica se, cada linha do mapa, com a ajuda da função @existeNenhumLinha@, tem pelo menos 
uma célula "Nenhum" para haver pelo menos um caminho possível para o jogador. Mais tarde será relevante também
verificar se as células Nenhum de cada linha estão alinhadas para, realmente haver caminho possível.
-}

existeNenhumMapa :: Mapa -> Bool
existeNenhumMapa (Mapa l []) = True
existeNenhumMapa (Mapa l ((terr,obs):t)) = if existeNenhumlinha obs == False then False else existeNenhumMapa (Mapa l t)

existeNenhumlinha :: [Obstaculo] -> Bool
existeNenhumlinha [] = False
existeNenhumlinha (ob:obs) = if ob == Nenhum then True else existeNenhumlinha obs

{- |
Exercício 6: 
A função @validaComprimentoMapa@ , verifica se, o tamanho da lista de obstáculos corresponde á largura indicada no mapa.
-}

validaComprimentoMapa :: Mapa -> Bool
validaComprimentoMapa (Mapa l []) = True
validaComprimentoMapa (Mapa l ((terr,(ob:obs)):t)) = if l == length (ob:obs) then validaComprimentoMapa (Mapa l t) else False

{- |
Exercício 7:
A função @validaContigualidade@ , verifica se, existem mais de 4 linhas seguidas que são Rios e mais que 5 linhas de estradas
e relva.
-}

validaContigualidade :: Mapa -> Bool
validaContigualidade (Mapa l ((terr,obs):t)) = validaContigualidade_aux (Mapa l ((terr,obs):t)) 0

validaContigualidade_aux :: Mapa -> Int -> Bool
validaContigualidade_aux (Mapa l []) acc = True
validaContigualidade_aux (Mapa l ((Rio x, obs):t)) acc = if numeroContigualidade_Rio (Mapa l t) (acc+1) > 4 then False else validaContigualidade_aux (Mapa l t) 0
validaContigualidade_aux (Mapa l ((Relva, obs):t)) acc = if numeroContigualidade_Relva (Mapa l t) (acc+1) > 5 then False else validaContigualidade_aux (Mapa l t) 0
validaContigualidade_aux (Mapa l ((Estrada x, obs):t)) acc = if numeroContigualidade_Estrada (Mapa l t) (acc+1) > 5 then False else validaContigualidade_aux (Mapa l t) 0


numeroContigualidade_Rio :: Mapa -> Int -> Int
numeroContigualidade_Rio (Mapa l []) acc = acc
numeroContigualidade_Rio (Mapa l ((Rio x, obs):t)) acc = numeroContigualidade_Rio (Mapa l t) (acc+1)
numeroContigualidade_Rio (Mapa l ((terr,obs):t)) acc = acc

numeroContigualidade_Relva :: Mapa -> Int -> Int
numeroContigualidade_Relva (Mapa l []) acc = acc
numeroContigualidade_Relva (Mapa l ((Relva, obs):t)) acc = numeroContigualidade_Relva (Mapa l t) (acc+1)
numeroContigualidade_Relva (Mapa l ((terr,obs):t)) acc = acc

numeroContigualidade_Estrada :: Mapa -> Int -> Int
numeroContigualidade_Estrada (Mapa l []) acc = acc
numeroContigualidade_Estrada (Mapa l ((Estrada x, obs):t)) acc = numeroContigualidade_Estrada (Mapa l t) (acc+1)
numeroContigualidade_Estrada (Mapa l ((terr,obs):t)) acc = acc