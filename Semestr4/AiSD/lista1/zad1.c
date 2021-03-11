#include <stdio.h>
#include <stdlib.h>

void push(int element);
void pop();
int size();

int* head;
int* tail;

void push(int element){
  if(head == NULL){
    head = malloc(sizeof(int*));
    tail = head;
  }
  *tail = element;
  tail++;
}

void pop(){
  if(size()==0){
    printf("Emty\n");
  }
  else{
    printf("%d\n",*(head)) ;
    head++;
  }
}

int size(){
  return (int)(tail-head);
}

int main() {
  int command=0;
  int element;
  printf("1: Wstawić element\n2: Pobrać element\n3: Czy jest pusta?\n4: Rozmiar\n\n");

  while(1){
    scanf("%d", &command);
    switch (command) {
      case 1:
        scanf("%d", &element);
        push(element);
        printf("Element %d wstawiono\n",element );
        break;
      case 2:
        printf("Twój element to: ");
        pop();
        break;
      case 3:
        if(size()==0){
          printf("True\n");
        }
        else{
          printf("False\n");
        }
        break;
      case 4:
        printf("Rozmiar: %d\n", size());
        break;
      default:
        printf("Zła komenda\n");
    }
  }

  return 0;
}
