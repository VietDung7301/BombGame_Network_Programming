#include <stdlib.h>
#include <string.h>
#include "user.h"
#include "util.h"

/* Tăng lên mỗi khi khởi tạo 1 user mới */
int userId = -1;

User* createUser(struct sockaddr_in addr) {
    userId++;
    User *user = (User*) malloc(sizeof(User));

    user->addr.sin_family = addr.sin_family;
    user->addr.sin_addr.s_addr = addr.sin_addr.s_addr;
    user->addr.sin_port = addr.sin_port;
    strcpy(user->name, "User"); 
    strcat(user->name, intToStr(userId));
    user->id = userId;
    user->status = 0;
    user->currentRoom = -1;

    return user;
}

