#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <stdbool.h>
#include "player.h"
#include "playRoom.h"
#define TIME_BOMB 2

void destroyBarrier(int row, int col, PlayRoom* room){
    int random = (int) (rand() * (4 - 1 + 1.0)/(1.0 + RAND_MAX));
    room->map[row][col] = - random;
}
//da co ben player
//int getCharacterRow(int position_y){}
//int getCharacterCol(int position_x){}

void eatItems(PlayRoom* room){
    int row, col;
    for(int i = 0; i < 4; i++){
        row = getCharacterRow(room->playerList[i]->position_y);
        col = getCharacterCol(room->playerList[i]->position_x);
        switch(room->map[row][col]){
            case -1:
                if(room->playerList[i]->live < 3)
                    room->playerList[i]->live++;
                room->map[row][col] = 0;
                break;
            case -2:
                if(room->playerList[i]->power < 5)
                    room->playerList[i]->power++;
                room->map[row][col] = 0;
                break;
            case -3:
                if(room->playerList[i]->bomb_quantity < 5)
                    room->playerList[i]->bomb_quantity++;
                room->map[row][col] = 0;
                break;
            case -4:
                if(room->playerList[i]->speed < 5)
                    room->playerList[i]->speed++;
                room->map[row][col] = 0;
                break;
            default:
                break;
        }
    }
}

void setBomb(PlayRoom* room){
    int row, col;
    for(int i = 0; i < 4; i++){ 
        row = getCharacterRow(room->playerList[i]->position_y);
        col = getCharacterCol(room->playerList[i]->position_x);
        if(room->playerList[i]->bomb_seted < room->playerList[i]->bomb_quantity && room->map[row][col] == 0){                 ////??? planting bomb
            room->map[row][col] = 7;
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
            ar = {-1, 0, 1, 0};
            ac = {0, -1, 0, 1};
            isBlock = {false, false, false, false};
            room->row = room->bomb_list[count]->row;
            room->col = room->bomb_list[count]->col;
            map[row][col] = 0;
            for(int i = 0; i < 4; i++){
                if(room->bomb_list[count]->PlayerId == room->playerList[i])
                    room->playerList[i]->bomb_seted -= 1;
            }
            for(int i = 0;  i < room->bomb_list[count]->length; i++){
                for(int j = 0; j < 4; j++){
                    if (!isBlock[j]) {
                        if (map[row + ar[j]*i][col + ac[j]*i] == 1 || map[row + ar[j]*i][col + ac[j]*i] == 2 || map[row + ar[j]*i][col + ac[j]*i] == 9) {
                            isBlock[j] = true;
                            continue;
                        }
                        if (map[row + ar[j]*i][col + ac[j]*i] == 3 || map[row + ar[j]*i][col + ac[j]*i] == 4 || map[row + ar[j]*i][col + ac[j]*i] == 5) {
                            destroyBarrier(row + ar[j]*i, col + ac[j]*i, room);
                            isBlock[j] = true;
                            continue;
                        }
                        for(int k = 0; k < 4; k++){
                            rowP = getCharacterRow(room->playerList[k]->position_y);
                            colP = getCharacterCol(room->playerList[k]->position_x);
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
