#include <stdlib.h>
#include <string.h>
#include "bomb.h"
#include "player.h"

Bomb* createBomb(Player *player) {
    Bomb *bomb = (Bomb*) malloc(sizeof(Bomb));

    bomb->row = player->position_x;
    bomb->col = player->position_y;
    bomb->player_id = player->playerId;
    bomb->createAt = 5;
    bomb->length = player->power;

    return bomb;
}
