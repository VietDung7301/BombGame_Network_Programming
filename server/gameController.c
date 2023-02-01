#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <stdbool.h>
#include "user.h"
#include "bomb.h"
#include "player.h"
#include "playRoom.h"
#define TIME_BOMB 2
#define TIME_ROOM 60

/*
    xu ly yeu cau cua player
    flow:
        1. xac dinh user gui request
        2. tim phong choi cua user do
        3. xu ly yeu cau di chuyen cua user (ben file player.c)
        4. xu ly viec an vat pham cua user
        4. xu ly yeu cau dat bomb cua user
        5. lay thoi gian cua phong choi do
        6. gui response ve cho user
*/


// tao bomb
void setBomb(Player* player,PlayRoom* room, bool isPlantingBomb){
    int row, col;
    row = getCharacterRow(player->position_y);
    col = getCharacterCol(player->position_x);
    if(isPlantingBomb && player->bomb_seted < player->bomb_quantity && player->live > 0 && room->map[row][col]==0){
        player->bomb_seted += 1;
        room->map[row][col] = 7;
        Bomb *bomb = createBomb(player);   // thoi gian tao bomb
        //them bomb
        room->bomb_list[room->number_of_bomb] = bomb;
        room->number_of_bomb += 1;
    }
}

//xu ly pha vat can
void destroyBarrier(int row, int col, PlayRoom* room){
    int random = (int) (rand() * (4 - 1 + 1.0)/(1.0 + RAND_MAX));
    room->map[row][col] = - random;
}


/*
    -xu ly bomb no trong phong
        + update lai ban do
        + pha vat can
        + gay sat thuong len nguoi choi
*/

void bombBoom(PlayRoom* room){
    int row, col, rowP, colP;

    time_t seconds;
    for (int count=0; count<room->number_of_bomb; count++) {
        seconds = time(NULL);
        if(seconds - room->bomb_list[count]->createAt >= TIME_BOMB){
            printf("bomb boom at: %d %d\n", room->bomb_list[count]->row, room->bomb_list[count]->col);
            //tao bomb trong boomList (boomList duoc dat trong room)
            Bomb *boom = (Bomb*) malloc(sizeof(Bomb));
            // tao ra 1 qua bom giong trong trong bomlist
            boom->row = room->bomb_list[count]->row;
            boom->col = room->bomb_list[count]->col;
            boom->player_id = room->bomb_list[count]->player_id;
            boom->length = room->bomb_list[count]->length;
            boom->createAt = room->bomb_list[count]->createAt + 2; //update time bomb no;
            //them boom
            room->boom_list[room->number_of_boom] = boom;
            room->number_of_boom+=1;

            //xy ly bomb no
            int ar[4] = {-1, 0, 1, 0};
            int ac[4] = {0, -1, 0, 1};
            bool isBlock[4] = {false, false, false, false};
            row = room->bomb_list[count]->row;
            col = room->bomb_list[count]->col;
            room->map[row][col] = 0;
            for(int i = 0; i < room->quantity; i++){
                if(room->bomb_list[count]->player_id == room->playerList[i]->playerId)
                    room->playerList[i]->bomb_seted -= 1;
            }

            // Gây sát thương cho player
            // Tâm bomb
            for(int k = 0; k < room->quantity; k++){
                rowP = getCharacterRow(room->playerList[k]->position_y);
                colP = getCharacterCol(room->playerList[k]->position_x);
                if (rowP == row && colP == col)
                    room->playerList[k]->live -= 1;
            }
            // Đường bomb
            for(int i = 1;  i < room->bomb_list[count]->length; i++){
                for(int j = 0; j < 4; j++){
                    if (!isBlock[j]) {
                        if (room->map[row + ar[j]*i][col + ac[j]*i] == 1 || room->map[row + ar[j]*i][col + ac[j]*i] == 2 || room->map[row + ar[j]*i][col + ac[j]*i] == 9) {
                            isBlock[j] = true;
                            continue;
                        }
                        if (room->map[row + ar[j]*i][col + ac[j]*i] == 3 || room->map[row + ar[j]*i][col + ac[j]*i] == 4 || room->map[row + ar[j]*i][col + ac[j]*i] == 5) {
                            destroyBarrier(row + ar[j]*i, col + ac[j]*i, room);
                            isBlock[j] = true;
                            continue;
                        }
                        for(int k = 0; k < room->quantity; k++){
                            rowP = getCharacterRow(room->playerList[k]->position_y);
                            colP = getCharacterCol(room->playerList[k]->position_x);
                            if(rowP == (row + ar[j]*i) && colP == (col + ac[j]*i))
                                room->playerList[k]->live -= 1;
                        }
                    }
                }
            }
            //xoa bomb
            room->map[room->bomb_list[count]->row][room->bomb_list[count]->col] = 0;
            free(room->bomb_list[count]);
            room->bomb_list[count] = room->bomb_list[room->number_of_bomb - 1];
            room->number_of_bomb -= 1;
            
        }
    }
}

// lay thong tin va update boom list
void* updateBoomList(PlayRoom* room){
    int count = 0;
    time_t now = time(NULL);
    while(room->boom_list[count]){
        if(now - room->boom_list[count]->createAt >= 0.1){
            free(room->boom_list[count]);
            room->boom_list[count] = room->boom_list[room->number_of_boom - 1];
            room->number_of_boom -= 1;
        }
        count++;
    }
}


// xu ly an vat pham cua nguoi choi
void eatItems(Player* player, PlayRoom* room){
    int row, col;
    for (int i=0; room->playerList[i] != NULL; i++){
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

// xu ly cac yee cau tren cua nguoi choi
void handlePlayerAction(Player* player, PlayRoom* room, int direction, bool isPlantingBomb){
    move(player, direction, room->map);   //ben player
    eatItems(player, room);
    setBomb(player, room, isPlantingBomb);
    bombBoom(room);
}