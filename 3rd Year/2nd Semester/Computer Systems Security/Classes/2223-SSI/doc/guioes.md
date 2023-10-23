
## Guião 3

### Protocolo *Diffie-Hellman*

Relembre o protocolo de acordo de chaves _Diffie\_Hellman_:

 1. Alice → Bob : g<sup>x</sup>
 1. Bob → Alice : g<sup>y</sup>
 1. Alice, Bob : Kmaster = g<sup>(x*y)</sup>

Onde `g` é um gerador de um grupo cíclico de ordem prima `p`, `x` e `y` são elementos aleatórios do grupo, e `Kmaster` é o segredo estabelecido pelo protocolo. Todas as operaçes são realizadas módulo `p`. Note que os segredos criptográficos requeridos devem ser derivados a partir de `Kmaster` (e.g. usando uma KDF como [`HKDF`](https://cryptography.io/en/latest/hazmat/primitives/key-derivation-functions/#hkdf).

Pretende-se implementar o protocolo de acordo de chaves *Diffie-Hellman* fazendo uso da funcionalidade oferecida pela biblioteca `cryptography`. Em concreto, utilizando a classe [`dh`](https://cryptography.io/en/latest/hazmat/primitives/asymmetric/dh/).

Algumas observações:
 * Se pretender, pode fixar os parâmetros do grupo utilizando por exemplo:
 ```
P = 99494096650139337106186933977618513974146274831566768179581759037259788798151499814653951492724365471316253651463342255785311748602922458795201382445323499931625451272600173180136123245441204133515800495917242011863558721723303661523372572477211620144038809673692512025566673746993593384600667047373692203583
G = 44157404837960328768872680677686802650999163226766694797650810379076416463147265401084491113667624054557335394761604876882446924929840681990106974314935015501571333024773172440352475358750668213444607353872754650805031912866692119819377041901642732455911509867728218394542745330014071040326856846990119719675
```
 * A documentação da biblioteca não é muito clara na forma como se pode comunicar as chaves públicas DH, tal como requerido pelo protocolo. Na prática, existem duas alternativas:
     - Aceder ao valor (inteiro) da chave pública através da classe `DHPublicNumbers`
     - Utilizar as facilidades de serialização da chave pública oferecidas pela biblioteca (acessível a partir do método `public_bytes` da classe `DHPublicKey`). Nesse caso devem fazer o _parsing_ na recepção recorrendo à função [load_der_public_key](https://cryptography.io/en/latest/hazmat/primitives/asymmetric/serialization/#cryptography.hazmat.primitives.serialization.load_der_public_key) ou [load_pem_public_key](https://cryptography.io/en/latest/hazmat/primitives/asymmetric/serialization/#cryptography.hazmat.primitives.serialization.load_pem_public_key), dependendo do _encoding_ utilizado. 

### Protocolo *Station-to-Station* simplificado

Pretende-se complementar o programa com o acordo de chaves *Diffie-Hellman* para incluir a funcionalidade
análoga à do protocolo *Station-to-Station*. Recorde que nesse protocolo é adicionado uma troca de assinaturas:

1. Alice → Bob : g<sup>x</sup>
1. Bob → Alice : g<sup>y</sup>, Sig<sub>B</sub>(g<sup>x</sup>, g<sup>y</sup>)
1. Alice → Bob :  Sig<sub>A</sub>(g<sup>x</sup>, g<sup>y</sup>)
1. Alice, Bob : K = g<sup>(x*y)</sup>

De notar que um requisito adicional neste protocolo é a manipulação de pares de chaves assimétricas para realizar as assinaturas digitais (e.g. RSA [Extra: utilizar uma assinatura baseada em curvas elípticas]). Para tal deve produzir um pequeno programa que gere os pares de chaves para cada um dos intervenientes e os guarde em ficheiros que serão lidos pela aplicação Cliente/Servidor.

Sugestão: comece por isolar as "novidades" requeridas pelo guião, nomeadamente:
  * criação do par de chaves para a assinatura e utilização dos métodos para ''assinar'' e ''verificar''

  * gravar as chaves públicas/privadas em ficheiro 
  
  * integrar as assinaturas no protocolo _Diffie-Hellman_


---
## Guião 2


### Comunicação segura entre cliente-servidor

As scripts [Client.py](scripts/Client.py) e
[Server.py](scripts/Server.py) constituem uma implementação elementar
de uma aplicação que permite a um número arbitrário de
clientes comunicar com um servidor que escuta num dado port
(e.g. 7777). O servidor atribui um número de ordem a cada cliente, e
simplesmente faz o _dump_ do texto enviado por eese cliente
(prefixando cada linha com o respectivo número de ordem). Quando um
cliente fecha a ligação, o servidor assinala o facto (e.g. imprimindo
[n], onde _n_ é o número do cliente).

Exemplo da execução do servidor (que comunica com 3 clientes):


```bash
$ python3 Servidor.py
1 : daskj djdhs slfghfjs askj
1 : asdkdh fdhss
1 : sjd
2 : iidhs
2 : asdjhf sdga
2 : sadjjd d dhhsj
3 : djsh
1 : sh dh d   d
3 : jdhd kasjdh as
2 : dsaj dasjh
3 : asdj dhdhsjsh
[3]
2 : sjdh
1 : dhgd ss
[1]
2 : djdj
[2]
```

Pretende-se:

 * Modificar as respectivas classes por forma a garantir a
   _confidencialidade_ e _integridade_ nas comunicações
   estabelecidas. Para tal deve recorrer a uma cifra por blocos num modo apropriado (e.g. GCM ou CCM).
 * Para já, pode desvalorizar as questões relativas
   à protecção da(s) chave(s) criptográficas requeridas (e.g. pode até considerar
   chaves fixas no próprio código). Nos guiões seguintes iremos abordar esses problemas.
 * obs: nas implementações fornecidas das classes `Client` e
   `ServerWorker`, não deverá ser necessário "mexer" muito para além
   do método `process`.

---

## Guião 1
### Programas simples em *Python*


Para exemplificar a utilização do `Python`, ilustra-se a sua utilização para codificar algumas das cifras clássicas mencionadas na aula introdutória do curso, nomeadamente:

#### **Algumas funções de suporte...**

Nas cifras clássicas, é normal usar-se um alfabeto restrito (e.g. só maiúsculas, sem acentuação, etc.). Para tal, o tb. como forma de ilustrar a utilização de algumas bibliotecas standard do Python, apresenta-se o código da função `purify` responsável por pré-processar um texto para depois aplicar a operação de cifra sobre o alfabeto restrito. Faz-se uso de alguns métodos de manipulação de *strings* ([upper](https://docs.python.org/3/library/stdtypes.html?highlight=maketrans#str.upper), [maketrans](https://docs.python.org/3/library/stdtypes.html?highlight=maketrans#str.maketrans), e [translate](https://docs.python.org/3/library/stdtypes.html?highlight=maketrans#str.translate)), e de expressões regulares ([re](https://docs.python.org/3/library/re.html)).

```
import re, string, random

def purify(txt):
    """ converte texto em maiúsculas """
	# tradução que remove caracteres acentuados
	tmap = txt.maketrans("ÇÁÀÃÂÉÈÊÍÌÎÓÒÔÕÚÙÛ","CAAAAEEEIIIOOOOUUU")
    # expressão regular responsável por filtrar tudo menos maiúsculas
	pattern = re.compile('[^A-Z]+')
    return pattern.sub('', txt.upper().translate(tmap))

txtA = """
As armas e os barões assinalados,
Que da ocidental praia Lusitana,
Por mares nunca de antes navegados,
Passaram ainda além da Taprobana,
Em perigos e guerras esforçados,
Mais do que prometia a força humana,
E entre gente remota edificaram
Novo Reino, que tanto sublimaram;
"""

txt2 = purify(txtA)
```

#### **Cifra de César**
Escreva um programa em `Python`, com o nome
`cesar.py` que receba 3 argumentos:
 * o tipo de operação a realizar: `enc` ou `dec`
 * a chave secreta: `A`, `B`, ..., `Z`
 * a mensagem a cifrar, por exemplo "Cartago esta no papo".

Apresenta-se de seguida um exemplo de utilização para este programa, através do terminal:
```
$ python cesar.py enc G "CartagoEstaNoPapo"
IGXZGMUKYZGTUVGVU
$ python cesar.py dec G "IGXZGMUKYZGTUVGVU"
CARTAGOESTANOPAPO
```

**Nota:** Para converter a letra da chave na sua posição do alfabeto, podem usar a função [ord](https://docs.python.org/3/library/functions.html?highlight=ord#ord) que recebe um carater e dá o seu valor ASCII. Depois podem converter esse valor ASCII para a posição dessa letra no alfabeto ao subtrair o ASCII da primeira letra (atenção que maiúsuclas e minúsculas têm valores ASCII diferentes). A função [chr](https://docs.python.org/3/library/functions.html?highlight=ord#chr) faz exatamente o oposto.

### Cripto-análise da cifra de César

Mostre, com auxílio de funções *Python* apropriadas, como quebrar o criptograma `PEVCGBTENSVNRFRTHENAPNQRERQRF`. Qual foi a chave utilizada para produzir este criptograma?

#### **Cifra de Vigenère**
Crie um novo programa `Python`, com o nome
`vigenere.py` que se comporta de forma similar ao programa anterior. Utilize o programa `cesar.py` como
base. Exemplo:
```
python vigenere.py enc BACO "CifraIndecifravel"
DIHFBIPRFCKTSAXSM
$ python vigenere.py dec BACO "DIHFBIPRFCKTSAXSM"
CIFRAINDECIFRAVEL
```

#### (EXTRA:) Cripto-análise da cifra de Vigenère

Pretende-se realizar parte do processo de cripto-análise da cifra de Vigenère. Para tal vamos omitir a primeira parte cujo objectivo é descobrir tamanhos prováveis da chave. No criptograma que se segue, sabe-se que o tamanho da chave utilizada foi *5*. Desenvolva funções em *Python* que o auxiliem a descobrir a chave utilizada para cifrar o seguinte criptograma:

"""
TJIXATJMUGURZUSLRAYWGRTGRHJYASWRWIWWVVZOEGZGWTCCYWMRVGDHIUGFXJVABVRLKOGKMYBTMMMO
WFAVOLJIXOFRQTRTRTKAWRBGDKFJGBTVUVSKZOUGXXCKFKRAKGYFZIOWFASOBJLUENVXXCFVBOOTWWXQ
TYCSOGRMKBMIMMSGKMXSFFBGSWZNOQTIISBHMWXSBEWWIXKITHHJCHZBDIXOFTMYGXDLUGTSQUUKVOUS
WFBXCBRVUOLEIBSZRKUSLXZGBWVAWIXWQFSKRUIOEVAKRXRTKLTELXCXUMZFTAITCTWISOWRABWMFZOO
LHCKHBMMXOFHCKSNTITHHFXKWMFQRILKZKZNJQZOGFIWIXDVKDMLVUSFRZZSHSMJSVVZGAVVAYSMLLUC
JLMGANJIGBMZOAOVRVZOJLMUIMIWBOEFZSOBJIRHHJMGZXMITHT
"""

#### **One Time Pad**
Considere a cifra One Time Pad apresentada na última aula. Crie um novo programa `Python`, com o nome
`otp.py` que se comporta da seguinte forma:
 * caso o primeiro argumento do program seja `setup` o segundo argumento será o número de bytes aleatórios a gerar e o terceiro o nome do ficheiro para se escrever os bytes gerados. O conteúdo do ficheiro deverá ser gerado com recurso à função `os.urandom` e escrito como `base64` ( https://docs.python.org/3/library/base64.html ). Leia a documentação da função em https://docs.python.org/3/library/os.html#os.urandom e atente à razão pela qual é necessário tomar cuidado com as funções utilizadas para gerar números aletórios no contexto da criptografia. Deixe uma nota no ficheiro README.md da pasta di guião que esclareça o porquê. Tome o seguinte excerto de código como exemplo:
```
random_bytes = os.urandom(number_of_bytes)
file.write(base64.b64encode(random_bytes))
```
 * caso o primeiro argumento seja `enc` então o segundo será a mensagem a cifrar e o terceiro o nome do ficheiro que contém a chave one-time-pad: deverá ser imprimida a mensagem em `base64`.
 * caso o primeiro argumento seja `dec` então o segundo será a mensagem a decifrar codificada em `base64`. O terceiro argumento mantém-se como o nome do ficheiro.

Exemplo:
```
$ python otp.py setup 20 otpkey.txt
$ cat otpkey.txt
T9xmDax00GP4mak4O5MjEmVCa4w=
$ python otp.py enc "mensagem a cifrar" otpkey.txt
b'IrkIfs0TtQ7Y+IlbUvVRcxc='
$ python otp.py dec "IrkIfs0TtQ7Y+IlbUvVRcxc=" otpkey.txt
b'mensagem a cifrar'
```
Dica1: pode começar por gravar `T9xmDax00GP4mak4O5MjEmVCa4w=` num ficheiro e tentar replicar as operações de cifra e decifra numa primeira fase.
Dica2: para ler do ficheiro poderá utilizar o seguinte excerto de código:
```
f = open(filename, "rb")
r = base64.b64decode(f.read())
f.close()
```
---

## Guião 0
### O Ambiente de Desenvolvimento

Esta primeira aula pretende essencialmente garantir uma utilização fluida do ambiente de trabalho adoptado
na UC. Isso pressupõe a utilização do `github` (em particular, do repositório atribuído ao grupo de trabalho), e
do ambiente `Python` (versão 3). Atente, em particular, às 
instruções presentes na secção ["ORGANIZAÇÃO DO REPOSITÓRIO"](#organização-do-repositório).

Nota: Sempre que solicitado, preserve o nome do programa e a ordem dos argumentos. Os programas submetidos serão testados automáticamente numa primeira fase de avaliação do Guião.



### Instalação de bibliotecas de suporte

#### Cryptography - https://cryptography.io/en/latest/

A biblioteca criptográfica que iremos usar maioritariamente no curso é `cryptography`. Trata-se de uma biblioteca
para a linguagem Python bem desenhada e bem documentada que oferece uma API de alto nível a diferentes
“Serviços Criptográficos” (_recipes_). No entanto, e no âmbito concreto deste curso, iremos fazer um uso
"menos standard" dessa biblioteca, na medida em que iremos recorrer directamente à funcionalidade de baixo nível.

Instalação:

Sugere-se o método de instalação baseado no `pip` (ver https://cryptography.io/en/latest/installation/).

```
pip3 install --upgrade pip
pip3 install cryptography
```

### Programa de Exemplo

Para testar o ambiente de desenvolvimento e a instalação do `Python`, escreva um pequeno programa `wc.py` que emule o comando `wc` do *Unix*, que conta o número de linhas, palavras e caracteres do `stdin`. Exemplo:

```
$ python wc.py < exemplo.txt
     580    3518   21268
```

---

## ORGANIZAÇÃO DO REPOSITÓRIO

### Arrumação do repositório

Por forma a permitir um acesso ao repositório mais efectivo, devem proceder à seguinte organização de directorias:

```
+-- Readme.md: ficheiro contendo: (i) composição do grupo (número, nome e login github de cada
|              membro); (ii) aspectos que entenderem relevante salientar (e.g. dar nota de
|              algum guião que tenha ficado por realizar ou incompleto; um ou outro guião
|              que tenha sido realizado apenas por um dos membros do grupo; etc.)
+-- Guioes
|        |
|        +-- G1
|        |    +-- Readme.md: indicação do aluno responsável pela apresentação do guião; 
|        |    |              notas sobre a realização (justificação das opções
|        |    |              tomadas; instruções de uso; dificuldades encontradas; etc.)
|        |    +-- ...
|        |
|        +-- G2
|        |   ...
...      ...
|
+-- Projs
|       |
|       +-- A88888: projeecto individual do aluno ...
|       ...
|
...
```


---

