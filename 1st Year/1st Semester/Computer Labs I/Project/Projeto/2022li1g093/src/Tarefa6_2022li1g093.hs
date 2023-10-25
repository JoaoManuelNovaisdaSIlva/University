{- |
Module      : Tarefa6_2022li1g093
Description : Aplicação Gráfica Completa
Copyright   : João Manuel Novais da Silva <a91671@alunos.uminho.pt>
              Pedro António Pires Correia Leite Sequeira <a91660@alunos.uminho.pt>

Módulo para a realização da Tarefa 6 do projeto de LI1 em 2022/23.
-}

module Tarefa6_2022li1g093 where

import LI12223
import SalvaJogo
import Graphics.Gloss
import Graphics.Gloss.Juicy (loadJuicyPNG, loadJuicyJPG)
import Graphics.Gloss.Interface.IO.Game
import Graphics.Gloss.Interface.Environment
import Graphics.Gloss.Data.Bitmap
import System.Exit
import Data.List
import Data.Maybe

import HighScore
import SalvaJogo
import Tarefa5_2022li1g093
import Tarefa3_2022li1g093
import Tarefa4_2022li1g093
import Tarefa2_2022li1g093

-- | É IMPORTANTE COMPILAR O CODIGO USANDO O COMANDO: LD_PRELOAD=/usr/lib/x86_64-linux-gnu/libglut.so.3 ghci
data Estado = Estado{
    menu :: Menu,
    jogo :: Jogo,
    tempo :: Float,
    highScore :: HighScore,
    socreAtual :: Int
}


data OpcaoJogar
    = Jogar 
    | Opcoes Bool
    | Creditos Bool
    | Sair
    deriving Eq

data OpcaoLoad
    = IniciarJogo Bool Int
    | CarregarJogo Bool Int
    deriving Eq

data OpcaoOver
    = VoltarAoMenu
    | TentarNovamente
    deriving Eq

data Menu 
    = MenuJogo OpcaoJogar
    | MenuLoad OpcaoLoad
    | MenuGameOver OpcaoOver
    deriving Eq

-- | Definição do tamanho e do nome da janela assim como a posição inicial da janela
window :: Display
window = InWindow "Crossy Road" (800,800) (0,0)

-- | Definição da framerate
fr :: Int 
fr = 1

-- | Função principal para reagir aos vário enventos
reageEvento :: Event -> Estado -> IO Estado
reageEvento ev e@(Estado (MenuJogo _) _ _ _ _) = reageEventoMenuJogo ev e
reageEvento ev e@(Estado (MenuLoad _) _ _ _ _) = reageEventoMenuSave ev e
reageEvento ev e@(Estado (MenuGameOver _) _ _ _ _) = reageEventoMenuGameOver ev e

-- | Função para reagir aos eventos do menuJogo
reageEventoMenuJogo :: Event -> Estado -> IO Estado
reageEventoMenuJogo (EventKey (SpecialKey KeyEnter) Down _ _) e@(Estado (MenuJogo Jogar) _ _ _ _) = return $ e{menu = MenuLoad (IniciarJogo False 0)}
reageEventoMenuJogo (EventKey (SpecialKey KeyEnter) Down _ _) e@(Estado (MenuJogo (Opcoes False)) _ _ _ _) = return $ e{menu = MenuJogo (Opcoes True)}
reageEventoMenuJogo (EventKey (SpecialKey KeyEsc) Down _ _) e@(Estado (MenuJogo (Opcoes True)) _ _ _ _) = return $ e{menu = MenuJogo (Opcoes False)}
reageEventoMenuJogo (EventKey (SpecialKey KeyEnter) Down _ _) e@(Estado (MenuJogo (Creditos False)) _ _ _ _) = return $ e{menu = MenuJogo (Creditos True)}
reageEventoMenuJogo (EventKey (SpecialKey KeyEsc) Down _ _) e@(Estado (MenuJogo (Creditos True)) _ _ _ _) = return $ e{menu = MenuJogo (Creditos False)}
reageEventoMenuJogo (EventKey (SpecialKey KeyEnter) Down _ _) e@(Estado (MenuJogo Sair) _ _ _ _) =
    do exitSuccess
reageEventoMenuJogo (EventKey (SpecialKey KeyUp) Down _ _) e@(Estado _ _ _ _ _) = if obtemMenu Cima e /= Nothing then
    do return $ e{menu = retiraDoJust $ obtemMenu Cima e} else return e
reageEventoMenuJogo (EventKey (SpecialKey KeyDown) Down _ _) e@(Estado _ _ _ _ _) = if obtemMenu Baixo e /= Nothing then
    do return $ e{menu = retiraDoJust $ obtemMenu Baixo e} else return e
reageEventoMenuJogo (EventKey (Char 'q') Down _ _) _ =
    do exitSuccess
reageEventoMenuJogo _ e = return e

-- | Função para reagir aos eventos do menuSave
reageEventoMenuSave :: Event -> Estado -> IO Estado
reageEventoMenuSave ev e@(Estado (MenuLoad (CarregarJogo _ _)) _ _ _ _) = reageEventoCarregaJogo ev e
reageEventoMenuSave ev e@(Estado (MenuLoad (IniciarJogo _ _)) _ _ _ _) = reageEventoIniciarJogo ev e
reageEventoMenuSave (EventKey (Char 'q') Down _ _) _ =
    do exitSuccess
reageEventoMenuSave _ e = return e 

-- | Função para reagir aos eventos do menuCarregaJogo
reageEventoCarregaJogo :: Event -> Estado -> IO Estado
reageEventoCarregaJogo (EventKey (SpecialKey KeyUp) Down _ _) e@(Estado (MenuLoad (CarregarJogo False 0)) _ _ _ _) =
    do return $ e{menu = MenuLoad (IniciarJogo False 0)}
reageEventoCarregaJogo (EventKey (SpecialKey KeyEnter) Down _ _) e@(Estado (MenuLoad (CarregarJogo False 0)) _ _ _ _) =
    do s@(JogoGuardado m j) <- lerSaveGame
       return $ e{menu = MenuLoad (IniciarJogo True 1), jogo = guardadoParaJogo s}
reageEventoCarregaJogo (EventKey (SpecialKey KeyEsc) Down _ _) e@(Estado (MenuLoad (CarregarJogo False 0)) _ _ _ _) = return $ e{menu = MenuJogo Jogar}
reageEventoCarregaJogo _ e = return e

-- | Função para reagir aos eventos dentro do jogo
reageEventoIniciarJogo :: Event -> Estado -> IO Estado
reageEventoIniciarJogo (EventKey (SpecialKey KeyEnter) Down _ _) e@(Estado (MenuLoad (IniciarJogo False 0)) _ _ _ _) =
    do return $ e{menu = MenuLoad (IniciarJogo True 1)}
reageEventoIniciarJogo (EventKey (SpecialKey KeyDown) Down _ _) e@(Estado (MenuLoad (IniciarJogo False 0)) _ _ _ _) =
    do return $ e{menu = MenuLoad (CarregarJogo False 0)}
reageEventoIniciarJogo (EventKey (SpecialKey KeyEsc) Down _ _) e@(Estado (MenuLoad (IniciarJogo False 0)) _ _ _ _) = return $ e{menu = MenuJogo Jogar}
reageEventoIniciarJogo (EventKey (SpecialKey KeyRight) Down _ _) e@(Estado (MenuLoad (IniciarJogo True 1)) j _ hs sa) =
    do let novoJ@(Jogo (Jogador (x1,y1)) _) = (animaJogo j (Move Direita))
       if jogoTerminou j == True then 
            do let novoHS = (verificaHS sa hs)
               escreveHSFicheiro novoHS 
               return $ e{menu = MenuGameOver (VoltarAoMenu), jogo = jogoInicial, highScore = novoHS, socreAtual = 0} else return $ e{jogo = novoJ}
reageEventoIniciarJogo (EventKey (SpecialKey KeyLeft) Down _ _) e@(Estado (MenuLoad (IniciarJogo True 1)) j _ hs sa) =
    do let novoJ@(Jogo (Jogador (x1,y1)) _) = animaJogo j (Move Esquerda)
       if jogoTerminou j == True then
            do let novoHS = (verificaHS sa hs)
               escreveHSFicheiro novoHS
               return $ e{menu = MenuGameOver (VoltarAoMenu), jogo = jogoInicial, highScore = novoHS, socreAtual = 0} else return $ e{jogo = novoJ}
reageEventoIniciarJogo (EventKey (SpecialKey KeyUp) Down _ _) e@(Estado (MenuLoad (IniciarJogo True 1)) j _ hs sa) =
    do let novoJ@(Jogo (Jogador (x1,y1)) _) = animaJogo j (Move Cima)
       if subiuNivel j (x1,y1) == True then return $ e{socreAtual= sa+1} 
           else if jogoTerminou j == True then
                do let novoHS = (verificaHS sa hs)
                   escreveHSFicheiro novoHS
                   return $ e{menu = MenuGameOver (VoltarAoMenu), jogo = jogoInicial, highScore = novoHS, socreAtual = 0} else return $ e{jogo = novoJ}
reageEventoIniciarJogo (EventKey (SpecialKey KeyDown) Down _ _) e@(Estado (MenuLoad (IniciarJogo True 1)) j _ hs sa) =
    do let novoJ@(Jogo (Jogador (x1,y1)) _) = animaJogo j (Move Baixo)
       if jogoTerminou j == True then
            do let novoHS = (verificaHS sa hs)
               escreveHSFicheiro novoHS
               return $ e{menu = MenuGameOver (VoltarAoMenu), jogo = jogoInicial, highScore = novoHS, socreAtual = 0} else return $ e{jogo=novoJ}
reageEventoIniciarJogo (EventKey (Char 'x') Down _ _) e@(Estado (MenuLoad (IniciarJogo True 1)) _ _ _ _) =
    do return $ e{menu = MenuLoad (IniciarJogo False 0)}
reageEventoIniciarJogo (EventKey (Char 'g') Down _ _) e@(Estado (MenuLoad (IniciarJogo True 1)) j _ _ _ ) =
    do escreverSaveGame (jogoParaGuardado j)
       exitSuccess
reageEventoIniciarJogo (EventKey (Char 'q') Down _ _) _ =
    do exitSuccess
reageEventoIniciarJogo _ e = return e

-- | Função para reagir aos eventos do menuGameOver
reageEventoMenuGameOver :: Event -> Estado -> IO Estado
reageEventoMenuGameOver (EventKey (SpecialKey KeyEnter) Down _ _) e@(Estado (MenuGameOver (VoltarAoMenu)) _ _ _ _) = return $ e{menu = MenuJogo Jogar}
reageEventoMenuGameOver (EventKey (SpecialKey KeyDown) Down _ _) e@(Estado (MenuGameOver (VoltarAoMenu)) _ _ _ _) = return $ e{menu = MenuGameOver (TentarNovamente)}
reageEventoMenuGameOver (EventKey (SpecialKey KeyEnter) Down _ _) e@(Estado (MenuGameOver (TentarNovamente)) _ _ _ _) = return $ e{menu = MenuLoad (IniciarJogo True 1)}
reageEventoMenuGameOver (EventKey (SpecialKey KeyUp) Down _ _) e@(Estado (MenuGameOver (TentarNovamente)) _ _ _ _) = return $ e{menu = MenuGameOver (VoltarAoMenu)}
reageEventoMenuGameOver (EventKey (Char 'q') Down _ _) _ =
    do exitSuccess
reageEventoMenuGameOver _ e = return e


-- | Funções para reageEventoMenuJogo
obtemMenu :: Direcao -> Estado -> Maybe Menu
obtemMenu dir e =
    case dir of
        Cima -> case m of 
            (MenuJogo Jogar) -> Nothing
            _ -> Just $ menus !! (i-1)
        _ -> case m of
            (MenuJogo Sair) -> Nothing
            _ -> Just $ menus !! (i+1)
    where m = (menu e)
          i = retiraDoJust $ elemIndex m menus
          menus = [MenuJogo Jogar, MenuJogo (Opcoes False), MenuJogo (Creditos False), MenuJogo Sair]

retiraDoJust :: Maybe a -> a
retiraDoJust (Just a) = a

-- | Funções para reageEventoIniciarJogo
subiuNivel :: Jogo -> (Int,Int) -> Bool
subiuNivel (Jogo (Jogador (x2,y2)) _) (x1,y1) = y2<y1

verificaHS :: Int -> HighScore -> HighScore
verificaHS x h = if x>h then x else h

reageTempo :: Float -> Estado -> IO Estado
reageTempo s e@(Estado menu jogo t hs sa) = 
    if s > 5 then
        do let novoJ = deslizaJogo (head (randomIntsL (ioConvert (randomizacao)) 1)) jogo
           return $ e{jogo = novoJ, tempo = t+s} else 
                                         do let novoJ2 = animaJogo jogo (Parado)
                                            return $ e{jogo = novoJ2, tempo = t+s}


main :: IO()
main = do
    highScore <- lerFicheiro
    let estadoInicial = (Estado (MenuJogo Jogar) jogoInicial 0 highScore 0)

    player             <- loadBMP  "./pictures/player.bmp" -- 0
    carLeftToRight     <- loadBMP  "./pictures/car-LeftToRight.bmp" -- 1
    carRightToLeft     <- loadBMP  "./pictures/car-RightToLeft.bmp" -- 2
    tronco             <- loadBMP  "./pictures/log.bmp" -- 3
    arvore             <- loadBMP  "./pictures/tree.bmp" -- 4
    estrada            <- loadBMP  "./pictures/road.bmp" -- 5
    relva              <- loadBMP  "./pictures/grass.bmp" -- 6
    agua               <- loadBMP  "./pictures/water.bmp" -- 7
    backgroundMenu     <- loadBMP  "./pictures/backgroundMenu.bmp" -- 8
    menuJogar          <- loadBMP  "./pictures/backgroundMenuJogar.bmp" -- 9
    menuOpcao          <- loadBMP  "./pictures/backgroundMenuOpcoes.bmp" -- 10
    menuCreditos       <- loadBMP  "./pictures/backgroundMenuCreditos.bmp" -- 11
    menuSair           <- loadBMP  "./pictures/backgroundMenuSair.bmp" -- 12
    menuLoadIniciar    <- loadBMP  "./pictures/backgroundMenuLoadIniciar.bmp" -- 13
    menuLoadCarregar   <- loadBMP  "./pictures/backgroundMenuLoadCarregar.bmp" -- 14
    menuGameOverVoltar <- loadBMP  "./pictures/backgroundMenuGameOverVoltar.bmp" -- 15
    menuGameOverTentar <- loadBMP  "./pictures/backgroundMenuGameOverTentar.bmp" -- 16
    menuOpcaoMenu      <- loadBMP  "./pictures/backgroundMenuOpcoesmenu.bmp" -- 17
    menuCreditosMenu   <- loadBMP  "./pictures/backgroundMenuCreditosmenu.bmp" -- 18

    playIO window
        (greyN 0.32)
        fr
        estadoInicial
        (desenhaEstado [player, carLeftToRight, carRightToLeft, tronco, arvore, estrada, relva, agua, backgroundMenu, menuJogar, menuOpcao, menuCreditos, menuSair,menuLoadIniciar, menuLoadCarregar, menuGameOverVoltar, menuGameOverTentar, menuOpcaoMenu, menuCreditosMenu])
        reageEvento
        reageTempo


--  LD_PRELOAD=/usr/lib/x86_64-linux-gnu/libglut.so.3 ghci
-- | Função que desenha o estado
desenhaEstado :: [Picture] -> Estado -> IO Picture
desenhaEstado pics e = do 
    let x = 1
    let y = 1
    let (Jogo (Jogador (x1,y1)) (Mapa l ((terr,obs):ts))) = (jogo e)
    let t = (tempo e)

    case (menu e) of
        MenuJogo Jogar -> return $ Scale x y $ (pics !! 9)
        MenuJogo (Opcoes False) -> return $ Scale x y $ (pics !! 10)
        MenuJogo (Opcoes True) -> return $ Scale x y $ (pics !! 17)
        MenuJogo (Creditos False) -> return $ Scale x y $ (pics !! 11)
        MenuJogo (Creditos True) -> return $ Scale x y $ (pics !! 18)
        MenuJogo Sair -> return $ Scale x y $ (pics !! 12)
        MenuLoad (IniciarJogo False 0) -> return $ Scale x y $ (pics !! 13)
        MenuLoad (CarregarJogo False 0) -> return $ Scale x y $ (pics !! 14)
        MenuLoad (IniciarJogo True 1) -> return $ Pictures [(pics !! 8), desenhaMapa e ((terr,obs):ts) 0 0 pics, desenhaJogador e pics, desenhaHighScore e, desenhaScoreAtual e]
        MenuGameOver (VoltarAoMenu) -> return $ Scale x y $ (pics !! 15)
        MenuGameOver (TentarNovamente) -> return $ Scale x y $ (pics !! 16)


-- | Função que desenha o mapa
desenhaMapa :: Estado -> [(Terreno, [Obstaculo])] -> Int -> Int -> [Picture] -> Picture
desenhaMapa _ [] _ _ _ = Blank
desenhaMapa e ((terr,[]):t) xAcc yAcc pics = desenhaMapa e t 0 (yAcc+1) pics
desenhaMapa e ((terr,(ob:obs)):t) xAcc yAcc pics=
    case terr of
        Rio v -> case ob of
            Nenhum -> Pictures [Translate ((fromIntegral xAcc)*50) ((fromIntegral yAcc)*50) $ Scale 0.17 0.17 $ (pics !! 7), desenhaMapa e ((terr, obs):t) (xAcc+1) yAcc pics]
            Tronco -> Pictures [Translate ((fromIntegral xAcc*50)) ((fromIntegral yAcc)*50) $ Scale 0.17 0.17 $ (pics !! 3), desenhaMapa e ((terr, obs):t) (xAcc+1) yAcc pics]
        Estrada v -> case ob of 
            Nenhum -> Pictures [Translate ((fromIntegral xAcc)*50) ((fromIntegral yAcc)*50) $ Scale 0.17 0.17 $ (pics !! 5), desenhaMapa e ((terr, obs):t) (xAcc+1) yAcc pics]
            Carro -> if v>0 then Pictures [Translate ((fromIntegral xAcc)*50) ((fromIntegral yAcc)*50) $ Scale 0.17 0.17 $ (pics !! 1), desenhaMapa e ((terr, obs):t) (xAcc+1) yAcc pics]
                else Pictures [Translate ((fromIntegral xAcc)*50) ((fromIntegral yAcc)*50) $ Scale 0.17 0.17 $ (pics !! 2), desenhaMapa e ((terr, obs):t) (xAcc+1) yAcc pics]
        Relva -> case ob of
            Nenhum -> Pictures [Translate ((fromIntegral xAcc)*50) ((fromIntegral yAcc)*50) $ Scale 0.17 0.17 $ (pics !! 6), desenhaMapa e ((terr,obs):t) (xAcc+1) yAcc pics]
            Arvore -> Pictures [Translate ((fromIntegral xAcc)*50) ((fromIntegral yAcc)*50) $ Scale 0.17 0.17 $ (pics !! 4), desenhaMapa e ((terr, obs):t) (xAcc+1) yAcc pics]

-- | Função que desenha o High Score
desenhaHighScore :: Estado -> Picture
desenhaHighScore e@(Estado (MenuLoad (IniciarJogo True _)) _ _ hs _) = Scale 0.3 0.3 $ Translate 2150 1650 $ Color white $ Text ("High Score: " ++ hs1)
    where hs1 = show $ hs

-- | Função que desenha o score atual
desenhaScoreAtual :: Estado -> Picture
desenhaScoreAtual e@(Estado (MenuLoad (IniciarJogo True _)) _ _ _ sa) = Scale 0.3 0.3 $ Translate 2150 1500 $ Color white $ Text ("Score atual: " ++ (show sa))

-- | Função que desenha o jogador
desenhaJogador :: Estado -> [Picture] -> Picture
desenhaJogador e@(Estado (MenuLoad (IniciarJogo True _)) (Jogo (Jogador (x,y)) (Mapa l ((terr,(ob:obs)):t))) _ _ _) pics = 
    Translate (fromIntegral x) (fromIntegral y) $ Scale 0.17 0.17 $ (pics !! 0)

-- | Jogo inicial, todos os jogos começam com este mapa e vão mudando ao longo do tempo coma função @deslizaMapa@
jogoInicial :: Jogo
jogoInicial = (Jogo (Jogador (2,5)) (Mapa 6 [(Rio 2, [Tronco, Tronco, Nenhum, Nenhum, Tronco, Nenhum]),
                                          (Rio (-4), [Tronco, Nenhum, Nenhum, Tronco, Nenhum, Tronco]),
                                          (Relva, [Nenhum, Arvore, Arvore, Nenhum, Nenhum, Nenhum]),
                                          (Estrada 4, [Nenhum, Carro, Carro, Nenhum, Nenhum, Nenhum]),
                                          (Relva, [Arvore, Nenhum, Arvore, Nenhum, Arvore, Nenhum]),
                                          (Relva, [Nenhum, Nenhum, Nenhum, Nenhum, Nenhum, Nenhum])]))

