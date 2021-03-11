all: main

main: main.o match.o
	clang  -o main main.o match.o

main.o: main.c
	clang -c main.c

match.o: match.c
	clang -c match.c

clean:
	rm -f main *.o *~
