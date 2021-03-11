rleDecode [] = []
rleDecode ((a,b):xs) = (replicate a b) ++ (rleDecode xs)
