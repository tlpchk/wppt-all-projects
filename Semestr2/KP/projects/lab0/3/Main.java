public class Main  			/*wypisuje liczby wraz z ich
					najwiekszymi dzielnikami**** 
					w kolejnych wierszach*******
					*****@autor Max Telepchuk***/
{
	public static int div (int n)        //oblicza naiwekszy podzielnik liczby n
		{	
			int sqrt = (int)(Math.sqrt(n));
			int i=2;

			if(n==1||n==2){
				return 1;
			}else{
				while(i<=sqrt){
					if(n%i==0){
						return n/i;
					}
				i++;
				}
			}
			return 1;
		}

    public static void main(String[] args) 
	{
		int n;
		
		for (int i=0; i<args.length; i++) 
		{
			try {	n=Integer.parseInt(args[i]);}
			catch (NumberFormatException ex) {
				System.out.println("Nie kupuję tego");
				continue;
			}
			if(n<=0){
				System.out.println("Robisz coś żle");				
			}else{
			System.out.println ("Najwiekszy dzielnik liczby " + n + " to " + div(n));
			}
		}
    }
}
