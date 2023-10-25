import Data.List
import System.IO
import Data.String
import Data.Char
import Data.Either

-- 50 questoes --


-- Exercisio 1 --

myenumFromTo :: Int -> Int -> [Int]
myenumFromTo x y = [x..y]

-- Exercicio 2 --

myenumFromThenTo :: Int -> Int -> Int -> [Int]
myenumFromThenTo x y z 
    | x == z = [x]
    | x < z = x : myenumFromThenTo y (y+(y-x)) z
    | x > z = [] 

-- Exercicio 3 --

myConnect :: [a] -> [a] -> [a]
myConnect l1 l2 = foldr (:) l2 l1

-- OU --

myConnect2 :: [a] -> [a] -> [a]
myConnect2 [] l = l
myConnect2 (h:t) l = h : myConnect2 t l

-- Exercicio 4 -- 

elementPosition :: [a] -> Int -> a
elementPosition (h:t) 0 = h
elementPosition (h:t) x = elementPosition t (x-1)

-- Exercicio 5 --

myReverse :: [a] -> [a]
myReverse (h:t) = aux h (myReverse t)
          where aux :: a -> [a] -> [a]
                aux x [] = [x]
                aux x (h:t) = h : aux x t

-- OU --

myReverse2 :: [a] -> [a]
myReverse2 [] = []
myReverse2 (h:t) = myReverse2 t ++ [h]

-- Exercicio 6 --

myTake :: Int -> [a] -> [a]
myTake x [] = []
myTake 0 (h:t) = []
myTake x (h:t)
    | x >= length (h:t) = (h:t)
    | otherwise = h : myTake (x-1) t

-- Exercicio 7 --

myDrop :: Int -> [a] -> [a]
myDrop x [] = []
myDrop 0 (h:t) = (h:t)
myDrop x (h:t)
    | x >= length (h:t) = []
    | otherwise = myDrop (x-1) t

-- Exercicio 8 --

myZip :: [a] -> [b] -> [(a,b)]
myZip [] l2 = []
myZip l1 [] = []
myZip (h1:t1) (h2:t2) = (h1,h2) : myZip t1 t2

-- Exercicio 9 --

myElem :: Eq a => a -> [a] -> Bool
myElem x [] = False
myElem x (h:t)
    | x == h = True
    | otherwise = myElem x t

-- Exercicio 10 --

myReplicate :: Int -> a -> [a]
myReplicate 0 x = []
myReplicate n x = x : myReplicate (n-1) x

-- Exercicio 11 --

myIntersperse :: a -> [a] -> [a]
myIntersperse x [] = []
myIntersperse x (h:t)
    | length (h:t) > 1 = h : x : myIntersperse x t
    | otherwise = [h]

-- Exercicio 12 (ENCONTRAR OUTRA MANEIRA) --

{--
myGroup :: Eq a => [a] -> [[a]]
myGroup [] = [[]]
myGroup (h:t) 
    | h == head t = h : head t : [] : myGroup t
    | otherwise = [h] : myGroup t
--}

myGroup :: Eq a => [a] -> [[a]]
myGroup [] = []
myGroup l = aaa : myGroup (myDrop (length aaa) l)
    where aaa = myGroup_aux l

myGroup_aux :: Eq a => [a] -> [a]
myGroup_aux [x] = [x]
myGroup_aux (x:h:t) = if x == h  then x : myGroup_aux (h:t) else [x]

-- Exercicio 13 --

myConcat :: [[a]] -> [a]
myConcat [] = []
myConcat (h:t)
    | length (h:t) > 1 = h ++ myConcat t
    | otherwise = h

-- Exercicio 14 (FAZER EU OUTRA MANEIRA) --

myInits :: [a] -> [[a]]
myInits (h:t) = myInits_aux 0 (h:t)

myInits_aux :: Int -> [a] -> [[a]]
myInits_aux _ [] = [[]]
myInits_aux x l = if x < length l then myTake x l : myInits_aux (x+1) l else [l]

-- Exercicio 15 --

myTails :: [a] -> [[a]]
myTails (h:t) = myTails_aux 0 (h:t)

myTails_aux :: Int -> [a] -> [[a]]
myTails_aux _ [] = [[]]
myTails_aux x l = if x < length l then myDrop x l : myTails_aux (x+1) l else [[]]

-- Exercicio 16 --

myIsPrefixOf :: Eq a => [a] -> [a] -> Bool
myIsPrefixOf [] l2 = True
myIsPrefixOf l1 [] = False
myIsPrefixOf (h1:t1) (h2:t2)
    | h1 == h2 && myIsPrefixOf t1 t2 = True
    | otherwise = False

-- Exercicio 17 --

myIsSuffixOf :: Eq a => [a] -> [a] -> Bool
myIsSuffixOf _ [] = False
myIsSuffixOf [] _ = True
myIsSuffixOf l1 l2 = myIsPrefixOf (reverse l1) (reverse l2)
    
-- Exercicio 18 --

myIsSubsequenceOf :: Eq a => [a] -> [a] -> Bool
myIsSubsequenceOf [] _ = True
myIsSubsequenceOf _ [] = False
myIsSubsequenceOf (h1:t1) (h2:t2) = if (h1 == h2) then myIsSubsequenceOf t1 t2 else myIsSubsequenceOf (h1:t1) t2

-- Exercicio 19 --

{-
myElemIndices :: Eq a => a -> [a] -> [Int]
myElemIndices x [] = []
myElemIndices x (h:t) = if (x==h) then i+1 : myElemIndices x t else myElemIndices x t
	where i = -1
-}

myElemIndices :: Eq a => a -> [a] -> [Int]
myElemIndices x [] = []
myElemIndices x (h:t) = myElemIndicesIndices_aux (-1) x (h:t)

myElemIndicesIndices_aux :: Eq a => Int -> a -> [a] -> [Int]
myElemIndicesIndices_aux  _ _ [] = []
myElemIndicesIndices_aux x y (h:t) = if (y == h) then x+1 : myElemIndicesIndices_aux (x+1) y t else myElemIndicesIndices_aux (x+1) y t

-- Exercicio 20 -- 

myNub :: Eq a => [a] -> [a]
myNub [] = []
myNub (h:t) = h : myNub (myNub_aux h t)

myNub_aux :: Eq a => a -> [a] -> [a]
myNub_aux _ [] = []
myNub_aux x (h:t) = if (h == x) then myNub_aux x t else h : myNub_aux x t

-- Ou--

mynub' :: Eq a => [a] -> [a]
mynub' [] = []
mynub' (x:xs) = x : mynub' (filter (/=x) xs)

-- Exercicio 21 --

myDelete :: Eq a => a -> [a] -> [a]
myDelete _ [] = []
myDelete x (h:t) = if (x == h) then t else h : myDelete x t

-- Exercicio 22 --

myRemoveFirstOccurences :: Eq a => [a] -> [a] -> [a]
myRemoveFirstOccurences [] l2 = []
myRemoveFirstOccurences l1 [] = l1
myRemoveFirstOccurences (h1:t1) (h2:t2) = if (h2 == h1) then myRemoveFirstOccurences t1 t2 else h1 : myRemoveFirstOccurences t1 (h2:t2)

-- Exercicio 23 --

myUnion :: Eq a => [a] -> [a] -> [a]
myUnion l1 [] = l1
myUnion [] l2 = l2
myUnion (h1:t1) (h2:t2) = if (h1 == h2) then myUnion (h1:t1) t2 else h1 : myUnion t1 (h2:t2)

-- Exercicio 24 --
{-
myIntercect :: Eq a => [a] -> [a] -> [a]
myIntercect l1 [] = []
myIntercect [] l2 = []
myIntercect (h1:t1) (h2:t2) = if (h1 == h2) then h1 : myIntercect t1 (h2:t2) else myIntercect (h1:t1) t2
-}
-- Exercicio 25 --

myInsert :: Ord a => a -> [a] -> [a]
myInsert x [] = [x]
myInsert x (h:t)
    | x > h = h : myInsert x t
    | x < h = (x : h : t) 
    | otherwise = (x : h : t)

-- Exercicio 26 --

myUnwords :: [String] -> String
myUnwords [] = ""
myUnwords [x] = x
myUnwords (h:t) = h ++ " " ++ myUnwords t

-- Exercicio 27 --

myUnlines :: [String] -> String
myUnlines [] = ""
myUnlines [x] = x ++ "\n"
myUnlines (h:t) = h ++ "\n" ++ myUnlines t

-- Exercicio 28 --

myPGreater :: Ord a => [a] -> Int
myPGreater l = myFindGreater 0 (maximum l) l

myFindGreater :: Ord a => Int -> a -> [a] -> Int
myFindGreater a _ [] = a
myFindGreater a b (h:t) = if (b == h) then myFindGreater a b [] else myFindGreater (a+1) b t

-- Exercicio 29 --

myHasRepetitive :: Eq a => [a] -> Bool
myHasRepetitive [] = False
myHasRepetitive [x] = False
myHasRepetitive (h:t) = if (elem h t) then True else myHasRepetitive t

-- Exercicio 30 --

myDigits :: [Char] -> [Char]
myDigits [] = []
myDigits (h:t) = if (isDigit h == True) then h : myDigits t else myDigits t

-- Exercicio 31 --

myPosOdd :: [a] -> [a]
myPosOdd [] = []
myPosOdd (h:t) = myDiscartOdd 0 (h:t)

myDiscartOdd :: Int -> [a] -> [a]
myDiscartOdd x [] = []
myDiscartOdd x (h:t) = if (mod x 2 /= 0) then h : myDiscartOdd (x+1) t else myDiscartOdd (x+1) t

-- Exercicio 32 --

myPosEven :: [a] -> [a]
myPosEven [] = []
myPosEven (h:t) = myDiscartEven 0 (h:t)

myDiscartEven :: Int -> [a] -> [a]
myDiscartEven x [] = []
myDiscartEven x (h:t) = if (mod x 2 == 0) then h : myDiscartEven (x+1) t else myDiscartEven (x+1) t

-- Exercicio 33 ..

myIsSorted :: Ord a => [a] -> Bool
myIsSorted [] = True
myIsSorted [x] = True
myIsSorted (h:t) = if (h <= head t) then myIsSorted t else False

-- Exercicio 34 --

myISort :: Ord a => [a] -> [a]
myISort [] = []
myISort [x] = [x]
myISort (h:t) = insert h (myISort t)

-- Exercicio 35 -- 

myMenor :: String -> String -> Bool
myMenor "" _ = True
myMenor _ "" = False
myMenor s1 s2 = if (length s1 < length s2) then True else False

-- Caso seja pelo dicionario --

myMenor2 :: String -> String -> Bool
myMenor2 "" _ = True
myMenor2 _ "" = False
myMenor2 (h1:t1) (h2:t2)
    | h1 < h2 = True
    | h1 > h2 = False
    | otherwise = myMenor2 t1 t2

-- Exercicio 36 --

myElemMSet :: Eq a => a -> [(a,Int)] -> Bool
myElemMSet _ [] = False
myElemMSet a ((x,y):t)
    | a == x = True
    |otherwise = myElemMSet a t

-- Exercicio 37 --

myLengthMSet :: [(a,Int)] -> Int
myLengthMSet [] = 0
myLengthMSet ((x,y):t) = y + myLengthMSet t

-- Exercicio 38 --

myConvertMSet :: [(a,Int)] -> [a]
myConvertMSet [] = []
myConvertMSet ((x,y):t) = if (y == 0) then myConvertMSet t else x : myConvertMSet ((x,y-1):t)

-- Exercicio 39 --

myInsertMSet :: Eq a => a -> [(a,Int)] -> [(a,Int)]
myInsertMSet a [] = [(a,1)]
myInsertMSet a ((x,y):t) = if (a == x) then (x,y+1):t else myInsertMSet a t

-- Exercicio 40 --

myRemoveMSet :: Eq a => a -> [(a,Int)] -> [(a,Int)]
myRemoveMSet _ [] = []
myRemoveMSet a ((x,y):t) 
    | a == x && y == 1 = t 
    | a == x = (x,y-1):t
    | otherwise = myRemoveMSet a t

-- Exercicio 41 --

myBuildMSet :: Ord a => [a] -> [(a,Int)]
myBuildMSet [] = []
myBuildMSet (h:t) = myCounter 0 h (h:t)

myCounter :: Ord a => Int -> a -> [a] -> [(a,Int)]
mycounter _ _ [] = []
myCounter a y [x] =  if (x == y) then [(x,a+1)] else [(y,a),(x,1)]
myCounter x y (h:t) = if (y == h) then myCounter (x+1) y t else (y,x) : myCounter 0 h (h:t)

-- OU --

exe41 :: Ord a => [a] -> [(a,Int)]
exe41 [] = []
exe41 (h:t) = exe39 h (exe41 t)
    where exe39 :: Eq a => a -> [(a,Int)] -> [(a,Int)]
          exe39 x [] = [(x,1)]
          exe39 x ((a,b):t) 
                | x == a = (a,b+1) : t
                | otherwise = (a,b) : exe39 x t

-- Exercicio 42 --

myPartitionEithers :: [Either a b] -> ([a],[b])
myPartitionEithers l = (partitionLeft l, partitionRight l)

partitionLeft [] = []
partitionLeft (Left x:t) = x : partitionLeft t
partitionLeft (Right x:t) = partitionLeft t

partitionRight [] = []
partitionRight (Right x:t) = x : partitionRight t
partitionRight (Left x:t) = partitionRight t

-- Exercicio 43 -- 

myCatMaybe :: [Maybe a] -> [a]
myCatMaybe [] = []
myCatMaybe (Just a:t) = a : myCatMaybe t
myCatMaybe (Nothing:t) = myCatMaybe t

-- Exercicio 44 --

data Movimento = North | South | East | West
               deriving Show


myPosicao :: (Int,Int) -> [Movimento] -> (Int,Int)
myPosicao (x,y) [] = (x,y)
myPosicao (x,y) (North:t) = myPosicao (x,y+1) t 
myPosicao (x,y) (South:t) = myPosicao (x,y-1) t 
myPosicao (x,y) (East:t) = myPosicao (x+1,y) t 
myPosicao (x,y) (West:t) = myPosicao (x-1,y) t 

-- Exercicio 45 -- 

myCaminho :: (Int,Int) -> (Int,Int) -> [Movimento]
myCaminho (x1,y1) (x2,y2)
    | x1 > x2 = North : myCaminho (x1,y1) (x2+1,y2)
    | x1 < x2 = South : myCaminho (x1,y1) (x2-1,y2)
    | y1 > y2 = East : myCaminho (x1,y1) (x2,y2+1) 
    | y1 < y2 = West : myCaminho (x1,y1) (x2,y2-1)
    | otherwise = []

-- Exercicio 46 --

myVertical :: [Movimento] -> Bool
myVertical [] = True
myVertical (North:t) = myVertical t
myVertical (South:t) = myVertical t
myVertical (h:t) = False

-- Exercicio 47 --

data Posicao = Pos Int Int
             deriving Show

myMaisCentral :: [Posicao] -> Posicao
myMaisCentral [] = Pos 0 0
myMaisCentral (h:t) = myMaisCentral_aux t h
    where myMaisCentral_aux :: [Posicao] -> Posicao -> Posicao
          myMaisCentral_aux [] max = max
          myMaisCentral_aux (Pos x y:t) (Pos x1 y1) = if x1^2 + y1^2 > x^2 + y^2
                                                    then myMaisCentral_aux t (Pos x y) 
                                                    else myMaisCentral_aux t (Pos x1 y1)

-- Exercicio 48 --

vizinhos :: Posicao -> [Posicao] -> [Posicao]
vizinhos _ [] = []
vizinhos (Pos x y) ((Pos x1 y1):t) | (y == y1) && (x == (x1 +1)) = (Pos x1 y1) : vizinhos (Pos x y) t
                                   | (y == y1) && (x == (x1 -1)) = (Pos x1 y1) : vizinhos (Pos x y) t
                                   | (x == x1) && (y == (y1 +1)) = (Pos x1 y1) : vizinhos (Pos x y) t
                                   | (x == x1) && (y == (y1 -1)) = (Pos x1 y1) : vizinhos (Pos x y)  t
                                   | otherwise = vizinhos (Pos x y) t

-- Exercicio 49 --

mesmaOrdenada :: [Posicao] -> Bool
mesmaOrdenada [] = True
mesmaOrdenada [x] = True
mesmaOrdenada ((Pos x1 y1):(Pos x2 y2): t) = if y1 == y2 then mesmaOrdenada ((Pos x2 y2):t) else False

-- Exercicio 50 --

data Semaforo = Verde | Amarelo | Vermelho
                deriving Show

interseccaoOK :: [Semaforo] -> Bool
interseccaoOK l = interseccaoOK_aux l False
    where interseccaoOK_aux :: [Semaforo] -> Bool -> Bool
          interseccaoOK_aux [] _ = True
          interseccaoOK_aux (Vermelho:t) v = interseccaoOK_aux t v 
          interseccaoOK_aux (_ :t) False = interseccaoOK_aux t True 
          interseccaoOK_aux (_ :t) True = False 

--Ou--          

exe50 :: [Semaforo] -> Bool
exe50 l = (conta_nv l) <= 1
    where conta_nv :: [Semaforo] -> Int
          conta_nv [] = 0
          conta_nv (Vermelho:t) = conta_nv t
          conta_nv (_:t) = 1 + conta_nv t