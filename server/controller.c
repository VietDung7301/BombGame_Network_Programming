#include <string.h>
#include <stdio.h>
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
    userList[++currentUser] = user;
    char* response = toUserInfor(user);
}

void getUserInfor() {

}

char* addRoom() {

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
        case 000: return addUser(addr, request);
        case 001: getUserInfor();
            break;
        case 004: addRoom();
            break;
        default:
            printf("Error from client: Invalid request!");
            return INVALID_MSG;
            break;
    }
    response = "#serr#&Unknow Error";
    return response;
}