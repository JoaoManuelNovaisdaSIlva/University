module Ficha2 where

removeComInicial :: Char -> [String] -> [String]
removeComInicial c [] = []
removeComInicial c (h:t) = if c == head h then removeComInicial c t else h : removeComInicial c t