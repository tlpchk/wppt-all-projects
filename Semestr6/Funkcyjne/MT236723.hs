import Control.Monad.Writer 

z1 :: Int -> (Int->Int->Bool) -> [(Int,Int)]
z1 n p = [(a,b) | a<-[1..n], b<-[1..n], (p a b) == True]

z2 0 = 1
z2 1 = 1
z2 n = head (func n 2 [z2 1,z2 0])
	where func n k all@(x1:x2:_)
		| (n == k) 	= all
		| otherwise = func n (k+1) ((x1-x2+k^2):all) 
		


subSeq :: [Int] -> [[Int]]
subSeq [] = [[]]
subSeq (x:xs) = [x:seq | seq <- (subSeq xs)] ++ (subSeq xs)


z3 :: Int -> [[Int]]
z3 n = init ((subSeq (filter (\x -> (x `mod` 2) == 0) [1..n])) ++ (subSeq (filter (\x -> (x `mod` 2) /= 0) [1..n])))


isPrime' n
 | n <= 1 = False
 |otherwise = null [a | a<-[2..(floor (sqrt (fromInteger n)))], n `mod` a == 0]

primes n = length [x | x <- [2..n], isPrime' x]

z4 n = [((log (fromIntegral k)) * (fromIntegral (primes k))) / (fromIntegral k)  | k<-[2..n]]



myWriter :: Int -> Writer [String] Int  
myWriter n
    | n == 1 = do    
        return n
	| even n = do
		tell [show (n `div` 2)]
		myWriter (n `div` 2)
    | otherwise = do  
        tell [show (n*3 + 1)] 
        myWriter (n*3 + 1)

z5 n = runWriter(myWriter n)
