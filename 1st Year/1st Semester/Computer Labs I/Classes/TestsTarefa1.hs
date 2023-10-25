{- |
Module      : Tarefa1_2022li1g093
Description : Testes para Tarefa1
Copyright   : João Manuel Novais da Silva <a91671@alunos.uminho.pt>
              Pedro António Pires Correia Leite Sequeira <a91660@alunos.uminho.pt>

Módulo para a realização de testes da Tarefa 1 do projeto de LI1 em 2022/23.
-}

module TestsTarefa1 where

import Test.HUnit
import LI12223
import Tarefa1_2022li1g093
import TestsLibrary

{-| 
Testes para o exercício 1 da tarefa 1
-}

testsT11 :: Test
testsT11 = TestList [
        "Tarefa 1 - valida obstáculos mapa1" ~: validaObstaculos mapa1 ~=? True,
        "Tarefa 1 - valida obstáculos mapa2" ~: validaObstaculos mapa2 ~=? False
        ]

{- |
Testes para o exercicio 3 da tarefa 1
-}

testsT13 :: Test
testsT13 = TestList [
         "Tarefa 1 - valida troncos mapa3" ~: validaTronco mapa3 ~=? False,
         "Tarefa 1 - valida troncos mapa4" ~: validaTronco mapa4 ~=? True
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
         "Tarefa 1 - valida contigualidade do mapa 6" ~: validaComprimentoMapa mapa6 ~=? False,
         "Tarefa 1 - valida contigualidade do mapa 5" ~: validaComprimentoMapa mapa5 ~=? True
         ]