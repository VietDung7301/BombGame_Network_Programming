#include <string.h>
#include <stdbool.h>
#include <stdarg.h>
#include <stdio.h>
#include <stdlib.h>
#include "converter.h"
#include "util.h"
#include "user.h"

char* toUserInfor(User* user) {
    char* result = (char*) malloc(100);
    strcat(result, "#s001");
    strcat(result, "&");
    strcat(result, user->name);
    strcat(result, "&");
    strcat(result, intToStr(user->id));
    strcat(result, "$$");
    return result;
}


/**
 * @new_name: new name of client
 * @is_success: client rename is success
 * return thành công: #s002#&new_name$$
 * return thất bại: #err#&Ten da ton tai$$
*/
char *responseS002 (char *new_name, bool is_success){

    char *result = (char*)malloc(100);
    if(is_success){
        strcat(result, "#s002#&");
        strcat(result, new_name);
        strcat(result, "$$");
    }else{
        strcat(result, "#err#&Ten da ton tai$$");
    }
    return result;
}


/**
 * @is_success: client join room is success
 * return thành công: #s005#&success$$
 * return thất bại: #serr#&phong da day$$
*/
char *responseS005 (bool is_success){
    
    char *result = (char*)malloc(100);
    if(is_success){
        strcat(result, "#s005#&");
        strcat(result, "success");
        strcat(result, "$$");
    }else{
        strcat(result, "#serr#&phong da day$$");
    }
    return result;
}