{- |
Module      : Tarefa3_2022li1g093
Description : Movimentação do personagem e obstáculos
Copyright   : João Manuel Novais da Silva <a91671@alunos.uminho.pt>
              Pedro António Pires Correia Leite Sequeira <a91660@alunos.uminho.pt>

Módulo para a realização da Tarefa 3 do projeto de LI1 em 2022/23.
-}
module Tarefa3_2022li1g093 where

import LI12223
import Data.List

{- |
A função @animaJogo@, usa todas as funções até agora defenidas para animar o jogo dependendo da jogada feita pelo
utilizador, foi feita uma divisão de casos a partir de cada movimento, e posteriormente cada movimento implementa 
a animação para cada linha e ou tipo de terreno. Não interessa aqui se o jogador faz uma jogada que o vai fazer perder o jogo
por isso a única coisa a verificar é se o jogador é "bloqueado" por uma árvore, isto é, se a célula para que se pretende
mover esteja ocupada por uma árvore, daí a função @jogadorBloquiadoArvore@, verifica se o jogador deve ser impedido de mover para 
onde queria. Além disto, na situação em que o jogador se encontra em cima de um tronco, se premanecer parado o jogador deve acompanhar
o movimento do tronco e se o jogador se mova quer para a direita quer para a esquerda devem ser somado os vetores de movimento, ou seja,
o jogador acompanha o tronco e ainda anda quer para a direita quer para a esquerda.
-}
animaJogo :: Jogo -> Jogada -> Jogo
animaJogo jogo Parado = animaJogoParado jogo (procuraTerrenoJogador jogo 0)
animaJogo jogo (Move Cima) = animaJogoMoveCima jogo (procuraTerrenoJogador jogo 0)
animaJogo jogo (Move Baixo) = animaJogoMoveBaixo jogo (procuraTerrenoJogador jogo 0)
animaJogo jogo (Move Direita) = animaJogoMoveDireita jogo (procuraTerrenoJogador jogo 0)
animaJogo jogo (Move Esquerda) = animaJogoMoveEsquerda jogo (procuraTerrenoJogador jogo 0)

procuraTerrenoJogador :: Jogo -> Int -> Terreno
procuraTerrenoJogador (Jogo (Jogador (x,y)) (Mapa l ((terr, (ob:obs)):t))) acc = if y == acc then terr else procuraTerrenoJogador (Jogo (Jogador (x,y)) (Mapa l t)) (acc+1)

animaJogoParado :: Jogo -> Terreno -> Jogo
animaJogoParado jogo (Rio x) = jogadorParadoEmTronco jogo
animaJogoParado jogo (Estrada x) =  divideMovers jogo 
animaJogoParado jogo (Relva) =  moverObstaculos jogo

animaJogoMoveCima :: Jogo -> Terreno -> Jogo
animaJogoMoveCima jogo (Rio x) = if jogadorBloquiadoArvore jogo (Move Cima) == True then jogadorParadoEmTronco jogo else moverObstaculos (moveJogador jogo (Move Cima))
animaJogoMoveCima jogo (Estrada x) = if jogadorBloquiadoArvore jogo (Move Cima) == True then divideMovers jogo else divideMovers (moveJogador jogo (Move Cima))
animaJogoMoveCima jogo (Relva) = if jogadorBloquiadoArvore jogo (Move Cima) == True then moverObstaculos jogo else moverObstaculos (moveJogador jogo (Move Cima))

animaJogoMoveBaixo :: Jogo -> Terreno -> Jogo
animaJogoMoveBaixo jogo (Rio x) = if jogadorBloquiadoArvore jogo (Move Baixo) == True then jogadorParadoEmTronco jogo else moverObstaculos (moveJogador jogo (Move Baixo))
animaJogoMoveBaixo jogo (Estrada x) = if jogadorBloquiadoArvore jogo (Move Baixo) == True then divideMovers jogo else divideMovers (moveJogador jogo (Move Baixo))
animaJogoMoveBaixo jogo (Relva) = if jogadorBloquiadoArvore jogo (Move Baixo) == True then moverObstaculos jogo else moverObstaculos (moveJogador jogo (Move Baixo))

animaJogoMoveDireita :: Jogo -> Terreno -> Jogo
animaJogoMoveDireita jogo (Rio x) = moveJogador (jogadorParadoEmTronco jogo) (Move Direita)
animaJogoMoveDireita jogo (Estrada x) = divideMovers (moveJogador jogo (Move Direita))
animaJogoMoveDireita jogo (Relva) = if jogadorBloquiadoArvore jogo (Move Direita) == True then moverObstaculos jogo else moverObstaculos (moveJogador jogo (Move Direita))

animaJogoMoveEsquerda :: Jogo -> Terreno -> Jogo
animaJogoMoveEsquerda jogo (Rio x) = moveJogador (jogadorParadoEmTronco jogo) (Move Esquerda)
animaJogoMoveEsquerda jogo (Estrada x) = divideMovers (moveJogador jogo (Move Esquerda))
animaJogoMoveEsquerda jogo (Relva) = if jogadorBloquiadoArvore jogo (Move Esquerda) == True then moverObstaculos jogo else moverObstaculos (moveJogador jogo (Move Esquerda))

encontraCelulaY :: Mapa -> (Int,Int) -> Int -> Obstaculo
encontraCelulaY (Mapa l ((terr,obs):t)) (x,y) acc = if y == acc then encontraCelulaX obs (x,y) 0 else encontraCelulaY (Mapa l t) (x,y) (acc+1)

encontraCelulaX :: [Obstaculo] -> (Int,Int) -> Int -> Obstaculo
encontraCelulaX (h:t) (x,y) acc = if x == acc then h else encontraCelulaX t (x,y) (acc+1)

jogadorBloquiadoArvore :: Jogo -> Jogada -> Bool
jogadorBloquiadoArvore (Jogo (Jogador (x,y)) (Mapa l ((terr, obs):t))) (Move Cima) = if encontraCelulaY (Mapa l ((terr, obs):t)) (x,y-1) 0 == Arvore then True else False
jogadorBloquiadoArvore (Jogo (Jogador (x,y)) (Mapa l ((terr, obs):t))) (Move Baixo) = if encontraCelulaY (Mapa l ((terr, obs):t)) (x,y+1) 0 == Arvore then True else False
jogadorBloquiadoArvore (Jogo (Jogador (x,y)) (Mapa l ((terr, obs):t))) (Move Direita) = if encontraCelulaY (Mapa l ((terr, obs):t)) (x+1,y) 0 == Arvore then True else False
jogadorBloquiadoArvore (Jogo (Jogador (x,y)) (Mapa l ((terr, obs):t))) (Move Esquerda) = if encontraCelulaY (Mapa l ((terr, obs):t)) (x-1,y) 0 == Arvore then True else False


{- |
Exercicio 1:
A função @moverObstaculos@ , altera, consoalte a velocidade, a posição dos Troncos e Carros na lista de obstáculos, deixando na posição original um "Nenhum". O raciocínio pretendido
consiste em guardar a posições dos Troncos ou Carros numa lista de inteiros e somar a cada uma destas posições o número de células que irá "viajar", sendo que se esta soma ultrapassar
o tamanho da lista de obstáculos é feito um @mod@ para transformar esse número numa posição válida da lista. Após isto, é feita uma passagem pela lista de obstáculos que passa todas as células
a "Nenhum", posteriormente esta nova lista é atualizada com a lista de inteiros anterior para colocar o Troncos ou Carros nas devidas posições.
A solução inicial envolvia usar a função pré-defenida @splitAt@ para dividir a lista de obstáculos em dois na posição equivalente á velocidade (fazendo ajustes com @mod@ e @abs@ para manter a velocidade
"válida") e trocar a ordem. Apresentaram-se problemas ao usar os testes defenidos em (__Tarefa3_2022li1g093_Spec.hs__), pois no caso da primeira linha do jogo1 ao mover-se os troncos 2 unidade para a direita
um deles deveria ficar no início da lista resultante, mas isso não seria possível uma vez que este ja se encontrava no 'snd' do tuplo resultante de @splitAt@.
Isto é:
>>> snd(splitAt 2 [Nenhum, Nenhum, Tronco, Nenhum, Tronco, Nehum]) : fst(splitAt 2 [Nenhum, Nenhum, Tronco, Nenhum, Tronco, Nehum])
[Tronco,Nenhum,Tronco,Nenhum,Nehum,Nehum]
*quando deveria dar [Tronco, Nenhum, Nenhum, Nenhum, Tronco, Nenhum]
-}

moverObstaculos :: Jogo -> Jogo
moverObstaculos (Jogo (Jogador (x,y)) (Mapa l ((terr,obs):t))) = (Jogo (Jogador (x,y)) (Mapa l (moverObstaculos_aux ((terr,obs):t)) ))

moverObstaculos_aux :: [(Terreno, [Obstaculo])] -> [(Terreno, [Obstaculo])]
moverObstaculos_aux [] = []
moverObstaculos_aux ((Rio x,obs):t) = if x < 0 then (Rio x, moverCoisas (Rio x) obs ( (length obs) - (mod (abs x) (length obs)) ) ) : moverObstaculos_aux  t else (Rio x, moverCoisas (Rio x) obs x) : moverObstaculos_aux t
moverObstaculos_aux ((Estrada x, obs):t) = if x < 0 then (Estrada x, moverCoisas (Estrada x) obs ( (length obs) - (mod (abs x) (length obs)) ) ) : moverObstaculos_aux t else (Estrada x, moverCoisas (Estrada x) obs x) : moverObstaculos_aux t
moverObstaculos_aux ((Relva, obs):t) = (Relva, obs) : moverObstaculos_aux t

moverCoisas :: Terreno -> [Obstaculo] -> Int -> [Obstaculo]
moverCoisas _ [] _ = []
moverCoisas (Rio x) obs v = colocaTroncos (passarANenhum obs) (sort(posicoesObjetivo obs v (length obs) 0)) 0
moverCoisas (Estrada x) obs v = colocaCarros (passarANenhum obs) (sort(posicoesObjetivo obs v (length obs) 0)) 0

passarANenhum :: [Obstaculo] -> [Obstaculo]
passarANenhum [] = []
passarANenhum (h:t)
    | h == Tronco = Nenhum : passarANenhum t
    | h == Carro = Nenhum : passarANenhum t
    | otherwise = h : passarANenhum t  

posicoesObjetivo :: [Obstaculo] -> Int -> Int -> Int -> [Int]
posicoesObjetivo [] _ _ _ = []
posicoesObjetivo (h:t) vel len acc
    | h == Tronco && (acc+vel) < len = (acc+vel) : posicoesObjetivo t vel len (acc+1)
    | h == Tronco && (acc+vel) >= len = (mod(acc+vel) len) : posicoesObjetivo t vel len (acc+1)
    | h == Carro && (acc+vel) < len = (acc+vel) : posicoesObjetivo t vel len (acc+1)
    | h == Carro && (acc+vel) >= len = (mod(acc+vel) len) : posicoesObjetivo t vel len (acc+1)
    | otherwise = posicoesObjetivo t vel len (acc+1) 

colocaTroncos :: [Obstaculo] -> [Int] -> Int -> [Obstaculo]
colocaTroncos [] _ _ = []
colocaTroncos l [] _ = l  
colocaTroncos (h1:t1) (h2:t2) acc
    | acc == h2 = Tronco : colocaTroncos t1 t2 (acc+1)
    | otherwise = h1 : colocaTroncos t1 (h2:t2) (acc+1) 

colocaCarros :: [Obstaculo] -> [Int] -> Int -> [Obstaculo]
colocaCarros [] _ _ = []
colocaCarros l [] _ = l  
colocaCarros (h1:t1) (h2:t2) acc
    | acc == h2 = Carro : colocaCarros t1 t2 (acc+1)
    | otherwise = h1 : colocaCarros t1 (h2:t2) (acc+1) 



{- |
Exercicio 2:
A função @moverJogador@ , move o jogador uma unidade para a direção que for introduzida.
-}

moveJogador :: Jogo -> Jogada -> Jogo
moveJogador (Jogo (Jogador (x,y)) (Mapa l ((terr,obs):t))) (Move dir)
    | dir == Cima = (Jogo (Jogador (x,y-1)) (Mapa l ((terr,obs):t)))
    | dir == Baixo = (Jogo (Jogador (x,y+1)) (Mapa l ((terr,obs):t)))
    | dir == Esquerda = (Jogo (Jogador (x-1,y)) (Mapa l ((terr,obs):t)))
    | dir == Direita = (Jogo (Jogador (x+1,y)) (Mapa l ((terr,obs):t)))

{- |
Exercicio 3:
A função @jogadorParadoEmTronco@ , procura se o jogador se encontra em cima de um Tronco e muda as suas coordenadas
consoante a velocidade de deslocamento do mesmo. É feita uma procura da prosição do jogador no mapa, primeiro linha a linha,
com a função @procuraY@ e depois célula a célula, com @procuraX@. Depois da descoberta e verificação da posição do jogadore me cima
do Tronco, são feitos os cálculos necessários para alterar as coordenadas.
-}

jogadorParadoEmTronco :: Jogo -> Jogo
jogadorParadoEmTronco (Jogo (Jogador (x,y)) (Mapa l ((terr,obs):t))) = moverObstaculos( (Jogo (Jogador (procuraY (x,y) ((terr,obs):t) 0)) (Mapa l ((terr,obs):t))) )

procuraY :: (Int,Int) -> [(Terreno, [Obstaculo])] -> Int -> (Int,Int)
procuraY (x,y) []  _ = (x,y)
procuraY (x,y) ((Rio v, obs):t) acc = if y == acc then procuraX (x,y) (Rio v,obs) 0 (length obs) else procuraY (x,y) t (acc+1)
procuraY (x,y) ((Estrada v, obs):t) acc = procuraY (x,y) t (acc+1)
procuraY (x,y) ((Relva, obs):t) acc = procuraY (x,y) t (acc+1)

procuraX :: (Int,Int) -> (Terreno, [Obstaculo]) -> Int -> Int -> (Int,Int)
procuraX (x,y) (terr, []) _ _ = (x,y)
procuraX (x,y) (Rio v, (ob:obs)) acc l = if x == acc && ob == Tronco then moveJogadorTronco (x,y) v l else procuraX (x,y) (Rio v, obs) (acc+1) l

moveJogadorTronco :: (Int,Int) -> Int -> Int -> (Int,Int)
moveJogadorTronco (x,y) v l
    | v < 0 = (x+(l-(mod (abs v) l)),y)
    | v > l = (x+(mod v l),y)
    | v+x >= l = ((mod (x+v) l),y)
    | v > 0 && v < l = (x+v,y)
    | otherwise = (x,y)

{- |
Exercicio 4:
A função @limiteMapa@ , pretende verificar se a jogada dada pelo utilizador não põe o jogador fora do mapa. Para isto é feita
uma simples verificação das coordenadas resultantes da simulação do movimento. Se a jogada fôr válida, as coordenadas do jogador
são atualizadas. Foi feita uma alteração no output desta função para poder ser usada na realização da tarefa4, assim se a função
devolver True é porque as coordenadas do jogador são inválidas.
-}

limiteMapa :: Jogo -> Jogada -> Jogo
limiteMapa jogo mov = if verificarCoordenadas (moveJogador jogo mov) == True then jogo else moveJogador jogo mov

verificarCoordenadas :: Jogo -> Bool
verificarCoordenadas (Jogo (Jogador (x,y)) (Mapa l ((terr,obs):t))) = if x<0 || y<0 || x>=l || y>=length(((terr,obs):t)) then True else False

{- |
Fase 2 - Exercicio 1:
A função @moverCarrosCelula@ recebe um par de terreno e lista de obstáculos, um inteiro que é a velocidade da dada linha e o par de coordenadas do jogador,
esta função apenas move os carros, usando a funcção @moverCoisas@, uma célula de cada vez. Também é feita uma verificação se o carro ja se encontra "por de cima"
do jogador, sendo esta a primeira verificação feita.
Ao elaborar esta função deparei-me com o problema em relação a como se iria proseguir quando for para animar uma linha que seja estrada, para isto foi criada a
função @divideMovers@ que dado um jogo, em que o jogador se encontra numa estrada, divide as linhas que devem ser animadas "normalmente" a partir da função
@moverObstaculos_aux@ e a linha especifica em que o jogador se encontra. 
-}

divideMovers :: Jogo -> Jogo
divideMovers (Jogo (Jogador (x,y)) (Mapa l ((terr,obs):t))) = (Jogo (Jogador (x,y)) (Mapa l mapa))
    where mapa = moverObstaculos_aux (fst (mysplit) ) ++ [moverCarrosCelula (head(snd (mysplit))) vel (x,y)] ++ moverObstaculos_aux (tail(snd (mysplit)))
          mysplit = splitAt y ((terr,obs):t)
          vel = devolveVelocidade terr

moverCarrosCelula :: (Terreno, [Obstaculo]) -> Int -> (Int,Int) -> (Terreno, [Obstaculo])
moverCarrosCelula (terr, obs) vel (x,y) = case vel of
    n | colisao obs (x,y) == True -> (terr,obs)
    n | n < 0 -> moverCarrosCelula (terr, (moverCarrosCelula_aux (terr) obs vel)) (vel+1) (x,y)
    n | n == 0 -> (terr,obs)
    n | n > 0 -> moverCarrosCelula (terr, (moverCarrosCelula_aux (terr) obs vel)) (vel-1) (x,y)

moverCarrosCelula_aux :: Terreno -> [Obstaculo] -> Int -> [Obstaculo]
moverCarrosCelula_aux (Estrada x) (h:t) v
    | v > 0 = moverCoisas (Estrada x) (h:t) 1
    | v < 0 = moverCoisas (Estrada x) (h:t) (-1)
    | otherwise = (h:t)

colisao :: [Obstaculo] -> (Int,Int) -> Bool
colisao obs (x,y) = if encontraCelulaX obs (x,y) 0 == Carro then True else False 


devolveVelocidade :: Terreno -> Int 
devolveVelocidade (Estrada v) = v 
devolveVelocidade (Rio v) = v 
devolveVelocidade (Relva) = 0
