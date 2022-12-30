#include <stdlib.h>
#include <string.h>
#include "room.h"

int roomId = 0;

Room* createRoom(char* name, int owner) {
    roomId++;
    Room *room = (Room*) malloc(sizeof(Room));

    room->id = roomId;
    strcpy(room->name, name);
    room->status = 0;
    room->quantity = 1;
    room->owner = owner;
    room->playerList[0] = owner;
    room->time = -1;
    
    return room;
}