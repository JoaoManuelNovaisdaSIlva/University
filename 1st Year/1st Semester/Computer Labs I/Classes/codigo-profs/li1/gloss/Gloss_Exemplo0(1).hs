module Main where 

import Graphics.Gloss

circulo1 :: Picture 
circulo1 = Circle 50 

circulo2 :: Picture 
circulo2 = Translate (-60) 30 circulo1 

circuloVermelho = Color red circulo1 
circuloAzul = Color blue circulo2 
circulos = Pictures [circuloVermelho, circuloAzul]

window :: Display 
window = InWindow  
  "Janela de Exemplo" -- título da janela 
  (200,200)           -- dimensão da janela  
  (10,10)             -- posição no ecrã 
 
background :: Color 
background = greyN 0.8 
 
main :: IO () 
main = display window background circulos 