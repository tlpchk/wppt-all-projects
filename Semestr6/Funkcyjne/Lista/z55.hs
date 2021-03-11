approx n = foldl (\acc x -> acc + 1/(foldr (*) 1 [1..x] )) 0 [1..n]
