{-# LANGUAGE DeriveGeneric, DeriveAnyClass #-}
{- |
Module      : SalvaJogo
Description : Salvar os jogos
Copyright   : João Manuel Novais da Silva <a91671@alunos.uminho.pt>
              Pedro António Pires Correia Leite Sequeira <a91660@alunos.uminho.pt>

Módulo para a realização das operações de guardar jogos do projeto de LI1 em 2022/23.
-}

module SalvaJogo where


import LI12223

import GHC.Generics
import Control.DeepSeq
import System.IO
import qualified System.IO.Strict as SIO



data JogoGuardado = 
    JogoGuardado
        Jogador
        Mapa
    deriving (Show, Read, Eq, Generic)

instance NFData JogoGuardado where rnf x = seq x ()

-- | é necessário fazer: cabal install --lib strict-io
lerSaveGame :: IO (JogoGuardado)
lerSaveGame = SIO.run $ lerSaveGame_aux "data/saveGame.txt"

lerSaveGame_aux :: String -> SIO.SIO (JogoGuardado)
lerSaveGame_aux fn = do
    handle <- SIO.openFile fn ReadMode
    c <- SIO.hGetContents handle
    SIO.hClose handle
    let (JogoGuardado j m) = (read c) :: JogoGuardado
    SIO.return' (JogoGuardado j m)

escreverSaveGame :: JogoGuardado -> IO ()
escreverSaveGame j = SIO.run $ escreverSaveGame_aux "data/saveGame.txt" (show j)

escreverSaveGame_aux :: String -> String -> SIO.SIO ()
escreverSaveGame_aux fn s = do
    handle <- SIO.openFile fn WriteMode
    SIO.hPutStr handle s
    SIO.hClose handle

guardadoParaJogo :: JogoGuardado -> Jogo
guardadoParaJogo (JogoGuardado j m) = (Jogo j m)

jogoParaGuardado :: Jogo -> JogoGuardado
jogoParaGuardado (Jogo j m) = (JogoGuardado j m)