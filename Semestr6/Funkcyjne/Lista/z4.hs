triple n = [(a,b,c) | a<-[1..n], b<-[1..a], c<-[1..b], gcd b c == 1, a^2 == b^2 + c^2, b<c] 
