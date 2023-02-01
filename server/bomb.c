#include <stdlib.h>
#include <string.h>
#include <time.h>
#include "bomb.h"
#include "player.h"

Bomb* createBomb(Player *player) {
    Bomb *bomb = (Bomb*) malloc(sizeof(Bomb));

    bomb->row = getCharacterRow(player->position_x);
    bomb->col = getCharacterCol(player->position_y);
    bomb->player_id = player->playerId;
    bomb->createAt = time(NULL);
    bomb->length = player->power;

    return bomb;
}
