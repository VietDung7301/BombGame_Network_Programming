#include <stdbool.h>
#include <stdlib.h>
#include <string.h>
#include <stdio.h>

bool isNumber(char a) {
    return ('0' <= a && a <= '9');
}

int strToInt(char* s) {
    if (strlen(s) == 0) return -1;

    int result = 0;
    for (int i=0; i<strlen(s); i++) {
        if (s[i] == ' ' || s[i] == '\n') break;
        if (!isNumber(s[i])) return -1;
        result = result * 10 + s[i] - '0';
    }
    return result;
}

char* intToStr(int a) {
    char* result = (char*) malloc(20);
    sprintf(result, "%d", a);
    return result;
}

char* doubleToStr(double a) {
    char* result = (char*) malloc(20);
    sprintf(result, "%f", a);
    return result;
}