module Main where

import Graphics.Gloss
import Graphics.Gloss.Interface.IO.Game
import System.Exit
import System.Directory

data Menu = Save | ModoJogo deriving (Show, Read, Eq)

-- Estado do Gloss com menu, coordenadas do pacman,
-- duas imagens e um valor de segundos passados desde o início do programa
type Estado = (Menu, (Float, Float))
        
type EstadoGloss = (Estado, (Picture, Picture, Float))

-- Inicialização do estado com os valores anteriormente guardados em ficheiro
estadoInicial :: (Float,Float) -> Estado 
estadoInicial c = (ModoJogo, c)

estadoGlossInicial :: Picture -> Picture -> ((Float,Float),Float) -> EstadoGloss
estadoGlossInicial p1 p2 (c,t)= (estadoInicial c, (p1, p2, t))

reageEventoGloss :: Event -> EstadoGloss -> IO EstadoGloss
-- termina jogo e guarda estado ao pressionar a tecla "q"
reageEventoGloss (EventKey (Char 'q') Down _ _) ((_, c), (_,_,t)) =     
      do writeFile "save.txt" (show (c,t))
         putStrLn "FIM"
         exitSuccess
-- ao pressionar tecla "Space" suspende jogo e guarda estado
reageEventoGloss  (EventKey (SpecialKey KeySpace) Down _ _) ((ModoJogo, c), (p1,p2,t)) =
      do writeFile "save.txt" (show (c,t)) -- grava coordenadas e tempo em ficheiro
         return ((Save, c),(p1,p2,t)) -- suspende jogo 
-- retoma jogo  ao pressionar tecla "Space" a partir do estado anterior
reageEventoGloss  (EventKey (SpecialKey KeySpace) Down _ _) ((Save, c),e) =
      return ((ModoJogo,c), e)
-- Modo de Jogo
reageEventoGloss (EventKey k Down _ _)  ((ModoJogo, (x,y)), e) =
 do
   let (dx,dy) = nextStateJogo k 
   return  ((ModoJogo, (x+dx,y+dy)), e)
      where 
      nextStateJogo :: Key -> (Float,Float)
      nextStateJogo (SpecialKey KeyUp)  = (0,5)
      nextStateJogo (SpecialKey KeyDown) = (0,(-5))
      nextStateJogo (SpecialKey KeyLeft) = ((-5),0)
      nextStateJogo (SpecialKey KeyRight) = (5,0)
      nextStateJogo _ = (0,0)
reageEventoGloss _ w = return w


reageTempoGloss :: Float -> EstadoGloss -> IO EstadoGloss 
reageTempoGloss n ((ModoJogo, (x,y)), (p1, p2, b)) =
      return $ ((ModoJogo, (x,y)), (p1, p2, b+n)) 
reageTempoGloss n ((Save, (x,y)), e) =
      return $ ((Save, (x,y)), e) -- efeito do tempo suspenso

fr :: Int 
fr = 50

dm :: Display
dm = InWindow "Novo Jogo" (400, 400) (0, 0)
 
-- alternar a imagem a cada 100 milissegundos e mostra tempo
desenhaEstadoGloss :: EstadoGloss -> IO Picture
desenhaEstadoGloss ((_, (x,y)), (p1, p2, b)) =
    return $ Pictures [Translate x y pacman, mostraTempo b]
      where pacman = if  (mod (round (b*1000)) 200) < 100 then  p1 else p2
            mostraTempo:: Float -> Picture
            mostraTempo n = Translate 150 150 $ scale 0.2 0.2 $ Text (show $ round n)


leEstado ::  IO ((Float,Float),Float)
leEstado = do
   fileExist <- doesFileExist "save.txt"
   saved <- if fileExist then readFile "save.txt"
                else return "((0,0),0)"
   return (read saved)


--- usa playIO 
main :: IO ()
main = do        
    p1 <- loadBMP "pac_open.bmp"
    p2 <- loadBMP "pac_closed.bmp"
    (coord, time) <- leEstado
    playIO dm                     
           (greyN 0.5)               
           fr                        
           (estadoGlossInicial p1 p2 (coord, time))
           desenhaEstadoGloss   
           reageEventoGloss  
           reageTempoGloss  