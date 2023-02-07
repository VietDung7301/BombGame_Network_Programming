#include "player.h"
#include <sys/time.h>

#ifndef __BOMB_H__
#define __BOMB_H__

typedef struct {
    int row;         // Tọa độ hàng
    int col;         // Tọa độ cột
    int player_id;          // Người sử hữu quả bomb đó
    struct timeval createAt;               // Thời gian tạo bomb
    double length;          // Chiều dài bomb
} Bomb;

Bomb* createBomb(Player *player);

#endif
