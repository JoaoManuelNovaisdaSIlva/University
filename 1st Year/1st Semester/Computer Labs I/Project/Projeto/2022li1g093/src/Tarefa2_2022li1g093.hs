{- |
Module      : Tarefa2_2022li1g093
Description : Geração contínua de um mapa
Copyright   : João Manuel Novais da Silva <a91671@alunos.uminho.pt>
              Pedro António Pires Correia Leite Sequeira <a91660@alunos.uminho.pt>

Módulo para a realização da Tarefa 2 do projeto de LI1 em 2022/23.
-}
module Tarefa2_2022li1g093 where

import LI12223
import System.Random
import Data.Time.Clock
import System.IO.Unsafe (unsafePerformIO)
import Tarefa1_2022li1g093

{- |
A função @estendeMapa@, vai gerar uma nova linha do mapa de maneira "aleatória", apsar de isto ser verdadeiramente impossível em linguagens de programação, principalmente
em Haskell em que o mesmo input numa função é tem sempre o mesmo output. O númro aleatório a ser passado na função @estendeMapa@ deve ser introduzido deste maneira
 __head (randomIntsL (ioConvert (randomizacao)) 1)__ . É usada uma função auxiliar @estedeMapa_aux@ que tem um caso seja a primeira iteração de gerar uma nova linha
 é criada essa nova linha, e caso seja necessário preencher-la é o feito a partir da @estendeLinha@. Para selecionar os terrenos, obstáculos e velocidades aleatórios 
 são usadas as funções @escolheTerreno@, @escolheObs@ e @velocidadeAleatoria@ respetivamente.
-}
estendeMapa :: Mapa -> Int -> Mapa
estendeMapa (Mapa l ((terr,obs):t)) rand = estendeMapa_aux mapa rand 0
    where mapa = (Mapa l ((terr,obs):t))
          --randV = head (randomIntsL (ioConvert (randomizacao)) 1)

estendeMapa_aux :: Mapa -> Int -> Int -> Mapa
estendeMapa_aux (Mapa l ((terr,obs):t)) rand acc
    | acc == 0 = estendeMapa_aux (Mapa l ((novoTerr, [escolheObs (proximosObstaculosValidos l (novoTerr, [])) rand]) : ((terr,obs):t))) rand (acc+1)
    | acc == l = mapa
    | otherwise = estendeMapa_aux (estendeLinha mapa rand) rand (acc+1)
    where mapa = (Mapa l ((terr,obs):t))
          novoTerr = escolheTerreno (proximosTerrenosValidos mapa) rand

estendeLinha :: Mapa -> Int -> Mapa
estendeLinha (Mapa l ((terr,obs):t)) rand
    | length obs < l = (Mapa l ((terr, newObs ):t))
    | otherwise = (Mapa l ((terr,obs):t))
    where newObs = obs ++ [escolheObs (proximosObstaculosValidos l (terr,obs)) rand]

escolheTerreno :: [Terreno] -> Int -> Terreno
escolheTerreno terr rand = velocidadeAleatoria(terr !! (mod rand (length terr)))

escolheObs :: [Obstaculo] -> Int -> Obstaculo
escolheObs obs rand = obs !! (mod rand (length obs))


-- | 1 para positivo 0 para negativo,  por alguma razão o where vel não funciona, por isso a função fica feia :(
velocidadeAleatoria :: Terreno -> Terreno
velocidadeAleatoria (Rio x) = if (mod ((mod (head (randomIntsL (ioConvert (randomizacao)) 1)) 10)) 2) == 1 then (Rio  ((mod (head (randomIntsL (ioConvert (randomizacao)) 1)) 10))) else (Rio (negate ((mod (head (randomIntsL (ioConvert (randomizacao)) 1)) 10))))
velocidadeAleatoria (Estrada x) = if (mod ((mod (head (randomIntsL (ioConvert (randomizacao)) 1)) 10)) 2) == 1 then (Estrada ((mod (head (randomIntsL (ioConvert (randomizacao)) 1)) 10))) else (Estrada (negate ((mod (head (randomIntsL (ioConvert (randomizacao)) 1)) 10))))
velocidadeAleatoria Relva = Relva   
    --where vel = (mod (head (randomIntsL (ioConvert (randomizacao)) 1)) 100)

{- |
A maneira em que consegui remendar este problema foi usando as funções @randomizacao@, @ioConvert@ e @randomIntsL@. A função @randomizacao@ extraí os segundos do 
sistema usando a função @getCurrentTime@ da lib Data.Time.Clock, depois é passado este valor para um número inteiro, mas esta função devolve um IO Int, agora é 
possível passar de IO Int para Int mas é necessário usar uma função @unsafePerformIO@ que, como o nome indica, faz uma transformação de IO Int para Int de maneira 
unsafe, não é recomendado a utilização deste função mas para os objetivos deste trabalho não notei alterações no comportamento do código. 
Por fim a função @randomIntsL@ foi fornecida pela equipa docente e recebendo uma seed e um tamanho gera uma lista de números aleatórios.
-}

randomizacao :: IO Int
randomizacao = do
    currentTime <- getCurrentTime
    let seconds = floor $ utctDayTime currentTime
    return seconds

-- | Função que converte o output da função @randomização@ de IO Int para Int
ioConvert :: IO Int -> Int 
ioConvert ioInt = unsafePerformIO ioInt

-- | Função facultada pelos docentes
randomIntsL :: Int -> Int -> [Int]
randomIntsL seed len = take len $ randoms (mkStdGen seed)


{- |
A função @proximosTerrenosValidos recebe um mapa e devolve o tipo de terreno que pode ser inserido numa nova linha em cima do mapa (na cabeça da lista mapa) seguindo
as regras estabelecidas na tarefa 1, como a impossibilidade de haver mais do que 5 estradas seguidas, etc. A função auxiliar recebe apenas as primeiras 5 linhas do 
mapa, uma vez que apenas interessa para pôr de fora os terrenos que ja estão repetidos. São udados acumuladores para os 3 tipos de terrenos para contar o número de linhas
desse terreno seguidas.
-}
proximosTerrenosValidos :: Mapa -> [Terreno]
proximosTerrenosValidos (Mapa l []) = [Rio 0, Estrada 0, Relva]
proximosTerrenosValidos (Mapa l mapa) = proximosTerrenosValidos_aux (reverse(take 5 mapa)) 0 0 0

proximosTerrenosValidos_aux :: [(Terreno, [Obstaculo])] -> Int -> Int -> Int -> [Terreno]
proximosTerrenosValidos_aux [] rioAcc estradaAcc relvaAcc
    | rioAcc >= 4 = [Estrada 0, Relva]
    | estradaAcc >= 5 = [Rio 0, Relva]
    | relvaAcc >= 5 = [Rio 0, Estrada 0]
    | otherwise = [Rio 0, Estrada 0, Relva]
proximosTerrenosValidos_aux ((terr,obs):t) rioAcc estradaAcc relvaAcc = case terr of
    Rio x -> proximosTerrenosValidos_aux t (rioAcc+1) 0 0
    Estrada x -> proximosTerrenosValidos_aux t 0 (estradaAcc+1) 0
    Relva -> proximosTerrenosValidos_aux t 0 0 (relvaAcc+1)


{- |
A função @proximosObstaculosValidos@ recebe um inteiro que representa a largura do mapa e uma linha do mesmo. Para os casos em que o mapa está vazio, e por isso, estamos
no início de gerar uma nova linha deve ser possível inserir qualquer tipo de obstaculo, isto feito pela função @proximosObstaculosValidos_listaVazia@ . São utilizadas
as funções @existeNenhumalinha@, @contaCarrosSeguidoDireita@, @contaCarrosSeguidosEsquerda@, @contaTroncosSeguidosDireita@ e @contaTroncosSeguidosEsquerda@ definidos
na tarefa 1 para verificar se existe pelo menos uma célula Nenhum, e se existe um tronco ou carro maior que 5 ou 3 unidades respetivamente que estejam "divididos"
visualmente nos cantos da linha.
-}
proximosObstaculosValidos :: Int -> (Terreno, [Obstaculo]) -> [Obstaculo]
proximosObstaculosValidos _ (terr, []) = proximosObstaculosValidos_listaVazia (terr,[])
proximosObstaculosValidos l (Rio x, obs) = proximosObstaculosValidos_Rio l obs
proximosObstaculosValidos l (Estrada x, obs) = proximosObstaculosValidos_Estrada l obs
proximosObstaculosValidos l (Relva, obs) = proximosObstaculosValidos_Relva l obs

proximosObstaculosValidos_listaVazia :: (Terreno, [Obstaculo]) -> [Obstaculo]
proximosObstaculosValidos_listaVazia (terr, []) = case terr of
    Rio x -> [Nenhum, Tronco]
    Estrada x -> [Nenhum, Carro]
    Relva -> [Nenhum, Arvore]


verificaComprimentoSeparado_Rio :: [Obstaculo] -> [Obstaculo]
verificaComprimentoSeparado_Rio obs = if (contaTroncosSeguidosEsquerda obs 0) + (contaTroncosSeguidosDireita obs 0) >= 5 then [Nenhum] else [Nenhum, Tronco]

verificaComprimentoSeparado_Estrada :: [Obstaculo] -> [Obstaculo]
verificaComprimentoSeparado_Estrada obs = if (contaCarrosSeguidosEsquerda obs 0) + (contaCarrosSeguidosDireita (reverse(obs)) 0) >= 3 then [Nenhum] else [Nenhum, Carro,Nenhum]

proximosObstaculosValidos_Rio :: Int -> [Obstaculo] -> [Obstaculo]
proximosObstaculosValidos_Rio l obs
    | l == length(obs) = []
    | l == length(obs)+1 && existeNenhumlinha obs == True = verificaComprimentoSeparado_Rio (obs ++ [Tronco])
    | validaTronco_aux [(Rio 0, (obs++[Tronco]))] 0 == True = [Nenhum, Tronco]
    | otherwise = [Nenhum]

proximosObstaculosValidos_Estrada :: Int -> [Obstaculo] -> [Obstaculo]
proximosObstaculosValidos_Estrada l obs
    | l == length(obs) = []
    | l == length(obs)+1 && existeNenhumlinha obs == True = verificaComprimentoSeparado_Estrada (obs ++ [Carro])
    | validaCarro_aux [(Estrada 0, (obs++[Carro]))] 0 == True = [Nenhum, Carro]
    | otherwise = [Nenhum]


proximosObstaculosValidos_Relva :: Int -> [Obstaculo] -> [Obstaculo]
proximosObstaculosValidos_Relva l obs
    | l == length(obs) = []
    | existeNenhumlinha obs == True = [Nenhum, Arvore]
    | otherwise = [Nenhum]
