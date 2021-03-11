perf :: Integer -> Bool
perf 1 = False
perf n = (sum [(d + n `div` d) | d <-[2..(floor (sqrt (fromInteger n)))], n `mod` d == 0])+1 == n
perfs m = [x | x<-[1..m], perf x] 






























