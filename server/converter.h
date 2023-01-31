#include "util.h"
#include "user.h"
#include "room.h"
#include "playRoom.h"

/**
 * Chuyển thông tin user thành response
*/
char *toUserInfor(User*);
char *responseS002 (char *new_name, bool is_success);
char *responseS005 (bool is_success);
char *responseS007 (Room* room, User* owner, User* player_list[]);
char *responseS008 (PlayRoom *play_room,char* string_map,int timeLeft);
