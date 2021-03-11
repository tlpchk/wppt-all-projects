CC = gcc
LDFLAGS = -lm -lsodium
OBJFILES = sort.c


all: zad1 zad2 zad3

zad1: zad1.c $(OBJFILES)
	$(CC) -o $@ $^ $(LDFLAGS)

zad2: zad2.c $(OBJFILES)
	$(CC) -o $@ $^ $(LDFLAGS)

zad3: zad3.c $(OBJFILES)
	$(CC) -o $@ $^ $(LDFLAGS)

clean:
	rm -f $(OBJFILES) *~
