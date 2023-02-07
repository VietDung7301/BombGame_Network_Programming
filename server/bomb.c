#include <stdlib.h>
#include <string.h>
#include "bomb.h"
#include "player.h"

Bomb* createBomb(Player *player) {
    Bomb *bomb = (Bomb*) malloc(sizeof(Bomb));
    bomb->row = getCharacterRow(player->position_y);
    bomb->col = getCharacterCol(player->position_x);
    bomb->player_id = player->playerId;
    gettimeofday(&(bomb->createAt), NULL);
    bomb->length = player->power;

    return bomb;
}
