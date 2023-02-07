#ifndef __GAME_CONTROLLER_H__
#define __GAME_CONTROLLER_H__

void handlePlayerAction(Player* player,PlayRoom* room,int direction, bool isPlantingBomb);
void setBomb(Player* player, bool isPlantingBomb);
void bombBoom(PlayRoom* room);
void* updateBoomList(PlayRoom* room);
void destroyBarrier(int row, int col, PlayRoom* room);
void eatItems(Player* player);
int getTimeLeft(PlayRoom* room);

#endif
