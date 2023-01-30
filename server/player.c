#include <stdlib.h>
#include <string.h>
#include "player.h"

int playerId = -1;

Player* createPlayer(int userId, int playRoomId) {
    playerId++;
    Player *player = (Player*) malloc(sizeof(Player));

    player->playerId = playerId;
    player->userId = userId;
    player->position_x = 2;
    player->position_y = 2;
    player->bomb_quantity = 1;
    player->bomb_seted = 2;
    player->live = 1;
    player->power = 1;
    player->speed = 1;
    player->currentPlayRoom = playRoomId;
    
    return player;
}