data Writer Int = Writer ([String], Int) deriving Show

instance Functor Writer where
  fmap f (Writer (s,x)) = Writer (s,f x)
    

instance Applicative Writer where
  pure = return
  fw <*> xw = do f<- fw; x<- xw; return( f x)

instance Monad Writer where
  return x = Writer (x, "")
  (Writer (x,s)) >>= f = let Writer (y,t) = f x in Writer (y, s++t)

half::Int -> Writer Int
half x = Writer (x `div` 2, "Polowie " ++ show x ++ "; ")

