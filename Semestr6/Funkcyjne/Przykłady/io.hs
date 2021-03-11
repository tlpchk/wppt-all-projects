main = do
  putStr "Podaj liczbę calkowitą : "
  str <- getLine
  let n = read str :: Int
  putStrLn ("Jej szescian to " ++ show(n * n * n))
