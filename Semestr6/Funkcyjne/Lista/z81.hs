import Control.Monad.Writer


myGCD :: Int -> Int -> Writer [String] Int
myGCD a 0 = writer (a, ["result = " ++ show a])
myGCD a b = do
	tell [show a ++ " mod " ++ show b ++ " = " ++ show modulo]
	prev <- myGCD b modulo
	return prev
	where modulo = a `mod` b
