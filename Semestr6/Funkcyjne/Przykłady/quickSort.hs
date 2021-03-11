qs :: (Ord a) => [a] -> [a]
qs [] = []
qs (x:xs) = qs smaller ++ [x] ++ qs bigger
	where 
		smaller = [a | a <- xs, a < x ]
		bigger = [a | a <- xs, a >= x]
