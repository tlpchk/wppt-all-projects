rev :: [a] -> [a]
rev xs = rev' xs []
	where
		rev' [] acc = acc
		rev'(x:xs) acc = rev' xs (x:acc) 		
