nub :: (Eq a) => [a] -> [a]
nub [] = []
nub [x] = [x]
nub (x:xs) = x:nub [a | a<-xs, a/=x]

nub' [] = []
nub' (x:xs)
	| x `elem` xs = nub' xs
	| otherwise   = x : (nub' xs)
