subSeq :: Int -> Int -> [[Int]]
subSeq _ 0 = [] 
subSeq n 1 = [[x] | x<-[1..n]]
subSeq n k = [[y] ++ xs | xs <- subSeq n (k-1), y <- [1..(head xs)-1]]
