CC = gcc
CFLAGS=-I.
DEPS = controller.h gameController.h network.h converter.h
OBJ = controller.o gameController.o network.o converter.o main.o

%.o: %.c $(DEPS)
	$(CC) -c -o $@ $< $(CFLAGS)

client: $(OBJ)
	gcc -o $@ $^ $(CFLAGS)
clean:
	rm -f *.o client