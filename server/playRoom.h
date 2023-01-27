#include "bomb.h"
#include "player.h"

#ifndef __PLAYROOM_H__
#define __PLAYROOM_H__

typedef struct {
    int id;
    Player *playerList[4];  // Danh sách người chơi
    int map[17][17];        // Bản đồ game
    Bomb *bomb_list[289];
    double time;            // Thời gian còn lại của game 
    int room_id;            // ID của phòng ban đầu
} PlayRoom;

PlayRoom *createPlayRoom();

#endif