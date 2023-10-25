No contexto da criptografia é necessário e importante gerar números realmente aleatórios para podermos defender os segredos de um possível atacante ou de qualquer outra entidade que tenha acesso à mensagem encriptada.

O principal problema que enfrentamos na implementação de sistemas criptógrafos é a, infeliz, realidade de que não é possível gerar números realmente aleatórios devido às limitações físicas das máquinas. Os nossos sistemas apenas usam algoritmos que produzem números aparentemente aleatórios, mas a realidade é que estes seguem um número de regras e se os atacantes saberem como funciona a implementação destes algoritmos então conseguem prever os números aleatórios.

Por exemplo, neste caso, a função os.urandom() gera os números de maneiras diferentes dependendo do sistema operativo em que é usada.

    . Para linux usa getrandom(), se este estiver disponível, em modo de bloqueio até que o conjunto de entropia seja inicializada
    . Para sistemas derivados de Unix, bytes aleatórios são lidos do dispositivo /dev/urandom
    . Para sistemas de Windows será usado a função BCryptGenRandom()

Por isso, tem de haver cuidado na utilização desta função (os.urandom()) porque esta tem comportamentos diferentes em máquinas diferentes e daí pode ser descoberta uma vulnerabilidade num dos casos, sendo que assim, apenas alguns dos utilizadores serão afetados por esta quebra. 
