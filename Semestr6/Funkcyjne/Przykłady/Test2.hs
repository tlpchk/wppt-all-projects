take' :: (Num i, Ord i) => i -> [a] -> [a]  
take' n (x:xs)  
    | n <= 0  	= []  
	| null xs 	= []  
	| otherwise = x : take' (n-1) xs  
