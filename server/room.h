#ifndef __ROOM_H__
#define __ROOM_H__

typedef struct {
    int id;
    char name[50];
    int status;         // 0: đang chờ, 1: trong game
    int quantity;       // Số lượng người chơi hiện tại
    int owner;          // Id của chủ phòng
    int playerList[4];  // Danh sách người chơi
    double time;        // Thời gian còn lại của game
} Room;

Room* createRoom(char*, int);

#endif