#include <string.h>
#include <stdbool.h>
#include <stdarg.h>
#include <stdio.h>
#include <stdlib.h>
#include "converter.h"
#include "util.h"
#include "user.h"
#include "room.h"

char* toUserInfor(User* user) {
    char* result = (char*) malloc(100);
    strcat(result, "#s001");
    strcat(result, "&");
    strcat(result, user->name);
    strcat(result, "&");
    strcat(result, intToStr(user->id));
    strcat(result, "$$");
    return result;
}


/**
 * @new_name: new name of client
 * @is_success: client rename is success
 * return thành công: #s002#&new_name$$
 * return thất bại: #err#&Ten da ton tai$$
*/
char *responseS002 (char *new_name, bool is_success){

    char *result = (char*)malloc(100);
    if(is_success){
        strcat(result, "#s002#&");
        strcat(result, new_name);
        strcat(result, "$$");
    }else{
        strcat(result, "#err#&Ten da ton tai$$");
    }
    return result;
}


/**
 * @is_success: client join room is success
 * return thành công: #s005#&success$$
 * return thất bại: #serr#&phong da day$$
*/
char *responseS005 (bool is_success){
    
    char *result = (char*)malloc(100);
    if(is_success){
        strcat(result, "#s005#&");
        strcat(result, "success");
        strcat(result, "$$");
    }else{
        strcat(result, "#serr#&phong da day$$");
    }
    return result;
}

/**
 * @room: Thông tin phòng chơi
 * @owner: Thông tin chủ phòng
 * @player_list: Thông tin người chơi khác tham gia phòng chơi
 * return #s007#&room_name&room_id&owner_name&so_luong_nguoi_choi&player_name1&player_name2&player_name3$$
*/
char *responseS007 (Room* room, User* owner, User* player_list[]){
    
    char *result = (char*)malloc(500);
    // response id
    strcat(result, "#s007#&");
    // room name
    strcat(result, room->name);
    strcat(result, "&");
    // room id
    strcat(result, intToStr(room->id));
    strcat(result, "&");
    // owner name
    strcat(result, owner->name);
    strcat(result, "&");
    // so luong nguoi choi
    strcat(result, intToStr(room->quantity));
    strcat(result, "&");
    // player name n
    for(int i = 0; i < room->quantity; i++){
        strcat(result, player_list[i]->name);
        strcat(result, "&");
    }
    strcat(result, "$$");
    return result;
}

/**
 * @play_room: Thông tin phòng chơi
 * @string_map: map dưới dạng một chuỗi
 * return #s008#&17*17 số&live_1&bomb_quantity_1&bomb_pow_1&speed_1&posx1&posy1&live_2&
 * ...&tọa độ x bomb 1 nổ&tọa độ y bomb 1 nổ&tọa độ x bomb 2 nổ&
 * ...&Thời gian còn lại của trò chơi$$
*/
char *responseS008 (PlayRoom *play_room, char* string_map){
    
    char *result = (char*)malloc(1000);
    // response id
    strcat(result, "#s008#&");
    // map
    strcat(result, string_map);
    strcat(result, "&");
    // player n
    for(int i = 0; play_room->playerList[i] != NULL; i++){
        Player* player = play_room->playerList[i];
        // live
        strcat(result, intToStr(player->live));
        strcat(result, "&");
        // bomb quantity
        strcat(result, intToStr(player->bomb_quantity));
        strcat(result, "&");
        // bomb pow
        strcat(result, intToStr(player->bomb_seted));
        strcat(result, "&");
        // speed
        strcat(result, intToStr(player->speed));
        strcat(result, "&");
        // pos x
        strcat(result, doubleToStr(player->position_x));
        strcat(result, "&");
        // pos y
        strcat(result, doubleToStr(player->position_y));
        strcat(result, "&");
    }

    // bomb n
        for(int i = 0; play_room->bomb_list[i] != NULL; i++){
        Bomb* bomb = play_room->bomb_list[i];
        // tọa độ x bomb
        strcat(result, doubleToStr(bomb->position_x));
        strcat(result, "&");
        // tọa độ y bomb
        strcat(result, doubleToStr(bomb->position_y));
        strcat(result, "&");
    }

    // Thời gian còn lại của trò chơi
    strcat(result, intToStr(play_room->time));
    strcat(result, "$$");
    return result;
}