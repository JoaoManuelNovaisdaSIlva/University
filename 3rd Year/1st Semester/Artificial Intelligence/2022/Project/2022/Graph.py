import math
import queue
from queue import PriorityQueue
import matplotlib.pyplot as plt  # idem

import networkx as nx

from Nodo import Node

from queue import Queue


class Graph:

    def __int__(self, directed=False):
        self.m_nodes = {}  # dicionário de nodos do grafo, em que o id serve como key e o objeto como value
        self.m_directed = directed  # se o grado é direcionado ou não
        self.m_graph = {}  # dicionário para armazenar os nodos, arestas e pesos
        self.m_h = {}  # dicionário para armazenar heuristica para cada nodo
        self.m_largura = 0  # largura do mapa
        self.m_altura = 0  # altura do mapa

    def __str__(self):
        out = ""
        for key in self.m_graph.keys():
            out = out + "node " + str(key) + ": " + str(self.m_graph[key]) + "\n"
        return out

    def add_edge(self, node1, node2, weight):  # node1 e node2 são objetos
        n1 = node1.getId
        n2 = node2.getId
        if n1 not in self.m_nodes.keys():
            self.m_nodes[n1] = node1  # adicionar novo objeto (nodo), ao dicionário com id como key
            self.m_graph[node1] = set()
        else:
            node1 = self.get_node_by_id(n1)

        if n2 not in self.m_nodes:
            self.m_nodes[n2] = node2
            self.m_graph[node2] = set()
        else:
            node2 = self.get_node_by_id(n2)

        self.m_graph[n1].add((node2, weight))

        # se o grafo for nao direcionado, colocar a aresta inversa
        # É PRECISO DISTO??
        if not self.m_directed:
            self.m_graph[node2].add((node1, weight))

    def get_node_by_id(self, id):
        return self.m_nodes.get(id)  # para devolver None caso não seja encontrada a key

    def imprime_aresta(self):
        listaA = ""
        lista = self.m_graph.keys()
        for nodo in lista:
            for (nodo2, custo) in self.m_graph[nodo]:
                listaA = listaA + nodo + " ->" + nodo2 + " custo:" + str(custo) + "\n"
        return listaA

    def getNodes(self):
        return self.m_nodes

    def get_arc_cost(self, node1, node2):
        custoT = math.inf
        a = self.m_graph[node1]  # lista de arestas para aquele nodo
        for (nodo, custo) in a:
            if nodo == node2:
                custoT = custo

        return custoT

    def path_cost(self, caminho):
        # caminho é uma lista de nodos
        teste = caminho
        custo = 0
        i = 0
        while i + 1 < len(teste):
            custo = custo + self.get_arc_cost(teste[i], teste[i + 1])
            i += 1
        return custo

    # metodo para obter os nodos adjacentes depois usados para inserir na queue
    def get_node_by_coords(self, x, y):
        return self.m_nodes[x+(y*self.m_largura)]

    def get_node_by_id(self, id):
        for ids in self.m_nodes:
            if id == ids:
                return self.m_nodes[ids]

    def getNeighbours_from_nodes(self, id):
        largura = self.m_largura
        list = []
        tamanhoMax = self.m_largura*self.m_altura

        if(id+1 < tamanhoMax):
                if(self.m_nodes[id+1].getY() == self.m_nodes[id].getY()):
                    list.append(self.m_nodes[id+1])
        if((id-1) >= 0):
            if(self.m_nodes[id-1].getY() == self.m_nodes[id].getY()):
                list.append(self.m_nodes[id-1])
        if(id+largura < tamanhoMax):
            list.append(self.m_nodes[id+largura])
        if(id-largura >= 0):
            list.append(self.m_nodes[id-largura])

        lista_Ordenada = sorted(list, key=lambda obj: obj.m_id)
        lista_Decrescente = lista_Ordenada[::-1]
        return lista_Decrescente

    #def cria_grafo_mapa(self):

    def calcula_custo(self, caminho):
        # caminho é uma lista de nomes de nodos
        teste = caminho
        custo = 0
        i = 0
        while i + 1 < len(teste):
            custo = custo + self.get_arc_cost(teste[i], teste[i + 1])
            i = i + 1
        return custo

    def imprime_mapa(self, path):
        for node in self.m_nodes.values():
            if node in path:
                print("X", end="")
            else:
                if node.getId() % self.m_largura == 0:
                    print("")
                if node.getTipo() == "PAREDE":
                    print("#", end="")
                if node.getTipo() == "CAMINHO":
                    print("-", end="")
                if node.getTipo() == "PARTIDA":
                    print("P", end="")
                if node.getTipo() == "FIM":
                    print("F", end="")


    # start é um nodo e end uma lista de nodos
    def procura_DFS(self,start, end, path=[], visited=set()):
        path.append(start)
        visited.add(start)

        if start in end:
            # calcular o custo do caminho funçao calcula custo.
            custoT= self.calcula_custo(path)
            return (path, custoT)
        for (adjacente, peso) in self.m_graph[start]:
            if adjacente.getTipo() != "PAREDE" and adjacente not in visited:
                resultado = self.procura_DFS(adjacente, end, path, visited)
                if resultado is not None:
                    return resultado
        path.pop()  # se nao encontra remover o que está no caminho......
        return None

    def procura_BFS(self, start, end):
        # definir nodos visitados para evitar ciclos
        visited = set()
        fila = Queue()

        # adicionar o nodo inicial à fila e aos visitados
        fila.put(start)
        visited.add(start)

        # garantir que o start node nao tem pais...
        parent = dict()
        parent[start] = None

        path_found = False
        while not fila.empty() and path_found == False:
            nodo_atual = fila.get()
            if nodo_atual in end:
                path_found = True
                end_aux = nodo_atual
            else:
                for (adjacente, peso) in self.m_graph[nodo_atual]:
                    if adjacente.getTipo() != "PAREDE" and adjacente not in visited:
                        fila.put(adjacente)
                        parent[adjacente] = nodo_atual
                        visited.add(adjacente)
        # Reconstruir o caminho
        path = []
        if path_found:
            path.append(end_aux)
            while parent[end_aux] is not None:
                path.append(parent[end_aux])
                end_aux = parent[end_aux]
            path.reverse()
            # funçao calcula custo caminho
            custo = self.calcula_custo(path)
        return (path, custo)


    def verifica_colisao(self, start, velocidade, visited):
        last_node = start
        while velocidade != (0,0):
            if velocidade[0] > 0:
                nodo = self.get_node_by_id(start.getId()+1)
                if nodo.getTipo() == "PAREDE":
                     return [], visited
                elif nodo.getTipo() == "FIM":
                    return nodo, visited
                elif nodo not in visited:
                    last_node = nodo
                    visited.add(nodo)
                velocidade = (velocidade[0] - 1, velocidade[1])
            if velocidade[0] < 0:
                nodo = self.get_node_by_id(start.getId()-1)
                if nodo.getTipo() == "PAREDE":
                     return [], visited
                elif nodo.getTipo() == "FIM":
                    return nodo, visited
                elif nodo not in visited:
                    last_node = nodo
                    visited.add(nodo)
                velocidade = (velocidade[0] + 1, velocidade[1])
            if velocidade[1] > 0:
                nodo = self.get_node_by_coords(start.getX(), start.getY()+1)
                if nodo.getTipo() == "PAREDE":
                     return [], visited
                elif nodo.getTipo() == "FIM":
                    return nodo, visited
                elif nodo not in visited:
                    last_node = nodo
                    visited.add(nodo)
                velocidade = (velocidade[0], velocidade[1] - 1)
            if velocidade[1] < 0:
                nodo = self.get_node_by_coords(start.getX(), start.getY()-1)
                if nodo.getTipo() == "PAREDE":
                     return [], visited
                elif nodo.getTipo() == "FIM":
                    return nodo, visited
                elif nodo not in visited:
                    last_node = nodo
                    visited.add(nodo)
                velocidade = (velocidade[0], velocidade[1] + 1)
        return last_node,visited


    # PROCURA INFORMADA

    def get_manhattan_distance(self, a, b):
        (x1,y1) = a
        (x2,y2) = b
        return abs(x1-x2) + abs(y1-y2)

    def set_heuristica(self, posFinal):
        for n in self.m_nodes.values():
            self.m_h[n] = self.get_manhattan_distance((n.getX(),n.getY()), posFinal)
    def getNeighbours(self, nodo):
        lista = []
        for (adjacente, peso) in self.m_graph[nodo]:
            lista.append((adjacente, peso))
        return lista
    def calcula_est(self, estima):
        l = list(estima.keys())
        min_estima = estima[l[0]]
        node = l[0]
        for k, v in estima.items():
            if v < min_estima:
                min_estima = v
                node = k
        return node

    def procura_aStar(self, start, end):
        # open_list is a list of nodes which have been visited, but who's neighbors
        # haven't all been inspected, starts off with the start node
        # closed_list is a list of nodes which have been visited
        # and who's neighbors have been inspected
        open_list = {start}
        closed_list = set([])

        # g contains current distances from start_node to all other nodes
        # the default value (if it's not found in the map) is +infinity
        g = {}  ##  g é apra substiruir pelo peso  ???

        g[start] = 0

        # parents contains an adjacency map of all nodes
        parents = {}
        parents[start] = start
        n = None
        while len(open_list) > 0:
            # find a node with the lowest value of f() - evaluation function
            calc_heurist = {}
            flag = 0
            for v in open_list:
                if n == None or g[v] + self.m_h[v] < g[n] + self.m_h[n]:
                    n = v
                else:
                    flag = 1
                    calc_heurist[v] = g[v] + self.getH(v)
            if flag == 1:
                min_estima = self.calcula_est(calc_heurist)
                n = min_estima
            if n == None:
                print('Path does not exist!')
                return None

            # if the current node is the stop_node
            # then we begin reconstructin the path from it to the start_node
            if n == end:
                reconst_path = []

                while parents[n] != n:
                    reconst_path.append(n)
                    n = parents[n]

                reconst_path.append(start)

                reconst_path.reverse()

                #print('Path found: {}'.format(reconst_path))
                return (reconst_path, self.calcula_custo(reconst_path))

            # for all neighbors of the current node do
            for (m, weight) in self.getNeighbours(n):  # definir função getneighbours  tem de ter um par nodo peso
                # if the current node isn't in both open_list and closed_list
                # add it to open_list and note n as it's parent
                if m not in open_list and m not in closed_list:
                    open_list.add(m)
                    parents[m] = n
                    g[m] = g[n] + weight

                # otherwise, check if it's quicker to first visit n, then m
                # and if it is, update parent data and g data
                # and if the node was in the closed_list, move it to open_list
                else:
                    if g[m] > g[n] + weight:
                        g[m] = g[n] + weight
                        parents[m] = n

                        if m in closed_list:
                            closed_list.remove(m)
                            open_list.add(m)

            # remove n from the open_list, and add it to closed_list
            # because all of his neighbors were inspected
            open_list.remove(n)
            closed_list.add(n)

        print('Path does not exist!')
        return None

    def getH(self, nodo):
        if nodo not in self.m_h.keys():
            return 1000
        else:
            return (self.m_h[nodo])

    def reconstruct_path(self, came_from, current):
        path = [current]
        while current in came_from:
            current = came_from[current]
            path.append(current)
        return path

    def aestrela(self, start, end):
        count = 0
        open_set = PriorityQueue()
        open_set.put((0, count, start)) # o primeiro argumento do tuplo é o F(n)
        came_from = {}
        g_score = {node : float("inf") for node in self.m_graph} # Distância menor para chegar do start ate este nodo
        g_score[start] = 0
        f_score = {node: float("inf") for node in self.m_graph} # Distância predicted deste node até ao end node
        f_score[start] = self.m_h[start]
        close_list = set([])

        open_set_hash = {start} # Set para ver o que está dentro da PriorityQueue

        while not open_set.empty():
            current = open_set.get()[2] # Tirar da queue
            open_set_hash.remove(current)

            if current == end:
                path = self.reconstruct_path(came_from, current)
                path.reverse()
                return (path, self.calcula_custo(path))

            for (n, c) in self.getNeighbours(current):
                temp_g_score = g_score[current] + c

                if temp_g_score < g_score[n]:
                    came_from[n] = current
                    g_score[n] = temp_g_score
                    f_score[n] = temp_g_score + self.m_h[n]
                    if n not in open_set_hash:
                        count += 1
                        open_set.put((f_score[n], count, n))
                        open_set_hash.add(n)
                        #open_set.add(n)

            if current != start:
                close_list.add(current)

        return None

    def greedy(self, start, end):
        # open_list é uma lista de nodos visitados, mas com vizinhos
        # que ainda não foram todos visitados, começa com o  start
        # closed_list é uma lista de nodos visitados
        # e todos os seus vizinhos também já o foram
        open_list = set([start])
        closed_list = set([])

        # parents é um dicionário que mantém o antecessor de um nodo
        # começa com start
        parents = {}
        parents[start] = start

        while len(open_list) > 0:
            n = None

            # encontraf nodo com a menor heuristica
            for v in open_list:
                if n == None or self.m_h[v] < self.m_h[n]:
                    n = v

            if n == None:
                print('Path does not exist!')
                return None

            # se o nodo corrente é o destino
            # reconstruir o caminho a partir desse nodo até ao start
            # seguindo o antecessor
            if n == end:
                reconst_path = []

                while parents[n] != n:
                    reconst_path.append(n)
                    n = parents[n]

                reconst_path.append(start)

                reconst_path.reverse()

                return (reconst_path, self.calcula_custo(reconst_path))

            # para todos os vizinhos  do nodo corrente
            for (m, weight) in self.getNeighbours(n):
                # Se o nodo corrente nao esta na open nem na closed list
                # adiciona-lo à open_list e marcar o antecessor
                if m not in open_list and m not in closed_list and m.getTipo() != "PAREDE":
                    open_list.add(m)
                    parents[m] = n

            # remover n da open_list e adiciona-lo à closed_list
            # porque todos os seus vizinhos foram inspecionados
            open_list.remove(n)
            closed_list.add(n)

        print('Path does not exist!')
        return None

    def desenha(self):
        ##criar lista de vertices
        lista_v = self.m_nodes.values()
        lista_a = []
        g = nx.Graph()
        for nodo in lista_v:
            n = nodo
            g.add_node(n)
            for (adjacente, peso) in self.m_graph[n]:
                lista = (n, adjacente)
                # lista_a.append(lista)
                g.add_edge(n, adjacente, weight=peso)

        pos = nx.spring_layout(g)
        nx.draw_networkx(g, pos, with_labels=False, font_weight='bold')
        labels = nx.get_edge_attributes(g, 'weight')
        nx.draw_networkx_edge_labels(g, pos, edge_labels=labels)

        plt.draw()
        plt.show()


    # COMPETITIVO

    def set_heuristicas_minmax(self, posFinal):
        for n in self.m_nodes.values():
            self.m_h[n] = (self.get_manhattan_distance((n.getX(), n.getY()), posFinal), -abs(self.get_manhattan_distance((n.getX(), n.getY()), posFinal)))

    def pesquisa_minmax(self, start, end):
        pathMin = [start]
        pathMax = [start]

        while end not in pathMax and end not in pathMin:
            if end not in pathMin:
                pathMin = self.pesquisa_min(start, pathMin, pathMax)
            if end not in pathMax:
                pathMax = self.pesquisa_max(start, pathMin, pathMax)

        return (pathMin, pathMax)


    def pesquisa_min(self, start, pathMin, pathMax):
        temp = start
        nodo = pathMin[-1]
        for (adjacente, peso) in self.m_graph[nodo]:
            if adjacente in pathMax and adjacente.getTipo() == "FIM":
                temp = adjacente
            if adjacente.getTipo() != "PAREDE" and adjacente not in pathMax:
                if self.m_h[adjacente][0] < self.m_h[temp][0]:
                    temp = adjacente

        pathMin.append(temp)
        return pathMin

    def pesquisa_max(self, start, pathMin, pathMax):
        temp = start
        nodo = pathMax[-1]
        for (adjacente, peso) in self.m_graph[nodo]:
            if adjacente in pathMin and adjacente.getTipo() == "FIM":
                temp = adjacente
            if adjacente.getTipo() != "PAREDE" and adjacente not in pathMin:
                if self.m_h[adjacente][1] > self.m_h[temp][1]:
                    temp = adjacente

        pathMax.append(temp)
        return pathMax

    def imprime_mapa_max(self, path):
        for node in self.m_nodes.values():
            if node in path:
                print("Y", end="")
            else:
                if node.getId() % self.m_largura == 0:
                    print("")
                if node.getTipo() == "PAREDE":
                    print("#", end="")
                if node.getTipo() == "CAMINHO":
                    print("-", end="")
                if node.getTipo() == "PARTIDA":
                    print("P", end="")
                if node.getTipo() == "FIM":
                    print("F", end="")


