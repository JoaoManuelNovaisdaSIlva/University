'''
 OS IDS DEVEM SER NUMEROS CRESCENTES DE 0 ATE N(NÚMERO MÁXIMO DE CÉLULAS) ASSIM:
    0  1  2  3  4  5  6  7  8  9
    10 11 12 13 14 15 16 17 18 19
    20 21 22 23 24 25 26 27 28 29
    30 31 32 33 34 35 36 37 38 39
    40 41 42 43 44 45 46 47 48 49
'''
from Graph import Graph
from Nodo import Node


class Circuito:

    def __int__(self, circuito):
        self.circuito = str(circuito)  # path para o ficheiro de texto

    def text_to_list(self, circuito):
        with open(circuito) as f:
            lines = f.readlines()
        game_map = []

        for line in lines:
            line = line.rstrip()
            game_map.append(list(line))

        start = [] # lista que guarda os pontos de partida, para teste
        finish = [] # lista que guarda os pontos de chegada, para teste

        for y, row in enumerate(game_map):
            for x, c in enumerate(row):
                if c == 'P':
                    start.append((x, y))
                elif c == 'F':
                    finish.append((x, y))

        return game_map, start, finish

    def list_to_graph(self, game_map):
        # Por alguma razão, ao apenas fazer grap=Graph(), não é criado o objeto com todos as variávies
        graph = Graph()
        graph.m_nodes = {}  # dicionário de nodos do grafo, em que o id serve como key e o objeto como value
        graph.m_directed = False  # se o grado é direcionado ou não
        graph.m_graph = {}  # dicionário para armazenar os nodos, arestas e pesos
        graph.m_h = {}  # dicionário para armazenar heuristica para cada nodo

        graph.m_largura = len(game_map[0])
        graph.m_altura = len(game_map)
        id = 0
        for y in range(graph.m_altura):
            for x in range(graph.m_largura):
                if game_map[y][x] == "-":
                    node = Node("CAMINHO", id, x, y)
                    id += 1
                    graph.m_nodes[node.getId()] = node
                    graph.m_graph[node] = []

                elif game_map[y][x] == "X":
                    node = Node("PAREDE", id, x, y)
                    id += 1
                    graph.m_nodes[node.getId()] = node
                    graph.m_graph[node] = []

                elif game_map[y][x] == "P":
                    node = Node("PARTIDA", id, x, y)
                    id += 1
                    graph.m_nodes[node.getId()] = node
                    graph.m_graph[node] = []

                elif game_map[y][x] == "F":
                    node = Node("FIM", id, x, y)
                    id += 1
                    graph.m_nodes[node.getId()] = node
                    graph.m_graph[node] = []

                else:
                    print("Erro ao ler o ficheiro!")
        return graph

    def populate_graph(self, grafo):
        for ids in grafo.m_nodes:
            l = grafo.getNeighbours_from_nodes(ids)
            for acc in l:
                ob = grafo.m_nodes[acc.getId()]
                if ob.m_type == "PAREDE":
                    grafo.m_graph[grafo.get_node_by_id(ids)].append((ob, 25))
                elif ob.m_type == "CAMINHO":
                    grafo.m_graph[grafo.get_node_by_id(ids)].append((ob, 1))
                elif ob.m_type == "PARTIDA":
                    grafo.m_graph[grafo.get_node_by_id(ids)].append((ob, 0))
                elif ob.m_type == "FIM":
                    grafo.m_graph[grafo.get_node_by_id(ids)].append((ob, 0))
                else:
                    print("Erro ao inserir no dicionario graph!")

        return grafo

