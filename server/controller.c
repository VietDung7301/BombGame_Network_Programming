#include <string.h>
#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <sys/types.h> 
#include <sys/socket.h> 
#include <arpa/inet.h> 
#include <netinet/in.h> 
#include "user.h"
#include "room.h"
#include "util.h"
#include "converter.h"
#include "user.h"
#include "room.h"
#include "bomb.h"
#include "player.h"
#include "playRoom.h"
#include "gameController.h"

#define INVALID_MSG "#serr#&Invalid request!"
#define CHECK_CORE_DUMPED printf("check cordumped\n");
#define TIME_ROOM 150
const double CELL = 710.0/17;

int currentUser = 0;
User *userList[100];

int currentRoom = 0;
Room *roomList[100];

int currentPlayRoom = 0;
PlayRoom *playRoomList[100];

int currentPlayer = 0;
Player *playerList[100];
/**
 * Tạo người dùng mới và thêm vào danh sách người dùng
 * @param addr Địa chỉ
 * @request: request
 * return: 1 string là response cho client
*/
char* addUser(struct sockaddr_in addr, char* request) {
    User* user = createUser(addr);
    userList[currentUser] = user;
    currentUser++;
    char* response = toUserInfor(user);
    return response;
}

/**
 * Tìm kiếm user poin thông qua addr
 * @param addr địa chỉ client
 * return: poin to user in user list
*/
User* requestUser(struct sockaddr_in addr) {
    for ( int i = 0; i < currentUser; i++){
        
        if(
            (addr.sin_addr.s_addr == userList[i]->addr.sin_addr.s_addr)
        &&  (addr.sin_port == userList[i]->addr.sin_port)
        ){
            return userList[i];
        }
        
    }
    return NULL;
}

char* getUserInfor(struct sockaddr_in addr) {
    User* user = requestUser(addr);
    char* response = toUserInfor(user);
    return response;
}

char* getRoomList(){
    char* response = (char*) malloc(2048 * sizeof(char)); 
    strcat(response, "#s003#&");
    strcat(response, intToStr(currentRoom));
    for(int i = 0; i < currentRoom; i++){
        strcat(response, "&");
        strcat(response, roomList[i]->name);
        strcat(response, "&");
        strcat(response, intToStr(roomList[i]->id));
        strcat(response, "&");
        strcat(response, intToStr(roomList[i]->quantity));
        strcat(response, "&");
        for(int j = 0; j < currentUser; j++){
            if(userList[j]->id == roomList[i]->owner){
                strcat(response, userList[j]->name);
                break;
            }
        }

    }
    strcat(response, "$$");
    return response;
}

/**
 * Tìm kiếm user thông qua id
 * @param id mã user
 * return: poin to user in user list
*/
User* getUserById(int id) {
    for ( int i = 0; i < currentUser; i++){
        
        if(userList[i]->id == id){
            return userList[i];
        }     
    }
    return NULL;
}

/**
 * Tìm kiếm room thông qua id
 * @param id mã room
 * return: poin to user in user list
*/
Room* getRoomById(int id) {
    for ( int i = 0; i < currentRoom; i++){
        
        if(roomList[i]->id == id){
            return roomList[i];
        }     
    }
    return NULL;
}


int checkValidateName(char* name){
    for(int i = 0; i < currentRoom; i++){
        if(strcmp(roomList[i]->name, name) == 0)
            return 0;
    }
    return 1;
}

int getUserID(struct sockaddr_in addr){
    for (int i = 0; i < currentUser; i++){
        if(strcmp(inet_ntoa(userList[i]->addr.sin_addr), inet_ntoa(addr.sin_addr))== 0 && userList[i]->addr.sin_port == addr.sin_port){
            return userList[i]->id;
        }
    }
    return -1;
}

char* getName(char* request){
    char* name = (char*) malloc(50 * sizeof(char));
    int count = 0;
    for(int i = 0; i < strlen(request); i++){
        printf("%c\n",request[i]);
        if(request[i] == '&'){
            while(1){
                i++;
                if(request[i] == '$')
                    break;
                name[count] = request[i];
                count++;
            }
            name[count] = '\0';
            return name;
        }
    }
    return NULL;
}

char* addRoom(char* request, struct sockaddr_in addr) {
    char *response = (char*) malloc(50 * sizeof(char));
    char *name = getName(request);
    //printf("%s\n",name);
    if(checkValidateName(name)){
        int owner = getUserID(addr);
        Room* room = createRoom(name, owner);
        User *user = getUserById(owner);
        user->currentRoom = room->id;
        roomList[currentRoom] = room;
        currentRoom++;
        strcpy(response, "#s004#&");
        strcat(response, name);
        strcat(response, "&");
        strcat(response, intToStr(room->id));
        strcat(response, "$$");
    }else{
        strcpy(response, "#serr#&Ten da ton tai$$");
    }
    return response;
}

/**
 * xử lý request đổi tên từ client
 * request struct: #c002#&new_name$$
 * @param request message từ client
 * @param addr địa chỉ client
 * return: response từ server
*/
char* changeClientName(char *request, struct sockaddr_in addr){
    
    User *user = requestUser(addr);
    //printf("%s %d\n",user->name , user->id);
    if(user == NULL){
        return "";
    }
    // Handle request get new name
    char new_name[strlen(request)];
    int new_name_counter = 0;
    for ( int i = 7; i < strlen(request) - 3; i++){
        new_name[new_name_counter++] = request[i];
    }
    new_name[new_name_counter] = '\0';
    bool is_success = true;
    // check name is already exit
    for ( int i = 0; i < currentUser; i++){
        if( strcmp(new_name, userList[i]->name) == 0 && user->id != userList[i]->id ){
            is_success = false;
            return responseS002(new_name, is_success);
        }
    }
    // name is not already exit
    strcpy(user->name, new_name);

    return responseS002(new_name, is_success);
}

/**
 * xử lý yêu cầu tham gia phòng client
 * request struct: #c005#&room_id$$
 * @param request message từ client
 * @param addr địa chỉ client
 * return: response từ server
*/
char* joinRoom(char *request, struct sockaddr_in addr){

    User *user = requestUser(addr);

    if(user == NULL){
        return "1";
    }

    // Handle request get room id
    char room_id[strlen(request)];
    int room_id_counter = 0;
    for ( int i = 7; i < strlen(request) - 2; i++){
        room_id[room_id_counter++] = request[i];
    }
    room_id[room_id_counter] = '\0';
    int id = strToInt(room_id);
    // get room by id
    Room *room = getRoomById(id);

    if(room == NULL){
        return "2";
    }

    bool is_success;
    // add client in to room
    if(room->quantity < 4){
        room->playerList[room->quantity++] = user->id;
        user->currentRoom = room->id;
        is_success = true;
    }else{
        // room is full
        is_success = false;
    }

    return responseS005(is_success);
}

/**
 * xử lý yêu cầu bắt đầu trò chơi
 * request struct: #c006#
 * @param request message từ client
 * @param addr địa chỉ client
 * return: response từ server
*/
char* startGame(char *request, struct sockaddr_in addr){
    // Lấy thông tin người chơi
    User *user = requestUser(addr);
    if(user == NULL){
        return "#serr#&Unknow error$$";
    }

    // Lấy thông tin phòng
    Room *room = getRoomById(user->currentRoom);
    if(room == NULL){
        return "#serr#&Unknow error$$";
    }

    // Nếu người chơi không phải chủ phòng => fail
    if (room->owner != user->id) {
        return "#serr#&Ban khong phai chu phong$$";
    }
    // Nếu phòng chơi chỉ có 1 người chơi
    // if (room->quantity < 2) {
    //     return "#serr#&Khong du nguoi choi$$";
    // }

    room->status = 1;
    PlayRoom *play_room = createPlayRoom();
    playRoomList[currentPlayRoom++] = play_room;
    play_room->quantity = room->quantity;

    for (int i = 0; i < room->quantity; i++){
        if (i == 0)
            play_room->playerList[i] = createPlayer(room->playerList[i], play_room->id, 1.5*CELL, 1.5*CELL, 0);
        else if (i == 1) 
            play_room->playerList[i] = createPlayer(room->playerList[i], play_room->id, 15.5*CELL, 1.5*CELL, 0);
        else if (i == 2)
            play_room->playerList[i] = createPlayer(room->playerList[i], play_room->id, 1.5*CELL, 15.5*CELL, 1);
        else if (i == 3)
            play_room->playerList[i] = createPlayer(room->playerList[i], play_room->id, 15.5*CELL, 15.5*CELL, 1);
        playerList[currentPlayer++] = play_room->playerList[i];
    }

    return "#s006#&1$$";
}

/**
 * Lấy thông tin chi tiết 1 phòng
 * request struct: #c007#&room_id$$
 * @request: message từ client
 * @addr: địa chỉ client
 * return: response từ server
*/
char* getRoomInfo(char *request, struct sockaddr_in addr){

    User *user = requestUser(addr);

    if(user == NULL){
        return "1";
    }

    // Handle request get room id
    char room_id[strlen(request)];
    int room_id_counter = 0;
    for ( int i = 7; i < strlen(request) - 2; i++){
        room_id[room_id_counter++] = request[i];
    }
    room_id[room_id_counter] = '\0';
    int id = strToInt(room_id);
    
    // get room by id
    Room *room = getRoomById(id);
    if(room == NULL){
        return "2";
    }

    User* owner = getUserById(room->owner);

    User* player_list[room->quantity];

    for(int i = 0; i < room->quantity; i++){
        player_list[i] = getUserById(room->playerList[i]);
    }

    return responseS007(room, owner, player_list);
}

//api choi game
bool getPlantingBomb(char* request){
    char space[2];
    space[0] = request[7];
    space[1] = '\0';
    int isPlanting = strToInt(space);
    if(isPlanting ==  0)
        return false;
    return true;
}

Player* getPlayerByUserId(int id){
    for(int i=0; i<currentPlayer; i++){
        if(playerList[i]->userId == id)
            return playerList[i];
    }
    return NULL;
}

PlayRoom* getPlayroomById(int id){
    for(int i=0; i<currentPlayRoom; i++){
        if(playRoomList[i]->id == id)
            return playRoomList[i];
    }
    return NULL;
}

int getTimeLeft(PlayRoom* room){
    time_t now = time(NULL);
    int time = now - room->startAt;
    return TIME_ROOM - time;
}

int getDirection(char* request){
    char direction_str[6];
    int count = 0;
    for (int i=9; request[i]!='$'; i++){
        direction_str[count] = request[i];
        count++;
    }
    direction_str[count] = '\0';
    if(strcmp(direction_str, "down") == 0)
        return 0;
    
    if(strcmp(direction_str, "up") == 0)
        return 1;
    
    if(strcmp(direction_str, "left") == 0)
        return 2;
    
    if(strcmp(direction_str, "right") == 0)
        return 3;

    if(strcmp(direction_str, "none") == 0)
        return 4;

    return -1;
}


char* getNewGameStatus(char* request, struct sockaddr_in addr){
    User* user = requestUser(addr);
    bool isPlantingBomb = getPlantingBomb(request);
    Player* player = getPlayerByUserId(user->id);
    PlayRoom* room = getPlayroomById(player->currentPlayRoom);
    int direction = getDirection(request);

    handlePlayerAction(player, room, direction, isPlantingBomb);

    int timeLeft = getTimeLeft(room);

    return responseS008(room, timeLeft);
}

/**
 * Xử lý request từ user
 * @request: message từ client
 * @addr: địa chỉ client
 * return: response từ server
*/
char* handlerRequest(char* request, struct sockaddr_in addr) {
    // Nếu request không đúng định dạng
    printf("--request: %s\n", request);
    if (request[0] != '#' || request[1] != 'c' || request[5] != '#') {
        printf("Err from client: Invalid request!\n");
        return INVALID_MSG;
    }
    
    char* response;
    char code[4];   // Tách lấy 3 ký tự đầu của request
    int icode;      // Đổi các ký tự đó thành số để dùng switch-case
    strncpy(code, request + 2, 3);
    code[3] = '\n';
    icode = strToInt(code);
    switch (icode) {
        case 0: return addUser(addr, request);break;
        case 1: return getUserInfor(addr);
            break;
        case 2: return changeClientName(request, addr);
        case 3: return getRoomList();
        case 4: return addRoom(request, addr);
        case 5: return joinRoom(request, addr);
        case 6: return startGame(request, addr);
        case 7: return getRoomInfo(request, addr);
        case 8: return getNewGameStatus(request, addr);
        default:
            printf("Error from client: Invalid request!");
            return INVALID_MSG;
            break;
    }
    response = "#serr#&Unknow Error";
    return response;
}