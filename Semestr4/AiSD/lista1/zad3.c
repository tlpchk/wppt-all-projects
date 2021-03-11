#include <stdio.h>
#include <stdlib.h>
#include <time.h>

typedef struct Element{
  int val;
  struct Element* next;
  struct Element* prev;
} Element;

typedef struct List{
  Element* start;
  int size;
} List;

void addElement(List* l,int v,int index);
void printList(List* l);
int getElement(List* l,int index);
int exists(List* l,int val);
void addRandomElements(List* list,int count);
void timeToSameElement(List* l,int index);
void timeToRandomElement(List* l);
void merge(List* lista1,List* lista2);


void addElement(List* l,int v,int index){
    Element *new = malloc(sizeof(Element*));
    int copyindex = index;
    new->val = v;

    if(l->start == NULL){
        new->next = new;
        new->prev = new;
        l->start = new;
    }
    else if(index > 0){
      Element* temp = l->start;

      while(index > 1){
        index--;
        temp = temp->next;
      }

      new->next = temp->next;
      new->prev = temp;
      temp->next = new;

      /*ogon musi być połączony z głową*/
      if(copyindex >= (l->size-1)){
        l->start->prev = new;
      }

    }else if(index == 0){
      new->next = l->start;
      new->prev = l->start->prev;
      l->start->prev = new;
      l->start = new;
    }

    l->size++;
}

void printList(List* l){
  Element *temp = l->start;
  int size = l->size;

  while(size > 0){
    printf("%d ", temp->val);
    temp = temp->next;
    size--;
  }
  printf("\n\n");
}

int getElement(List* l,int index){
  Element *temp = l->start;
  index = index % l->size;

  if(l->start == NULL || index < 0){
    return -1;
  }

  if(index <= l->size / 2){
    while(index > 0){
      temp = temp->next;
      index--;
    }
  }
  else{
    index = l->size - index;
    while(index > 0){
      temp = temp->prev;
      index--;
    }
  }

  return temp->val;
}

int exists(List* l,int val){
  Element *temp1 = l->start;
  Element *temp2 = temp1->prev;
  int size = l->size;

  while (size > 0) {
    if(temp1->val == val || temp2->val == val ){
      return 1;
    }
    temp1 = temp1->next;
    temp2 = temp2->prev;
    size -= 2;
  }
  return 0;
}

void addRandomElements(List* list,int count){
  int r;
  while (count>0) {
    r = rand();
    addElement(list,r%1000,0);
    count--;
  }
}

void timeToSameElement(List* l,int index){
  clock_t start = clock();
  for (int i = 0; i < 1000; i++) {
      getElement(l,index);
  }
  printf( "Czas wykonywania: %.2f ms\n", ((double)(clock() - start))/1000);
}

void timeToRandomElement(List* l){
  clock_t start = clock();
  int index;
  for (int i = 0; i < 1000; i++) {
      index=rand()%1000;
      getElement(l,index);
  }
  printf( "Czas wykonywania: %.2f ms\n", ((double)(clock() - start))/1000);
}

/*Sklejam dwie listy, zapisuję w pierwszą*/
void merge(List* lista1,List* lista2){

  Element* temp = lista2->start;
  List* copyLista2 = malloc(sizeof(List*));
  int i = 0;
  while(i < lista2->size){
    addElement(copyLista2,temp->val,i);
    temp = temp->next;
    i++;
  }
  lista2 = copyLista2;

  Element* head1 = lista1->start;
  Element* tail1 = lista1->start->prev;
  Element* head2 = lista2->start;
  Element* tail2 = lista2->start->prev;

  head1->prev = tail2;
  tail1->next = head2;
  head2->prev = tail1;
  tail2->next = head1;
  lista1->size += lista2->size;
}


int main(){
  srand(time(NULL));
  List* list = malloc(sizeof(List*));
  List* list2 = malloc(sizeof(List*));

  int command,element,index,end;
  end=0;

  addRandomElements(list, 1000);


  printf("1: Dodać element\n");
  printf("2: Sprawdzić czy istnieje \n");
  printf("3: Dostęp do elementu\n");
  printf("4: Wyświetlić listę\n");
  printf("5: Zmierzyć czas do tego samego\n");
  printf("6: Zmierzyć średni czas do losowego elementu\n");
  printf("9: Koniec\n------------\n");

  while(!end){
    printf("Podaj komędę: ");
    scanf("%d", &command);
    switch (command) {
      case 1:
        printf("Podaj element: ");
        scanf("%d", &element);
        printf("Podaj index: ");
        scanf("%d", &index);

        addElement(list,element,index);
        printf("Element %d wstawiono\n\n", element);
        break;
      case 2:
          printf("Podaj wartość: ");
          scanf("%d", &element);
          printf("%d\n\n",exists(list,element));
        break;
      case 3:
          printf("Podaj index: ");
          scanf("%d",&index);
          printf("%d\n\n",getElement(list,index));
        break;
      case 4:
          printList(list);
        break;
      case 5:
          printf("Podaj index: ");
          scanf("%d",&index);
          timeToSameElement(list,index);
          break;
      case 6:
          timeToRandomElement(list);
          break;
      case 9:
          end = 1;
          break;
      default:
        printf("Zła komenda\n");
    }
    command = 0;
  }

  return 0;
}
