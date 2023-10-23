from Circuito import Circuito
import os
from Graph import Graph

def main():
    file = "./Circuitos/circuito.txt"
    opcao = 0
    opcaoAlg = 0
    c = Circuito()

    while opcao != 9:
        os.system('cls')

        print("Bem vindo ao VectorRace!")
        print("1: circuito.txt")
        print("2: circuito2.txt")
        print("3: circuito3.txt")
        print("4: circuito4.txt")
        print("5: Introduzir manualmente o caminho.")
        print("9: Sair")
        opcao = input("Selecione um ficheiro (circuito.txt por defeito): ")
        try:
            opcao = int(opcao)
        except ValueError:
            print("Introduza um número válido")

        if opcao == 5:
            exists = False
            while exists == False:
                file = input("Introduza o caminho do ficheiro: ")
                exists = os.path.exists(file)
                if(exists == False):
                    print("Ficheiro não existe...")
        if opcao == 9:
            break
        if opcao == 1:
            file = "./Circuitos/circuito.txt"
        elif opcao == 2:
            file = "./Circuitos/circuito2.txt"
        elif opcao == 3:
            file = "./Circuitos/circuito3.txt"
        elif opcao == 4:
            file = "./Circuitos/circuito4.txt"

        print("Selecionado o ficheiro " + file)

        print("Selecione o Algoritmo que pretende utilizar!")
        print("1: DFS")
        print("2: BFS")
        print("3: A*")
        print("4: Greedy")
        print("5: MinMax (competitivo)")
        print("9: Sair")
        opcaoAlg = input("Opção: ")

        try:
            opcaoAlg = int(opcaoAlg)
        except ValueError:
            print("Número inválido.")

        

        if opcaoAlg == 9:
            opcao = 9
            break
        if opcaoAlg == 1:
            print("ALGORITMO DFS")
            game_map, start, finish = c.text_to_list(file)
            print("A coordenada de inicial é: " + str(start))
            print("As coordenadas de fim são: " + str(finish))
            graph = c.list_to_graph(game_map)
            graph = c.populate_graph(graph)
            print("Mapa antes da travessia: ")
            graph.imprime_mapa([])
            print("\n\nMapa após travessia: ")

            startnode = graph.get_node_by_coords(start[0][0], start[0][1])
            endnode = [None] * len(finish)
            i = 0

            for f in finish:
                endnode[i] = graph.get_node_by_coords(f[0], f[1])
                i += 1

            path, custo = graph.procura_DFS(startnode, endnode)
            graph.imprime_mapa(path)
            print("\n\nO custo desta operação foi: " + str(custo))

        elif opcaoAlg == 2:
            print("ALGORITMO BFS")
            game_map, start, finish = c.text_to_list(file)
            print("A coordenada de inicial é: " + str(start))
            print("As coordenadas de fim são: " + str(finish))
            graph = c.list_to_graph(game_map)
            graph = c.populate_graph(graph)
            print("Mapa antes da travessia: ")
            graph.imprime_mapa([])
            print("\n\nMapa após travessia: ")

            startnode = graph.get_node_by_coords(start[0][0], start[0][1])
            endnode = [None] * len(finish)
            i = 0

            for f in finish:
                endnode[i] = graph.get_node_by_coords(f[0], f[1])
                i += 1

            path, custo = graph.procura_BFS(startnode, endnode)
            graph.imprime_mapa(path)
            print("\n\nO custo desta operação foi: " + str(custo))

        elif opcaoAlg == 3:
            print("ALGORITMO A*")
            game_map, start, finish = c.text_to_list(file)
            finish_copy = finish
            print("A coordenada de inicial é: " + str(start))
            print("As coordenadas de fim são: " + str(finish))
            graph = c.list_to_graph(game_map)
            graph = c.populate_graph(graph)

            print("Selecione um dos nodos de fim para servir de meta")
            i = 0
            for f in finish:
                print(str(i) + ": " + str(f))
                i += 1
            fim_selecionado = input()

            try:
                fim_selecionado = int(fim_selecionado)
            except ValueError:
                print("Introduza um número válido")

            print("Mapa antes da travessia: ")
            graph.imprime_mapa([])
            print("\nMapa após travessia: ")

            startnode = graph.get_node_by_coords(start[0][0], start[0][1])
            endnode = graph.get_node_by_coords(finish_copy[fim_selecionado][0], finish_copy[fim_selecionado][1])

            graph.set_heuristica(finish_copy[fim_selecionado])
            path, custo = graph.aestrela(startnode, endnode)
            graph.imprime_mapa(path)
            print("\nO custo desta operação foi: " + str(custo))

        elif opcaoAlg == 4:
            print("ALGORITMO GREEDY")
            game_map, start, finish = c.text_to_list(file)
            finish_copy = finish
            print("A coordenada de inicial é: " + str(start))
            print("As coordenadas de fim são: " + str(finish))
            graph = c.list_to_graph(game_map)
            graph = c.populate_graph(graph)

            print("Selecione um dos nodos de fim para servir de meta")
            i = 0
            for f in finish:
                print(str(i) + ": " + str(f))
                i += 1
            fim_selecionado = input()
            try:
                fim_selecionado = int(fim_selecionado)
            except ValueError:
                print("Introduza um número válido")

            print("Mapa antes da travessia: ")
            graph.imprime_mapa([])
            print("\nMapa após travessia: ")

            startnode = graph.get_node_by_coords(start[0][0], start[0][1])
            endnode = graph.get_node_by_coords(finish_copy[fim_selecionado][0], finish_copy[fim_selecionado][1])

            graph.set_heuristica(finish_copy[fim_selecionado])
            path, custo = graph.greedy(startnode, endnode)
            graph.imprime_mapa(path)
            print("\nO custo desta operação foi: " + str(custo))
            #graph.desenha()

        elif opcaoAlg == 5:
            print("ALGORITMO MINMAX (COMPETITIVO)")
            game_map, start, finish = c.text_to_list(file)
            finish_copy = finish
            print("A coordenada inicial é: " + str(start))
            print("As coordenadas de fim são: " + str(finish))
            graph = c.list_to_graph(game_map)
            graph = c.populate_graph(graph)

            print("Selecione um dos nodos de fim para servir de meta")
            i = 0
            for f in finish:
                print(str(i) + ": " + str(f))
                i += 1
            fim_selecionado = input()
            try:
                fim_selecionado = int(fim_selecionado)
            except ValueError:
                print("Introduza um número válido")

            print("Mapa antes da travessia: ")
            graph.imprime_mapa([])
            print("\nMapa após travessia: ")

            startnode = graph.get_node_by_coords(start[0][0], start[0][1])
            endnode = graph.get_node_by_coords(finish_copy[fim_selecionado][0], finish_copy[fim_selecionado][1])

            graph.set_heuristicas_minmax(finish_copy[fim_selecionado])
            pathmin, pathmax = graph.pesquisa_minmax(startnode, endnode)

            print("Travessia do min (caminho denotado por X): ")
            graph.imprime_mapa(pathmin)
            print("\n Travessia do max (caminho denotado por Y): ")
            graph.imprime_mapa_max(pathmax)


        input("\n\nPrima enter para continuar...")



if __name__ == "__main__":
    main()
