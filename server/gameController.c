#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include "player.h"
#include "playRoom.h"
#define TIME_BOMB 2

void destroyBarrier(int row, int col, PlayRoom* room){
    int rand = (int) (rand() * (4 - 1 + 1.0)/(1.0 + RAND_MAX));
    room->map[row][col] = - rand;
}
//da co ben player
//int getCharacterRow(int posY){}
//int getCharacterCol(int posX){}

void eatItems(PlayRoom* room){
    int row, col;
    for(int i = 0; i < 4; i++){
        row = getCharacterRow(room->playerList[i]->posY);
        col = getCharacterCol(room->playerList[i]->posX);
        switch(room->map[row][col]){
            case -1:
                if(room->playerList[i]->live < 3)
                    room->playerList[i]->live++;
                map[row][col] = 0;
                break;
            case -2:
                if(room->playerList[i]->power < 5)
                    room->playerList[i]->power++;
                map[row][col] = 0;
                break;
            case -3:
                if(room->playerList[i]->bomb_quantity < 5)
                    room->playerList[i]->bomb_quantity++;
                map[row][col] = 0;
                break;
            case -4:
                if(room->playerList[i]->speed < 5)
                    room->playerList[i]->speed++;
                map[row][col] = 0;
                break;
            default:
                break;
        }
    }
}

void setBomb(PlayRoom* room){
    int row, col;
    for(int i = 0; i < 4; i++){ 
        row = getCharacterRow(room->playerList[i]->posY);
        col = getCharacterCol(room->playerList[i]->posX);
        if(room->playerList[i]->bomb_seted < room->playerList[i]->bomb_quantity && room->map[row][col] == 0){                 ////??? planting bomb
            room->playerList[i]->bomb_quantity -= 1;
            map[row][col] = 7;
            room->playerList[i]->bomb_seted += 1;
            Bomb *bomb = createBomb(room->playerList[i]);   // thoi gian tao bomb
            for (int j = 0; j < 20; j++){
                if(room->bomb_list[j] == NULL){
                    room->bomb_list[j] = bomb;
                    break;
                }
            }
        }
    }
}

void bombBoom(PlayRoom* room){
    int count, ar[4], ac[4], row, col, rowP, colP;
    bool isBlock[4];
    count = 0;
    time_t seconds;
    while(room->bomb_list[count]){
        seconds = time(NULL);
        if(seconds - room->bomb_list[count]->time >= TIME_BOMB){
            ar[4] = {-1, 0, 1, 0};
            ac[4] = {0, -1, 0, 1};
            isBlock[4] = {false, false, false, false};
            row = room->bomb_list[count]->row;
            col = room->bomb_list[count]->col;
            map[row][col] = 0;
            for(int i = 0; i < 4; i++){
                if(room->bomb_list[count]->PlayerId == room->playerList[i])
                    room->playerList[i]->bomb_seted -= 1;
            }
            for(int i = 0;  i < room->bomb_list[count]->length; i++){
                for(int j = 0; j < 4; j++){
                    if (!isBlocked[j]) {
                        if (map[row + ar[j]*i][col + ac[j]*i] == 1 || map[row + ar[j]*i][col + ac[j]*i] == 2 || map[row + ar[j]*i][col + ac[j]*i] == 9) {
                            isBlocked[j] = true;
                            continue;
                        }
                        if (map[row + ar[j]*i][col + ac[j]*i] == 3 || map[row + ar[j]*i][col + ac[j]*i] == 4 || map[row + ar[j]*i][col + ac[j]*i] == 5) {
                            destroyBarrier(row + ar[j]*i, col + ac[j]*i, room);
                            isBlocked[j] = true;
                            continue;
                        }
                        for(int k = 0; k < 4; k++){
                            rowP = getCharacterRow(room->playerList[k]->posY);
                            colP = getCharacterCol(room->playerList[k]->posX);
                            if(rowP == (row + ar[j]*i) && colP == (col + ac[j]*i))
                                room->playerList[k]->live -= 1;
                        }
                }
            }
            room->bomb_list[count] = NULL;
            count++;
            }
        }
    }
}

void updateTimeLeft(playRoom* room){
    room->time -= 0.03;
}

void createGameLoop(PlayRoom **room){
    time_t now;
    
    long lastUpdate = 0;
    while(1){
        now = time(NULL); // tra ve so giay
        if (now - lastUpdate >= 1/30) {
            lastUpdate = now;
            while(room[count]){
                updateTimeLeft(room[i]);
                //moveCharacter();
                setBomb(room[i]);
                bombBoom(room[i]);
                //createGameElement();
                eatItems(room[i]);
                //updateLabel();
                //createBoomAnimation();
            }
	    }
    }
}