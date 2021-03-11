splits xs = [(a,b) | i <- [0..length xs],let a = take i xs; b = drop i xs ]
		
