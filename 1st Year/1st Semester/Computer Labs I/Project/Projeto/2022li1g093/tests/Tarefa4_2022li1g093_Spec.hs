module Tarefa4_2022li1g093_Spec where

import LI12223
import Tarefa4_2022li1g093
import Test.HUnit
import TestsLibrary

testsT4 :: Test
-- testsT4 = TestLabel "Testes Tarefa 4" $ test ["Teste 1" ~: 1 ~=? 1]

testsT4 = TestList [
           "Tarefa 4 - encontra posicao do jogador e verifica se e gameover ou nao jogo3" ~: jogoTerminou jogo3 ~=? True,
           "Tarefa 4 - encontra posicao do jogador e verifica se e gameover ou nao jogo4" ~: jogoTerminou jogo4 ~=? True,
           "Tarefa 4 - encontra posicao do jogador e verifica se e gameover ou nao jogo5" ~: jogoTerminou jogo5 ~=? True,
           "Tarefa 4 - encontra posicao do jogador e verifica se e gameover ou nao jogo6" ~: jogoTerminou jogo6 ~=? False
           ]
