{-|
Module : Exemplo
Description : Módulo Haskell contendo exemplos de funções recursivas
Copyright : Alguém <alguem@algures.com>;
Outro alguém <outro@algures.com>
Este módulo contém definições Haskell para o cálculo de funções
recursivas simples (obs: isto é apenas uma descrição mais
longa do módulo para efeitos de documentação...).
-}
module Ficha5 where

import Test.HUnit

{-|
A função my sum some dois inteiros
-}

mysum :: Int -> Int -> Int
mysum x y = x+y

test1 = TestCase (assertEqual "for 1+2," 3 (mysum 1 2))
test2 = TestCase (assertEqual "for 5+5," 10 (mysum 5 5))
test3 = TestCase (assertEqual "for 100+100," 100 (mysum 100 100))

tests = TestList [
    TestLabel "Teste 1 (1, 2)" test1,
    TestLabel "Teste 2 (5, 5)" test2,
    TestLabel "Teste 3 (100, 100)" test3]

-- TestLabel "Teste 1 (1, 2)" test1
-- é a mesma coisa que --
-- "Teste 1 (1, 2)" ∼: test1