from graph import Graph

g = Graph()

g.add_edge("s", "e", 2)
g.add_edge("s", "a", 2)
g.add_edge("e", "f", 5)
g.add_edge("f", "g", 2)
g.add_edge("g", "t", 2)
g.add_edge("a", "b", 2)
g.add_edge("b", "c", 2)
g.add_edge("c", "d", 3)
g.add_edge("d", "t", 3)

print(g.imprime_aresta())

start = g.get_node_by_name("s")
end = g.get_node_by_name("t")

print(g.procura_DFS(start, end))
print(g.procura_BFS(start, end))
