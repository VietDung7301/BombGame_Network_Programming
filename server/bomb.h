#include "player.h"

#ifndef __BOMB_H__
#define __BOMB_H__

typedef struct {
    int row;         // Tọa độ hàng
    int col;         // Tọa độ cột
    int player_id;          // Người sử hữu quả bomb đó
    int createAt;               // Thời gian còn lại cho đến khi phát nổ
    double length;          // Chiều dài bomb
} Bomb;

Bomb* createBomb(Player *player);

#endif
