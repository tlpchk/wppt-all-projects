#include "f.h"

int main(){
	char *p,*c;
	bool b;

	scanf("%s",p);
	scanf("%s",c);
	b=match(p,c);
	if(b)
		printf("1");
	else
		printf("0");
	return 0;
}
