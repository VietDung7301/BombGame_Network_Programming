#include <stdio.h> 
#include <stdlib.h> 
#include <unistd.h> 
#include <string.h> 
#include <stdbool.h>
#include <sys/types.h> 
#include <sys/socket.h> 
#include <arpa/inet.h> 
#include <netinet/in.h> 
    
#define MAXLINE 1024 

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
    
// Driver code 
int main(int argc, char** argv) { 
    if (argc != 3) {
        printf("Need 2 arguments: server address, server port\n");
        exit(-1);
    }
    char* serverAddr = argv[1];
    int serverPort = strToInt(argv[2]);

    int sockfd; 
    char buffer[MAXLINE]; 
    char *hello = "#c000#"; 
    struct sockaddr_in     servaddr; 
    
    // Creating socket file descriptor 
    if ( (sockfd = socket(AF_INET, SOCK_DGRAM, 0)) < 0 ) { 
        perror("socket creation failed"); 
        exit(EXIT_FAILURE); 
    } 
    
    memset(&servaddr, 0, sizeof(servaddr)); 
        
    // Filling server information 
    servaddr.sin_family = AF_INET; 
    servaddr.sin_port = htons(serverPort); 
    servaddr.sin_addr.s_addr = inet_addr(serverAddr); 
        
    int n, len; 
        
    sendto(sockfd, (const char *)hello, strlen(hello), MSG_CONFIRM, (const struct sockaddr *) &servaddr, sizeof(servaddr)); 
    printf("Hello message sent.\n"); 
            
    n = recvfrom(sockfd, (char *)buffer, MAXLINE,  
                MSG_WAITALL, (struct sockaddr *) &servaddr, 
                &len); 
    buffer[n] = '\0'; 
    printf("Server : %s\n", buffer); 
    
    close(sockfd); 
    return 0; 
}