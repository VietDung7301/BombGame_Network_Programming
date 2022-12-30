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