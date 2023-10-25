

data Point a = Point{x::a, y::a, z::a} deriving (Eq, Show)
--Point :: a -> a -> a -> Point a

p1 = fst
p2 = snd

split :: (a -> b) -> (a -> c) -> a -> (b,c)
split f g x = (f x, g x)