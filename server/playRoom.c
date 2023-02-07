#include <stdlib.h>
#include <string.h>
#include <time.h>
#include "playRoom.h"
#include "player.h"

int playRoomId = 0;

int defaultMap[SIZE_X][SIZE_Y] = {
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
		{1, 0, 0, 3, 3, 4, 1, 0, 1, 0, 2, 0, 0, 0, 0, 0, 1},
		{1, 0, 2, 0, 2, 5, 0, 0, 1, 5, 0, 0, 2, 4, 2, 0, 1},
		{1, 0, 2, 0, 1, 1, 2, 3, 5, 5, 2, 2, 1, 3, 2, 0, 1},
		{1, 0, 3, 4, 4, 0, 2, 2, 1, 1, 2, 0, 0, 0, 0, 0, 1},
		{1, 4, 2, 1, 1, 4, 4, 0, 0, 0, 0, 3, 2, 1, 1, 0, 1},
		{1, 0, 0, 2, 2, 3, 2, 2, 1, 0, 1, 0, 1, 1, 0, 0, 1},
		{1, 2, 0, 0, 1, 4, 3, 9, 9, 9, 4, 0, 5, 5, 0, 2, 1},
		{1, 0, 0, 2, 2, 4, 1, 9, 9, 9, 1, 0, 1, 2, 0, 3, 1},
		{1, 0, 2, 1, 1, 0, 0, 9, 9, 9, 0, 3, 2, 1, 2, 4, 1},
		{1, 0, 0, 4, 0, 0, 1, 1, 1, 1, 2, 0, 5, 0, 0, 0, 1},
		{1, 5, 1, 0, 2, 2, 2, 0, 5, 0, 2, 1, 1, 0, 2, 0, 1},
		{1, 0, 1, 0, 2, 3, 3, 4, 2, 0, 0, 0, 1, 0, 2, 0, 1},
		{1, 3, 0, 5, 5, 0, 1, 0, 2, 5, 2, 3, 5, 0, 0, 0, 1},
		{1, 0, 1, 0, 1, 0, 0, 0, 1, 0, 2, 2, 0, 2, 1, 0, 1},
		{1, 0, 0, 0, 1, 0, 2, 0, 4, 0, 3, 3, 0, 0, 0, 0, 1},
		{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
    };

PlayRoom* createPlayRoom() {
    playRoomId++;
    PlayRoom *playRoom = (PlayRoom*) malloc(sizeof(PlayRoom));

    playRoom->id = playRoomId;

    for(int i = 0; i < SIZE_Y; i++){
        for(int j = 0; j < SIZE_X; j++){
            setMap(playRoom, i, j, defaultMap[i][j]);
        }
    }

	playRoom->smap[SIZE_X * SIZE_Y];
    playRoom->startAt = time(NULL);
    playRoom->number_of_bomb = 0;
    playRoom->number_of_boom = 0;

    return playRoom;
}

void setMap(PlayRoom* room, int i, int j, int value) {
	room->map[i][j] = value;
	if (value >= 0)
		room->smap[i* SIZE_X + j] = value + '0';
	else
		room->smap[i* SIZE_X + j] = 'a' - value -1;
}