# Semana 04 - Aula PL
## turno:  PL 4

## grupo 
|Número |             Nome             |
|-------|------------------------------| 
| 91697 | Luís Filipe Fernandes Vilas  |
| 91671 | João Manuel Novais da Silva  |
| 91660 | Pedro António Sequeira       |


## Exercícios da aula (ficha 02):

### Exercício 1 

**Perfis do Administrador – checklist**
- Informação sobre o Administrador
  - Grupo etário: > 18 anos
  - Formação académica: Licenciatura em Engenharia Informática ou equivalente
  - Competências: Conhecimento de Java, Bases de Dados, Computadores
  - Tipo de utilizador/Experiência: Bem familiarizado com a aplicação
- Utilização do sistema 
  - Opcional ou obrigatória: Obrigatória para a criação de corridas, carros e pilotos.
- Informação sobre trabalho
  - Classe de utilizador: Administrador do Sistema
  - Descrição do trabalho: Criador ou contratado pelo criador do sistema para administrar as corridas,
 circuitos, carros, pilotos e manter a aplicação sem problemas técnicos
  - Tarefas principais: Gerir corridas, gerir circuitos, gerir carros, gerir pilotos e manter a boa
  funcionalidade da aplicação
  - Responsabilidades: Manter a aplicação funcional em termos técnicos e aplicacionais

**Perfis do Utilizador – checklist**
- Informação sobre o Utilizador
  - Grupo etário: > 9 anos
  - Formação académica: Sem requesitos
  - Competências: Saber utilizar o computador
  - Tipo de utilizador/Experiência: Utilizador ocasional e iniciante do sistema
- Utilização do sistema 
  - Opcional ou obrigatória: Opcional para fins de lazer.
- Informação sobre trabalho
  - Classe de utilizador: Utilizador/Jogador da aplicação.
  - Descrição do trabalho: Para fins de lazer o utilizador pode criar campeonatos, ver rankings, 
  escolher pilotos e carros, convidar jogadores.
  - Tarefas principais: Selecionar opções válidas, simular campeonatos e corridas.
  - Responsabilidades: Não piratiar.

**Perfis do Convidado – checklist**
- Informação sobre o Convidado
  - Grupo etário: > 9 anos
  - Formação académica: Sem requesitos
  - Competências: Saber utilizar o computador
  - Tipo de utilizador/Experiência: Utilizador ocasional e iniciante do sistema
- Utilização do sistema 
  - Opcional ou obrigatória: Opcional para fins de lazer.
- Informação sobre trabalho
  - Classe de utilizador: Utilizador/Jogador com restrições.
  - Descrição do trabalho: Para fins de lazer o convidado apenas pode participar em corridas 
  para as quais é convidado.
  - Tarefas principais: Juntar-se a corridas.
  - Responsabilidades: Não piratiar.

### Exercício 2

## a)

0. Receitar medicamento
  1. Autenticar médico
  2. Criação da Receita\
    2.1. Identificar o Doente pelo Nº SNS [U]\
    2.2. Identificar o Médico pelo Nº SNS [U]
  3. Adicionar medicamento até 3 medicamentos [U]\
    3.1. Identificar o medicamento [U]\
    3.2. Verificar se o medicamento pertence à lista de medicamentos do SNS [U]\
    3.3. Verificar se o medicamento é psicotrópico [U]\
        3.3.1. Caso seja, remover todos os medicamentos de outros tipos [S]\
    3.4. Inserir principio ativo [U]\
    3.5. Inserir quantidade [U]\
    3.6. Inserir posologia [U]
  4. Termino da receita [S]

Plano 0: fazer 1 - 2 - 3 por essa ordem.\
Plano 2: fazer 2.1 - 2.2 por qualquer ordem.\
Plano 3: fazer 3.1 - 3.2 - 3.3 por essa ordem e fazer 3.4 - 3.5 - 3.6 por qualquer ordem.\
Plano 3: caso haja mais de 3 medicamentos receitados, fazer 4.

## b)

Neste cenário o médico gera uma lista de vários medicamentos e insere-o no sistema, este vai agora agrupar os medicamentos
em grupos de 3, sendo que cada grupo poderá ser introduzido numa receita. Após serem gerados estes conjuntos o médico poderá
escolher qual dos conjuntos acha melhor para o caso que está a tratar.

A resposta a este exercício poderá ser enviada em modo texto (estruturado) ou em notação gráfica. No caso da notação gráfica, pode utilizar um SmartArt hierárquico para desenhar os modelos de tarefas em Word (ou outro editor de texto que suporte essa funcionalidade). Nesse caso, envie imagens com o resultado de cada modelo e coloque links para as mesmas neste ficheiro seguindo o exemplo: \
![UMinho](UMinho-50-02.png "Universidade do Minho")


### Exercício 3

## a)

0. Jogar o Racing Manager
    1. Autenticação do jogador
    2. Escolha do campeonato pelo jogador\
        2.1 São apresentadas todas as corridas do campeonato\
    3. Escolha de um carro e eum piloto pelo jogador
    4. O jogador convida o pessoas para jogar\
        4.1 Os convidados escolhem os seus carros\
    5. É inicializada a simulação do campeonato\
        5.1 São aprensetados o clima da corrida\
        5.2 É inicializada a simulação da corrida\
            5.2.1 Em todos os terrenos é calculado as possíveis ultrapassagens\
            5.2.2 Em todas as voltas é atualizada a tabela de liderança desta
        5.3 No final da corrida é atualizada a tabela de liderança do campeonato\
    6. No final do campeonato são anunciados os vencedores deste

Plano 0: fazer 1 - 2 - 3, por esssa ordem.\
Plano 3: fazer 3 - 5, caso o jogador queira jogar sozinho.\
Plano 5: fazer 5.1 - 5.2 - 5.3 por ordem.
      