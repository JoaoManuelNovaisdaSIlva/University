module Main where

import Graphics.Gloss
import Graphics.Gloss.Interface.Pure.Game

type Estado = ([Key],(Float, Float))
type EstadoGloss=                    -- inclui 2 imagens e o valor de segundos
 (Estado, (Picture, Picture, Float)) --  passados desde o início do programa


estadoInicial :: Estado
estadoInicial = ([],(0,0))

estadoGlossInicial:: Picture -> Picture -> EstadoGloss
estadoGlossInicial p1 p2 = (estadoInicial, (p1,p2,0.0))  

-- altera imagem a cada 100 milisseconds
desenhaEstado :: EstadoGloss -> Picture
desenhaEstado ((_,(x,y)), (p1,p2,n)) 
  | mod (milissecondsPassed) 200 < 100 = Translate x y p1
  | otherwise = Translate x y p2
    where milissecondsPassed = round (n*1000)

reageEvento :: Event -> EstadoGloss -> EstadoGloss
reageEvento (EventKey k Down _ _) (([],(x,y)), e) = (([k],(x,y)), e)
reageEvento (EventKey k Up _ _) (([_],(x,y)), e) = (([],(x, y)), e)
reageEvento _ s = s  -- ignora qualquer outro evento

reageTempo :: Float -> EstadoGloss -> EstadoGloss
-- regista no estado passagem do tempo, mas não altera coordenadas
reageTempo n (([],(x,y)), (p1,p2,b)) = (([],(x, y)), (p1,p2,b+n))
-- altera coordenadas enquanto tecla está pressionada
reageTempo n (([k],(x,y)), (p1,p2,b)) =
  case  k of
    (SpecialKey KeyDown) -> (([k],(x,y-5)), (p1,p2,b+n))
    (SpecialKey KeyUp) -> (([k],(x,y+5)), (p1,p2,b+n))
    (SpecialKey KeyLeft) -> (([k],(x-5,y)), (p1,p2,b+n))
    (SpecialKey KeyRight) -> (([k],(x+5,y)), (p1,p2,b+n))

fr:: Int
fr = 50

dm :: Display
dm = InWindow
       "Novo Jogo"  -- título da janela
       (400, 400)   -- dimensão da janela
       (0,0)        -- posição no ecran

main :: IO ()
main = do 
         let p1 = color green $ rectangleSolid 20 10
         let p2 = color green $ rectangleSolid 40 10
         play  dm             -- janela onde irá decorrer o jogo
               (greyN 0.5)    -- cor do fundo da janela
               fr             -- frame rate
               (estadoGlossInicial p1 p2)  -- define estado inicial do jogo
               desenhaEstado   -- desenha o estado do jogo
               reageEvento    -- reage a um evento
               reageTempo     -- reage ao passar do tempo