gcc -c src/main.c src/interface.c src/board.c src/logic.c src/linkedlist.c src/bots.c -g
gcc -o final/rastos main.o interface.o board.o logic.o linkedlist.o bots.o -lm
rm *.o 