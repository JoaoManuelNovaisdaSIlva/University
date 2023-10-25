module Tarefa1_2022li1g093_Spec where

import LI12223
import Tarefa1_2022li1g093
import Test.HUnit
import TestsLibrary

testsT1 :: Test
-- tests dado pelos docentes, testlist funciona da mesma
--testsT1 = TestLabel "Testes Tarefa 1" $ test ["Teste 1" ~: 1 ~=? 1]

testsT1 = TestList [testsT11,testsT12,testsT13,testsT14,testsT15,testsT16,testsT17]


{-| 
Testes para o exercício 1 da tarefa 1
-}

testsT11 :: Test
testsT11 = TestList [
        "Tarefa 1 - valida obstaculos mapa1" ~: validaObstaculos mapa1 ~=? True,
        "Tarefa 1 - valida obstaculos mapa2" ~: validaObstaculos mapa2 ~=? False
        ]

{- |
Testes para o exercício 2 da tarefa 2
-}

testsT12 :: Test
testsT12 = TestList [
           "Tarefa 1 - verifica direcoes dos rios mapa7" ~: verificaDirecoesRios mapa7 ~=? True,
           "Tarefa 1 - verifica direcoes dos rios mapa8" ~: verificaDirecoesRios mapa8 ~=? False
           ]

{- |
Testes para o exercicio 3 da tarefa 1
-}

testsT13 :: Test
testsT13 = TestList [
         "Tarefa 1 - valida troncos mapa3" ~: validaTronco mapa3 ~=? False,
         "Tarefa 1 - valida troncos mapa4" ~: validaTronco mapa4 ~=? True,
         "Tarefa 1 - valida troncos mapa9" ~: validaTronco mapa9 ~=? False
         ]

{- |
Testes para o exercicio 4 da tarefa 1
-}

testsT14 :: Test
testsT14 = TestList [
           "Tarefa 1 - valida carros mapa7" ~: validaCarro mapa7 ~=? True,
           "Tarefa 1 - valida carros mapa8" ~: validaCarro mapa8 ~=? False
           ]

{- |
Testes para o exercicio 5 da tarefa 1
-}

testsT15 :: Test
testsT15 = TestList [
         "Tarefa 1- valida nenhum mapa 3" ~: existeNenhumMapa mapa3 ~=? False,
         "Tarefa 1 - valida nenhum mapa 4" ~: existeNenhumMapa mapa4 ~=? True
         ]

{- |
Testes para exercicio 6 da tarefa 1
-}
testsT16 :: Test
testsT16 = TestList [
         "Tarefa 1 - valida comprimento mapa 5" ~: validaComprimentoMapa mapa5 ~=? False,
         "Tarefa 1 - valida comprimento mapa 4" ~: validaComprimentoMapa mapa4 ~=? True
         ]
{- |
Testes para exercicio 7 da tarefa 1
-}
testsT17 :: Test
testsT17 = TestList [
         "Tarefa 1 - valida contigualidade do mapa 6" ~: validaContigualidade mapa6 ~=? False,
         "Tarefa 1 - valida contigualidade do mapa 5" ~: validaContigualidade mapa5 ~=? True
         ]