#include <stdio.h>
#include <stdlib.h>
#include <string.h>
//#include <sys/types.h>
#include <unistd.h> /* chamadas ao sistema: defs e decls essenciais */
#include <fcntl.h> /* O_RDONLY, O_WRONLY, O_CREAT, O_* */

#include <errno.h> //errros
//#include <malloc.h>
//#include <stdlib.h>

typedef struct pessoa
{
    char name[200];
    int age;
}PESSOA;

int insertPerson(char* name, int age){
    int res;

    PESSOA p;
    p.age=age;
    strcpy(p.name, name);

    // append permite escrever logo no fim
    int fd = open("database.db", O_CREAT | O_APPEND | O_WRONLY, 0600);

    res = write(fd,&p, sizeof(PESSOA));
    if(res<0){
        perror("Error creating person");
        return -1;
    }
    close(fd);
    return 0;
}

int updatePerson(char* name, int newAge){
    int bytes_read;
    int fd;
    PESSOA p;

    if((fd = open("database.db", O_RDONLY)) == -1){
        printf("Msg: %s, Nr: %d\n", strerror(errno), errno);
        perror("Erro ao abrir o ficheiro de origem");
        return -1;
    }
    while((bytes_read = read(fd, &p, sizeof(PESSOA))) != 0){
        if(strcmp(p.name, name) == 0){
            lseek(fd, sizeof(p), SEEK_CUR);
            p.age = newAge;
        }
    }

    close(fd);
    return 0;

}

static char *rand_string(char *str, size_t size)
{
    const char charset[] = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJK...";
    if (size) {
        --size;
        for (size_t n = 0; n < size; n++) {
            int key = rand() % (int) (sizeof charset - 1);
            str[n] = charset[key];
        }
        str[size] = '\0';
    }
    return str;
}

int main(int argc, char * argv[]){
    if(strcmp(argv[1],"-i") == 0){
        insertPerson(argv[2], atoi(argv[3]));
    }
    if(strcmp(argv[1],"-u") == 0){
        updatePerson(argv[2], atoi(argv[3]));
    }
    if(strcmp(argv[1],"-t") == 0){
        for(int i = 0; i < atoi(argv[2]); i++){
            char nome[200];
            rand_string(nome, 15);
            insertPerson(nome, 30);
        }
    }
    if(strcmp(argv[1], "-l") == 0){
        int bytes_read;
        int fd;
        PESSOA pessoa;
        if((fd = open("database.db", O_RDWR)) == -1){
            printf("Msg: %s, Nr: %d\n", strerror(errno), errno);
            perror("Erro ao abrir o ficheiro de origem");
            return -1;
        }
        printf("Pessoas:\n");
        int i = 0;
        while((bytes_read = read(fd, &pessoa, sizeof(PESSOA))) != 0){
            char* nomePessoaAAlterar = argv[2];
            i++;
            printf("%s com %d anos\n", pessoa.name, pessoa.age);
        }
        printf("Temos %d pessoas\n", i);

        close(fd);

    }
}
/**
int person_change_v2(long pos, int age){
    PESSOA  p;

    int fd = open(FILENAME, O_RDWR, 0600);

    int seek_res = lseek(fd,pos*sizeof(PESSOA), SEEK_SET);
    if(seek_res < 0){
        perror("Error lseek");
        return  -1;
    }

    int bytes_read = read(fd,&p,sizeof(PESSOA));
    if(bytes_read<0){
        perror("Error read");
        return -1;
    }
    printf(("Read Person name %s age- %d\n",p.name,p.age));
    p.age = age;

    seek_res = lseek(fd, -sizeof(PESSOA), SEEK_CUR);
    if(seek_res<0){
        perror("Error ");
        return -1;
    }

    int res = write(fd,&p, sizeof(PESSOA));
    if(res < 0){
        perror("Error write");
        return -1;
    }
}
**/
