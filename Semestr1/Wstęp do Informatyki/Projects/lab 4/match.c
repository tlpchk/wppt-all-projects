#include "f.h"

bool match(char *pattern, char *chain){
	if(*pattern!='*'){	
		while(*pattern==*chain||*pattern=='?'){
			pattern++;
			chain++;
			if(*chain=='\0' && *pattern=='\0')
				return true;
		}

		if(*pattern!='*')
			return false;
	
		match(pattern,chain);
	}

	else{		
		char *p, *c;
		int test=0;

		while(*pattern=='*')
			pattern++;

		if(*pattern=='\0')
			return true;

		for(;;chain++){
			p=pattern;
			c=chain;
			if(*pattern==*chain||*pattern=='?'){					
				for(;(test==0)&&(*pattern==*chain||*pattern=='?'||*pattern=='*'||*pattern!='\0'||*chain!='\0');pattern++,chain++){
					if (*pattern=='*'||*pattern=='\0'||*chain=='\0')
						test=1;
				}
			}

			if (test==1){
				if (*pattern=='*')
					match(pattern,chain);

				if (*pattern=='\0'&&*chain=='\0')
					return true;
				else
					return false;
			pattern=p;
			chain=c;
			}
		}
	}	
}
