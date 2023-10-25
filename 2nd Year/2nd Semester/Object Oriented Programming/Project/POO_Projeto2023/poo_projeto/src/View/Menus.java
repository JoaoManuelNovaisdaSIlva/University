package View;

import Exceptions.InputInvalidoException;

import java.util.Scanner;

public class Menus {
    public static int menuPrincipal() throws InputInvalidoException {
        int opcao;
        Scanner scanner = new Scanner(System.in);
        System.out.println("------BEM VINDO AO MARKETPLACE VINTAGE------");
        System.out.println("----MENU PRINCIPAL----");
        System.out.println("1) Criação de Entidades");
        System.out.println("2) Fazer Login");
        System.out.println("3) Visualizar dados");
        System.out.println("4) Fazer saltos no tempo");
        System.out.println("5) Estatisticas");
        System.out.println("9) Sair");
        System.out.print("Selecione uma das opções: ");
        opcao = scanner.nextInt();
        if (opcao != 1 && opcao != 2 && opcao != 3 && opcao != 4 && opcao != 5 && opcao != 9) {
            throw new InputInvalidoException("Opção inválida. Por favor, insira uma opção válida.");
        }
        return opcao;

    }

    public static int menuCriacao() throws InputInvalidoException{
        int opcao;
        Scanner scanner = new Scanner(System.in);

        System.out.println("----MENU DE CRIAÇÂO DE ENTIDADES----");
        System.out.println("1) Criar um Comprador");
        System.out.println("2) Criar um Vendedor");
        System.out.println("3) Criar uma Transportadora");
        System.out.println("4) Voltar para o menu anterior");
        System.out.println("9) Sair");
        System.out.print("Selecione uma das opções: ");
        opcao = scanner.nextInt();
        if(opcao != 1 && opcao != 2 && opcao != 3 && opcao != 4 && opcao != 9){
            throw new InputInvalidoException("Opção inválida. Por favor, insira uma opção válida.");
        }
        return opcao;

    }

    public static int menuLogin() throws InputInvalidoException{
        int opcao;
        Scanner scanner = new Scanner(System.in);

        System.out.println("----MENU DE LOGINS----");
        System.out.println("1) Login como comprador");
        System.out.println("2) Login como Vendedor");
        System.out.println("3) Login como Transportadora");
        System.out.println("4) Voltar para o menu anterior");
        System.out.println("9) Sair");
        System.out.print("Selecione uma das opções: ");
        opcao = scanner.nextInt();
        if(opcao != 1 && opcao != 2 && opcao != 3 && opcao != 4 && opcao != 9){
            throw new InputInvalidoException("Opção inválida. Por favor, insira uma opção válida.");
        }
        return opcao;
    }

    public static int menuVisualizacao() throws InputInvalidoException{
        int opcao;
        Scanner scanner = new Scanner(System.in);

        System.out.println("----MENU DE VISUALIZAÇÃO DE DADOS----");
        System.out.println("1) Ver artigos");
        System.out.println("2) Ver vendedores");
        System.out.println("3) Ver compradores");
        System.out.println("4) Ver transportadoras");
        System.out.println("5) Voltar para o menu anterior");
        System.out.println("9) Sair");
        System.out.print("Selecione uma das opções: ");
        opcao = scanner.nextInt();
        if(opcao != 1 && opcao != 2 && opcao != 3 && opcao != 4 && opcao != 5 && opcao != 9){
            throw new InputInvalidoException("Opção inválida. Por favor, insira uma opção válida.");
        }
        return opcao;
    }

    public static int menuComprador() throws InputInvalidoException{
        int opcao;
        Scanner scanner = new Scanner(System.in);

        System.out.println("----MENU DO COMPRADOR----");
        System.out.println("1) Alterar dados da conta");
        System.out.println("2) Visualizar Artigos");
        System.out.println("3) Visualizar Encomendas"); // Daqui vai para o controller, este mostra as encomendas todas, e so depois de selecionar chama a menuEncomenda
        System.out.println("4) Voltar ao menu principal");
        System.out.println("9) Sair");
        System.out.print("Selecione uma das opções: ");
        opcao = scanner.nextInt();
        if(opcao != 1 && opcao != 2 && opcao != 3 && opcao != 4 && opcao != 9){
            throw new InputInvalidoException("Opção inválida. Por favor, insira uma opção válida.");
        }
        return opcao;
    }

    public static int menuEncomenda() throws InputInvalidoException{
        int opcao;
        Scanner scanner = new Scanner(System.in);

        System.out.println("----MENU DE ENCOMENDAS----");
        System.out.println("1) Pagar Encomenda");
        System.out.println("2) Devolver Encomenda");
        System.out.println("3) Reportar um problema com a encomenda");
        System.out.println("4) Voltar para o menu anterior");
        System.out.println("9) Sair");
        System.out.print("Selecione uma das opções: ");
        opcao = scanner.nextInt();
        if(opcao != 1 && opcao != 2 && opcao != 3 && opcao != 4 && opcao != 9){
            throw new InputInvalidoException("Opção inválida. Por favor, insira uma opção válida.");
        }
        return opcao;
    }

    public static int menuVendedor() throws InputInvalidoException{
        int opcao;
        Scanner scanner = new Scanner(System.in);

        System.out.println("----MENU DO VENDEDOR----");
        System.out.println("1) Alterar dados da conta");
        System.out.println("2) Visualizar os seus artigos publicados");
        System.out.println("3) Adicionar um novo artigo para venda");
        System.out.println("4) Voltar para o menu principal");
        System.out.println("9) Sair");
        System.out.print("Selecione uma das opções: ");
        opcao = scanner.nextInt();
        if( opcao != 1 && opcao != 2 && opcao != 3 && opcao != 4 && opcao != 9){
            throw new InputInvalidoException("Opção inválida. Por favor, insira uma opção válida.");
        }
        return opcao;
    }

    public static int menuTransportadora() throws InputInvalidoException{
        int opcao;
        Scanner scanner = new Scanner(System.in);

        System.out.println("----MENU DAS TRASNPORTADORAS----");
        System.out.println("1) Alterar dados da conta");
        System.out.println("2) Visualizar os artigos associados");
        System.out.println("3) Alterar a fórmula");
        System.out.println("4) Voltar para o menu principal");
        System.out.println("9) Sair");
        System.out.print("Selecione uma das opções: ");
        opcao = scanner.nextInt();
        if(opcao != 1 && opcao != 2 && opcao != 3 && opcao != 4 && opcao != 9){
            throw new InputInvalidoException("Opção inválida. Por favor, insira uma opção válida.");
        }
        return opcao;
    }

    public static int menuSaltosTempo(){
        int opcao;
        Scanner scanner = new Scanner(System.in);

        System.out.println("----MENU DE SALTOS NO TEMPO----");
        System.out.println("Indique quantos dias quer saltar: ");
        System.out.println("-1) Sair");
        opcao = scanner.nextInt();
        return opcao;
    }

    public static int menuEstatisticas() throws InputInvalidoException {
        int opcao;
        Scanner scanner = new Scanner(System.in);

        System.out.println("----MENU DE ESTATISTICAS----");
        System.out.println("1) Top faturação de vendedores de sempre");
        System.out.println("2) Top faturação de transportadoras premium de sempre");
        System.out.println("3) Top faturação de transportadoras de sempre");
        System.out.println("4) Voltar para o menu anterior");
        System.out.println("9) Sair");
        System.out.print("Selecione uma das opções: ");
        opcao = scanner.nextInt();
        if(opcao != 1 && opcao != 2 && opcao != 3 && opcao != 4 && opcao != 9){
            throw new InputInvalidoException("Opção inválida. Por favor, insira uma opção válida.");
        }
        return opcao;
    }
}
