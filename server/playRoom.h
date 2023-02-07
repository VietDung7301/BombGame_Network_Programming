#include "bomb.h"
#include "player.h"

#ifndef __PLAYROOM_H__
#define __PLAYROOM_H__

#define SIZE_X 17
#define SIZE_Y 17

typedef struct {
    int id;
    Player *playerList[4];  // Danh sách người chơi
    /*
    1: Pine tree
	2: Snowman
	3: Hat
	4: Candle
	5: Socks
	7: Bomb
	9: Santa Clause
	-1: Live item
	-2: Power item
	-3: Quantity item
	-4: Speed item
    */
    int map[17][17]; 
    char smap[17*17 + 1];   // Bản đồ game dưới dạng string
    Bomb *bomb_list[289];
    int number_of_bomb;
    int number_of_boom;
    int quantity;
    Bomb *boom_list[289];
    double startAt;            // Thời gian còn lại của game 
    int room_id;            // ID của phòng ban đầu
} PlayRoom;

PlayRoom *createPlayRoom();
/**
 * Thay đổi giá trị của ô có tọa độ (i, j) của map
 * @param room Phòng chơi
 * @param i tọa độ hàng của ô
 * @param j tạo độ cột của ô
 * @param value giá trị cần thay đổi
*/
void setMap(PlayRoom* room, int i, int j, int value);

#endif
