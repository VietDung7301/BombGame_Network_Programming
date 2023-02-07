#ifndef __USER_H__
#define __USER_H__

#include <sys/types.h> 
#include <sys/socket.h> 
#include <arpa/inet.h> 
#include <netinet/in.h> 

typedef struct {
    struct sockaddr_in addr;
    char name[50];
    int id;             // id được đánh số tăng dần
    int status;         // 0: offline, 1: online, 2: ingame
    int currentRoom;    // Phòng hiện tại. -1 nếu chưa tham gia phòng nào
    int token;          // Xác định người dùng
} User;

/**
 * @param sockaddr_in Địa chỉ user
*/
User* createUser(struct sockaddr_in);

#endif