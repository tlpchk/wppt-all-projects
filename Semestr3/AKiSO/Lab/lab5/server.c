#include <sys/types.h>
#include <sys/socket.h>
#include <sys/time.h>
#include <netinet/in.h>
#include <stdio.h>
#include <unistd.h>
#include <fcntl.h>
#include <string.h>
#include <stdlib.h>

#define BUFSIZE 1024
#define max(a,b) a > b ? a : b
#define max_clients 6

int max_element (int tab[], int n)
{
	int max=tab[0];
	for (int i=1; i<n; i++)
	{
		if (max<tab[i])
			max=tab[i];
	}
	return max;

}


int main(int argc,char** argv)
{
	int listener;
	struct sockaddr_in addr;
	char buf[BUFSIZE];
	char* login;
	int i,mx;
	int bytes_read;
	int count_clients=0;
	int clients[max_clients];
	char* logins[max_clients];


	listener = socket(AF_INET, SOCK_STREAM, 0);
	if(listener < 0){
		perror("socket");
		exit(1);
	}

	fcntl(listener, F_SETFL, O_NONBLOCK);

	addr.sin_family = AF_INET;
	addr.sin_port = htons(atoi(argv[1]));
	addr.sin_addr.s_addr = INADDR_ANY;
	if(bind(listener, (struct sockaddr *)&addr, sizeof(addr)) < 0){
		perror("bind");
		exit(2);
	}

	listen(listener, max_clients);

	while(1){
		  // Wypełniamy set socketów
		fd_set readset;
		FD_ZERO(&readset);
		FD_SET(listener, &readset);

		for(i=0; i<count_clients; i++){
			FD_SET(clients[i], &readset);
		}

		// Dajemy timeout
		struct timeval timeout;
		timeout.tv_sec = 30;
		timeout.tv_usec = 0;

	  // Czekamy na zdarzenie w jednym z socketów
		mx=max(listener,max_element(clients, count_clients));
		if(select(mx+1, &readset, NULL, NULL, &timeout) <= 0)
		{
			perror("select");
			exit(3);
		}

		// Wyznaczamy typ zdarzenia i obsługujemy go
		if(FD_ISSET(listener, &readset))
		{
			// Jest nowy client, który czeka na połączenie, accept
			int sock = accept(listener, NULL, NULL);
			if(sock < 0)
			{
				perror("accept");
				exit(3);
			}

			//recv(sock,logins[count_clients],BUFSIZE,0);
			//strcpy(buf,strcat("Przyjęty jako ",logins[count_clients]));
			//fcntl(sock, F_SETFL, O_NONBLOCK);
			bytes_read = recv(sock, buf, 1024, 0);
			strcat(buf,": Registered");
			send(sock,buf,BUFSIZE,0);
			clients[count_clients]=sock;
			count_clients++;
			//snprintf(buf, sizeof buf, "Zalogowałeś się pod %s\n",logins[count_clients]);
			//send(sock,buf,BUFSIZE, 0);
		}

		for(i=0; i<count_clients; i++)
		{
			if(FD_ISSET(clients[i], &readset))
			{
				  // Są nowe dane od klient
				bytes_read = recv(clients[i], buf, 1024, 0);
				if(bytes_read <= 0)
				{
					// Wylogowanie, usuwamy z socketów
					close(clients[i]);
					printf("// Wylogował się\n");
					for(int j=i; j<count_clients; j++)
					{
							if(j==count_clients-1){
								clients[j]=0;
							}else{
								clients[j]=clients[j+1];
								logins[j]=logins[j+1];
							}
					}
					count_clients--;
					continue;
				}

				for(int j=0;j<count_clients;j++){
					send(clients[j],buf,BUFSIZE,0);
				}
		}
	}
}

    return 0;
}
