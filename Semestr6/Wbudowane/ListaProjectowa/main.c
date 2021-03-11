#include <stdio.h>


int bmpToTxt(char* bmpPath,char* txtPath){

    FILE *ptr_bmp_in;
    FILE *ptr_text_out;
    int c;

    ptr_bmp_in = fopen(bmpPath,"rb");
    ptr_text_out = fopen(txtPath,"w");

    if(!ptr_bmp_in)
    {
        printf("Unable to open file\n");
        return 1;
    }

    int header = 0;
    int count = 0;

    while((c=fgetc(ptr_bmp_in)) != EOF)
    {

        for(int i=0;i<=7;i++)
        {
            if(c&(1<<(7-i)))
            {
                fputc('1',ptr_text_out);
            }
            else
            {
                fputc('0',ptr_text_out);
            }
        }
        if(header > 1145) {
            if(c==255) {
                printf("■");
            }else{
                printf("□");
            }

            count += 1;

            if (count == 28){
                printf("\n");
                count = 0;
            }

        }
        header += 1;

    }

    fclose(ptr_bmp_in);
    fclose(ptr_text_out);
    printf("Writing done\n");

    return 0;
}

char byteFromTxt(unsigned char *text)
{
    char result = 0;
    for(int i=0;i<8;i++)
    {
        if(text[i]=='1')
        {
            result |= (1 << (7-i));
        }
    }
    return result;
}

int txtToBmp(char* txtPath, char* bmpPath){

    FILE *ptr_txt_in;
    FILE *ptr_bmp_out;
    unsigned char buf[8];
    int c;
    int j = 0;


    ptr_txt_in=fopen(txtPath,"r");
    ptr_bmp_out=fopen(bmpPath,"wb");


    if(!ptr_txt_in)
    {
        printf("Unable to open file\n");
        return 1;
    }

    while((c=fgetc(ptr_txt_in)) != EOF)
    {
        buf[j++] = c;
        if(j==8)
        {
            fputc(byteFromTxt(buf),ptr_bmp_out);
            j=0;
        }
    }


    fclose(ptr_txt_in);
    fclose(ptr_bmp_out);
    printf("Writing done\n");

    return 0;
}

int main(){
    char* bmpInPath = "/home/telepchuk/Documents/6 semestr/Wbudowane/ListaProjectowa/asserts/bmp/line.bmp";
    char* txtPath = "/home/telepchuk/Documents/6 semestr/Wbudowane/ListaProjectowa/asserts/txt/line_to_txt.txt";
    char* bmpOutPath = "/home/telepchuk/Documents/6 semestr/Wbudowane/ListaProjectowa/asserts/bmp/line_out.bmp";

    bmpToTxt(bmpInPath,txtPath);
    txtToBmp(txtPath,bmpOutPath);
}