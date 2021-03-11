#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <sys/wait.h>
#include <signal.h>

void cntl_d_handler(int dummy){
	signal(SIGKILL,cntl_d_handler);
	printf("halo\n");
}


int main(int argc,char **argv){
while(1)
{
	char *buf = malloc(sizeof(char)*1024);
	char c;
	char *newargv[16];
	int i,n,j,newargc,status;
	char korektor[] = " ";
	char *schowek;

	signal(SIGQUIT,cntl_d_handler);
	newargc=0;
	n=read(0,buf,1024);
	buf[n-1]='\0';
	schowek = strtok(buf,korektor);
	while(schowek!=NULL){
		newargv[newargc]=schowek;
		//printf("%s\n",newargv[newargc]);
		newargc++;
		schowek=strtok(NULL,korektor);	
	}
	newargv[newargc]=NULL;
 	
	i=fork();
	if(i>0){
		wait(&j);
	}
	else{
		//printf("%s\n",newargv[0]);
		if(strcmp(newargv[0],"exit")==0){
			exit(2);				
		}
		else{
			j=execvp(newargv[0],newargv);
			perror("Blad uruchmienia programu");
			exit(1);
		}
	}
}
}
