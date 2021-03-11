isPrime n
 | n <= 1 = False
 |otherwise = null [a | a<-[2..(floor (sqrt (fromInteger n)))], n `mod` a == 0]
