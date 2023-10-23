#include <fcntl.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <signal.h>
#include <sys/stat.h>
#include <unistd.h>

#define MAX_BUFFER 1024
#define DEBUG 1

void reverse(char s[]);
void itoa(int n, char s[]);
int escreverParaServer(char* nomePipe, char* mensagem);
char* lerDoServer(char* nomePipe);
void inicializaComunicacao();
void status();
void transformacao(int argc, char const *argv[]);
void fecharForcado();
void usr1_handle();
void usr2_handle();

char pid_processo[MAX_BUFFER];
char ficheiroComunicacao[MAX_BUFFER];

void reverse(char s[]) {
    int i, j;
    char c;

    for (i = 0, j = strlen(s)-1; i<j; i++, j--) {
        c = s[i];
        s[i] = s[j];
        s[j] = c;
    }
}

void itoa(int n, char s[]){
    int i, sign;

    if ((sign = n) < 0)  /* record sign */
        n = -n;          /* make n positive */
    i = 0;
    do {       /* generate digits in reverse order */
        s[i++] = n % 10 + '0';   /* get next digit */
    } while ((n /= 10) > 0);     /* delete it */
    if (sign < 0)
        s[i++] = '-';
    s[i] = '\0';
    reverse(s);
}

int escreverParaServer(char* nomePipe, char* mensagem){
    int pipeCliente = open(nomePipe, O_WRONLY);

    if(pipeCliente < 0)
        perror("Erro ao abrir");


    int status = write(pipeCliente, mensagem, strlen(mensagem)*sizeof(char));
    close(pipeCliente);

    return status;
}

char* lerDoServer(char* nomePipe){
    char resposta[2000];

    for(int i = 0; i<2000; i++) resposta[i] = '\0';
    int pipeCliente = open(nomePipe, O_RDONLY);

    if(pipeCliente < 0){
        perror("Erro ao abrir");
    }

    int res = 0;
    while(read(pipeCliente, resposta+res, 1) > 0){
        res++;
    }

    resposta[res+1] = '\0';
    close(pipeCliente);

    return strdup(resposta);
}

void inicializaComunicacao(){
    int pipePrincipal = open("tmp/servidor", O_WRONLY);

    if (pipePrincipal == -1) {
        perror("Erro ao abrir pipe com o servidor!");
        _exit(-1);
    }

    write(pipePrincipal, pid_processo,  strlen(pid_processo));

    close(pipePrincipal);

    char fPipeCliente[9+strlen(pid_processo)];
    strcpy(fPipeCliente, "tmp/pipe_");
    strcpy(fPipeCliente+9, pid_processo);



    strcpy(ficheiroComunicacao, fPipeCliente);

    if(mkfifo(fPipeCliente, 0666) != 0){
        perror("Impossível criar pipe para comunicar com o servidor!");
        _exit(-1);
    }


}

void status(){
    escreverParaServer(ficheiroComunicacao, "status");
    char* lido = lerDoServer(ficheiroComunicacao);

    if(DEBUG) write(1, "Output do comando:\n", 19);

    write(1, lido, strlen(lido));

}

void transformacao(int argc, char const *argv[]){
    int comunicacao = open(ficheiroComunicacao, O_WRONLY);

    if (comunicacao == -1) {
        perror("Erro ao abrir pipe");
        unlink(ficheiroComunicacao);
        _exit(-1);
    }

    for (int i = 1; i < argc; i++) {
        write(comunicacao, argv[i], strlen(argv[i]));
        write(comunicacao, " ", 1);
    }

    close(comunicacao);
    char* mensagem = lerDoServer(ficheiroComunicacao);
    while(1){
        write(1, mensagem, strlen(mensagem));
        mensagem = lerDoServer(ficheiroComunicacao);

        if(strcmp(mensagem, "(concluido)") == 0){
            write(1, "(concluido)\n", 12);
            unlink(ficheiroComunicacao);
            exit(1);
        }

        usleep(300);
    }
}

void fecharForcado() {
    unlink(ficheiroComunicacao);
    _exit(0);
}

void usr1_handle(){
    write(1, "Não posso aceitar mais pedidos\n", 32);
    unlink(ficheiroComunicacao);
    exit(1);
}

void usr2_handle(){
    write(1, "(concluido)\n", 12);
    unlink(ficheiroComunicacao);
    exit(0);
}

int main(int argc, char const *argv[]) {
    itoa(getpid(), pid_processo);

    if (signal(SIGINT, fecharForcado) == SIG_ERR) {
        unlink(ficheiroComunicacao);
        perror("Signal1");
        _exit(-1);
    }
    if (signal(SIGTERM, fecharForcado) == SIG_ERR) {
        unlink(ficheiroComunicacao);
        perror("Signal2");
        _exit(-1);
    }

    if (signal(SIGUSR2, usr2_handle) == SIG_ERR) {
        unlink(ficheiroComunicacao);
        perror("Signal2");
        _exit(-1);
    }

    if (signal(SIGUSR1, usr2_handle) == SIG_ERR) {
        unlink(ficheiroComunicacao);
        perror("Signal2");
        _exit(-1);
    }

    if (argc >= 2 && !strcmp(argv[1], "status")) {
        inicializaComunicacao();
        status();
        unlink(ficheiroComunicacao);
    } else if (argc >= 5 && !strcmp(argv[1], "proc-file")) {
        inicializaComunicacao();
        transformacao(argc, argv);
    } else {
        write(1,"./sdstore status\n", 17);
        write(1,"./sdstore proc-file <priority> <input-filename> <output-filename> transformation-id-1 transformation-id-2 ...\n",105);
    }


    return 0;
}