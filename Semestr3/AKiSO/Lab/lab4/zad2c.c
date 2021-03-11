#include <stdio.h>
#include <stdlib.h>
#include <signal.h>
#include <sys/types.h>
#include <unistd.h>

void handler(int sig){
	if(sig==SIGUSR1){
		printf("Sygnał\n");
	}	
}

int main(){
	int i;
	signal(SIGUSR1,handler);
	for(i=0;i<1000;i++){
		printf("%d ", i);
		raise(SIGUSR1);
	}
	return 0;
}
