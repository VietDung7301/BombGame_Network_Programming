#include <stdlib.h>
#include <string.h>
#include <stdbool.h>
#include <stdio.h>
#include <time.h>
#include "bomb.h"
#include "player.h"
#include "util.h"


int playerId = 0;

const int DOWN = 0;
const int UP = 1;
const int LEFT = 2;
const int RIGHT = 3;
const int NONE = 4;
const double CELL_SIZE = 710.0/17;
const double HEIGHT = 62;               // height of image that display as character
const double WIDTH = 43;                // width of image that display as character
struct timeval timeKey;

Player* createPlayer(int userId, int playRoomId, double position_x, double position_y, int direction) {
    playerId++;
    Player *player = (Player*) malloc(sizeof(Player));

    player->playerId = playerId;
    player->userId = userId;
    player->position_x = position_x;
    player->position_y = position_y;
    player->bomb_quantity = 1;
    player->bomb_seted = 0;
    player->live = 1;
    player->power = 1;
    player->speed = 1;
    player->currentPlayRoom = playRoomId;
    player->direction = direction;
    player->lastTimeHit = clock();              // Hàm này trả về số tích tắc đồng hồ đã trôi qua kể từ khi bắt đầu chương trình
    return player;
}

bool isCanSetBomb( Player* player ) {
    if ( player->live <= 0 ) return false;
    if ( player->bomb_quantity > player->bomb_seted ) {
        return true;
    }

    return false;
}

void restoreBomb( Player *player ) {
    player->bomb_seted--;
}

void increaseLives( Player *player ) {
    if ( player->live == 1 ) player->live++;
}

void decreaseLive(Player *player) {
    
    long currentTime = clock();
    if ( currentTime - player->lastTimeHit > 3000 ) {
        player->live--;
        if( player->live == 0 ) {
            // this.getChildren().setAll(); ?? 
        }
        player->lastTimeHit = currentTime;
    }
}

void increasePowBomb (Player *player) {
    player->power++;
}

void increaseQuantityBomb( Player *player ) {
    player->bomb_quantity++;
}

void setDirection( Player *player, int direction ) {
    player->direction = direction;
}

double getDisMove( Player *player ) {
    return 3.0 + player->speed*0.2;
}

int getCharacterRow(double posY) {
    double result = posY / CELL_SIZE;
    return (int) result;
}

int getCharacterCol(double posX) {
    double result = posX / CELL_SIZE;
    return (int) result;
}

void move( Player *player, int direction, int map[17][17] ) {
    struct timeval now;
    gettimeofday(&now, NULL);
    if ( direction == NONE || player->live <= 0) return;

    bool moveable = true;

    if (getMicroSecond(timeKey, now) > 100000) {
        player->currentImage = (player->currentImage + 1) % 4;
        timeKey = now;
    }

    int currentCol = getCharacterCol( player->position_x );
    int currentRow = getCharacterRow( player->position_y );

    player->direction = direction;

    if (direction == DOWN) {
			int row = getCharacterRow( player->position_y + 18 + getDisMove( player ) );
			int colL = getCharacterCol( player->position_x - 15);
			int colR = getCharacterCol( player->position_x + 15);
			
			if ((map[row][colL] > 0 || map[row][colR] > 0)) {
				moveable = false;
				if (map[currentRow][currentCol] == 7 && (currentRow == row) && (currentCol == colL || currentCol == colR))
					moveable = true;
			}
		} else if (direction == UP) {
			int row = getCharacterRow( player->position_y  - 18 - getDisMove( player ));
			int colL = getCharacterCol( player->position_x - 15 );
			int colR = getCharacterCol( player->position_x + 15 );
			
			if (map[row][colL] > 0 || map[row][colR] > 0) {
				moveable = false;
				if (map[currentRow][currentCol] == 7 && (currentRow == row) && (currentCol == colL || currentCol == colR))
					moveable = true;
			}
		} else if (direction == LEFT) {
			int col = getCharacterCol( player->position_x  - 18 - getDisMove( player ));
			int rowL = getCharacterRow( player->position_y - 15 );
			int rowR = getCharacterRow( player->position_y + 15);
			
			if (map[rowL][col] > 0 || map[rowR][col] > 0) {
				moveable = false;
				if (map[currentRow][currentCol] == 7 && (currentCol == col) && (currentRow == rowL || currentRow == rowR))
					moveable = true;
			}
		} else if (direction == RIGHT) {
			int col = getCharacterCol( player->position_x  + 18 + getDisMove( player ));
			int rowL = getCharacterRow( player->position_y - 15);
			int rowR = getCharacterRow( player->position_y + 15);
			
			if (map[rowL][col] > 0 || map[rowR][col] > 0) {
				moveable = false;
				if (map[currentRow][currentCol] == 7 && (currentCol == col) && (currentRow == rowL || currentRow == rowR))
					moveable = true;
			}
		}
		
		if (moveable) {
            if(direction == UP){
                player->position_y-= getDisMove( player );
            }

            if ( direction == DOWN ) {
                player->position_y+= getDisMove( player );
            }

            if ( direction == LEFT ) {
                player->position_x-= getDisMove( player );
            }

            if ( direction == RIGHT ) {
                player->position_x+= getDisMove( player );
            }
		}
}
