#include <fcntl.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <unistd.h>
#include <sys/stat.h>
#include <signal.h>
#include <wait.h>


#define MAXBUFFER 1024
#define DEBUG 1
#define TEMPO_TESTE 10

typedef struct Transformacao {
    char nome[MAXBUFFER];
    int nrExecucoes;
    int maxExecucoes;
    int execsAtuais;
    struct Transformacao *prox;
} *TRANSFORMACAO;

typedef struct Utilizacoes {
    char nome[MAXBUFFER];
    int utilizacoes;
    struct Utilizacoes *prox;
} *UTILIZACOES;

typedef struct Tarefa {
    char *comando;
    int id;
    int numComandos; // numero de comandos da tarefa
    int status; // 0 - a espera; 1 - a processar;
    char* pipeCliente; //pid do cliente
    char* fifoCliente;
    char *input_file; // ficheiro input
    char *output_file; //output
    int prioridade;
    struct Tarefa *prox;
} *TAREFA;

void adicionaTarefa(TAREFA t);
void executaTarefa(int input, int output, TAREFA t);
void fecharFilhoTarefa(TAREFA t, int pid_server);
int podeFazerTarefa(TAREFA t);
void writeTerminal(char* mensagem);
void adicionaTarefa(TAREFA t);
void executaTarefa(int input, int output, TAREFA t);
void fecharFilhoTarefa(TAREFA t, int pid_server);
int podeFazerTarefa(TAREFA t);
void reverse(char []);
void itoa(int n, char s[]);
void removerTarefa(TAREFA r);
void removeTarefasDeCliente(char* nomeCliente);
int escreverParaCliente(char* nomePipe, char* mensagem);
char* lerDoCliente(char* nomePipe);
int atingiuMaximo(char* transformacao, int numTransformacoes);
int numTransformacoes();
int maxTransformSimultaneas();
ssize_t readln(int fd, char *line, ssize_t size);
TRANSFORMACAO loadTransforms(char* config);
UTILIZACOES inicializaUti(char* comando);
int getProxID();
void updateExecTransf(TAREFA t, int incrementaOuDecrementa);
char* getFifoFromPID(char* pid);
void trabalhador();
void inicializaTarefa(char* pid_cliente, int priority, char* inputFile, char* outputfile, char* comando);
int getNumUtilizacoes(char* transformacao);
char* criaComando(char* executavel, char* comando);
void writeToFile(char* mensagem);
void term_handler();
void status(char* fPipeCliente);
void finalizarForcado();
void transforma(char* pid_cliente, char *argumentos);
void removeTarefa(int id, int avisarPipe);
void updateTarefa(int sigin);
void handle_fecho();

char pid_processo[MAXBUFFER];
TRANSFORMACAO transformacoes;
TAREFA tasks;
char *loc_transformacoes;
int podeAceitarTransformacoes = 1;

void reverse(char s[]) {
    int i, j;
    char c;

    for (i = 0, j = strlen(s)-1; i<j; i++, j--) {
        c = s[i];
        s[i] = s[j];
        s[j] = c;
    }
}

//FALTA !
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

void removerTarefa(TAREFA r){
    TAREFA t = tasks;
    TAREFA ant = tasks;

    while(t != NULL){
        if(t->id == r->id){

            ant->prox = t->prox;
        }
        t = t->prox;

    }
}

void removeTarefasDeCliente(char* nomeCliente){
    TAREFA t = tasks;

    while(t != NULL){
        if(t->pipeCliente == nomeCliente)
            removerTarefa(t);
    }
}

int escreverParaCliente(char* nomePipe, char* mensagem){
    int pipeCliente = open(nomePipe, O_WRONLY);

    if(pipeCliente < 0) {
        perror("Erro ao abrir pipe");
        writeTerminal("do cliente:");
        writeTerminal(nomePipe);
        writeTerminal("não foi possivel escrever a mensagem: ");
        writeTerminal(mensagem);
        unlink(nomePipe);
        removeTarefasDeCliente(nomePipe);
        _exit(0);
    }



    int escrito = write(pipeCliente, mensagem, strlen(mensagem)*sizeof(char));
    close(pipeCliente);

    return escrito;
}

char* lerDoCliente(char* nomePipe){
    char resposta[MAXBUFFER];
    int pipeCliente = open(nomePipe, O_RDONLY);

    if(pipeCliente < 0){
        perror("Erro ao abrir");
        _exit(0);
    }

    int res = 0;
    while(read(pipeCliente, resposta+res, 1) > 0){
        res++;
    }

    close(pipeCliente);

    return strdup(resposta);
}

int atingiuMaximo(char* transformacao, int numTransformacoes){
    TRANSFORMACAO t = transformacoes;
    int entrou = 0;
    while(t != NULL){
        if(strcmp(t->nome, transformacao) == 0){
            entrou+=1;
            if(numTransformacoes > t->maxExecucoes)
                return 1;
        }
        t=t->prox;
    }
    if(entrou > 0) return 0;
    return 1;
}

int numTransformacoes(){
    TRANSFORMACAO atual = transformacoes;
    int conta = 0;
    while(atual != NULL){
        conta += 1;
        atual = atual->prox;
    }

    return conta;
}

void writeTerminal(char* mensagem){
    write(1, mensagem, strlen(mensagem));
}

int maxTransformSimultaneas(){
    TRANSFORMACAO atual = transformacoes;
    int conta = 0;
    while(atual != NULL){
        conta += atual->maxExecucoes;
        atual = atual->prox;
    }

    return conta;
}

ssize_t readln(int fd, char *line, ssize_t size) {
    ssize_t res = 0;
    ssize_t i = 0;
    while ((res = read(fd, &line[i], size)) > 0 && ((char) line[i] != '\n')) {
        i+=res;
    }
    return i;
}

TRANSFORMACAO loadTransforms(char* config){
    char line[MAXBUFFER];
    TRANSFORMACAO principal = NULL;
    TRANSFORMACAO atual = NULL;
    TRANSFORMACAO anterior = NULL;

    int ficheiroConfigs;
    if ((ficheiroConfigs = open(config, O_RDONLY)) < 0) {
        perror("Erro ao abrir ficheiro");
        _exit(-1);
    }

    char *token;
    int i = 0;
    while (readln(ficheiroConfigs, line, 1) > 0) {
        atual = malloc(sizeof(struct Transformacao));
        if (i == 0) principal = atual;
        else anterior->prox = atual;

        token = strtok(line, " ");
        strcpy(atual->nome, token);

        token = strtok(NULL, " ");
        atual->maxExecucoes = atoi(token);

        atual->execsAtuais = 0;
        atual->nrExecucoes = 0;
        atual->prox = NULL;
        anterior = atual;
        i++;
    }

    return principal;

}

UTILIZACOES inicializaUti(char* comando){
    UTILIZACOES inicial = NULL;
    char* token = malloc(sizeof(char)*MAXBUFFER);
    char* tmp = malloc(sizeof(char) * MAXBUFFER);
    strcpy(tmp, comando);
    token = strtok(tmp, " ");
    int primeiro = 1;
    while(token != NULL){
        if(primeiro == 1){
            inicial = malloc(sizeof(UTILIZACOES));
            inicial->prox = NULL,
            inicial->utilizacoes = 1;
            strcpy(inicial->nome, token);
            primeiro = 0;
            token = strtok(NULL, " ");
            continue;
        }

        int encontrado = 0;
        UTILIZACOES temp = inicial;
        while(temp->prox != NULL){
            if(strcmp(temp->nome, token) == 0){
                temp->utilizacoes++;
                encontrado = 1;
            }
            temp = temp->prox;
        }

        if(strcmp(temp->nome, token) == 0){
            temp->utilizacoes++;
            encontrado = 1;
        }

        if(encontrado == 0){
            temp->prox = malloc(sizeof(UTILIZACOES));
            temp->prox->prox = NULL;
            temp->prox->utilizacoes = 1;
            strcpy(temp->prox->nome, token);

        }
        token = strtok(NULL, " ");
    }

    free(token);
    free(tmp);
    return inicial;


}

int getProxID(){
    TAREFA t = tasks;
    if(t == NULL) return 1;

    while(t->prox != NULL) t=t->prox;

    return t->id+1;
}

//Suposto receber uma TAREFA (acabar!)
void updateExecTransf(TAREFA t, int incrementaOuDecrementa){
    char* token = malloc(sizeof(char)*MAXBUFFER);
    char* token_inicial = token;
    char* tmp = malloc(sizeof(char) * MAXBUFFER);
    strcpy(tmp, t->comando);
    token = strtok(tmp, " ");
    while(token != NULL){
        TRANSFORMACAO atual = transformacoes;
        while(atual != NULL){
            if(strcmp(atual->nome, token) == 0){
                if(incrementaOuDecrementa) atual->nrExecucoes++;
                else atual->nrExecucoes--;
            }
            atual = atual->prox;
        }
        token = strtok(NULL, " ");
    }


    free(tmp);
    free(token_inicial);
}

char* getFifoFromPID(char* pid){
    char* nomeFicheiro = malloc(sizeof(char)*(9+strlen(pid)));
    strcpy(nomeFicheiro, "tmp/pipe_");
    strcpy(nomeFicheiro+9, pid);

    return nomeFicheiro;
}

void trabalhador(){
    TAREFA tarefas = tasks;
    if(DEBUG) writeTerminal("A procurar por trabalho...\n");
    if(tarefas == NULL && DEBUG) writeTerminal("Não ha tarefas\n");
    while(tarefas != NULL){
        if(tarefas->status != 0 || !podeFazerTarefa(tarefas) ) {
            if(DEBUG) writeTerminal("Não pode fazer a tarefa\n");
            sleep(1);
            tarefas = tarefas->prox;
            break;
        }
        updateExecTransf(tarefas, 1);
        tarefas->status = 1;
        sleep(1);
        if(fork() == 0){
            sleep(1);
            escreverParaCliente(tarefas->fifoCliente, "(processing)\n");
            sleep(1);
            if(fork() == 0){
                int input = open(tarefas->input_file, O_RDONLY);
                int output = open(tarefas->output_file, O_CREAT | O_TRUNC | O_WRONLY, 0777);
                if(DEBUG) sleep(TEMPO_TESTE);
                executaTarefa(input, output, tarefas);

                _exit(1);
            }else{
                int status;

                wait(&status);
                fecharFilhoTarefa(tarefas, atoi(pid_processo));
            }

            _exit(1);
        }
        tarefas = tarefas->prox;
    }
}

void inicializaTarefa(char* pid_cliente, int priority, char* inputFile, char* outputfile, char* comando){
    if(!podeAceitarTransformacoes){
        writeTerminal("Não posso aceitar mais tarefas");
        kill(atoi(pid_cliente), SIGUSR1);
        return;
    }
    TAREFA nova = malloc(sizeof (struct Tarefa));
    nova->pipeCliente = malloc(sizeof(char)*strlen(pid_cliente));
    nova->comando = malloc(sizeof(char)*strlen(comando));
    nova->input_file = malloc(sizeof(char)*strlen(inputFile));
    nova->output_file = malloc(sizeof(char)*strlen(outputfile));
    nova->fifoCliente = malloc(sizeof(char)* strlen(pid_cliente)+9);
    nova->status = 0;
    nova->prioridade = priority;
    nova->id = getProxID();
    if(DEBUG) {
        writeTerminal("Nova tarefa com ID: ");
        char id[MAXBUFFER];
        itoa(nova->id, id);
        int i = 0;
        while (id[i] != '\0') write(1, id+(i++), 1);
        writeTerminal("\n");
    }
    nova->prox = NULL;

    strcpy(nova->pipeCliente, pid_cliente);
    strcpy(nova->output_file, outputfile);
    strcpy(nova->input_file, inputFile);
    strcpy(nova->comando, comando);
    strcpy(nova->fifoCliente, getFifoFromPID(pid_cliente));



    UTILIZACOES uti = inicializaUti(comando);
    int numeroComandos = 0;
    int ultrapassouMaximo = 0;
    while(uti != NULL){
        numeroComandos+=uti->utilizacoes;
        if(atingiuMaximo(uti->nome, uti->utilizacoes)){
            ultrapassouMaximo = 1;
        }
        uti = uti->prox;

    }

    nova->numComandos = numeroComandos;
    if(!ultrapassouMaximo) {
        adicionaTarefa(nova);
        trabalhador();
    }else{
        escreverParaCliente(nova->fifoCliente, "Atingiu o maximo de transformações simultâneas ou tem transformações inexistentes!");
        sleep(1);
        kill(atoi(pid_cliente), 1);
    }
}

int getNumUtilizacoes(char* transformacao) {
    TRANSFORMACAO t = transformacoes;
    while(t != NULL) {
        if(strcmp(transformacao, t->nome) == 0) {
            return t->nrExecucoes;
        }
        t = t->prox;
    }
}

//pode fazer tarefa
int podeFazerTarefa(TAREFA t){
    UTILIZACOES uti = inicializaUti(t->comando);

    while(uti != NULL){
        if(uti->utilizacoes > uti->utilizacoes + getNumUtilizacoes(uti->nome))
            return 0;
        uti = uti->prox;
    }
    return 1;
}

void adicionaTarefa(TAREFA t){
    TAREFA backup = tasks;


    if(tasks == NULL){
        tasks = t;
    }
    else if(backup->prox == NULL) {
        if(backup->prioridade < t->prioridade && backup->status == 0){
            tasks = t;
            t->prox = backup;
        }else{
            backup->prox = t;
        }
    }else{
        TAREFA temp = tasks;
        while(temp->prox != NULL && t->prioridade > temp->prox->prioridade){
            temp = temp->prox;
        }
        t->prox = temp->prox;
        temp->prox = t;
    }
}

char* criaComando(char* executavel, char* comando){
    int len = strlen(loc_transformacoes);
    strcpy(executavel, loc_transformacoes);
    executavel[len] = '/';
    strcpy(executavel+len+1, comando);

    return executavel;
}

void writeToFile(char* mensagem){
    int fd = open("mensagens.txt", O_CREAT | O_WRONLY | O_APPEND, 0777);
    write(fd, mensagem, strlen(mensagem));
    close(fd);
}

void executaTarefa(int input, int output, TAREFA t) {
    signal(SIGCHLD, SIG_DFL);
    int i;
    char **ordem_comandos;
    char *tmp = strdup(t->comando);
    char *token = strdup(t->comando);
    token = strtok(tmp, " ");
    ordem_comandos = malloc(sizeof(char *) * t->numComandos);
    for (int i = 0; i < t->numComandos; i++) {
        ordem_comandos[i] = malloc(sizeof(char) * strlen(token));
        strcpy(ordem_comandos[i], token);
        token = strtok(NULL, " ");
    }


    int status;
    int x = t->numComandos;

    int pip[2];


    while (i < x) {
        if (i != 0) {
            dup2(pip[0], 0);
            close(pip[0]);
        } else dup2(input, 0);

        if (i == x-1) dup2(output, 1);
        else {
            if (pipe(pip) == 0) {
                dup2(pip[1], 1);
                close(pip[1]);
            } else {
                perror("Pipe");
                _exit(-1);
            }
        }

        int f;
        if ((f = fork()) == -1) {
            perror("Fork");
            _exit(-1);
        } else if (f == 0) {
            char *executavel = malloc(sizeof(char) * MAXBUFFER);
            executavel = criaComando(executavel, ordem_comandos[i]);
            execlp(executavel, executavel, NULL);
            perror("Exec");
            _exit(-1);
        }

        i++;
    }

    for(int i = 0; i<x; i++){
        wait(&status);
    }



}


void term_handler() {
    char pid[MAXBUFFER];
    itoa(getpid(), pid);
    _exit(0);
}

void status(char* fPipeCliente) {
    int f = fork();


    if (f == 0) {
        int pipeCliente = open(fPipeCliente, O_WRONLY);

        signal(SIGINT, SIG_IGN);
        signal(SIGTERM, SIG_IGN);

        if(pipeCliente < 0){
            perror("Erro");
            _exit(0);
        }

        TAREFA iterador = tasks;

        while (iterador != NULL && iterador->status == 1) {
            write(pipeCliente, "task #", 6);
            char num[MAXBUFFER];
            itoa(iterador->id, num);
            write(pipeCliente, num, strlen(num));
            write(pipeCliente, ": ", 2);
            write(pipeCliente, iterador->comando, strlen(iterador->comando));
            write(pipeCliente, "\n", 1);
            iterador = iterador->prox;
        }

        TRANSFORMACAO it = transformacoes;
        while (it != NULL) {
            write(pipeCliente, "Transformação ", 16);
            write(pipeCliente, it->nome, strlen(it->nome));
            write(pipeCliente, ": ", 2);

            char num[MAXBUFFER];
            itoa(it->nrExecucoes,num);
            write(pipeCliente, num, strlen(num));

            write(pipeCliente, "/", 1);

            itoa(it->maxExecucoes, num);
            write(pipeCliente, num, strlen(num));

            write(pipeCliente, " (running/max)\n", 15);

            it = it->prox;
        }


        char num[MAXBUFFER];

        itoa(getppid(),num);
        write(pipeCliente, "pid do server: ", 14);
        write(pipeCliente, num, strlen(num));
        write(pipeCliente, "\n", 1);

        close(pipeCliente);

        _exit(0);
    }
}

void finalizarForcado() {
    unlink("tmp/servidor");
    while (tasks != NULL) {
        pause();
    }

    write(1,"\n",1);
    _exit(0);
}

void fecharFilhoTarefa(TAREFA t, int pid_servidor) {
    if (mkfifo("tmp/fecharTarefa", 0666) == 0) {
        kill(pid_servidor, SIGUSR1);

        char num[MAXBUFFER];
        itoa(t->id, num);

        int pipe = open("tmp/fecharTarefa", O_WRONLY);

        int i = 0;
        while (num[i] != '\0') write(pipe, num+(i++), 1);

        close(pipe);


        _exit(0);
    } else {
        sleep(1);
        //fecharFilhoTarefa(t);
        _exit(-1);
    }
}

void transforma(char* pid_cliente, char *argumentos){
    char* comando_completo = malloc(sizeof(char)*strlen(argumentos));
    strcpy(comando_completo, argumentos);

    //Variaveis serao definidas abaixo
    int priority = 0;

    int n_remover = 0;
    char *token = strtok(argumentos, " ");
    n_remover += strlen(token);

    token = strtok(NULL, " ");
    n_remover += strlen(token);
    priority = atoi(token);

    token = strtok(NULL, " "); // ficheiro input
    char *input_file = malloc(sizeof(char) * strlen(token));
    strcpy(input_file, token);
    n_remover += strlen(token);

    token = strtok(NULL, " "); // ficheiro output
    char *output_file = malloc(sizeof(char) * strlen(token));
    strcpy(output_file, token);
    n_remover += strlen(token);

    char* comando_completo_copia = malloc(sizeof(char)*strlen(comando_completo));
    strcpy(comando_completo_copia, comando_completo);

    comando_completo_copia += n_remover+4; // 4 -> numero de espacos

    inicializaTarefa(pid_cliente, priority, input_file, output_file, comando_completo_copia);



}

void removeTarefa(int id, int avisarPipe){
    TAREFA tarefa = tasks;

    if(tarefa != NULL && tarefa->prox == NULL){
        if(avisarPipe){
            if(fork() == 0){
                kill(atoi(tarefa->pipeCliente), SIGUSR2);
                _exit(0);
            }
        }
        tarefa->status = 1;
        updateExecTransf(tarefa, 0);
        free(tasks);
        tasks = NULL;
    }//SA
    else if(tarefa != NULL && tarefa->prox != NULL){
        TAREFA anterior = tarefa;
        while(tarefa != NULL){
            if(tarefa->id == id){
                if(avisarPipe){
                    if(fork() == 0){
                        kill(atoi(tarefa->pipeCliente), SIGUSR2);
                        _exit(0);
                    }
                }
                updateExecTransf(tarefa, 0);
                tarefa->status = 1;
                if(anterior == tarefa) tasks = tarefa->prox;
                anterior->prox = tarefa->prox;
            }
            tarefa = tarefa->prox;

        }
    }else{
        writeTerminal("Erro ao fechar tarefa.");
    }

}

void updateTarefa(int sigin){
    if(DEBUG) write(1, "Recebi uma atualizacao de tarefa!\n", 35);


    int pipeFechar = open("tmp/fecharTarefa", O_RDONLY);

    if(pipeFechar > 0){
        char num_string[MAXBUFFER];
        int res = 0;
        while (read(pipeFechar,num_string+res,1) > 0) {res++;}

        if(DEBUG) {
            writeTerminal("Vou fechar a tarefa ");
            writeTerminal(num_string);
            writeTerminal("\n");
        }
        int num = atoi(num_string);
        removeTarefa(num, 1);

        close(pipeFechar);
        unlink("tmp/fecharTarefa");

        trabalhador();
    }else{
        writeTerminal("Não consegui saber qual era a tarefa a terminar !");
    }
}

void handle_fecho(){
    podeAceitarTransformacoes = 0;
    writeTerminal("A partir de agora não posso aceitar mais operações....");
}

int main(int argc, char **argv) {
    unlink("tmp/servidor");
    unlink("tmp/fecharTarefa");
    itoa(getpid(), pid_processo);

    inicializaUti("nop sdlib sdpica nop nop sdlib sdlib nop nop nop nop nop nop sdpica sdpica sdpica");

    if (argc != 3) {
        write(1,"./sdstored config-filename transformations-folder\n", 49);
        return -1;
    }

    loc_transformacoes = malloc(sizeof(char)*strlen(argv[2]));
    strcpy(loc_transformacoes, argv[2]);

    transformacoes = loadTransforms(argv[1]);

    if(mkfifo("tmp/servidor", 0666) != 0){
        perror("Erro ao criar a fifo inicial");
        return -1;
    }

    if (signal(SIGUSR1, updateTarefa) || signal(SIGTERM, handle_fecho) || signal(SIGINT, term_handler) || signal(SIGCHLD, SIG_IGN)) {
        perror("Signal");
        _exit(-1);
    }

    if(DEBUG){
        write(1, "Servidor iniciado com o PID: ", 29);
        write(1, pid_processo, strlen(pid_processo));
        write(1, "\n", 1);
    }

    char pid_cliente[MAXBUFFER];
    char buff_cliente[MAXBUFFER];

    while(podeAceitarTransformacoes) {
        if(DEBUG) write(1,"Iteração do While\n", 20);
        int pipeServidor = open("tmp/servidor", O_RDONLY);


        int res = 0;
        while (read(pipeServidor, pid_cliente+res,1) > 0) {
            res++;
        }
        pid_cliente[res++] = '\0';

        close(pipeServidor);

        char fPipeCliente[9+strlen(pid_cliente)];
        strcpy(fPipeCliente, strdup("tmp/pipe_"));
        strcpy(fPipeCliente+9, pid_cliente);
        usleep(2000);
        int pipeCliente = open(fPipeCliente, O_RDONLY);
        res = 0;
        while (read(pipeCliente, buff_cliente+res,1) > 0){
            res++;
        }
        buff_cliente[res] = '\0';

        if(DEBUG){
            write(STDOUT_FILENO, buff_cliente, strlen(buff_cliente));
            write(1, "\n", 1);
        }
        int opcaoValida = 0;
        if(strcmp(buff_cliente, "status") == 0){
            if(DEBUG){
                write(STDOUT_FILENO, "O cliente ", 10);
                write(STDOUT_FILENO, pid_cliente, strlen(pid_cliente));
                write(STDOUT_FILENO, " quer status\n", 14);
            }
            opcaoValida = 1;
            status(fPipeCliente);
        }

        if(buff_cliente[0] == 'p' && buff_cliente[1] == 'r' && buff_cliente[2] == 'o' && buff_cliente[3] == 'c' &&
           buff_cliente[4] == '-' && buff_cliente[5] == 'f' && buff_cliente[6] == 'i' && buff_cliente[7] == 'l' &&
           buff_cliente[8] == 'e'){
            usleep(100000);
            escreverParaCliente(fPipeCliente, "(pending)\n");
            opcaoValida = 1;
            transforma(pid_cliente, buff_cliente);
        }

        if(opcaoValida == 0){
            escreverParaCliente(fPipeCliente, "Não foi encontrada uma opção valida, por favor tente mais tarde!\n");
            int pid_c = atoi(pid_cliente);
            if(fork() == 0){
                sleep(1);
                kill(pid_c, 1);
                _exit(0);
            }

        }

    }



    return 0;
}


