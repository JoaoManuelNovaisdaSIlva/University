#define _XOPEN_SOURCE
#define _DEFAULT_SOURCE
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>
#include <glib.h>

#include "queries.h"
#include "catalogo-commits.h"
#include "users.h"
#include "constructors.h"
#include "./catalogo-repos.h"

/**
###########
Estatística
########### 
**/ 

//Quantidade de bots, organizações e utilizadores.
//ID: 1
//Descrição: Quantidade de cada tipo de utilizador
//Output: (X, Y e Z representam as quantidades calculadas)

gboolean countType_internal(gpointer key, gpointer value, gpointer data) {
    USER utilizador = value;
    int* qt = (int*) data;

    if(getTypeUser(utilizador) == 1)
        qt[0]++;
    if(getTypeUser(utilizador) == 2)
        qt[1]++;
    if(getTypeUser(utilizador) == 3)
        qt[2]++;

    return FALSE;
}

char* numTypes(GTree *t){
    int* num = malloc(sizeof(int)*3);
    num[0] = 0;
    num[1] = 0;
    num[2] = 0;
    g_tree_foreach(t, countType_internal, num);

    char dest[20000] = "";

    strcat(dest, "Bot: ");
    strcat(dest, intToString(num[1]));
    strcat(dest, "\n");
    strcat(dest, "Organization: ");
    strcat(dest, intToString(num[2]));
    strcat(dest, "\n");
    strcat(dest, "Users: ");
    strcat(dest, intToString(num[0]));

    return strdup(dest);

}

//Número médio de colaboradores por repositório:
//ID: 2
//Descrição: (Total colaboradores) / (Total repositórios)
//Output: Média calculada arredondada a duas casas decimais (Avg)

gint inteiros(gconstpointer a, gconstpointer b){
    if(a > b) return 1;
    if(a < b) return -1;
    if(a==b) return 0;
}

struct array_int {
    int* array;
    int size;
};

struct repo_commiters {
    GTree* tree;
    GHashTable * table;
    int total;
};

typedef struct repo_commiters* REPO_COMMITERS;
typedef struct array_int* ARRAY_INT;

gboolean conta_total_por_repo(gpointer key, gpointer value, gpointer data) {
    REPO_COMMITERS repo_commiters = (REPO_COMMITERS) data;
    GTree* repos = repo_commiters->tree;
    COMMITS commit = (COMMITS) value;
    int ID_COMMIT = getCommitter(commit);
    int n = g_tree_nnodes(repos);

    ARRAY_INT look = g_tree_lookup(repos, getRepos(commit));
    if(look == NULL){
        ARRAY_INT new = malloc(sizeof(struct array_int));
        new->size = 1;
        new->array = malloc(sizeof(int)*100);
        new->array[0] = getCommitter(commit);
        g_tree_insert(repos, getRepos(commit), new);
        repo_commiters->total++;
    }else{
        int add = 1;
        for(int i = 0; i<look->size; i++){
            if(getCommitter(commit) == look->array[i]) add = 0;
            //if(getAuthor(commit) == look->array[i]) add = 0;
        }

        if(add){
            look->array[look->size++] = getCommitter(commit);
            repo_commiters->total++;
        }
    }


    return FALSE;
}

float numOfCommitters(GTree *commits, GTree *repos){
    REPO_COMMITERS rp = malloc(sizeof(struct repo_commiters));
    GTree* commiters_per_repo = g_tree_new(inteiros);
    rp->tree = commiters_per_repo;
    rp->total = 0;
    g_tree_foreach(commits, conta_total_por_repo, rp);
    int nRepos = g_tree_nnodes(repos);
    float total = (float) rp->total;
    float numRepos = (float) nRepos;
    float res = total/numRepos;
    return res;
}

float averageNumOfCollab(GTree *commitsTree, GTree *reposTree){
    return numOfCommitters(commitsTree, reposTree);
}

//Quantidade de repositórios com bots;
//ID: 3
//Descrição: Número de repositórios contendo bots como colaboradores;
//Output: Total de repositórios

gboolean adiciona_bots (gpointer key, gpointer value, gpointer data) {
    REPO_COMMITERS repo_ret = (REPO_COMMITERS) data;
    GHashTable* ht = repo_ret->table;
    USER u = (USER) value;

    if(getTypeUser(u) == 2){
        GHashTable* ht = repo_ret->table;
        g_hash_table_add(ht, toIntAsterix(getUserId(u)));
    }

    return FALSE;
}


gboolean conta_bots (gpointer key, gpointer value, gpointer data) {
    REPO_COMMITERS repo_ret = (REPO_COMMITERS) data;
    GTree* users = repo_ret->tree;
    COMMITS commit = (COMMITS) value;
    GHashTable* table = repo_ret->table;
    int commiter = getCommitter(commit);
    int author = getAuthor(commit);

    //int nRepo = g_tree_nnodes(users);
    if(g_hash_table_contains(table, toIntAsterix(commiter))  ||
            g_hash_table_contains(table, toIntAsterix(author)) ){
        repo_ret->total++;
    }


    return FALSE;
}

int quantidadeReposWithBots(GTree* users, GTree* commits){
    REPO_COMMITERS rp = malloc(sizeof(struct repo_commiters));

    GHashTable* bots = g_hash_table_new(g_int_hash, inteiros);
    rp->table = bots;
    g_tree_foreach(users, adiciona_bots,rp);
    GHashTable* repos_com_bots = g_hash_table_new(g_int_hash, inteiros);
    rp->total = 0;
    rp->tree = users;
    g_tree_foreach(commits, conta_bots, rp);
    int totalBots = g_hash_table_size(bots);
    int total = rp->total;
    g_hash_table_destroy(bots);
    g_hash_table_destroy(repos_com_bots);
    return total;
}


//Qual a quantidade média de commits por utilizador?
//ID: 4
//Descrição: (Total de commits) / (Total de utilizadores)
//Output: Média calculada arredondada a duas casas decimais (Avg)

float commitsPerUser(GTree *commitTree, GTree *userTree){
    float media;
    float numOfCommits = (float) g_tree_nnodes(commitTree);
    float numOfUser = (float) g_tree_nnodes(userTree);
    media = numOfCommits/numOfUser;
    return media;
}

/**
###############
Parametrizáveis
###############
**/

//Qual o top N de utilizadores mais ativos num determinado intervalo de datas?
//ID: 5
//Descrição: Top N (indicado por parâmetro) de utilizadores com mais commits num determinado intervalo de datas.
//Input:
//N (nr desejado de users)
//data_inicio (YYYY-MM-DD)
//data_fim (YYYY-MM-DD)
//Output: N linhas em que cada uma indica o id, login e nr. de commits de um utilizador.

struct dataTree{
    GTree *tree;
    struct tm dataINICIO;
    struct tm dataFIM;
};
typedef struct dataTree *DATATREE;


gboolean g_func_StrartDate (gpointer key, gpointer value, gpointer data){
    int* chave = key;
    DATATREE structDate = (DATATREE) data;
    GTree *treeDate = structDate->tree;
    COMMITS structTreeCommits = (COMMITS) value;
    struct tm data_commit = getDate(structTreeCommits);

    struct tm data_inicial = structDate->dataINICIO;
    struct tm data_final = structDate->dataFIM;

    int insert = 0;
    if(data_commit.tm_year > data_inicial.tm_year && data_commit.tm_year < data_final.tm_year){
        insert = 1;
    }
    if(data_commit.tm_year == data_inicial.tm_year || data_commit.tm_year == data_final.tm_year){
        if(data_commit.tm_mon > data_inicial.tm_mon && data_commit.tm_mon < data_final.tm_mon)
            insert = 1;

        if(data_commit.tm_mon == data_inicial.tm_mon && data_commit.tm_mon == data_final.tm_mon){
            if(data_commit.tm_mday > data_inicial.tm_mday && data_commit.tm_mday < data_final.tm_mday)
                insert = 1;

        }

    }

    if(insert == 1)
        g_tree_insert(treeDate, key, structTreeCommits);
    return FALSE;
}

gboolean g_func_findMoreOccur (gpointer key, gpointer value, gpointer data){
    COMMITS structTree = (COMMITS) value;
    GHashTable *occurences = data;
    int* commiter_id = malloc(sizeof(int));
    commiter_id = toIntAsterix(getCommitter(structTree));
    if(g_hash_table_contains(occurences, commiter_id))
    {
        g_hash_table_replace(occurences, commiter_id, g_hash_table_lookup(occurences, commiter_id)+1);
    }
    else g_hash_table_insert(occurences, commiter_id, 1);
    return FALSE;

}

struct commitsFromValues {
    int* user_ids;
    int* num_commits_per_user;
    int valor_encontrar;
    int num_user_ids;
    int max_user_ids;
};

typedef struct commitsFromValues* COMMITSFROMVALUES;

gboolean g_func_UserFromNumCommits (gpointer key, gpointer value, gpointer data){
    COMMITS structTree = (COMMITS) value;
    COMMITSFROMVALUES c = data;
    int occurences = asterixToInt(&data);
    int num_occ = asterixToInt(&value);
    int user_id = asterixToInt(key);

    if(num_occ == c->valor_encontrar)
    {
        if(c->num_user_ids <= c->max_user_ids){
            c->user_ids[c->num_user_ids] = user_id;
            c->num_commits_per_user[c->num_user_ids++] = num_occ;
            printf("%d->%d\n", user_id, num_occ);
        }



        return TRUE;
    }

    return FALSE;

}

char* numOfUsersActive(GTree *treeCommits, GTree *users, int nTop, char* dateStart, char* dateEnd){
    struct tm dataInicio = {0};
    strptime(dateStart, "%Y-%m-%d %H:%M:%S", &dataInicio);
    struct tm dataFim = {0};

    strptime(dateEnd, "%Y-%m-%d %H:%M:%S", &dataFim);
    GTree *dateCommits = g_tree_new(inteiros);
    DATATREE u = malloc(sizeof(struct dataTree));

    dataFim.tm_year = dataFim.tm_year+1900;
    dataInicio.tm_year = dataInicio.tm_year+1900;
    dataFim.tm_mon = dataFim.tm_mon+1;
    dataInicio.tm_mon = dataInicio.tm_mon+1;
    u->tree = dateCommits;
    u->dataINICIO = dataInicio;
    u->dataFIM = dataFim;



    g_tree_foreach(treeCommits, g_func_StrartDate, u);
    int nn = g_tree_nnodes(treeCommits);
    nn = g_tree_nnodes(u->tree);


    GHashTable *occurrencesTable = g_hash_table_new(g_int_hash, inteiros);
    g_tree_foreach(u->tree, g_func_findMoreOccur, occurrencesTable);

    nn = g_hash_table_size(occurrencesTable);

    GList* numeroDeCommits = g_hash_table_get_values(occurrencesTable);
    GList *numeroCommitsSorted = g_list_reverse(g_list_sort(numeroDeCommits, inteiros));
    GList* atual = numeroCommitsSorted;

    COMMITSFROMVALUES c = malloc(sizeof(struct commitsFromValues));
    c->user_ids = malloc(sizeof(int)*nTop);
    c->num_commits_per_user = malloc(sizeof(int)*nTop);
    c->max_user_ids = nTop;
    c->num_user_ids = 0;
    for(int i = 0; i<nTop; i++)
    {
        c->valor_encontrar = asterixToInt(&atual->data);
        g_hash_table_foreach_remove(occurrencesTable, g_func_UserFromNumCommits, c);
        if(c->num_user_ids == nTop)
            i = nTop;
        atual = atual->next;
    }


    char dest[20000] = "";

    for(int i = 0; i<nTop; i++){
        char* id = intToString(c->user_ids[i]);
        strcat(dest, id);
        strcat(dest, ";");
        strcat(dest, getLoginFromID(users, (c->user_ids[i])));
        strcat(dest, ";");
        strcat(dest, intToString(c->num_commits_per_user[i]));
        strcat(dest, "\n");
    }

    return strdup(dest);

}


//Qual o top N de utilizadores com mais commits em repositórios de uma determinada linguagem?
//ID: 6
//Descrição: Top N (indicado por parâmetro) de utilizadores presentes em commits de uma determinada linguagem (indicada por parâmetro)
//Input:
//N (nr desejado de users)
//linguagem (case insensitive)
//Output: N linhas em que cada uma indica o id, login e nr. de commits de um utilizador.

struct repo_language {
    GHashTable *table;
    char* language;
};
typedef struct repo_language* REPO_LANGUAGE;

struct user_hashtable {
    GHashTable *table;
    GHashTable *table2;
    GTree *users;
};
typedef struct user_hashtable* USER_HASHTABLE;


gboolean find_repos_with_language (gpointer key, gpointer value, gpointer data){
    int* chave = key;
    REPO_LANGUAGE info = (REPO_LANGUAGE) data;
    GHashTable *table = info->table;
    REPO repo = (REPO) value;
    char* repoLang = getLanguage(repo);
    toUppercase(repoLang);
    if(strcmp(repoLang, info->language) == 0){
        int repo_id = getReposid(repo);
        g_hash_table_add(table, &repo_id);
    }

    return FALSE;
}

gboolean count_per_user_num_commits_repos (gpointer key, gpointer value, gpointer data){
    int* chave = key;
    USER_HASHTABLE info = (USER_HASHTABLE) data;
    GHashTable *table = info->table;
    GHashTable *table_users_count_commits = info->table2;
    GTree *users = info->users;
    COMMITS commit = (COMMITS) value;

    int userID = (getAuthor(commit));
    int reposID = (getRepos(commit));
    int* USERid = toIntAsterix(userID);
    int* repoID = toIntAsterix(reposID);
    USER user = getUserFromID(users, userID);

    if(g_hash_table_contains(table, repoID)){
        //printf("%s\n", );
        if(g_hash_table_contains(table_users_count_commits, USERid)){
            g_hash_table_replace(table_users_count_commits, USERid, g_hash_table_lookup(table_users_count_commits, USERid)+1);
        }else{
            g_hash_table_insert(table_users_count_commits, USERid, 1);
        }
    }


    return FALSE;
}


char* topNlanguage_language(GTree *users, GTree *commits, GTree *repos, int nTop, char* lang){
    GHashTable *repos_with_language = g_hash_table_new(g_int_hash, inteiros);

    REPO_LANGUAGE u = malloc(sizeof(struct repo_language));
    u->table = repos_with_language;
    u->language = lang;
    g_tree_foreach(repos, find_repos_with_language, u);

    GHashTable *tables_users_commits = g_hash_table_new(g_int_hash, inteiros);
    USER_HASHTABLE ua = malloc(sizeof(struct user_hashtable));
    ua->table = repos_with_language;
    ua->table2 = tables_users_commits;
    ua->users = users;
    g_tree_foreach(commits, count_per_user_num_commits_repos, ua);


    int nn = g_hash_table_size(u->table);
    int nn_table_users_commits = g_hash_table_size(ua->table2);


    GList* numeroDeCommits = g_hash_table_get_values(tables_users_commits);
    GList *numeroCommitsSorted = g_list_reverse(g_list_sort(numeroDeCommits, inteiros));
    GList* atual = numeroCommitsSorted;

    COMMITSFROMVALUES c = malloc(sizeof(struct commitsFromValues));
    c->user_ids = malloc(sizeof(int)*nTop);
    c->num_commits_per_user = malloc(sizeof(int)*nTop);
    c->max_user_ids = nTop;
    c->num_user_ids = 0;
    for(int i = 0; i<nTop && i<nn_table_users_commits; i++)
    {
        c->valor_encontrar = asterixToInt(&atual->data);
        g_hash_table_foreach_remove(tables_users_commits, g_func_UserFromNumCommits, c);
        if(c->num_user_ids == nTop)
            i = nTop;
        atual = atual->next;
    }


    char dest[200000] = "";

    for(int i = 0; i<nTop && i<nn_table_users_commits; i++){
        char* id = intToString(c->user_ids[i]);
        strcat(dest, id);
        strcat(dest, ";");
        strcat(dest, getLoginFromID(users, (c->user_ids[i])));
        strcat(dest, ";");
        strcat(dest, intToString(c->num_commits_per_user[i]));
        strcat(dest, "\n");
    }

    return strdup(dest);

}

//Quais os repositórios inativos a partir de uma determinada data?
//ID: 7
//Descrição: Lista de repositórios sem commits a partir de uma determinada data (indicada por parâmetro).
//Input: data (YYYY-MM-DD)
//Output: Repo1;Descricao1

struct inactiveDates{
    GTree *tree1;
    GTree *tree2;
    GHashTable *table1;
    GHashTable *table2;
    int firstTime;
    struct tm date;
    char* fileName;
    char* buffer;
    int* reposID;
};

struct hasElement {
    int element;
    int has;
};

typedef struct inactiveDates *INACTIVE;
typedef struct hasElement *HAS_ELEMENT;

gboolean adiciona_repos_sem_commmits (gpointer key, gpointer value, gpointer data) {
    INACTIVE structDate = (INACTIVE) data;
    REPO repo = (REPO) value;
    GHashTable* repos_sem_commits = structDate->table1;
    GHashTable* conta_commits = structDate->table2;
    int* repo_ID = toIntAsterix(getReposid(repo));

    if(!g_hash_table_contains(conta_commits, repo_ID))
    {
        g_hash_table_insert(repos_sem_commits, repo_ID, 1);
    }

    return FALSE;

}

gboolean gera_hashtable_repos_c_commits (gpointer key, gpointer value, gpointer data) {
    INACTIVE structDate = (INACTIVE) data;
    COMMITS valor = (COMMITS) value;
    GHashTable* invalidos = structDate->table1;
    GHashTable* conta_commits = structDate->table2;
    int id_repo = getRepos(valor);
    int* id_rePO = toIntAsterix(id_repo);
    struct tm data_commit = getDate(value);
    struct tm data_inicial = structDate->date;

    if(!g_hash_table_contains(invalidos, id_rePO)){
        if(data_commit.tm_year > data_inicial.tm_year || data_commit.tm_year == data_inicial.tm_year && data_commit.tm_mon > data_inicial.tm_mon
           || data_commit.tm_year == data_inicial.tm_year && data_commit.tm_mon == data_inicial.tm_mon && data_commit.tm_mday > data_inicial.tm_mday){

        }else{
            g_hash_table_insert(invalidos, id_rePO, 1);
        }
    }

    if(!g_hash_table_contains(conta_commits, id_rePO)){
        g_hash_table_insert(conta_commits, id_rePO, 1);
    }else{
        g_hash_table_replace(conta_commits, id_rePO, g_hash_table_lookup(conta_commits, id_rePO)+1);
    }

    return FALSE;
}

gboolean escreveParaFicheiro (gpointer key, gpointer value, gpointer data) {
    INACTIVE u = (INACTIVE) data;
    REPO valor = (REPO) value;
    GHashTable* invalidos = u->table1;
    GHashTable* sem_commits = u->table2;

    int* id_repo = key;

    if(g_hash_table_contains(invalidos, id_repo) || g_hash_table_contains(sem_commits, id_repo)){
        char write[400000] = "";
        if(u->firstTime != 1)
            strcat(write, "\n");
        strcat(write, intToString(*id_repo));
        strcat(write, ";");
        strcat(write, getDescription(valor));


        char* copiaBuffer = malloc(sizeof(char)*400000);
        strcpy(copiaBuffer, u->buffer);
        strcat(copiaBuffer, write);
        u->buffer = copiaBuffer;
        if(u->firstTime == 1){
            writeLineToFile(u->fileName, write, 1);
            strcpy(u->buffer, "");
        }
        if(u->firstTime == 200){
            writeLineToFile(u->fileName, u->buffer, u->firstTime);
            u->firstTime = 2;
            strcpy(u->buffer, "");
        }

        u->firstTime++;
    }

    return FALSE;
}


char* inactiveRepos(GTree *commits, GTree *repos, char* dateStart, char* fileName){
    struct tm dataInicio = {0};
    strptime(dateStart, "%Y-%m-%d %H:%M:%S", &dataInicio);
    dataInicio.tm_year = dataInicio.tm_year+1900;
    dataInicio.tm_mon = dataInicio.tm_mon+1;

    GTree *inactiveRepos = g_tree_new(inteiros);
    INACTIVE u = malloc(sizeof(struct inactiveDates));
    GHashTable* tabela_repos_invalidos = g_hash_table_new(g_int_hash, inteiros);
    GHashTable* tabela_conta_commits = g_hash_table_new(g_int_hash, inteiros);
    GHashTable* tabela_repos_sem_commits = g_hash_table_new(g_int_hash, inteiros);

    u->table1 = tabela_repos_invalidos;
    u->table2 = tabela_conta_commits;
    u->date = dataInicio;
    g_tree_foreach(commits, gera_hashtable_repos_c_commits, u);
    u->table1 = tabela_repos_sem_commits;
    g_tree_foreach(repos, adiciona_repos_sem_commmits, u);

    int num_invalidos = g_hash_table_size(tabela_repos_invalidos);
    int num_repos_sem_commits = g_hash_table_size(tabela_repos_sem_commits);
    int num_tem_commits = g_hash_table_size(u->table2);
    //printf("Invalidos=%d, Sem Commits=%d, Num_Com_Commits=%d", num_invalidos, num_repos_sem_commits, num_tem_commits);


    u->table1 = tabela_repos_sem_commits;
    u->table2 = tabela_repos_invalidos;
    u->firstTime = 1;
    u->fileName = fileName;
    u->buffer = malloc(sizeof(char)*400000);
    u->buffer = "";
    g_tree_foreach(repos, escreveParaFicheiro, u);


    g_hash_table_destroy(tabela_repos_invalidos);
    g_hash_table_destroy(tabela_repos_sem_commits);
    g_hash_table_destroy(tabela_conta_commits);

    return "";
}

//Qual o top N de linguagens mais utilizadas a partir de uma determinada data?
//ID: 8
//Descrição: Top N de linguagens presentes com mais frequência em repositórios a partir de uma determinada data.
//Input:
//N (nr desejado de linguagens)
//data (YYYY-MM-DD)
//Output: N linhas em que cada uma apresenta o nome de uma linguagem.

gboolean commitsAfter (gpointer key, gpointer value, gpointer data){
    INACTIVE structDate = (INACTIVE) data;
    COMMITS valor = (COMMITS) value;
    GHashTable* reposAfterDate = structDate->table1;
    GHashTable* langsAfterDate = structDate->table2;
    int id_repo = getRepos(valor);
    int* id_rePO = toIntAsterix(id_repo);
    struct tm data_commit = getDate(value);
    struct tm data_inicial = structDate->date;

    char* commitId, committerId;
    if(data_commit.tm_year > data_inicial.tm_year || data_commit.tm_year == data_inicial.tm_year && data_commit.tm_mon > data_inicial.tm_mon
           || data_commit.tm_year == data_inicial.tm_year && data_commit.tm_mon == data_inicial.tm_mon && data_commit.tm_mday > data_inicial.tm_mday){

    }
    else{
        if(!g_hash_table_contains(reposAfterDate, id_rePO)){
            g_hash_table_insert(reposAfterDate, id_rePO, 1);
            }
        else{
            g_hash_table_replace(reposAfterDate, id_rePO, g_hash_table_lookup(reposAfterDate, id_rePO)+1);
        } 
    }
}

gboolean parseLang (gpointer key, gpointer value, gpointer data){
    int *v = data;
    int valor = *v;
    INACTIVE structDate = (INACTIVE) data;
    int* reposID = structDate->reposID;
    int id = *reposID;

    if(valor == id){
        
    }

}

char *topNlanguage_afterTime (GTree *repos, GTree *commits, char *date, int N){
    struct tm dataInicio = {0};
    strptime(date, "%Y-%m-%d %H:%M:%S", &dataInicio);
    dataInicio.tm_year = dataInicio.tm_year+1900;
    dataInicio.tm_mon = dataInicio.tm_mon+1;

    INACTIVE u = malloc(sizeof(struct inactiveDates));
    GHashTable *reposAfterDate = g_hash_table_new(g_int_hash, inteiros);
    GHashTable *langsAfterDate = g_hash_table_new(g_int_hash, inteiros);

    u->table1 = reposAfterDate;
    u->table2 = langsAfterDate;
    u->date = dataInicio;
    g_tree_foreach(commits, commitsAfter, u);

    GList* numeroDeCommits = g_hash_table_get_values(reposAfterDate);
    GList *numeroCommitsSorted = g_list_reverse(g_list_sort(numeroDeCommits, inteiros));
    GList* atual = numeroCommitsSorted;


    for(int i = 0; i<N; i++){
        u->reposID = atual->data;
        g_tree_foreach(repos, parseLang, u);
        atual = atual->next;
    }
}

//Qual o top N de utilizadores com mais commits em repositórios cujo owner é um amigo seu?
//ID: 9
//Descrição: Top N (indicado por parâmetro) de utilizadores com mais commits em repositórios cujo owner está contido na sua lista de followers e following.
//Input: N (nr desejado de users)
//Output: ID1;Login1


//Qual o top N de utilizadores com as maiores mensagens de commit por repositório?
//ID: 10
//Descrição: Top N de utilizadores, ordenados pelo tamanho da maior mensagem de commit, em cada repositório.
//Input: N (nr desejado de users)
//Output: ID1;Login1;Commit_msg_size1