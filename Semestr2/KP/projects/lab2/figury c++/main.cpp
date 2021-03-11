/**LAB 3_ZAD.2
*@author Max_Telepchuk
*/


#include <iostream>
#include <cmath>
#include <vector>
#include <cstring>
#include <string>
#include <sstream>

#define PI 3.14159265

using namespace std;

class Figura
{
	public:
        virtual float ObliczObwod(){};
        virtual float ObliczPole(){};
};

class Czworokat: public Figura
{
	protected:
        float bok1;
        float bok2;
        float bok3;
        float bok4;
        float kat;
	public:
        virtual float ObliczObwod(){};
    	virtual float ObliczPole(){};
};

class Okrag: public Figura
{
	private:
        float promien;
	public:
        float ObliczObwod()
	{
		return  2.0f *PI* promien;
	}

	float ObliczPole()
	{
		return PI * promien * promien;
	}

	Okrag (float promien1)
	{
		promien = promien1;
	}
};

class Pieciokat: public Figura
{
	private:
        float bok;
	public:
	    Pieciokat (float bok1)
        {
            bok = bok1;
        }
        float ObliczObwod()
        {
            return  (float)(bok*5.0);
        }

        float ObliczPole()
        {
            return (float)(bok*bok*1.72);
        }
};

class Szesciokat: public Figura
{
	private:
        float bok;
	public:
        float ObliczObwod()
        {
            return  6.0f * bok;
        }
        float ObliczPole()
        {
            return  bok * bok * 2.59f;
        }
        Szesciokat (float n)
        {
            bok = n;
        }
};

class Kwadrat: public Czworokat
{
	public:
        float ObliczObwod()
	{
		return  4.0f * bok1;
	}
	float ObliczPole()
	{
		return bok1 * bok1;
	}
	Kwadrat (float n)
	{
		bok1 = n;
	}
};

class Romb: public Czworokat
{
	public:
        float ObliczObwod()
        {
            return  4.0f * bok1;
        }
        float ObliczPole()
        {
            return bok1 * bok1 * sin(kat);
        }
	Romb (float n1, float n2)
	{
		bok1 = n1;
		kat = n2;
	}
};

class Prostokat: public Czworokat
{
	public:
        float ObliczObwod()
        {
            return  2.0f * bok1 + 2.0f * bok2;
        }
        float ObliczPole()
        {
            return bok1 * bok2;
        }
        Prostokat (float n, float m)
        {
            bok1 = n;
            bok2 = m;
        }
};

int Tablica (char tab[], int *j, vector<Figura*> *dane, char* argv[], int argc, int wskliczby, int k, int liczby[])
	{
		int a=1;
		switch(tab[*j]){

		case 'o':{              //j - wskaźnik na pierwszy argument
		    if (wskliczby<argc-2)    // wskliczby - indeks parametrów
            {
                if (liczby[wskliczby]>=0)
                {
                    dane->push_back(new Okrag(liczby[wskliczby]));
                }
                else
                {
                    cout <<"NIEPOPRAWNE DANE"<< endl;
                    a=-1;
                }
            }
            else
            {
                a=-1;
                cout <<"NIEPOPRAWNE DANE" << endl;
            }
            break;
		}

		case 'c':{
		    if(wskliczby+4<argc-2)
            {
                int* i=&liczby[wskliczby];
                if ((*i>=0) && (*(i+1)>=0) && (*(i+2)>=0) && (*(i+3)>=0) && (*(i+4)>=0))
                {
                    if ((*i==*(i+1)) && (*i==*(i+2)) && (*i==*(i+3)))
                    {
                        if(*(i+4)==90)
                            dane->push_back(new Kwadrat(*i));
                        else if (*i<=180 && *i>0)
                                dane->push_back(new Romb(*i, *(i+4)));
                        else
                                cout <<"NIEPOPRAWNE DANE"<< endl;

                    }
                    else if ((((*i==*(i+1))&&(*(i+2)==*(i+3)))||((*i==*(i+2))&&(*(i+1)==*(i+3)))||((*i==*(i+4))&&(*(i+1)==*(i+2))))&&(*(i+4)==90))
                            dane->push_back(new Prostokat((min(*i,*(i+1)),min(*(i+2),*(i+3))),(max(*i,*(i+1)),max(*(i+2),*(i+3)))));
                    else
                        {
                            cout <<"NIEPOPRAWNE DANE" << endl;
                            a=-1;
                        }
                }
                else
                {
                    cout <<"NIEPOPRAWNE DANE"<< endl;
                    a=-1;
                }
            }
            else
            {
                a=-1;
                cout <<"NIEPOPRAWNE DANE"<< endl;
            }
            break;
		}
		case 'p':{
		    if (wskliczby<argc-2)
            {
                if (liczby[wskliczby]>=0)
                {
                    dane->push_back(new Pieciokat(liczby[wskliczby]));
                }
                else
                {
                    cout <<"NIEPOPRAWNE DANE"<< endl;
                    a=-1;
                }
            }
            else
            {
                a=-1;
                cout <<"NIEPOPRAWNE DANE"<< endl;
            }
            break;
		}
		case 's':{
		    if (wskliczby<argc-2)
            {
                if (liczby[wskliczby]>=0)
                {
                    dane->push_back(new Szesciokat(liczby[wskliczby]));
                }
                else
                {
                    cout <<"NIEPOPRAWNE DANE"<< endl;
                    a=-1;
                }
            }
            else
            {
                a=-1;
                cout <<"NIEPOPRAWNE DANE"<< endl;
            }
            break;
		}
		}
		return a;
	}

int TestTablica (char tab[], int *j, vector <Figura*> *dane, char* argv[], int argc, int wskliczby, int k, int liczby[]) throw (string)
{
			if ((tab[*j]!='o') && (tab[*j]!='c') && (tab[*j]!='p') && (tab[*j]!='s'))
				throw (string) "NIEPOPRAWNE DANE";
		return Tablica (tab, j, dane, argv, argc, wskliczby, k, liczby);
}

int main(int argc, char* argv[])
{
    if (argc>1)
    {
        char *tab = argv[1];
        int j=0, k=0, wskliczby=0;
        vector <Figura*> dane;
        int length = strlen(tab);
        int *liczby=new int [argc-2];
        for (int j=2; j<argc; j++)
        {
            stringstream s;
            s << argv[j];
            s >> liczby[j-2];
            if (s.fail())
            {
                cout <<"NIEPOPRAWNE DANE"<< endl;
                return 0;
            }
        }
        int a;
        for (j=0; j<length; j++)
        {
            try
            {
                a = TestTablica(tab, &j, &dane, argv, argc, wskliczby, k, liczby);
                if (a==1)
                {
                    k++;
                }
                if (tab[j]=='c')
                    wskliczby=wskliczby+5;
                else
                    wskliczby++;
				}
            catch (string w)
            {
                cout << tab[j] << " - " << w << endl;
            }
        }
            for (int i=0; i<k; i++)
            {
                cout << "Pole figury "<<i+1<<" = "<< dane[i]->ObliczPole() << endl;
                cout << "Obwod figury "<<i+1<<" = "<< dane[i]->ObliczObwod() << endl;
                cout<<""<<endl;
            }
    }

    return 0;
}
