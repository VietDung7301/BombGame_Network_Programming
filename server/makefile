CC = gcc
CFLAGS=-I.
DEPS = controller.h gameController.h network.h converter.h room.h user.h util.h player.h playRoom.h bomb.h
OBJ = controller.o gameController.o network.o converter.o room.o user.o util.o main.o player.o playRoom.o bomb.o

%.o: %.c $(DEPS)
	$(CC) -c -o $@ $< $(CFLAGS)

server: $(OBJ)
	gcc -o $@ $^ $(CFLAGS)
clean:
	rm -f *.o server
