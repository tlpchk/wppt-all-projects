#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <fcntl.h>

#define BUFSIZE 1024

char buf[1024];

int main(int argc,char** argv)
{
		char c;
		char* massange;
		int i;
		int sock;
		struct sockaddr_in addr;

		sock = socket(AF_INET, SOCK_STREAM, 0);
		if(sock < 0)
		{
			perror("socket");
			exit(1);
		}
		addr.sin_family = AF_INET;
		addr.sin_port = htons(atoi(argv[1])); 
		addr.sin_addr.s_addr = htonl(INADDR_LOOPBACK);
		if(connect(sock, (struct sockaddr *)&addr, sizeof(addr)) < 0)
		{
			perror("connect");
			exit(2);
		}


		while (1)
		{
			massange = malloc(sizeof(char) * BUFSIZE);
			c = getchar();
			i=0;
			while (c!='\n')
			{
				if (c==EOF)
					exit(0);
				massange[i] = c;
				i++;
				if (i >= BUFSIZE)
				{
					massange = realloc(massange, BUFSIZE*2);
				}
			c = getchar();
			}
			massange[i]='\0';
			send(sock, massange, i+1, 0);
			recv(sock, buf, BUFSIZE, 0);

			printf("%s\n",buf);
		}
	close(sock);

	return 0;
	}
