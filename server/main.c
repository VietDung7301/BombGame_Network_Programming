#include <stdio.h>
#include <stdlib.h>
#include "util.h"
#include "network.h"

/* THam số truyền vào là port của server, phía client cũng phải chọn port tương ứng */
int main(int argc, char** argv) {
    if (argc != 2) {
        printf("Need 1 params: server port\n");
        exit(0);
    }
    int port = strToInt(argv[1]);

    startServer(port);
}