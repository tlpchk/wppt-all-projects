newt :: (Integral a) => a -> a -> a  
newt n k
	| n < k = 0 
	| k == 0 = 1
	| k == n = 1
	| otherwise = newt (n-1) (k-1) + newt (n-1) k



















