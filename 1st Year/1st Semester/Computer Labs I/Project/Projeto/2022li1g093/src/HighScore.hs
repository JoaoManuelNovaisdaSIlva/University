{- |
Module      : HighScore
Description : Sistema de highscore em ficheiro
Copyright   : João Manuel Novais da Silva <a91671@alunos.uminho.pt>
              Pedro António Pires Correia Leite Sequeira <a91660@alunos.uminho.pt>

Módulo para a realização da funcionalidade de high score do projeto de LI1 em 2022/23.
-}

module HighScore where

import System.IO


type HighScore = Int 

lerFicheiro :: IO HighScore
lerFicheiro = do 
    contents <- readFile "data/highScore.txt"
    return (read (contents))

escreveHSFicheiro :: HighScore -> IO ()
escreveHSFicheiro hs = writeFile "data/highScore.txt" (show hs)

