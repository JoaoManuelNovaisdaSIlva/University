import System.IO

data MyType = MyType Int String
  deriving (Show, Read)

main = do
  -- Open the file in read mode
  handle <- openFile "input.txt" ReadMode

  line <- hGetLine handle
  print line

  -- Close the file
  hClose handle