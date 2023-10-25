#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>
#include <time.h>

int ex_1_2() {

    pid_t pid = fork(); // cria um novo processo e executao o codigo a baixo ao mesmo tempo, o pid assume valores diferente dependendo do processo (pai e filho)
    if(pid == 0){
        //codigo do filho
        printf("[FILHO] O meu PID é: %d\n", getpid());
        printf("[FILHO] O PID do meu pai: %d\n", getppid());
    }
    else{
        //codigo do pai
        printf("[PAI] O meu PID é: %d\n", getpid());
        printf("[PAI] O PID do meu pai: %d\n", getppid());
        printf(("[PAI] O PID do meu filho é: %d\n"),pid);
        sleep(10);
    }
    //sleep(10);
    return 0;
}

int ex_3(){
    int ordem = 1, status;
    while(ordem<=10) {
        pid_t pid = fork();
        if (pid != 0) {
            wait(&status);
            ordem++;
        } else {
            printf(("O meu pai é: %d - e eu sou o filho %d - e o meu PID é:% d \n"), getppid(), ordem, getpid());
            _exit(&ordem);
        }
    }
}


int ex_4(){
    int ordem = 1, status;
    while(ordem<=10){
        pid_t pid = fork();
        if(pid == 0){
            printf(("Eu sou o processo: %d - o meu pai é: %d\n"),getpid(),getppid());
            sleep(5);
            _exit(ordem);
        }
        else{

        }
        ordem++;

    }
    int i=1;
    while(i<=10){
        wait(&status);
        printf("O codigo de saia dos meus filhos é %d\n", WEXITSTATUS(status));
        i++;
    }
}

int ex_5(){
    int nlinhas = 10, ncolunas = 4000000;
    int** arr = malloc(sizeof(int*)*nlinhas);
    for(int i = 0; i<nlinhas; i++){
        arr[i] = malloc(sizeof(int)*ncolunas);
        for(int j = 0; j<ncolunas; j++){
            arr[i][j] = rand()%100000000;
        }
    }
    arr[1][390000] = 447;
    arr[9][1] = 447;

    int ordem = 1;
    int status, encontrou = 0;
    while(ordem < nlinhas){
        pid_t pid = fork();
        if(pid == 0) {
            for(int z = 0; z<ncolunas; z++)
                if(arr[ordem][z] == 447)  encontrou = 1;
            exit(encontrou);
        }
        ordem++;
    }
    wait(&status);
    if(WEXITSTATUS(status) == 1) printf("O valor foi encontrado");
}

int main(){
    int nlinhas = 10, ncolunas = 4000000;
    srand(time(NULL));
    int** arr = malloc(sizeof(int*)*nlinhas);
    for(int i = 0; i<nlinhas; i++){
        arr[i] = malloc(sizeof(int)*ncolunas);
        for(int j = 0; j<ncolunas; j++){
            arr[i][j] = rand()%1000;
        }
    }
    arr[1][390000] = 4470;
    arr[9][1] = 4470;

    int ordem = 0;
    pid_t  pid[nlinhas];
    while(ordem < nlinhas){
        pid[ordem] = fork();
        if(pid[ordem] == 0) {
            for(int z = 0; z<ncolunas; z++){
                if(arr[ordem][z] == 4470){
                    printf("Entre!)");
                    _exit(1);
                }
            }
            _exit(0);
        }
        ordem++;
    }
    int p=0, iEncontrado = 0;
    while(p<nlinhas){
        int status = 0;
        waitpid(pid[p], &status, 0);
        if(WEXITSTATUS(status) == 1) {
            printf("O valor foi encontrado na linha %d\n", p);
            iEncontrado = 1;
        }
        p++;
    }
    free(arr);
    if(iEncontrado == 0) printf("Nao encontrado!\n");
}
