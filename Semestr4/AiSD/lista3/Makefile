CC = gcc
LDFLAGS = -lm -lsodium
SOURCE_DIR = ./

all: zad1.cmp zad2.cmp zad3.cmp zad4.cmp

zad1.cmp: zad1.c sort.c
	$(CC) -o $(SOURCE_DIR)zad1/main $^ $(LDFLAGS)

zad2.cmp: zad2.c select.c
	$(CC) -o $(SOURCE_DIR)zad2/main $^ $(LDFLAGS)

zad3.cmp: zad3.c sort.c
	$(CC) -o $(SOURCE_DIR)zad3/main $^ $(LDFLAGS)

zad4.cmp: zad4.c sort.c
	$(CC) -o $(SOURCE_DIR)zad4/main $^ $(LDFLAGS)

clean:
	rm -f $(OBJFILES) *~
