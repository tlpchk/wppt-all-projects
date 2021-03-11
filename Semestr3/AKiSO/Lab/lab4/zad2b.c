#include <stdio.h>
#include <stdlib.h>
#include <signal.h>
#include <sys/types.h>
#include <unistd.h>

void handler(int sig){
	if(sig==SIGINT){
		printf("oki\n");		
	}	
}

int main(){
	if(kill(1,9)!=0){printf("Porażka\n");}
	return 0;
}
