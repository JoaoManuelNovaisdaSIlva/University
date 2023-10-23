module Tarefa3_2022li1g093_Spec where

import LI12223
import Tarefa3_2022li1g093
import Test.HUnit
import TestsLibrary

testsT3 :: Test
--testsT3 = TestLabel "Testes Tarefa 3" $ test ["Teste 1" ~: 1 ~=? 1]

testsT3 = TestList [testsT31, testsT32, testsT33, testsT34,testsTAnimajogo]

{- |
Teste para o exercicio 1 da tarefa 3
-}
testsT31 :: Test
testsT31 = TestList [
           "Tarefa 3 - move os obstaculos jogo1" ~: moverObstaculos jogo1 ~=? jogo1Valido,
           "Tarefa 3 - move os obstaculos jogo2" ~: moverObstaculos jogo2 ~=? jogo2Valido
           ]
{- |
Teste para exercicio 2 da tarefa 3
-}
testsT32 :: Test 
testsT32 = TestList [
           "Tarefa 3 - move o jogador jogo1 jogada1" ~: moveJogador jogo1 jogada1 ~=? jogada1Valida,
           "Tarefa 3 - move o jogador jogo2 jogada2" ~: moveJogador jogo2 jogada2 ~=? jogada2Valida
           ]
{- |
Teste para o exercício 3 da tarefa 3
-}
testsT33 :: Test
testsT33 = TestList [
           "Tarefa 3 - move jogador se estiver em tronco jogo1" ~: jogadorParadoEmTronco jogo1 ~=? jogo1ValidoMoveJogador,
           "Tarefa 3 - move jogador se estiver em tronco jogo2" ~: jogadorParadoEmTronco jogo2 ~=? jogo2ValidoMoveJogador
           ]
{- |
Teste para o exercicio 4 da tarefa 4
-}
testsT34 :: Test
testsT34 = TestList [
           "Tarefa 3 - verifica se o jogador sai do mapa jogo1" ~: limiteMapa jogo1 (Move Esquerda) ~=? jogo1,
           "Tarefa 3 - verifica se o jogador sai do mapa jogo1 (2)" ~: limiteMapa jogo1 (Move Direita) ~=? movimentovalido1
           ]
testsTAnimajogo :: Test 
testsTAnimajogo = TestList [
                  "Tarefa 3 - anima o jogo7" ~: animaJogo jogo7 (Move Cima) ~=? jogoanimado1,
                  "Tarefa 3 - anima o jogo7 (2)" ~: animaJogo jogo7 (Move Direita) ~=? jogoanimado2
                  ]


jogo1Valido :: Jogo 
jogo1Valido = (Jogo (Jogador (0,1)) (Mapa 6 [(Rio 2, [Tronco, Nenhum, Nenhum, Nenhum, Tronco, Nenhum]),
                                       (Rio (-3), [Tronco, Nenhum, Nenhum, Tronco, Nenhum, Tronco]),
                                       (Estrada 5, [Carro, Carro, Nenhum, Nenhum, Nenhum, Nenhum]),
                                       (Estrada (-11), [Nenhum, Carro, Carro, Carro, Nenhum, Nenhum]),
                                       (Relva, [Nenhum, Arvore, Arvore, Nenhum, Arvore, Nenhum])]))
jogo2Valido :: Jogo
jogo2Valido = (Jogo (Jogador (0,0)) (Mapa 5 [(Rio 13, [Tronco, Tronco, Tronco, Tronco, Tronco])]))

jogada1 :: Jogada 
jogada1 = (Move Cima)

jogada2 :: Jogada
jogada2 = (Move Direita)

jogada1Valida :: Jogo
jogada1Valida = (Jogo (Jogador (0,0)) (Mapa 6 [(Rio 2, [Nenhum, Nenhum, Tronco, Nenhum, Tronco, Nenhum]),
                                  (Rio (-3), [Tronco, Nenhum, Tronco, Tronco, Nenhum, Nenhum]),
                                  (Estrada 5, [Nenhum, Carro, Carro, Nenhum, Nenhum, Nenhum]),
                                  (Estrada (-11), [Carro, Carro, Carro, Nenhum, Nenhum, Nenhum]),
                                  (Relva, [Nenhum, Arvore, Arvore, Nenhum, Arvore, Nenhum])]))
jogada2Valida :: Jogo
jogada2Valida = (Jogo (Jogador (1,0)) (Mapa 5 [(Rio 13, [Tronco, Tronco, Tronco, Tronco, Tronco])]))

jogo1ValidoMoveJogador :: Jogo 
jogo1ValidoMoveJogador = (Jogo (Jogador (3,1)) (Mapa 6 [(Rio 2, [Tronco, Nenhum, Nenhum, Nenhum, Tronco, Nenhum]),
                                       (Rio (-3), [Tronco, Nenhum, Nenhum, Tronco, Nenhum, Tronco]),
                                       (Estrada 5, [Carro, Carro, Nenhum, Nenhum, Nenhum, Nenhum]),
                                       (Estrada (-11), [Nenhum, Carro, Carro, Carro, Nenhum, Nenhum]),
                                       (Relva, [Nenhum, Arvore, Arvore, Nenhum, Arvore, Nenhum])]))
jogo2ValidoMoveJogador :: Jogo
jogo2ValidoMoveJogador = (Jogo (Jogador (3,0)) (Mapa 5 [(Rio 13, [Tronco, Tronco, Tronco, Tronco, Tronco])]))

movimentovalido1 :: Jogo 
movimentovalido1 = (Jogo (Jogador (1,1)) (Mapa 6 [(Rio 2, [Nenhum, Nenhum, Tronco, Nenhum, Tronco, Nenhum]),
                                  (Rio (-3), [Tronco, Nenhum, Tronco, Tronco, Nenhum, Nenhum]),
                                  (Estrada 5, [Nenhum, Carro, Carro, Nenhum, Nenhum, Nenhum]),
                                  (Estrada (-11), [Carro, Carro, Carro, Nenhum, Nenhum, Nenhum]),
                                  (Relva, [Nenhum, Arvore, Arvore, Nenhum, Arvore, Nenhum])]))

jogoanimado1 :: Jogo 
jogoanimado1 = Jogo (Jogador (9,6)) (Mapa 10 [(Rio 2,[Nenhum,Tronco,Tronco,Nenhum,Tronco,Tronco,Tronco,Nenhum,Nenhum,Tronco]),
                                       (Rio (-4),[Nenhum,Tronco,Tronco,Nenhum,Tronco,Nenhum,Nenhum,Nenhum,Tronco,Tronco]),
                                       (Relva,[Arvore,Arvore,Nenhum,Nenhum,Arvore,Nenhum,Arvore,Nenhum,Arvore,Nenhum]),
                                       (Estrada 11,[Carro,Carro,Carro,Carro,Nenhum,Nenhum,Carro,Carro,Carro,Nenhum]),
                                       (Estrada (-1),[Nenhum,Carro,Nenhum,Nenhum,Carro,Carro,Nenhum,Nenhum,Nenhum,Nenhum]),
                                       (Relva,[Nenhum,Nenhum,Nenhum,Arvore,Nenhum,Arvore,Arvore,Arvore,Nenhum,Nenhum]),
                                       (Rio (-4),[Tronco,Nenhum,Nenhum,Nenhum,Tronco,Nenhum,Tronco,Nenhum,Tronco,Tronco])])
jogoanimado2 :: Jogo
jogoanimado2 = Jogo (Jogador (10,6)) (Mapa 10 [(Rio 2,[Nenhum,Tronco,Tronco,Nenhum,Tronco,Tronco,Tronco,Nenhum,Nenhum,Tronco]),
                                        (Rio (-4),[Nenhum,Tronco,Tronco,Nenhum,Tronco,Nenhum,Nenhum,Nenhum,Tronco,Tronco]),
                                        (Relva,[Arvore,Arvore,Nenhum,Nenhum,Arvore,Nenhum,Arvore,Nenhum,Arvore,Nenhum]),
                                        (Estrada 11,[Carro,Carro,Carro,Carro,Nenhum,Nenhum,Carro,Carro,Carro,Nenhum]),
                                        (Estrada (-1),[Nenhum,Carro,Nenhum,Nenhum,Carro,Carro,Nenhum,Nenhum,Nenhum,Nenhum]),
                                        (Relva,[Nenhum,Nenhum,Nenhum,Arvore,Nenhum,Arvore,Arvore,Arvore,Nenhum,Nenhum]),
                                        (Rio (-4),[Tronco,Nenhum,Nenhum,Nenhum,Tronco,Nenhum,Tronco,Nenhum,Tronco,Tronco])])
