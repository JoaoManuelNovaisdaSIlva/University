
-- Exercício 3 

mylength :: [a] -> Int
mylength [] = 0
mylength (h:t) = 1 + mylength t 

myreverse :: [a] -> [a]
myreverse [] = []
myreverse (x:xs) = myreverse xs ++ [x] -- não é myreverse xs : x pq o (:) da append ao elemento da sua esquerda á lista da direita mas eu tinha ao contrário

-- Exercício 4

myfilter :: Eq a => [a] -> a -> [a]
myfilter [] _ = []
myfilter (h:t) x = if h == x then myfilter t x else h : myfilter t x

-- Exercício 6

testeUncurrySoma :: Int -> Int -> Int
testeUncurrySoma a b = a+b

testeCurrySoma :: (Int, Int) -> Int
testeCurrySoma (x,y) = x+y

testeFlip :: Eq a => [a] -> a -> [a]
testeFlip l c = take 10 . (c:) . filter(/=c) $ l

myuncurry :: (a -> b -> c) -> (a,b) -> c
myuncurry f (x,y) = f x y 

mycurry :: ((a,b) -> c) -> a -> b -> c
mycurry f a b = f (a,b)

myflip :: (a -> b -> c) -> b -> a -> c
myflip f y x = f x y
