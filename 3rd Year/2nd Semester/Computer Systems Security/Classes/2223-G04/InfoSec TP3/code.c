#define _GNU_SOURCE
#include <stdio.h>
#include <stdlib.h>
#include <fcntl.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <string.h>

// CODE 1

void code1(){
    int arr[5] = {1,2,3,4,5};
    int index = 5;
    int value = 10;
    arr[index] = value; 
}
/**
 * é acedida uma posição de memória que não pertence ao array, isto significa, estamos a alterar o valor de uma posição de memória que pode
 * estar encarregue de coisas relacionadas com a segurança do sistema
 * 
 * CWE-787 : Out-of-bounds Write - Violação de integridade
 * É escrita informação depois do final ou antes do início do limites do buffer, com isto é possível corrumpir data, provocar crashs ou até 
 * correr código não entendido. É modificado o valor de memória de um endereço fora dos limites do buffer
 * **/ 

// CODE 1 FIX

void code1_fix(){
    int arr[5] = {1,2,3,4,5};
    int index = 4; // or int index = sizeof(arr)/4-1;
    int value = 10;
    arr[index] = value;
}



// CODE 2

int code2(){
    int fd;
    char filename[] = "file.txt";

    fd = open(filename, O_CREAT); // Má pratica deixar o controlo das permissões para o compilador

    if(fd == -1){
        perror("Error opening file");
        return 1;
    }

    printf("File created successfully!\n");

    close(fd);

    return 0;
}

/**
 * CWE-276 : Incorrect Default Permissions - Violação de integridade, autenticidade
 * Como descrito no manual, a função open() necessita de receber uma flag que indique as premissões, porêm isto não é obrigatório, no caso de não ser
 * fornecida as premissões, a atribuição destas é feita pelo compilador. No nosso caso, usando uam máquina com o Windows 11, todos os utilizadores têm
 * premissões de escrever, ler e execurar o ficheiro 'file.txt'.
 * **/

// CODE 2 FIX

int code2_fix(){
    int fd;
    char filename[] = "file.txt";

    fd = open(filename, O_CREAT, O_RDONLY);
    if(fd == -1){
        perror("Error opening file");
        return 1;
    }

    printf("File created successfully!\n");

    close(fd);
    return 0;
}

// CODE 3

void code3(int n){
    int *arr;
    int i;

    arr = (int*) malloc(n * sizeof(int));

    if(arr == NULL){
        perror("Error allocating memory");
        return;
    }

    for(i = 0; i < n; i++){
        arr[i] = i*2;
    }

    printf("Memory allocation and operations completed successfully!\n");
}

/**
 * CWE-190 && CWE-20: Integer Overflow or Wraparoud && Improper Input Validation(lead to data corruption, unexpected behavior, infinite loops and system crashes)
 * Como podemos considerar que o valor de n pode ser um valor introduzido pelo utilizador, isto pode resultar num caso em que o utilizador introduza uma
 * número muito grande, provocando assim, que quando esse número é usado para muntiplicar com 'sizeof(int)' possa haver um integer overflow or wraparound
 * em que o valor calculado passe para um número muito pequeno ou até negativo. Com isto é possível ver um caso em que seja alocado uma lista pequena
 * mas o programa está a assumir que ela é grande originando em varios problemas do tipo Out-of-Bounds (CWE-119)
 * **/ 

// CODE 3 FIX

void code3_fix(int n){
    int *arr;
    int i;

    if(n<=0 || n > INT_MAX/sizeof(int)){
        printf("The number inputed causes a Integer Overflow or Wraparoud!");
        return;
    }

    arr = (int*) malloc(n*sizeof(int));

    if(arr == NULL){
        perror("Error allocating memory");
        return;
    }

    for(i=0; i<n; i++){
        arr[i] = i*2;
    }

    printf("Memory allocation and operations completed successfully!\n");
}

// CODE 4

void code4() {
    char username[20];
    char password[20];

    printf("Enter username: ");
    scanf("%s", username);
    strcpy(password, "mypassword");

    printf("Access granted for user: %s with password: %s\n", username, password);
}

/**
 * CWE-787 && CWE-798 : Out-of-bounds Write && Use of Hard-coded Credentials
 * **/

void code4_fix(){
    char *username;
    char *password;
    size_t len=0;
    size_t read;

    printf("Enter username: ");
    read = getline(&username, &len, stdin);

    if(read == -1){
        perror("Error reading the username input!");
        return;
    }

    printf("Enter password: ");
    read = getline(&password, &len, stdin);

    if(read == -1){
        perror("Error reading the password input!");
        return;
    }

    printf("Access granted for user: %s with password: %s\n", username, password);
}

int main() {
    code4_fix();
    return 0;
}


