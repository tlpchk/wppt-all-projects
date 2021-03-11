evenList :: (Integral a) => [a] -> a
evenList = foldr (\x acc -> if even x then acc+1 else acc) 0
