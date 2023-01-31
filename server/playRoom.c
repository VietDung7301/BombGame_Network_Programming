#include <stdlib.h>
#include <string.h>
#include <time.h>
#include "playRoom.h"
#include "player.h"

int playRoomId = 0;

PlayRoom* createPlayRoom() {
    playRoomId++;
    PlayRoom *playRoom = (PlayRoom*) malloc(sizeof(PlayRoom));

    playRoom->id = playRoomId;

    for(int i = 0; i < 17; i++){
        for(int j = 0; j < 17; j++){
            playRoom->map[i][j] = 0;
        }
    }

    playRoom->startAt = time(NULL);

    return playRoom;
}
