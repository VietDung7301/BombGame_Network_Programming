#ifndef _UTIL_H_
#define _UTIL_H_

#include <stdbool.h>

bool isNumber(char);
int strToInt(char*);
char* intToStr(int);
char* doubleToStr(double a);
int getMicroSecond(struct timeval, struct timeval);

#endif