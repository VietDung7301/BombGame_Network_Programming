#ifndef __CONTROLLER_H__
#define __CONTROLLER_H__

void destroyBarrier(int row, int col, PlayRoom* room);
void eatItems(PlayRoom* room);
void setBomb(PlayRoom* room);
void bombBoom(PlayRoom* room);
void updateTimeLeft(playRoom* room);
void createGameLoop(PlayRoom **room);

#endif