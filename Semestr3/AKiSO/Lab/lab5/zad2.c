#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <time.h>
#define MAX_SIZE 10000

/*
                 m2
                1 0 0
                0 0 1
              n 1 1 1
                0 1 1
                1 0 1
        n
    0 0 1 0 1
    1 1 1 0 0
    0 1 0 0 1
m1  0 1 0 0 0
    1 1 1 1 0
    1 1 0 1 1
    0 0 1 1 0

*/


short matrix1[MAX_SIZE][MAX_SIZE];
short matrix2[MAX_SIZE][MAX_SIZE];
int final_matrix[MAX_SIZE][MAX_SIZE];

typedef struct {
  int iter;
  int nthreads;
  int m1;
  int m2;
  int n;
} Thread_struct;


void *mult(void *x){
  int i;
  int result,row,column;
  Thread_struct *t;
  t=(Thread_struct *)x;
  int limit=(t->m1)*(t->m2);
  while(t->iter<limit){
    result=0;
    row=t->iter/t->m2;
    column=t->iter%t->m2;
    for(i=0;i<t->n;i++){
      result+=matrix1[row][i]*matrix2[i][column];
    }
    final_matrix[row][column]=result;
    t->iter+=t->nthreads;
  }
  pthread_exit(0);
}

int main(int argc,char **argv){
  Thread_struct *t;
  pthread_t *treads;
  void *status;
  int nthreads,i,j,n,m1,m2;

  if(argc!=5){
    fprintf(stderr, "Zła ilość argómentów\n");
    exit(1);
  }

  n=atoi(argv[1]);
  m1=atoi(argv[2]);
  m2=atoi(argv[3]);
  nthreads=atoi(argv[4]);
  treads=(pthread_t *)malloc(sizeof(pthread_t)*nthreads);
  t=(Thread_struct *)malloc(sizeof(Thread_struct)*nthreads);

  //wstawianie randomowych wartości
  srand(time(NULL));

  for(i=0;i<m1;i++){
    for(j=0;j<n;j++){
      matrix1[i][j]=rand()%2;
      printf("%d ",matrix1[i][j] );
    }
    printf("\n");
  }

  printf("-------\n");
  for(i=0;i<n;i++){
    for(j=0;j<m2;j++){
      matrix2[i][j]=rand()%2;
      printf("%d ",matrix2[i][j] );
    }
    printf("\n");
  }
  printf("-------\n");

    for (i=0;i<nthreads;i++){
      t[i].iter=i;
      t[i].nthreads=nthreads;
      t[i].n=n;
      t[i].m1=m1;
      t[i].m2=m2;

      pthread_create(treads+i,NULL,mult,t+i);
     }
     for(i=0;i<nthreads;i++){
       pthread_join(treads[i],&status);
     }
   /*for(i=0;i<m1;i++){
     for(j=0;j<m2;j++){
       printf("%d ",final_matrix[i][j]);
     }
     printf("\n");
   }*/

  return 0;
}
