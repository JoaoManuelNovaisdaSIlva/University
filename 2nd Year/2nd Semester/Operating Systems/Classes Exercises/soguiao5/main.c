#include <stdio.h>
#include <unistd.h>
#include <sys/wait.h>
#include <string.h>


int ex_1_part1() {
    int fildes[2];
    int res = pipe(fildes);
    char buffer[20];

    printf("Resultado do Pipe = %d \n",res);

    int resf = fork();

    switch (resf) {
        case -1:
            perror("fork");
            return -1;
        case 0:
            close(fildes[1]); // o fildes tem 2 file escriptores, uma para ler outro para escrever, o de escrita é o 1
            int read_res = read(fildes[0], &buffer, 20);
            printf("[FILHO] Li %s com %d caracteres\n",buffer,read_res);
            close(fildes[0]);
            _exit(0);
        default:
            close(fildes[0]);
            sleep(5);
            write(fildes[1],"Ola mundo!",11);
            printf("[PAI] Ecrevi uma linha\n");
            close(fildes[1]);
            wait(NULL);
    }
    return 0;
}

int ex_1_part2(){
    int fildes[2];
    int res = pipe(fildes);
    char buffer[20];

    printf("Resultado do Pipe = %d \n",res);

    int resf = fork();

    switch (resf) {
        case -1:
            perror("fork");
            return -1;
        case 0:
            close(fildes[0]); // o fildes tem 2 file escriptores, uma para ler outro para escrever, o de escrita é o 1
            write(fildes[1],"ola mundo!",11);
            printf("[FILHO] Escrevi uma linha\n");
            close(fildes[1]);
            _exit(0);
        default:
            close(fildes[1]);
            int read_res = read(fildes[0],&buffer,20);
            printf("[PAI] Li %s com %d caracteres\n",buffer,read_res);
            close(fildes[1]);
    }
    return 0;
}

int ex_2(){
    int fildes[2];
    int res = pipe(fildes);
    char buffer[20];

    printf("Resultado do Pipe = %d \n",res);

    int resf = fork();

    switch (resf) {
        case -1:
            perror("fork");
            return -1;
        case 0:
            close(fildes[1]); // o fildes tem 2 file escriptores, uma para ler outro para escrever, o de escrita é o 1
            while(read(fildes[0],&buffer,20)>0){
                printf("[FILHO] Li %s\n",buffer);
            }
            close(fildes[0]);
            _exit(0);
        default:
            close(fildes[0]);
            for(int i=0; i<10; i++){
                write(fildes[1],"Ola mundo!",11);
                printf("[PAI] Ecrevi uma linha\n");
            }
            close(fildes[1]);
            wait(NULL);
    }
    return 0;
}

int main(){
    int fildes[2];
    int res = pipe(fildes);
    char buffer[20];

    int resf = fork();

    switch (resf) {
        case -1:
            perror("fork");
            return -1;
        case 0:
            close(fildes[1]);
            dup2(fildes[0], STDIN_FILENO);
            execlp("wc","wc",NULL);
            close(fildes[0]);
            _exit(0);
        default:
            close(fildes[0]);
            char buff1[200];
            int numBytes;
            while( (numBytes = read(STDIN_FILENO, &buff1, 200)) > 0){
                write(fildes[1], buff1, numBytes);
            }
            close(fildes[1]);
            wait(NULL);
    }

}