#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <sys/wait.h>
#include <signal.h>
#include <stdbool.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#define BUFSIZE 1024

int woo;

void lsh_loop();
char** parse_line(char* buf,int* newargc,char* korektor);
char* read_input_data(int* przekierowanie,char* plik);
char*** parse_cmd(char* buf,int* args);
void lsh_loop();

void handler(int sig){
	if(sig==SIGINT)
	{
		fflush(STDIN_FILENO);
		printf("\n");
	}
	//printf("\n");
 	//printf("! ");
}
/*
	buf  linia ze wszystkimi poleceniami
	args  w tą liczbę wpisuje się ilość poleceń

	return zwraca tablicę dwuwymiarową z argumentami poleceń
*/
char*** parse_cmd(char* buf,int* args){
	char **pipes,***commands;
	int i,ag;

	ag=0;
	(*args)=0;
	pipes=malloc(sizeof(char*)*32);
	commands=malloc(sizeof(char**)*16);
	pipes=parse_line(buf,args,"|");
	for(i=0;i<(*args);i++){
		ag=0;
		commands[i]=parse_line(pipes[i],&ag," ");
	}
	commands[*args]=NULL;

	return commands;
}

int lsh_cd(char* dir){
			if (dir == NULL)
		{
			perror("lsh:expected argument to \"cd\"\n");
			exit(1);
		}
		else
		{
			if (chdir(dir) != 0)
			{
				perror("lsh:Bład zmainy katalogu");
				exit(1);
			}
		}
		return EXIT_SUCCESS;
}

/*
	buf  linia ze wszystkimi poleceniami komendy
	args  w tą liczbę wpisuje się ilość argumentóœ

	return zwraca tablicę z argumentami komendy
*/
char** parse_line(char* buf,int* newargc,char* korektor)
{
		char *schowek,**newargv,*line;
		line=malloc(sizeof(char)*BUFSIZE);
		newargv= malloc(sizeof(char*)*32);

		strcpy(line,buf);
		schowek=strtok(line,korektor);
		while(schowek!=NULL)
		{
			newargv[*newargc]=schowek;
			schowek=strtok(NULL,korektor);
			(*newargc)++;
		}
		newargv[*newargc]=NULL;
		return newargv;
}

void lsh_loop(){
	int status,i,count_of_cmd,iter,fd_in,indx_of_last,przekierowanie,fd;
	int pipefd[2];
	pid_t pid;
	char *buf,***cmd,*myfile;
	FILE* file;

	while(1)
	{
		printf("! ");
		buf=malloc(sizeof(char)*BUFSIZE);
		cmd=malloc(sizeof(char**)*16);
		myfile=malloc(sizeof(char)*32);
		fd_in=0;
		buf=read_input_data(&przekierowanie,myfile);
		if(buf[0]!='\0'){
		cmd=parse_cmd(buf,&count_of_cmd);
		/*for(int k=0;k<count_of_cmd;k++){
			printf("%s",cmd[k]);
		}*/
		indx_of_last=0;
		while(cmd[count_of_cmd-1][indx_of_last+1]!=NULL){
			indx_of_last++;
		}

		if(*cmd!=NULL){
		 	if(strcmp((*cmd)[0],"exit")==0)
			{
				raise(9);
			}

			if(strcmp(cmd[0][0],"cd")==0)
			{
				lsh_cd(cmd[0][1]);
			}

			if((pid=fork()) > 0)
			{
				if(strcmp(cmd[count_of_cmd-1][indx_of_last],"&")!=0)
				{
					cmd[count_of_cmd-1][indx_of_last]=NULL;
					wait(&status);
				}
			}
			else if(pid==0 && strcmp(cmd[0][0],"cd")!=0)
			{
				while (*cmd!=NULL) {
					pipe(pipefd);
					if ((pid = fork()) == -1)
		        {
		          exit(EXIT_FAILURE);
		        }
		      else if (pid == 0)
		        {
							if(przekierowanie==0){
								file = fopen(myfile, "a+");
								dup2(fileno(file), STDIN_FILENO);
							}
		          else{
								dup2(fd_in, 0); //zmień wejście zgodnie ze starym
							}

		          if (*(cmd + 1) != NULL)
							{
		            dup2(pipefd[1], 1);
							}
							else if(przekierowanie==1)
							{
								file = fopen(myfile, "w+");
								dup2(fileno(file), STDOUT_FILENO);
							}
							close(file);
		         	close(pipefd[0]);
							if(przekierowanie==2){
								file = fopen(myfile, "w+");
								dup2(fileno(file), STDERR_FILENO);
							}
		          execvp((*cmd)[0], *cmd);
							perror("lsh");
		          exit(EXIT_FAILURE);
		        }
		      else
		        {
		          wait(NULL);
		          close(pipefd[1]);
		          fd_in = pipefd[0]; //Zachowuje wejście dla następnej komendy
		          cmd++;
		        }

					}
					exit(0);
				}
			}
			buf=NULL;
			cmd=NULL;
		}
	}
}

char* read_input_data(int* przekierowanie,char* plik)
{
	char* buf = malloc(sizeof(char)*BUFSIZE);
	int c,i;
	i=0;
	*przekierowanie=-1;
	c=getchar();
	while(c!='\n')
	{
		if(c==EOF){return "exit";}

		buf[i]=c;
		c=getchar();
		if(c=='>'||c=='<'){
			if(c=='>'){
				if(buf[i-1]=='2'){
							buf[i-1]=' ';
							*przekierowanie=2;
				}
				else{
					*przekierowanie=1;
				}
			}
			else{
				*przekierowanie=0;
			}
			c=getchar();
			while(c!='\n')
			{
				*plik=c;
				c=getchar();
				plik++;
			}
			*plik='\0';
		}
		i++;
	}
	buf[i]='\0';

	return buf;
}

int main(int argc,char **argv)
{
	signal(SIGINT,handler);
	lsh_loop();
	return 0;
}
