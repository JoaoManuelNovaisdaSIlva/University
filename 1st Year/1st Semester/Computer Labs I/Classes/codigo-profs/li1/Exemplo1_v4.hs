module Exemplo1_v4 where

import LI12223

-- Identificar padrões no código e criar abstrações para evitar a sua repetição 
-- Usar funções pré-definidas (all, por exemplo)

obstaculosValidos :: Mapa -> Bool
obstaculosValidos (Mapa _ ls) = all obstaculosValidosLinha ls

obstaculosValidosLinha :: (Terreno, [Obstaculo]) -> Bool
obstaculosValidosLinha (t, obs) = all (`elem` obstaculosTerreno t) obs

obstaculosTerreno :: Terreno -> [Obstaculo]
obstaculosTerreno t = Nenhum : obs
    where obs = case t of Relva -> [Arvore]
                          Rio _ -> [Tronco]
                          Estrada _ -> [Carro]
