
#include <unistd.h>
#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define BUFSIZE 1024

void print_int(int integer){
  int r,i,i2,n;
  n=0;
  r=integer;
  if(integer<0){r=-integer;}
  i=1;
  i2=0;
  while(r>0){
    r=r/10;
    i=i*10;
    n++;
  }

  if(integer<0){
    n++;
  }
  char result[n];

  if(integer<0){
    result[0]='-';
    i2=1;
    integer*=(-1);
  }
  while(i2<n){
    result[i2]=(((integer%i)-(integer%(i/10)))/(i/10))+'0';
    i2++;
    i/=10;
  }
  write(1,result,sizeof(result));
}

int to_deck(char* str,int len,int s){
    int integer,i;
    integer=0;
    i=1;

    while(len>0){
        len--;
      /*  if(str[len]!='1'&&str[len]!='0'){
          write(2,"Złę paramety dla bin",19);
          exit(1);
        }*/
        if(str[len]>'9'){
          integer+=(str[len]-'0'-7)*i;
        }
        else{
          integer+=(str[len]-'0')*i;
        }
        i*=s;
    }
    return integer;
}

void dec_to(int s,int liczba){
  if(liczba<0){
    write(2,"\nLiczba jest ujemną\n",20);
    exit(1);
  }
  int n=0;
  int i;
	int check=1;
	while(liczba>=check) {
		n++;
		check=check*s;
	}
  i=n-1;
  int tab[n];
  while(i>=0)
  {
    tab[i] = (liczba%s)+'0';
    if(liczba%s>9){
      tab[i]+=7;
    }
    liczba/=s;
    i--;
  }
  write(1,tab,sizeof(tab));
}

void myprintf(char* buffer, ...){
	char* arg = (char *) &(buffer) + sizeof(buffer);
	char* str;
  char c;
	int integer;
	str = malloc(sizeof(char)*BUFSIZE);

	while((*buffer)!='\0'){
		if((*buffer)=='%'){
			buffer++;
			c=*buffer;

			if(c=='d'){
				integer = *((int *)arg);
				arg += sizeof(int);
        print_int(integer);
			}

			if(c=='s'){
        write(1,*((char **)arg),strlen(*((char **)arg)));
        arg +=sizeof(char *);
			}

			if(c=='b'){
				integer = *((int *)arg);
				arg += sizeof (int);
        dec_to(2,integer);
			}
      if(c=='x'){
				integer = *((int *)arg);
				arg += sizeof (int);
        dec_to(16,integer);
			}

			buffer++;
		}
		write(1,buffer,sizeof(char));
		buffer++;
    str=malloc(sizeof(char)*BUFSIZE);
  }
}

void myscanf(char* buffer,...){
  char** arg = (char **)&(buffer) + sizeof (buffer)+3;
  char *input_data,*str,c,c1;
  int i,n,*integer;
  str = malloc(sizeof(char)*BUFSIZE);
  input_data=malloc(sizeof(char)*BUFSIZE);
  n=read(0,input_data,BUFSIZE);
  input_data[n-1]='\0';

  while((*buffer)!='\0'){
    if((*buffer)=='%'){
      buffer++;
      c=*buffer;

      if(c=='d'){
        i=0;
        do{
          str[i]=*input_data;
          input_data++;
          i++;
        }while(*input_data!=*(buffer+1));
        integer=(int* )arg;
        *integer=to_deck(str,i,10);
        arg ++;
       }

       if(c=='s'){
         i=0;
         printf("%s\n", *arg);
         do{
           *arg[i]=*input_data;
           input_data++;
           i++;
         }while(*input_data!=*(buffer+1));
         arg++;
       }
       if(c=='b'){
 				integer=(int* )arg;
        i=0;
        do{
          str[i]=*input_data;
          input_data++;
          i++;
        }while(*input_data!=*(buffer+1));
        *integer=to_deck(str,i,2);
        arg++;
 			}
      if(c=='x'){
       integer=(int* )arg;
       i=0;
       do{
         str[i]=*input_data;
         input_data++;
         i++;
       }while(*input_data!=*(buffer+1));
       *integer=to_deck(str,i,16);
       arg++;
     }

      buffer++;
    }
    if((*buffer)!=*input_data){
      write(2,"Niezgodnosc\n",12);
      return;
    };
    if(*buffer=='\0'){
      return;
    }
    buffer++;
    input_data++;
    str=malloc(sizeof(char)*BUFSIZE);
  }
}

void main(){
	int i=11;
  char* s="Str";
  int bin=15;
  int hex=255;
  myscanf("%d %b %x",&i,&bin,&hex);
  myprintf("Int = %x, Bin = %b Hex = %x, STR = %s\n",i,bin,hex,s);

}
