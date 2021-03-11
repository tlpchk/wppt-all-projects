zeros_fac 0 = 0
zeros_fac n = (n `div` 5) + zeros_fac (n `div` 5) 
