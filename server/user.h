#ifndef __USER_H__
#define __USER_H__

typedef struct {
    char ip[20];    
    int port;
    char name[50];
    int id;             // id được đánh số tăng dần
    int status;         // 0: offline, 1: online, 2: ingame
    int currentRoom;    // Phòng hiện tại. -1 nếu chưa tham gia phòng nào
} User;

User* createUser(char*, int, char*);

#endif