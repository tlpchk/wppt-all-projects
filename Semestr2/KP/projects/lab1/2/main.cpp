#include <iostream>
#include <string>
#include <stdexcept>
#include <sstream>

using namespace std;

class WierszTrojkataPascala{
	public:
	int* tab;

	WierszTrojkataPascala(int n)
	{
			tab=new int[n+1];

			for(int i=1;i<=n;i++)
					tab[i]=0;

			tab[0]=1;

			for(int i=0;i<n;i++){
				for(int j=n;j>0;j--){
					if((tab[j]==0) && (tab[j-1]!=0))
						tab[j]=1;
					if(j!=n)
						if(tab[j+1]!=0)
							tab[j]=tab[j-1]+tab[j];
				}
			}
		}

	int wspolczynik(int m){
		return tab[m];
	}
};



int main (int argc,char** args)
{
    int n;
    stringstream s;
    s<<args[1];
    s>>n;
    if (n>33||n<0){
        cout<<"Nie moge stworzyc tego wiersza"<<endl;
        return 0;
    }

    if(s.fail()){
        cout<<"Niepoprawne dane"<<endl;
        return 0;
    }
    WierszTrojkataPascala wiersz=WierszTrojkataPascala(n);
    for(int i=2;i<argc;i++){
        s.sync();
        s.clear();

        s<<args[i];
        s>>n;
        if(s.fail())
        {
                cout<<"To nie jest liczba"<<endl;
                continue;
        }
        if((n>=0)&&(n<=argc))
        {
            cout<<args[i]<<": "<<wiersz.wspolczynik(n)<<endl;
        }
        else
        {
            cout<<n<<": liczba spoza zakresu"<<endl;

        }

    }
}
