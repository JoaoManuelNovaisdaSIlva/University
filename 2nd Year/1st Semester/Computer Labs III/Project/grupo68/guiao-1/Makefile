CC=gcc
CFLAGS=-Wall -O2


BIN     := .
SRC     := src
INCLUDE := .

LIBRARIES   :=
EXECUTABLE  := guiao-1


all: $(BIN)/$(EXECUTABLE)

run: clean all
	clear
	./$(BIN)/$(EXECUTABLE)

$(BIN)/$(EXECUTABLE): $(SRC)/*.c
	$(CC) $(CFLAGS) -I $(INCLUDE) $^ -o $@ $(LIBRARIES)

clean:
	-rm $(INCLUDE)/$(EXECUTABLE)
