#define _XOPEN_SOURCE
#define _DEFAULT_SOURCE

#include "constructors.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/resource.h>

		
int main(int argc, char const *argv[])
{
    //argv[1] = "exercicio-1";
	if(strcmp(argv[1], "exercicio-1")==0)
	{
        printMemory();
		loadUsers("./entrada/users.csv");
        printMemory();
		loadCommits("./entrada/commits.csv");
        printMemory();
		loadRepos("./entrada/repos.csv");
        printMemory();
        //free(arrayRepos);

	}


    if(strcmp(argv[1], "exercicio-2")==0)
    {
        printMemory();
        struct node* users = loadUsersBin("./saida/users-ok.csv");
        printf("Utilizadores na Binary Tree!\n");
        struct node* repos = loadReposBin("./saida/repos-ok.csv");
        printf("Repos na Binary Tree!\n");
        printMemory();
        removeInvalidCommits("./saida/commits-ok.csv", users, repos);
        freeAbin(repos);
        struct node* reposWCommits = loadReposWCommits("./saida/commits-final.csv");
        removeInvalidRepos("./saida/repos-ok.csv", users, reposWCommits);
        saveUsersToFinal("./saida/users-ok.csv");
        freeAbin(users);
        freeAbin(reposWCommits);



        printMemory();

    }

    //EXTRA
    if(strcmp(argv[1], "exercicio-1-small")==0)
    {
        printMemory();


        loadUsers("./entrada/users-small.csv");

        loadCommits("./entrada/commits-small.csv");

        loadRepos("./entrada/repos-small.csv");
        printMemory();
        //free(arrayRepos);

    }

    if(strcmp(argv[1], "exercicio-teste")==0)
    {
        printMemory();
        
        printf("Utilizadores na Binary Tree!\n");

        //inorder(repos);
        printMemory();

    }
	

	return 0;
}

