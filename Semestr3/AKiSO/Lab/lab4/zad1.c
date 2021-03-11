#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>
#include <unistd.h>


int main(int argc,char **argv,char **envp){
	setuid(0);
	system("/bin/bash");
	return 0;
}
/* 
gcc zad1.c
sudo chown root a.out
sudo chmod u+s a.out
ll a.out
./a.out
*/
