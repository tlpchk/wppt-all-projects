#include<stdio.h>
#include <stdlib.h>

int match(char* example, char* word, char* tmp, char* afterStar, int * star)
{
	if ((*example == NULL) && (*word == NULL))
	return 1;
	if ((*example == NULL) && (*word != NULL) && !(*star))
	return 0;
	if ((*example != NULL) && (*example != '*') && (*word == NULL))
	return 0;
	if ((*example == '*') && (*word == NULL))
    return 1;
    if((*example == '*') && (*(example+1) == NULL))
    return 1;
    if((*example != *word) && (*example != '*') && (*example != '?') && !(*star))
    {
        return 0;
    }
    if((*example != *word) && (*example != '*') && (*example != '?') && (*star))
    {
        example = afterStar;
        tmp = (tmp+1);
        word = tmp;
        return match(example, word, tmp, afterStar, star);
    }
	if(*example == '?'|| *example == *word)
	{
		return match(example+1, word+1, tmp, afterStar, star);
	}

    if((*example == '*') && (*(example+1) == '*'))//przeskoczyc do ostatniej gwiazdki w ciagu
    {
        return match(example+1, word, tmp, afterStar, star);
    }
    if((*example == '*') && (*(example+1) != NULL))//nie ma roznicy pierwsza czy kolejna *
    {
        *star = 1;
        afterStar  = (example+1);
        tmp = word;
        return match(example+1, word, tmp, afterStar, star);

    }
}

int main()
{
	char *example = "a*bca";
	char *word = "abc";
	char * tmp = NULL;
	char * afterStar = NULL;
	int star = 0;
	printf("%d\n", match(example, word, tmp, afterStar, &star));
	return 0;
}

