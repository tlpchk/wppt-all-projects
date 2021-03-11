#include <stdio.h>
#include <stdlib.h>
#include <signal.h>
#include <sys/types.h>
#include <unistd.h>

void handler(int sig){
	int i;
	for(i=1;i<65;i++){
		if(sig==i){
			printf("Ja ci nie słyszę\n");
		}
	}	
}

int main(){
	int i;
	char str[20];
	for(i=1;i<65;i++){
		signal(i,handler);
		printf("%d sygnał:\n", i);
		sprintf(str,"kill -%d %d",i,(int)getpid());
		system(str);
	}
	while(1);
	return 0;
}
