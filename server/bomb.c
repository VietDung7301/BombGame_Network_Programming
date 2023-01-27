#include <stdlib.h>
#include <string.h>
#include "bomb.h"
#include "player.h"

Bomb* createBomb(Player *player) {
    Bomb *bomb = (Bomb*) malloc(sizeof(Bomb));

    bomb->position_x = player->position_x;
    bomb->position_y = player->position_y;
    bomb->player_id = player->playerId;
    bomb->time = 5;
    bomb->length = player->power;

    return bomb;
}