statistic::(Fractional a) => [a]->(a,a)

f (sum', sumsq) x = ((sum'+x),(sumsq+x^2))
func = fold f(0,0)
statistic l = (avg,var)
	where n = fromInteger(length l)
		  sum
	      solution = func l
		  avg = ((fst solution)/n)
	      var = (((snd solution) -(2*avg*(fst solution))/n + avg^2)
