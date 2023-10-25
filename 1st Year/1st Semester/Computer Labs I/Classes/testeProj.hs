module teste where

type Numero = Int 
type Chabs = Int 

data Coisa
    = Coisa Numero
    | Cena Chabs
    deriving (Show, Read, Eq)


incrementaCoisa :: Coisa -> Coisa
incrementaCoisa () = ()
incrementaCoisa c