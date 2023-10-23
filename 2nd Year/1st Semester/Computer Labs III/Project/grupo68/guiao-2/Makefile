CC=gcc
CFLAGS=-Wall -O2 `pkg-config --cflags glib-2.0`


BIN     := .
SRC     := src
INCLUDE := .

LIBRARIES   := `pkg-config --libs glib-2.0`
EXECUTABLE  := guiao-2


all: $(BIN)/$(EXECUTABLE)

run: clean all
	clear
	./$(EXECUTABLE)

$(BIN)/$(EXECUTABLE): $(SRC)/*.c
	$(CC) $(CFLAGS) -lm  $^ -o $@ $(LIBRARIES)

clean:
	-rm ./$(EXECUTABLE)
