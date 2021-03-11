-- pack [] = []
-- pack (x:xs) = (x:takeWhile (==x) xs) : pack (dropWhile (==x) xs)



dropFirst (x:xs) = foldl (\acc i -> if i==x then acc else acc++[i]) [] xs
pack []   = []
pack (x:xs) = [replicate (  (length (x:xs)) - (length withoutX) ) x ] ++ pack withoutX
	where withoutX = dropFirst (x:xs)






