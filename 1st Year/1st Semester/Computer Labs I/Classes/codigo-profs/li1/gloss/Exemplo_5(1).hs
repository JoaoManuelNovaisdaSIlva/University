module Main where

import Graphics.Gloss
import Graphics.Gloss.Interface.Pure.Game

data Jogo = Jogo Jogador [(Int, Int)]

type Jogador = (Int, Int)

data Opcao = Jogar
            | Sair

data Menu = Opcoes Opcao
          | ModoJogo 
          | VenceuJogo

type World = (Menu, Jogo)

window :: Display
window = InWindow "Pacman" (700, 700) (0,0)

fr :: Int
fr = 25

initialState :: World
initialState = (Opcoes Jogar, Jogo (0, (-300)) [(50, 50), (-250, -100), (-100, -50)])

drawState :: World -> Picture
drawState (VenceuJogo, jogo) = Translate (-200) 0 $ Color red $ Text "Ganhou"
drawState (Opcoes Jogar, jogo) =
  Pictures [Color blue $ drawOption "Jogar",
            Translate 0 (-70) $ drawOption "Sair"]
drawState (Opcoes Sair, jogo) =
  Pictures [drawOption "Jogar",
            Color blue $ Translate 0 (-70) $ drawOption "Sair"]
drawState (ModoJogo, (Jogo (x, y) l)) =
  Pictures $ (map drawSmallCircles l) ++
             [Translate i j $ Color red (circleSolid 10)]
  where
      i = fromIntegral x
      j = fromIntegral y

drawOption option = Translate (-50) 0 $ Scale (0.5) (0.5) $ Text option

drawSmallCircles :: (Int, Int) -> Picture
drawSmallCircles (x, y) = Translate i j $ Circle 5
    where
      i = fromIntegral x
      j = fromIntegral y

newState :: (Int, Int) -> [(Int, Int)] -> Jogo
newState p l = Jogo p (filter (p/=) l)  -- retira circulos "comidos"

event :: Event -> World -> World
-- Menu
event (EventKey (SpecialKey KeyEnter) Down _ _) (Opcoes Jogar, jogo) =
    (ModoJogo, jogo)
event (EventKey (SpecialKey KeyUp) Down _ _) (Opcoes Jogar, jogo) = (Opcoes Sair, jogo)
event (EventKey (SpecialKey KeyDown) Down _ _) (Opcoes Jogar, jogo) =
    (Opcoes Sair, jogo)
event (EventKey (SpecialKey KeyUp) Down _ _) (Opcoes Sair, jogo) = (Opcoes Jogar, jogo)
event (EventKey (SpecialKey KeyDown) Down _ _) (Opcoes Sair, jogo) =
    (Opcoes Jogar, jogo)
-- sair à força
event (EventKey (SpecialKey KeyEnter) Down _ _) (Opcoes Sair, jogo) =
    error "Fim de Jogo"
-- Venceu Jogo
-- continuar a jogar depois de vencer
event (EventKey (SpecialKey KeyEnter) Down _ _) (VenceuJogo, jogo) =
   initialState
-- identificar que venceu jogo
event _ (ModoJogo, (Jogo (x, y) [])) = (VenceuJogo, (Jogo (x, y) []))
-- Modo Jogo
event (EventKey (SpecialKey KeyUp) Down _ _) (ModoJogo, (Jogo (x, y) l)) =
    (ModoJogo, newState (x, y + 50) l)
event (EventKey (SpecialKey KeyDown) Down _ _) (ModoJogo, (Jogo (x, y) l)) =
    (ModoJogo, newState (x, y - 50) l)
event (EventKey (SpecialKey KeyLeft) Down _ _) (ModoJogo, (Jogo (x, y) l)) =
    (ModoJogo, newState (x - 50, y) l)
event (EventKey (SpecialKey KeyRight) Down _ _) (ModoJogo, (Jogo (x, y) l)) =
    (ModoJogo, newState (x + 50, y) l)
-- não fazer nada em outros casos 
event _ w = w

time :: Float -> World -> World
time _ w = w    -- tempo não afeta jogo

main :: IO ()
main = do
  play window white fr initialState drawState event time
