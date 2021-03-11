-- pary względnie pierwsze
import Control.Monad.Writer  

z1 n = [(a,b)| a<-[1..n], b<-[a+1..n], (gcd a b == 1)]
		
z2 n
	| n == 0 	= 0
	| n == 1 	= 0
	| otherwise = (z2 (n-1)) + (z2 (n-2)) + n 


z3 (x:xs) k 
	| length (x:xs) == k = []
	| otherwise = [take k xs] ++ (z3 xs k)
	
z4 n = sum [ (fromIntegral (phi k)) / (2.0^k) | k<-[1..n]]
	where phi k = length [x | x <- [1..k], ((gcd k x) == 1) ]

-- myGCD :: Int -> Int -> Writer [String] Int

  
logNumber :: Int -> Writer [String] Int  
logNumber x = writer (x, ["Got number: " ++ show x])  
  
multWithLog :: Writer [String] Int  
multWithLog = do  
    a <- logNumber 3  
    b <- logNumber 5  
    tell ["Gonna multiply these two"]  
    return (a*b) 


gcd' :: Int -> Int -> Writer [String] Int  
gcd' a b  
    | b == 0 = do  
        tell ["Finished with " ++ show a]  
        return a  
    | otherwise = do  
        tell [show a ++ " mod " ++ show b ++ " = " ++ show (a `mod` b)]  
        gcd' b (a `mod` b)  


