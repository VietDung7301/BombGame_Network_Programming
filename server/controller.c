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
        case 004: 
            return addRoom(request, addr);
            break;
        default:
            printf("Error from client: Invalid request!");
            return INVALID_MSG;
            break;
    }
    response = "#serr#&Unknow Error";
    return response;
}