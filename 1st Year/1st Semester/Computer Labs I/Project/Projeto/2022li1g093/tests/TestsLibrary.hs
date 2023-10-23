{- |
Module      : Tarefa1_2022li1g093
Description : Livraria com objetos criados pelos alunos usados nos teste
Copyright   : João Manuel Novais da Silva <a91671@alunos.uminho.pt>
              Pedro António Pires Correia Leite Sequeira <a91660@alunos.uminho.pt>

Módulo para livraria de objetos usados nos testes do projeto de LI1 em 2022/23.
-}

module TestsLibrary where

import LI12223

{-| 
Mapa para teste criado corretamente
-}
mapa1 :: Mapa
mapa1 = (Mapa 5 [(Rio 2, [Nenhum, Tronco, Tronco, Nenhum, Nenhum]),
           (Relva, [Arvore, Nenhum, Arvore, Arvore, Nenhum]),
           (Relva, [Nenhum, Nenhum, Arvore, Nenhum, Nenhum]),
           (Estrada 5, [Nenhum, Carro, Carro, Nenhum, Nenhum])])
{-|
Mapa para teste criado com 2 erros, (Um carro num rio, e um tronco na relva)
-}
mapa2 :: Mapa
mapa2 = (Mapa 5 [(Rio 2, [Nenhum, Tronco, Carro, Nenhum, Nenhum]),
           (Relva, [Arvore, Tronco, Arvore, Arvore, Nenhum]),
           (Relva, [Nenhum, Nenhum, Arvore, Nenhum, Nenhum]),
           (Estrada 5, [Nenhum, Carro, Carro, Nenhum, Nenhum])])
{- |
Mapa com 5 troncos seguidos e, portanto, com uma linha sem "Nenhum"
-}
mapa3 :: Mapa
mapa3 = (Mapa 7 [(Rio 2, [Nenhum, Tronco, Tronco, Nenhum, Nenhum, Nenhum, Nenhum]),
           (Relva, [Arvore, Nenhum, Arvore, Arvore, Nenhum, Nenhum, Nenhum]),
           (Rio 3, [Tronco, Tronco, Tronco, Tronco, Tronco, Tronco, Tronco]),
           (Estrada 5, [Nenhum, Carro, Carro, Nenhum, Nenhum, Nenhum, Nenhum])])
{- |
Mapa valido para testar se o validaTronco funciona como pretendido
-}
mapa4 :: Mapa
mapa4 = (Mapa 6 [(Rio 2, [Nenhum, Tronco, Tronco, Nenhum, Nenhum, Nenhum]),
           (Relva, [Arvore, Nenhum, Arvore, Arvore, Nenhum, Nenhum]),
           (Rio 3, [Tronco, Tronco, Nenhum, Tronco, Tronco, Tronco]),
           (Estrada 5, [Nenhum, Carro, Carro, Nenhum, Nenhum, Nenhum])])
{- |
Mapa com comprimento da lista de obstáculos diferente da largura indicada
-}
mapa5 :: Mapa
mapa5 = (Mapa 6 [(Rio 2, [Nenhum, Tronco, Tronco, Nenhum, Nenhum, Nenhum]),
           (Relva, [Arvore, Nenhum, Arvore, Arvore, Nenhum, Nenhum]),
           (Rio 3, [Tronco, Tronco, Tronco, Tronco, Tronco, Tronco, Nenhum]),
           (Estrada 5, [Nenhum, Carro, Carro, Nenhum, Nenhum, Nenhum])])
{- | 
Mapa com um maior número de linhas
-}
mapa6 :: Mapa
mapa6 = (Mapa 6 [(Rio 2, [Nenhum, Tronco, Tronco, Nenhum, Nenhum, Nenhum]),
           (Relva, [Arvore, Nenhum, Arvore, Arvore, Nenhum, Nenhum]),
           (Rio 3, [Tronco, Tronco, Tronco, Tronco, Tronco, Tronco]),
           (Estrada 5, [Nenhum, Carro, Carro, Nenhum, Nenhum, Nenhum]),
           (Estrada 5, [Nenhum, Carro, Carro, Nenhum, Nenhum, Nenhum]),
           (Estrada 5, [Nenhum, Carro, Carro, Nenhum, Nenhum, Nenhum]),
           (Estrada 5, [Nenhum, Carro, Carro, Nenhum, Nenhum, Nenhum]),
           (Estrada 5, [Nenhum, Carro, Carro, Nenhum, Nenhum, Nenhum]),
           (Estrada 5, [Nenhum, Carro, Carro, Nenhum, Nenhum, Nenhum]),
           (Rio 5, [Nenhum, Carro, Carro, Nenhum, Nenhum, Nenhum])])
{- |
Mapas para testar direções dos rios
-}
mapa7 :: Mapa
mapa7 = (Mapa 5 [(Rio 2, [Nenhum, Tronco, Tronco, Nenhum, Nenhum]),
           (Rio (-2), [Tronco, Nenhum, Tronco, Tronco, Nenhum]),
           (Relva, [Nenhum, Nenhum, Arvore, Nenhum, Nenhum]),
           (Estrada 5, [Carro, Carro, Carro, Nenhum, Nenhum])])

mapa8 :: Mapa
mapa8 = (Mapa 5 [(Rio 2, [Nenhum, Tronco, Tronco, Nenhum, Nenhum]),
           (Rio 2, [Tronco, Nenhum, Tronco, Tronco, Nenhum]),
           (Relva, [Nenhum, Nenhum, Arvore, Nenhum, Nenhum]),
           (Estrada 5, [Carro, Carro, Nenhum, Nenhum, Carro]),
           (Estrada 2, [Carro,Carro, Carro,Nenhum, Carro])])

mapa9 :: Mapa
mapa9 = (Mapa 7 [(Rio 2, [Nenhum, Tronco, Tronco, Nenhum, Nenhum, Nenhum, Nenhum]),
           (Relva, [Arvore, Nenhum, Arvore, Arvore, Nenhum, Nenhum, Nenhum]),
           (Rio 3, [Tronco, Tronco, Tronco, Tronco, Tronco, Nenhum, Tronco]),
           (Estrada 5, [Nenhum, Carro, Carro, Nenhum, Nenhum, Nenhum, Nenhum])])
{-| 
Mapas para testar a funcao proximos terrenos validos
-}

mapa10 :: Mapa
mapa10 = (Mapa 2 [(Relva, [Arvore, Nenhum]), 
            (Estrada 2, [Carro, Nenhum]), 
            (Rio 2,[Tronco, Nenhum]), 
            (Relva, [Nenhum, Arvore]),
            (Estrada 1, [Nenhum, Carro])])

mapa11 :: Mapa
mapa11 = (Mapa 3 [])

mapa12 :: Mapa
mapa12 = (Mapa 2 [(Estrada 3, [Carro, Nenhum]), 
            (Estrada 3, [Nenhum, Nenhum]), 
            (Relva,[Nenhum, Nenhum]), 
            (Estrada 3, [Nenhum, Carro]),
            (Relva, [Nenhum, Arvore])])

mapa13 :: Mapa
mapa13 = (Mapa 2 [(Rio 3, [Tronco, Nenhum]), 
            (Rio 3, [Nenhum, Tronco]), 
            (Rio 3,[Tronco, Nenhum]), 
            (Rio 3, [Nenhum, Tronco]),
            (Estrada 4, [Nenhum, Carro])])

{-|
Linhas de terrenos para testar na função @proximosObstaculosValidos@ 
-}

obs1 :: (Terreno, [Obstaculo])
obs1 = (Relva, [])


obs2 :: (Terreno, [Obstaculo])
obs2 = (Rio 0, [])

obs3 :: (Terreno, [Obstaculo])
obs3 = (Estrada 0, [])

obs4 :: (Terreno, [Obstaculo])
obs4 = (Relva, [Nenhum, Nenhum, Nenhum, Nenhum, Nenhum, Nenhum])

obs5 :: (Terreno, [Obstaculo])
obs5 = (Rio 0, [Nenhum, Nenhum, Nenhum, Nenhum, Nenhum, Nenhum])

obs6 :: (Terreno, [Obstaculo])
obs6 = (Estrada 0, [Nenhum, Nenhum, Nenhum, Nenhum, Nenhum, Nenhum])

obs7 :: (Terreno, [Obstaculo])
obs7 = (Relva, [Arvore, Arvore])

obs8 :: (Terreno, [Obstaculo])
obs8 = (Relva, [Arvore, Arvore, Arvore, Arvore, Arvore])

obs9 :: (Terreno, [Obstaculo])
obs9 = (Rio 0, [Tronco, Tronco, Tronco, Tronco, Tronco])

obs10 :: (Terreno, [Obstaculo])
obs10 = (Estrada 0, [Carro, Carro, Nenhum, Nenhum, Nenhum])

obs11 :: (Terreno, [Obstaculo])
obs11 = (Estrada 0, [Carro, Carro, Nenhum, Nenhum, Carro])

{- |
Jogo para teste
-}
jogo1 :: Jogo
jogo1 = (Jogo (Jogador (0,1)) (Mapa 6 [(Rio 2, [Nenhum, Nenhum, Tronco, Nenhum, Tronco, Nenhum]),
                                  (Rio (-3), [Tronco, Nenhum, Tronco, Tronco, Nenhum, Nenhum]),
                                  (Estrada 5, [Nenhum, Carro, Carro, Nenhum, Nenhum, Nenhum]),
                                  (Estrada (-11), [Carro, Carro, Carro, Nenhum, Nenhum, Nenhum]),
                                  (Relva, [Nenhum, Arvore, Arvore, Nenhum, Arvore, Nenhum])]))
{- |
Jogo de uma linha para teste 
-}
jogo2 :: Jogo
jogo2 = (Jogo (Jogador (0,0)) (Mapa 5 [(Rio 13, [Tronco, Tronco, Tronco, Tronco, Tronco])]))
{- |
Jogos em que o jogador perdeu o jogo
-}
jogo3 :: Jogo 
jogo3 = (Jogo (Jogador (2,1)) (Mapa 6 [(Relva, [Arvore, Nenhum, Arvore, Arvore, Arvore, Nenhum]),
                                     (Rio 2, [Tronco, Nenhum, Nenhum, Nenhum, Tronco, Tronco]),
                                     (Rio (-2), [Tronco, Tronco, Tronco, Tronco, Nenhum, Nenhum]),
                                     (Estrada 5, [Carro, Nenhum, Nenhum, Nenhum, Carro, Carro]),
                                     (Estrada (-10), [Nenhum, Nenhum, Nenhum, Carro, Carro, Carro])]))
jogo4 :: Jogo 
jogo4 = (Jogo (Jogador (0,3)) (Mapa 6 [(Relva, [Arvore, Nenhum, Arvore, Arvore, Arvore, Nenhum]),
                                     (Rio 2, [Tronco, Nenhum, Nenhum, Nenhum, Tronco, Tronco]),
                                     (Rio (-2), [Tronco, Tronco, Tronco, Tronco, Nenhum, Nenhum]),
                                     (Estrada 5, [Carro, Nenhum, Nenhum, Nenhum, Carro, Carro]),
                                     (Estrada (-10), [Nenhum, Nenhum, Nenhum, Carro, Carro, Carro])]))
jogo5 :: Jogo 
jogo5 = (Jogo (Jogador (-1,1)) (Mapa 6 [(Relva, [Arvore, Nenhum, Arvore, Arvore, Arvore, Nenhum]),
                                     (Rio 2, [Tronco, Nenhum, Nenhum, Nenhum, Tronco, Tronco]),
                                     (Rio (-2), [Tronco, Tronco, Tronco, Tronco, Nenhum, Nenhum]),
                                     (Estrada 5, [Carro, Nenhum, Nenhum, Nenhum, Carro, Carro]),
                                     (Estrada (-10), [Nenhum, Nenhum, Nenhum, Carro, Carro, Carro])]))
jogo6 :: Jogo 
jogo6 = (Jogo (Jogador (1,0)) (Mapa 6 [(Relva, [Arvore, Nenhum, Arvore, Arvore, Arvore, Nenhum]),
                                     (Rio 2, [Tronco, Nenhum, Nenhum, Nenhum, Tronco, Tronco]),
                                     (Rio (-2), [Tronco, Tronco, Tronco, Tronco, Nenhum, Nenhum]),
                                     (Estrada 5, [Carro, Nenhum, Nenhum, Nenhum, Carro, Carro]),
                                     (Estrada (-10), [Nenhum, Nenhum, Nenhum, Carro, Carro, Carro])]))

jogo7 :: Jogo 
jogo7 = (Jogo (Jogador (3,6)) (Mapa 10 [(Rio 2, [Tronco, Nenhum, Tronco, Tronco, Tronco, Nenhum, Nenhum, Tronco, Nenhum, Tronco]),
                                        (Rio (-4), [Nenhum, Nenhum, Tronco, Tronco, Nenhum , Tronco, Tronco, Nenhum, Tronco, Nenhum]),
                                        (Relva, [Arvore, Arvore, Nenhum, Nenhum, Arvore, Nenhum, Arvore, Nenhum, Arvore, Nenhum]),
                                        (Estrada 11, [Carro, Carro, Carro, Nenhum, Nenhum, Carro, Carro, Carro, Nenhum, Carro]),
                                        (Estrada (-1), [Nenhum, Nenhum, Carro, Nenhum, Nenhum, Carro, Carro, Nenhum, Nenhum, Nenhum]),
                                        (Relva, [Nenhum, Nenhum, Nenhum, Arvore, Nenhum, Arvore, Arvore, Arvore, Nenhum, Nenhum]),
                                        (Rio (-4), [Tronco, Nenhum, Tronco, Tronco, Tronco, Nenhum, Nenhum, Nenhum, Tronco, Nenhum])]))