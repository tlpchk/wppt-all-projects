euler_in x y =
	if (x <= 1)
		then 1
	else if (gcd x y == 1)
		then succ (euler_in (x-1) y)
	else euler_in (x-1) y

euler x = euler_in x x

coprime a b = (gcd a b == 1 )
euler2 n = length (filter(coprime n) [1..n-1])
euler3 n = length [a | a<-[1..n], gcd a n == 1 ]
dividersEulerSum n = sum [ euler a | a<-[1..n], mod n a == 0]

