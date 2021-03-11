perm :: (Eq a) => [a] -> [[a]]
perm [] =[[]]
perm xs = [x:ys|x<-xs, ys<-perm (delete x xs)]
	where
		delete x xs = [a | a<-xs, a/=x]
