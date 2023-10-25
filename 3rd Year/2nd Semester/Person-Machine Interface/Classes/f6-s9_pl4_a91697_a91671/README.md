[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-24ddc0f5d75046c5622901739e7c5dd533143b0c8e959d652212380cedb1ea36.svg)](https://classroom.github.com/a/K8jq4QGU)
# Semana 09 - Aula PL

## turno:  PL 4

## grupo 

|Número |             Nome             |
|-------|------------------------------| 
| 91697 | Luís Filipe Fernandes Vilas  |
| 91671 | João Manuel Novais da Silva  |

## Resolução dos Exercícios (ficha 06):

[Enunciado da Ficha 06](Ficha_IPM_06_enunciado.pdf)

Material de apoio para a resolução desta ficha em [Aulas &gt; Semana 09](https://elearning.uminho.pt/webapps/blackboard/content/listContentEditable.jsp?content_id=_1336495_1&course_id=_55891_1)

### Exercício 1

Considere o protótipo [Fly2023](https://www.di.uminho.pt/~jfc/ensino/fly2023/) de  gestão de sumários.

Realize uma análise com _Cognitive Walkthrough_ para a tarefa de Editar um Sumário e, com base
nos resultados da análise, apresente uma proposta de melhoria da interface.
Considere que o utilizador é um docente universitário.

    Editar Sumários de 15/03 e 17/05:*
      Ponto de partida: Menu principal da aplicação
      Sequência de passos da tarefa a testar*
          (a) Selecionar Edição de sumários: Clicar Sumários>Editar
          (b) Seleccionar sumário de 15/03: Clicar checkbox respectiva
          (c) Seleccionar sumário de 17/05: Clicar checkbox respectiva
          (d) Ir para formulário de edição: Fazer scroll down para tornar visível o botão “Editar” e clicar o botão
          (e) Alterar data e texto do primeiro sumário: Fazer scroll down [dados já alterados no protótipo]
          (f) Actualizar sumário: Clicar botão “Actualizar”
          (g) Alterar data e texto do segundo sumário: Fazer scroll down [dados já alterados no protótipo]
          (h) Actualizar sumário: Clicar botão “Actualizar” Protótipo de um sistema de gestão de sumários.

Formulário para _Cognitive Walkthrough_
   [ [CW.xlsx](https://elearning.uminho.pt/bbcswebdav/pid-1336503-dt-content-rid-6828666_1/xid-6828666_1) ]  [ [CW.pdf](https://elearning.uminho.pt/bbcswebdav/pid-1336503-dt-content-rid-6828665_1/xid-6828665_1)  ]

**Resposta:**

Análise com _Cognitive Walkthrough_ para a tarefa de Editar um Sumário: [CW_Ex01.pdf](CW_Ex01.pdf)

Resumo da proposta de melhoria da interface:

- Fazer alterações gráficas entre menus para poderem ser mais facilmente destintos
- Emitir mensagens do sistema para o utilizador quando, por exemplo, é feita a atualização dum sumário
- Mudar as posições dos botões necessário para a utilização da aplicação, fazendo com que estes estajam visíveis

### Exercício 2

Protótipo criado pelos colegas:

- [Protótipo](Protótipo_enviado.pdf) do protótipo

# Passos

- Carregar um novo jogo 
- Selecionar um campeonato da listagem de campeonatos
- Inserir número de jogadores
- Inserir nome e morada dos jogadores, respetivamente
- No menu jogada, apresentado automaticamente, selecionar botão de apostar no campeonato
- Escolher e selecionar o tipo da aposta: Apostar que um carro chega ao pódio ou que um carro termina em 1º lugar
- Escolher e inserir o id do carro, da lista apresentada, em que vamos apostar
- Colocar o valor que prentedemos apostar


**Resposta:**

Análise com _Cognitive Walkthrough_ para a tarefa *Jogar o Campeonato*: [Documento](CW_enviado.pdf)

Resumo da proposta de melhoria da interface:

- Mudar o menu de seleção de jogadores, nomeadamente os labels dos botões, pois pode ser percebido que é necessário selecionar os jogadores e depois clicar em 'Adicionar Jogador' para adicionar ao campeonato
- Juntar as ações dos botões das apostas num só, pois existem redundâncias nestes, o menu 'Apostas em Jogo' leva à apresentação das apostas e depois pode levar ao mesmo menu que o botão 'Colocar Aposta' apresenta.
