#include "player.h"

#ifndef __BOMB_H__
#define __BOMB_H__

typedef struct {
    int position_x;         // Tọa độ hàng
    int position_y;         // Tọa độ cột
    int player_id;          // Người sử hữu quả bomb đó
    int time;               // Thời gian còn lại cho đến khi phát nổ
    double length;          // Chiều dài bomb
} Bomb;

Bomb* createBomb(Player *player);

#endif