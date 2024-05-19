import math
from queue import Queue


from node import Nodo

class Graph:

    def __init__(self, directec=False):
        self.m_nodes = [] # lista de nodos
        self.m_directed = directec # se o grafo é diricionado ou não
        self.m_graph = {} # dicionario para armazenar os nodos, arestas e pesos, a key é uma string
        self.m_h = {} # dicionario para posteriormente armazenar as heurísticas

    def __str__(self):
        out = ""
        for key in self.m_graph.keys():
            out = out + "node " + str(key) + ": " + str(self.m_graph[key]) + "\n"
        return out

    def add_edge(self, node1, node2, weight): # node1 e node2 são os nomes dos nodos
        n1 = Nodo(node1)
        n2 = Nodo(node2)

        if(n1 not in self.m_nodes):
            self.m_nodes.append(n1)
            self.m_graph[n1] = set()
        else:
            n1 = self.get_node_by_name(node1)

        if(n2 not in self.m_nodes):
            self.m_nodes.append(n2)
            self.m_graph[n2] = set()
        else:
            n2 = self.get_node_by_name(node2)

        self.m_graph[n1].add((n2, weight))

        if not self.m_directed:
            self.m_graph[n2].add((n1, weight))


    def set_h(self, nodo, heuristica): # finalNode é o nome do nodo final
        self.m_h[self.get_node_by_name(nodo)] = heuristica

    def get_node_by_name(self,name):
        search_node = Nodo(name)
        for node in self.m_nodes:
            if node == search_node:
                return node
        return None

    def imprime_aresta(self):
        listaA = ""
        for nodo in self.m_graph.keys():
            for (nodo2, custo) in self.m_graph[nodo]:
                listaA = listaA + "(" + str(nodo.m_name) + " -> " + str(nodo2.m_name) + " custo: " + str(custo) + ") ; "
        return listaA

    def get_arc_cost(self, node1, node2):
        custoT = math.inf

        a = self.m_graph[self.get_node_by_name(node1)]
        for (nodo, custo) in a:
            if nodo == node2:
                custoT = custo

        return custoT

    def calcula_custo(self, caminho):
        test = caminho
        custo = 0
        i = 0
        while i+1 < len(test):
            custo = custo + self.get_arc_cost(test[i] , test[i+1])
            i += 1
        return custo

    def procura_DFS(self, start, end, path=[], visited=set()):
        path.append(start)
        visited.add(start)

        if start == end:
            custoT = self.calcula_custo(path)
            return (path, custoT)

        for (adjacentes, pese) in self.m_graph[start]:
            if adjacentes not in visited:
                resultado = self.procura_DFS(adjacentes, end, path, visited)
                if resultado is not None:
                    return resultado

        path.pop()
        return None

    def procura_BFS(self, start, end):
        visited = set()
        fila = Queue()
        custo = 0

        fila.put(start)
        visited.add(start)

        parent = dict()
        parent[start] = None

        path_found = False
        while not fila.empty() and path_found == False:
            nodo_atual = fila.get()
            if nodo_atual == end:
                path_found = True
                end_aux = nodo_atual
            else:
                for (adjacente, peso) in self.m_graph[nodo_atual]:
                    if adjacente not in visited:
                        fila.put(adjacente)
                        parent[adjacente] = nodo_atual
                        visited.add(adjacente)

        path = []
        if path_found:
            path.append(end_aux)
            while parent[end_aux] is not None:
                path.append(parent[end_aux])
                end_aux = parent[end_aux]

            path = path[::-1]
            custo = self.calcula_custo(path)
        return (path, custo)

    def get_adjacentes(self, nodo):
        adjacentes = []
        for (adj, custo) in self.m_graph[nodo]:
            adjacentes.append(adj)
        return adjacentes

    def a_star(self, start, end): # f(n) = g(n) + h(n) , onde g(n) custo do caminho já precorrido, h(n) heuristica dos nodos adjacentes
        path = []
        visited = []
        fila = Queue()
        g = 0

        path.append(start)
        visited.append(start)
        fila.put(self.get_node_by_name(start))

        path_found = False
        while not fila.empty() and path_found == False:
            atual = fila.get()
            if atual.m_name == end:
                path_found = True

            lista = {}
            for nodo in self.get_adjacentes(atual):
                if nodo.m_name not in visited:
                    lista[nodo] = self.m_h[nodo] + self.calcula_custo(path)

            path.append(min(lista, key=lista.get).m_name)
            visited.append(min(lista, key=lista.get).m_name)
            fila.put(min(lista, key=lista.get))

        return (path, self.calcula_custo(path))


    def greedy(self): # f(n) = h(n) , apenas quer saber da heurísitca
        pass

