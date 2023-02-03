CC = gcc
CFLAGS=-I.
OBJ = transport.o

%.o: %.c
	$(CC) -c -o $@ $< $(CFLAGS)

transport: $(OBJ)
	gcc -o $@ $^ $(CFLAGS)
clean:
	rm -f *.o transport