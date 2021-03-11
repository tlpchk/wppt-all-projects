isPrime' :: Integer -> Bool

isPrime' 1 = False
isPrime' n = null [ x | x <- [2..(floor (sqrt (fromInteger n)))], n `mod` x == 0]

primeDiv n = length [x | x <- [2..n `div`2], n `mod` x == 0, isPrime' x]





f xs = foldl (\acc x -> if (x `elem` acc) == False then acc++[x] else acc) [] xs
