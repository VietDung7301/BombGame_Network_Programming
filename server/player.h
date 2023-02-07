#ifndef __PLAYER_H__
#define __PLAYER_H__

typedef struct {
    double position_x;
    double position_y;
    int power;              // 0: đang chờ, 1: trong game
    int speed;              // Số lượng người chơi hiện tại
    int bomb_quantity;      // Id của chủ phòng
    int bomb_seted;         // Danh sách người chơi
    int live;               // Thời gian còn lại của game
    int currentPlayRoom;    // Play room dang choi
    int playerId;
    int userId;
    int currentImage;       // (0 -> 3), thể hiện xem đang là ảnh nào
    int direction;          //(0 ->3) thể hiện hướng xoay hiện tại của nhân vật. 0: down 1: up 2: left 3: right
    long lastTimeHit;       // Thời gian bất tử lấy bằng clock()
} Player; 

Player* createPlayer(int userId, int playRoomId, double position_x, double position_y, int direction);
// Bomb *setBomb( Player *player );
void restoreBomb( Player *player );
void increaseLives( Player *player );
void decreaseLive(Player *player);
void increasePowBomb (Player *player);
void increaseQuantityBomb( Player *player );
void setDirection( Player *player, int direction );
int getCharacterRow(double posY);
int getCharacterCol(double posX);
void move( Player *player, int direction, int map[17][17] );

#endif
