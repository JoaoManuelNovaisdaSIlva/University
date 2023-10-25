#include <stdio.h>
#include <unistd.h>
#include <sys/wait.h>
#include <string.h>


int ex_1() {
    /**
     * se houver erro sao feitos todos os prints
     * caso contrario apenas o "ola" e printed
     */
    int i = 0;
    printf("Ola\n");
    i = execl("/bin/ls","ls","-l",NULL);
    printf("Adeus\n");
    if(i != 0)printf("Houve erro\n");
}

int ex_2(){
    pid_t pid = fork();
    int i ;
    if(pid == 0){
        i = execl("/bin/ls","ls","-l", NULL);
        _exit(i);
    }
    int status;
    wait(&status);
    if(WIFEXITED(status)) printf("O filho fez o suposto");
    else printf("O filho teve bug");
}

int ex_3(int argc, char** argv){
    int j = 0;
    for(int i = 1; i<argc; i++){
        pid_t pid = fork();
        if(pid == 0){
            j = execlp(argv[i],argv[i],NULL);
            _exit(j);
        }
    }
    int status;
    for(int z = 1; z<argc; z++){
        wait(&status);
        if(WEXITSTATUS(status) == 0) printf("O programa correu corretamente\n");
        else printf("O programa correu incorretamente\n");
    }
}

int mySystem(char* args){
    int fork_ret, exec_ret, wait_ret, status,res;

    char *exec_args[20];
    char *string;
    int i=0;

    string = strtok(args, " "); // ou strsep  (separar uma string pelos espaços)
    while(string != NULL){
        exec_args[i]=string;
        string= strtok(NULL," ");
        i++;
    }
    exec_args[i]=NULL;

    fork_ret = fork();
    if(fork_ret == 0){
        exec_ret = execlp();
        _exit(exec_ret);
    }

}

int main(int argc, char** argv){
    char command1[] = "ls -l -a -h";
    int ret;
    ret = mySystem(command1);
    printf("ret: %d\n",ret);
}


