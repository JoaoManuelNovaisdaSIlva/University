module MiniTeste_Spec where

import MiniTeste
import Test.HUnit


test1 = TestCase (assertEqual "for [(2,1),(1,5),(4,6)]," [(1,4),(4,5)] (f [(2,1),(1,5),(4,6)]))
test2 = TestCase (assertEqual "for [(5, 5),(1,1),(10,-10)]," [(5, 4)] (f [(5, 5),(1,1),(10,-10)]))
test3 = TestCase (assertEqual "for [(0,0),(10,-10),(1,1)]," [] (f [(0,0),(10,-10),(1,1)]))

tests = TestList [
    TestLabel "Teste 1 [(2,1),(1,5),(4,6)]" test1,
    TestLabel "Teste 2 [(5, 5),(1,1),(10,-10)]" test2,
    TestLabel "Teste 3 [(0,0),(10,-10),(1,1)]" test3]