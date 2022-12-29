#include "user.h"

/* Tăng lên mỗi khi khởi tạo 1 user mới */
int userId = 0;

User* createUser(char* ip, int port, char* name) {
    userId++;
    User *user = (User*) malloc(sizeof(User));

    strcpy(user->ip, ip);
    user->port = port;
    strcpy(user->name, name);
    user->id = userId;
    user->status = 0;
    user->currentRoom = -1;

    return user;
}