#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <stdbool.h>
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
char* handlePlayerAction(struct sockaddr_in addr, char* request){
    User* user = requestUser(addr);
    Player* player = getPlayerById(user->id);
    Player* room = getPlayrommById(player->currentRoom);
    bool isPlantingBomb = plantingBomb(request);
    movePlayer();   //ben player
    eatItems(player);
    setBomb(player, isPlantingBomb);
    bombBoom(room);
    int timeLeft = getTimeLeft(room);

    // thong tin response
    strcat(response, "#s008#&");
    strcat(response, mapToString(room->map));
    strcat(response, playerInfoToString(player));
    strcat(response, getBoomList(room));
    strcat(response, timeRoomTostring(timeleft))
    return response;
}

// tao bomb
void setBomb(Player* player, bool isPlantingBomb){
    Player* room = getPlayrommById(player->currentRoom);
    int row, col;
    row = getCharacterRow(player->position_y);
    col = getCharacterCol(player->position_x);
    if(isPlantingBomb && player->bomb_seted < player->bomb_quantity && room->map[row][col]==0){
        player->bomb_seted += 1;
        room->map[row][col] = 7;
        Bomb *bomb = createBomb(player);   // thoi gian tao bomb
        addBombList(rom, bomb);
    }
}

/*
    -xu ly bomb no trong phong
        + update lai ban do
        + pha vat can
        + gay sat thuong len nguoi choi
*/

void bombBoom(PlayRoom* room){
    int count, ar[4], ac[4], row, col, rowP, colP;
    bool isBlock[4];
    count = 0;
    time_t seconds;
    while(room->bomb_list[count]){
        seconds = time(NULL);
        if(seconds - room->bomb_list[count]->time >= TIME_BOMB){
            //tao bomb trong boomList (boomList duoc dat trong room)
            Bomb *boom = createBomb(room->bomb_list[count]); // tao ra 1 qua bom giong trong trong bomlist
            boom->createAt = room->bomb_list[count]->createAt + 2; //update time bomb no;
            addBoomList(rom, boom);

            //xy ly bomb no
            ar = {-1, 0, 1, 0};
            ac = {0, -1, 0, 1};
            isBlock = {false, false, false, false};
            room->row = room->bomb_list[count]->row;
            room->col = room->bomb_list[count]->col;
            map[row][col] = 0;
            for(int i = 0; i < 4; i++){
                if(room->bomb_list[count]->PlayerId == room->playerList[i]->id)
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

// lay thong tin va update boom list
char* getBoomList(playRoom* room){
    char* response = (char *)malloc(100*sizeof(char));
    count = 0;
    time_t now = time(NULL);
    while(room->boomList[count]){
        if(now - boomList[count]->createAt <= 0.1){
            strcat(response, toInfoBoom(boomList[count]))
        }else{
            freeBoom(room->boomList[count]);
        }
        count++;
    }
    return response;
}

//xu ly pha vat can
void destroyBarrier(int row, int col, PlayRoom* room){
    int random = (int) (rand() * (4 - 1 + 1.0)/(1.0 + RAND_MAX));
    room->map[row][col] = - random;
}

// xu ly an vat pham cua nguoi choi
void eatItems(Player* player){
    PlayRoom* room = getPlayrommById(player->id);
    int row, col;
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

// lay thoi gian con lai cua phong choi
int getTimeLeft(playRoom* room){
    time_t now = time(NULL);
    int time = now - room->startAt;
    return TIME_ROOM - time;
}