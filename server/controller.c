#include <string.h>
#include <stdio.h>
#include <stdlib.h>
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

#define INVALID_MSG "#serr#&Invalid request!"
#define CHECK_CORE_DUMPED printf("check cordumped\n");

int currentUser = 0;
User *userList[100];

int currentRoom = 0;
Room *roomList[100];

/**
 * Tạo người dùng mới và thêm vào danh sách người dùng
 * @addr: Địa chỉ
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
 * @addr: địa chỉ client
 * return: poin to user in user list
*/
User* requestUser(struct sockaddr_in addr) {
    for ( int i = 1; i <= currentUser; i++){
        
        if(
            (addr.sin_addr.s_addr == userList[i]->addr.sin_addr.s_addr)
        &&  (addr.sin_port == userList[i]->addr.sin_port)
        ){
            return userList[i];
        }
        
    }
    return NULL;
}

void getUserInfor() {
    
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
    printf("%s\n", request);
    for(int i = 0; i < strlen(request); i++){
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
    if(checkValidateName(name)){
        int owner = getUserID(addr);
        Room* room = createRoom(name, owner);
        roomList[currentRoom] = room;
        currentRoom++;
        strcpy(response, "#s004#");
        strcat(response, name);
        strcat(response, "$$");
    }else{
        strcpy(response, "#serr#Ten da ton tai$$");
    }
    return response;
}

/**
 * xử lý request đổi tên từ client
 * request struct: #c002#&new_name$$
 * @request: message từ client
 * @addr: địa chỉ client
 * return: response từ server
*/
char* changeClientName(char *request, struct sockaddr_in addr){
    
    User *user = requestUser(addr);
    printf("%s %d\n",user->name , user->id);
    if(user == NULL){
        return "";
    }
    // Handle request get new name
    char new_name[strlen(request)];
    int new_name_counter = 0;
    for ( int i = 7; i < strlen(request) - 2; i++){
        new_name[new_name_counter++] = request[i];
    }
    new_name[new_name_counter] = '\0';
    bool is_success = true;
    // check name is already exit
    for ( int i = 1; i <= currentUser; i++){
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
 * @request: message từ client
 * @addr: địa chỉ client
 * return: response từ server
*/
char* joinRoom(char *request, struct sockaddr_in addr){

    User *user = requestUser(addr);

    if(user == NULL){
        return "";
    }

    // Handle request get room id
    char room_id[strlen(request)];
    int room_id_counter = 0;
    for ( int i = 7; i < strlen(request) - 2; i++){
        room_id[room_id_counter++] = request[i];
    }
    room_id[room_id_counter] = '\0';

    // get room by id
    Room *room = roomList[strToInt(room_id)];

    if(room == NULL){
        return "";
    }

    bool is_success;
    // add client in to room
    if(room->quantity < 4){
        room->playerList[room->quantity++] = user->id;
        is_success = true;
    }else{
        // room is full
        is_success = false;
    }

    return responseS005(is_success);
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
        case 000: return addUser(addr, request);break;
        case 001: getUserInfor();
            break;
        case 002: return changeClientName(request, addr);
        case 004: return addRoom(request, addr);
        case 005: return joinRoom(request, addr);
        default:
            printf("Error from client: Invalid request!");
            return INVALID_MSG;
            break;
    }
    response = "#serr#&Unknow Error";
    return response;
}



