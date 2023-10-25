module Tarefa2_2022li1g093_Spec where

import LI12223
import Tarefa2_2022li1g093
import Test.HUnit
import TestsLibrary

testsT2 :: Test
testsT2 = TestList [test_proximosTerrenosValidos, test_proximosObstaculosValidos]

{-|
Teste da funcao proximosTerrenosValidos
-}
test_proximosTerrenosValidos :: Test
test_proximosTerrenosValidos = TestList [
           "Tarefa 2 - Mapa com as primeiras 4 linhas diferentes" ~: proximosTerrenosValidos mapa10 ~=? lista1,
           "Tarefa 2 - Mapa vazio" ~: proximosTerrenosValidos mapa11 ~=? lista1,
           "Tarefa 2 - Mapa com as primeiras 5 linhas a serem estradas e relva" ~: proximosTerrenosValidos mapa12 ~=? lista2,
           "Tarefa 2 - Mapa com as primeiras 4 linhas a serem rios" ~: proximosTerrenosValidos mapa13 ~=? lista3
           ]

test_proximosObstaculosValidos :: Test
test_proximosObstaculosValidos = TestList [
            "Tarefa 2 - Relva sem obstaculos" ~: proximosObstaculosValidos 6 obs1 ~=? listao1,
            "Tarefa 2 - Rio sem obstaculos" ~: proximosObstaculosValidos 6 obs2 ~=? listao2,
            "Tarefa 2 - Estrada sem obstaculos" ~: proximosObstaculosValidos 6 obs3 ~=? listao3,
            "Tarefa 2 - Relva com obstaculos ocupando toda a largura" ~: proximosObstaculosValidos 6 obs4 ~=? listao4,
            "Tarefa 2 - Rio com obstaculos ocupando toda a largura" ~: proximosObstaculosValidos 6 obs5 ~=? listao4,
            "Tarefa 2 - Estrada com obstaculos ocupando toda a largura" ~: proximosObstaculosValidos 6 obs6 ~=? listao4,
            "Tarefa 2 - Relva com obstaculos sem ocupar toda a largura" ~: proximosObstaculosValidos 6 obs7 ~=? listao1,
            "Tarefa 2 - Relva com arvores a preencher até largura-1" ~: proximosObstaculosValidos 6 obs8 ~=? listao5,
            "Tarefa 2 - Tronco de comprimento 5" ~: proximosObstaculosValidos 6 obs9 ~=? listao5,
            "Tarefa 2 - Carro comprimento 2" ~: proximosObstaculosValidos 6 obs10 ~=? listao3,
            "Tarefa 2 - Carro comprimento 3" ~: proximosObstaculosValidos 6 obs11 ~=? listao5
            ]


{-|
Listas de terrenos para testar a funçao proximosTerrenosValidos
-}
lista1 :: [Terreno]
lista1 = [Rio 0, Estrada 0, Relva]

lista2 :: [Terreno]
lista2 = [Rio 0]

lista3 :: [Terreno]
lista3 =  [Relva, Estrada 0]

{-|
Listas de obstaculos
-}
listao1 :: [Obstaculo]
listao1 = [Nenhum, Arvore]

listao2 :: [Obstaculo]
listao2 = [Nenhum, Tronco]

listao3 :: [Obstaculo]
listao3 = [Nenhum, Carro]

listao4 :: [Obstaculo]
listao4 = []

listao5 :: [Obstaculo]
listao5 = [Nenhum]
