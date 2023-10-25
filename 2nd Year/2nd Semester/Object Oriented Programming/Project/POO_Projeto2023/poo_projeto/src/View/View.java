package View;

import Model.TipoFormula;
import Model.TransportadoraPremium;
import Model.TipoConta;
import Exceptions.InputInvalidoException;
import Controller.Controller;


import java.io.IOException;
import java.util.*;

public class View {
    private Controller controller;

    public View(Controller controller){
        this.controller = controller;
    }

    public void run() throws InputInvalidoException, IOException {
        Scanner scanner = new Scanner(System.in);
        boolean sair = false;


        while(!sair) {
            int opcaoMenuPrincipal = Menus.menuPrincipal();

            outer:
            switch (opcaoMenuPrincipal) {
                case 1 -> {
                    boolean menuCriacao = false;
                    while(!menuCriacao) {
                        int opcaoMenuCriacao = Menus.menuCriacao();
                        switch (opcaoMenuCriacao) {
                            case 1 -> criarComprador();
                            case 2 -> criarVendedor();
                            case 3 -> criarTransportadora();
                            case 4 -> {
                                menuCriacao = true;
                                break;
                            }
                            case 9 -> {
                                salvarEstado();
                                sair = true;
                                break outer;
                            }
                        }
                    }
                }
                case 2 -> {

                    boolean login = false;
                    while (!login) {
                        int opcaoMenuLogin = Menus.menuLogin();

                        loginLable:
                        switch (opcaoMenuLogin) {
                            case 1:

                                int idComprador = loginComprador();
                                if (idComprador == -1){
                                    login = true;
                                    break;
                                }

                                this.controller.enviaFatura(idComprador);

                                boolean menuComprador = false;
                                while(!menuComprador){
                                    int opcaoMenuComprador = Menus.menuComprador();

                                    switch (opcaoMenuComprador){
                                        case 1 -> {
                                            alterarDadosComprador(idComprador);
                                        }
                                        case 2 -> {
                                            visualizacaoArtigosComprador(idComprador);
                                        }
                                        case 3 -> {
                                            int encomenda = visualizacaoEncomendas(idComprador);

                                            boolean menuEncomenda = false;
                                            while(!menuEncomenda) {
                                                int opcaoEncomenda = Menus.menuEncomenda();

                                                switch (opcaoEncomenda) {
                                                    case 1 -> {
                                                        this.controller.pagarEncomenda(encomenda);
                                                    }
                                                    case 2 -> {
                                                        //enc.devolucao();
                                                        this.controller.devolverEncomenda(encomenda);
                                                        //break;
                                                    }
                                                    case 3 -> {
                                                        System.out.println("Será devolvido o valor da encomenda: " + this.controller.getPrecosEncomenda(encomenda));
                                                        //break;
                                                    }
                                                    case 4 -> menuEncomenda = true;
                                                    case 9 -> {
                                                        salvarEstado();
                                                        sair = true;
                                                        break outer;
                                                    }
                                                }
                                            }
                                        }
                                        case 4 -> {
                                            break loginLable;
                                        }
                                        case 9 -> {
                                            salvarEstado();
                                            sair = true;
                                            break outer;
                                        }
                                    }
                                }

                            case 2:
                                int idVendedor = loginVendedor();
                                if (idVendedor == -1){
                                    login = true;
                                    break;
                                }

                                boolean menuVendedor = false;
                                while(!menuVendedor){
                                    int opcaoMenuVendedor = Menus.menuVendedor();

                                    switch (opcaoMenuVendedor){
                                        case 1 -> {
                                            alterarDadosVendedor(idVendedor);
                                        }
                                        case 2 -> {
                                            visualizacaoArtigosVendedor(idVendedor);
                                        }
                                        case 3 -> {
                                            adicionarNovoArtigoVendaVendedor(idVendedor);
                                        }
                                        case 4 -> {
                                            break loginLable;
                                        }
                                        case 9 -> {
                                            salvarEstado();
                                            sair = true;
                                            break outer;
                                        }
                                    }
                                }

                            case 3:
                                int idTransportadora = loginTransportadora();
                                if (idTransportadora == -1) {
                                    login = true;
                                    break;
                                }

                                boolean menuTransportadora = false;
                                while(!menuTransportadora){
                                    int opcaoMenuTransportadora = Menus.menuTransportadora();

                                    switch (opcaoMenuTransportadora){
                                        case 1 -> {
                                            alterarDadosTransportadora(idTransportadora);
                                        }
                                        case 2 -> {
                                            System.out.println(this.controller.listArtigosAssociadosTransportadora(idTransportadora));
                                        }
                                        case 3 -> {
                                            alterarFormula(idTransportadora);
                                        }
                                        case 4 -> {
                                            break loginLable;
                                        }
                                        case 9 -> {
                                            salvarEstado();
                                            sair = true;
                                        }
                                    }
                                }
                            case 4:
                                login = true;
                                break;
                            case 9:
                                salvarEstado();
                                sair = true;
                                break outer;
                        }
                    }
                }
                case 3 -> {
                    boolean menuVisualizacao = false;
                    while(!menuVisualizacao){
                        int opcaoMenuVisualizacao = Menus.menuVisualizacao();

                        switch (opcaoMenuVisualizacao){
                            case 1 -> {
                                System.out.println(this.controller.listaArtigos());
                            }
                            case 2 -> {
                                System.out.println(this.controller.listVendedores());
                            }
                            case 3 -> {
                                System.out.println(this.controller.listCompradores());
                            }
                            case 4 -> {
                                System.out.println(this.controller.listTransportadoras() + this.controller.listTransportadorasPremium());
                            }
                            case 5 -> menuVisualizacao = true;
                            case 9 -> {
                                salvarEstado();
                                sair = true;
                                break outer;
                            }
                        }
                    }
                }
                case 4 ->{
                    boolean menuSaltosNoTempo = false;
                    while(!menuSaltosNoTempo){
                        int opcaoMenuTempo = Menus.menuSaltosTempo();

                        if (opcaoMenuTempo == -1) {
                            menuSaltosNoTempo = true;
                        } else {
                            this.controller.avancaTempo(opcaoMenuTempo);
                        }

                    }
                }
                case 5 -> {
                    boolean menuEstatistica = false;
                    while(!menuEstatistica){
                        int opcaoMenuEstatistica = Menus.menuEstatisticas();

                        switch (opcaoMenuEstatistica){
                            case 1 -> {
                                System.out.println(this.controller.listTopRankingVendedores());
                            }
                            case 2 -> {
                                System.out.println(this.controller.listTopRankingTransportadorasPremium());
                            }
                            case 3 -> {
                                System.out.println(this.controller.listTopRankingTransportadoras());
                            }
                            case 4 -> menuEstatistica = true;
                            case 9 -> {
                                salvarEstado();
                                sair = true;
                                break outer;
                            }
                        }
                    }
                }
                case 9 ->{
                    salvarEstado();
                    sair = true;
                    break outer;
                }
            }
        }
    }

    public void criarComprador(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Introduza o email: ");
        String email = scanner.nextLine();
        System.out.print("Introduza a sua palavra passe: ");
        String passWord = scanner.nextLine();
        System.out.print("Introduza o seu nome: ");
        String nome = scanner.nextLine();
        System.out.print("Introduza a sua morada: ");
        String morada = scanner.nextLine();
        System.out.print("Introduza o seu nif: ");
        int nif = scanner.nextInt();
        this.controller.adicionaCompradorNovo(email, nome, morada, nif, passWord);
    }

    public void criarVendedor(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Introduza o email: ");
        String email1 = scanner.nextLine();
        System.out.print("Introduza a sua palavra passe: ");
        String passWord1 = scanner.nextLine();
        System.out.print("Introduza o seu nome: ");
        String nome1 = scanner.nextLine();
        System.out.print("Introduza a sua morada: ");
        String morada1 = scanner.nextLine();
        System.out.print("Introduza o seu nif: ");
        int nif1 = scanner.nextInt();
        this.controller.adicionaVendedorNovo(email1, nome1, morada1, nif1, passWord1);
    }

    public void criarTransportadora() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Pretende criar uma Transportadora de que tipo?");
        System.out.println("1) Normal");
        System.out.println("2) Premium");
        System.out.print("Selecione uma destas: ");
        int opcaoTrans = scanner.nextInt();
        if (opcaoTrans != 1 && opcaoTrans != 2){
            System.out.println("Input inválido");
            return;
        }

        System.out.print("Introduza o email: ");
        String email2 = scanner.next();
        System.out.print("Introduza a sua palavra passe: ");
        String passWord2 = scanner.next();
        System.out.print("Introduza o  nome: ");
        String nome2 = scanner.next();
        System.out.print("Introduza o seu nif: ");
        int nif2 = scanner.nextInt();
        System.out.print("Introduza a sua margem de lucro(em flaot): ");
        float margem = scanner.nextFloat();

        TipoFormula tipoFormula;
        String formula;

        if (opcaoTrans == 1) {
            System.out.println("A fórmula predifinida é: (preco*(margemLucro)*(1+imposto))");
            System.out.println("Pertende qual tipo de fórmula?");
            System.out.println("1) Fórmula Prédefinida");
            System.out.println("2) Fórmula Costumizada");
            System.out.print("Selecione uma destas: ");
            int opcaoFormula = scanner.nextInt();
            if (opcaoFormula != 1 && opcaoFormula != 2){
                System.out.println("Input inválido");
                return;
            }

            if (opcaoFormula == 2) {
                System.out.print("Introduza a sua formula (tem que ter as variaveis 'imposto', 'preco' e 'margem'): ");
                formula = scanner.next();
                tipoFormula = TipoFormula.Customized;
            } else {
                formula = "";
                tipoFormula = TipoFormula.Default;
            }

            this.controller.adicionaTransportadoraNova(email2, nome2, nif2, margem, tipoFormula, formula, passWord2);
        } else {
            System.out.println("A fórmula predifinida é: (preco*(margemLucro)*(1+imposto))+impostoPremium");
            System.out.println("Pertende qual tipo de fórmula?");
            System.out.println("1) Fórmula Prédefinida");
            System.out.println("2) Fórmula Costumizada");
            System.out.print("Selecione uma destas: ");
            int opcaoFormula1 = scanner.nextInt();
            if (opcaoFormula1 != 1 && opcaoFormula1 != 2){
                System.out.println("Input inválido");
                return;
            }

            if (opcaoFormula1 == 2) {
                System.out.print("Introduza a sua formula (tem que ter as variaveis 'imposto', 'preco', 'margem' e 'impostoPremium'): ");
                formula = scanner.nextLine();
                tipoFormula = TipoFormula.Customized;
            } else {
                formula = "";
                tipoFormula = TipoFormula.Default;
            }

            this.controller.adicionaTransportadoraPremiumNova(email2, nome2, nif2, margem, tipoFormula, formula, formula, passWord2);
        }
    }

    public int loginComprador(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Login como comprador");
        System.out.print("Introduza o email: ");
        String email = scanner.nextLine();
        System.out.print("Introduza a palavra-passe: ");
        String password = scanner.nextLine();

        return this.controller.login(TipoConta.Comprador, email, password);
    }

    public int loginVendedor(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Login como vendedor");
        System.out.print("Introduza o email: ");
        String email1 = scanner.nextLine();
        System.out.print("Introduza a palavra-passe: ");
        String password1 = scanner.nextLine();

        return this.controller.login(TipoConta.Vendedor, email1, password1);
    }

    public int loginTransportadora(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Login como Transportadora");
        System.out.print("Introduza o email: ");
        String email2 = scanner.nextLine();
        System.out.print("Introduza a palavra-passe: ");
        String password2 = scanner.nextLine();

        return this.controller.login(TipoConta.Transportadora, email2, password2);
    }

    public void alterarDadosComprador(int idComprador) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Introduza o novo email: ");
        String email3 = scanner.nextLine();
        System.out.print("Introduza o novo nome: ");
        String nome3 = scanner.nextLine();
        System.out.print("Introduza a nova morada: ");
        String morada3 = scanner.nextLine();
        System.out.print("Introduza o novo nif: ");
        int nif3 = scanner.nextInt();
        this.controller.atualizaComprador(idComprador,email3, nome3, morada3, nif3);
    }

    public void visualizacaoArtigosComprador(int idComprador){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Visualização de Artigos");
        System.out.println(this.controller.listaArtigos());
        while(true) {
            System.out.println("Indique o código de barras do artigo que prentende acicionar á sua encomenda(-1 para sair): ");
            int artigo = scanner.nextInt();
            if (artigo == -1) break;

            this.controller.adicionaArtigoAEncomenda(idComprador, String.valueOf(artigo));
        }
    }

    public int visualizacaoEncomendas(int idComprador){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Visualização das sua Encomendas");
        System.out.println(this.controller.listEncomendasComprador(idComprador));
        System.out.print("Selecione o id duma das encomendas: ");
        return scanner.nextInt();
    }

    public void alterarDadosVendedor(int idVendedor) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Introduza o novo email: ");
        String email4 = scanner.nextLine();
        System.out.print("Introduza o novo nome: ");
        String nome4 = scanner.nextLine();
        System.out.print("Introduza a nova morada: ");
        String morada4 = scanner.nextLine();
        System.out.print("Introduza o novo nif: ");
        int nif4 = scanner.nextInt();
        this.controller.atualizaVendedor(idVendedor, email4, nome4, morada4, nif4);
    }

    public void visualizacaoArtigosVendedor(int idVendedor){
        System.out.println("Visualização de artigos do vendedor");
        System.out.println(this.controller.listArtigosEmVenda(idVendedor));
        System.out.print("Intoduza qualquer tecla para voltar ao menu anterior: ");
    }

    public void adicionarNovoArtigoVendaVendedor(int atual){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Adicionar um novo artigo");
        System.out.print("Descrição do artigo: ");
        String descricao = scanner.nextLine();
        System.out.print("O seu artigo é usado? (0-não, 1-sim) : ");
        int usado = scanner.nextInt();
        System.out.print("Preço base do artigo: ");
        float preco = scanner.nextFloat();
        System.out.print("Preço do desconto: ");
        float desconto = scanner.nextFloat();
        System.out.print("Qual o estado de usado? (0-mau,1-medio,2-bom): ");
        int estado = scanner.nextInt();
        System.out.print("Quantos utilziadores teve este produto? (0 caso seja novo) :");
        int utilizadores = scanner.nextInt();
        System.out.print("Qual é o stock do produto : ");
        int stock = scanner.nextInt();

        System.out.print("Que tipo de artigo é (0-sapatilha, 1-t-shirt, 2-mala)? : ");
        int tipoDeArtigo = scanner.nextInt();

        if (tipoDeArtigo == 0) {
            adicionaSapatilha(scanner, atual, usado, descricao, preco, desconto, estado, utilizadores, stock);

        } else if (tipoDeArtigo == 1) {
            adicionaTshirt(scanner, atual, usado, descricao, preco, desconto, estado, utilizadores, stock);

        } else if (tipoDeArtigo == 2) {
            adicionaMala(scanner, atual, usado, descricao, preco, desconto, estado, utilizadores, stock);
        }
    }

    public void adicionaSapatilha(Scanner scanner, int atual, int usado, String descricao, float preco, float desconto, int estado, int utilizadores, int stock){
        System.out.print("Indique o tamanho da sapatilha: ");
        int tamanho = scanner.nextInt();
        System.out.print("A sapatilha tem atilhos? (0-não, 1-sim): ");
        int atilhos = scanner.nextInt();
        System.out.print("Indique o ano de coleção desta sapatilha? : ");
        int anoSpatilha = scanner.nextInt();
        System.out.print("Indique a cor da sapatilha? : ");
        String cor = scanner.next();

        int premium = getPremiumOption(scanner);
        if(premium == 1){
            System.out.println(this.controller.listTransportadorasPremium());

            System.out.print("Selecione o id duma destas transportadoras: ");
            int idTransportadoraPremium = scanner.nextInt();
            System.out.print("Indique o autor deste artigo premium: ");
            String autor = scanner.next();
            this.controller.adicionaNovaSapatilhaNova(atual, usado, descricao, preco, desconto, estado, utilizadores, premium, idTransportadoraPremium, stock, autor, tamanho, atilhos, cor, anoSpatilha);
        }
        else{
            System.out.println(this.controller.listTransportadoras());

            System.out.print("Selecione o id duma destas transportadoras: ");
            int idTransportadora = scanner.nextInt();
            this.controller.adicionaNovaSapatilhaNova(atual, usado, descricao, preco, desconto, estado, utilizadores, premium, idTransportadora, stock, "", tamanho, atilhos, cor, anoSpatilha);
        }
    }

    public void adicionaTshirt(Scanner scanner, int atual, int usado, String descricao, float preco, float desconto, int estado, int utilizadores, int stock){
        System.out.println(this.controller.listTransportadoras());

        System.out.print("Selecione o id duma destas transportadoras: ");
        int idTransportadora = scanner.nextInt();

        System.out.print("Indique o tamanho da Tshirt (0-Small, 1-Medium, 2-Large, 3-Extra Large) : ");
        int tamanho = scanner.nextInt();
        System.out.print("Indique o padrão da Tshirt (0-Liso, 1-Riscas, 2-Palmeiras) : ");
        int padrao = scanner.nextInt();
        this.controller.adicionaNovoTshirt(atual, usado, descricao, preco, desconto, estado, utilizadores, idTransportadora, stock, tamanho, padrao);
    }

    public void adicionaMala(Scanner scanner, int atual, int usado, String descricao, float preco, float desconto, int estado, int utilizadores, int stock){
        System.out.print("Indique a dimensão da mala (0-Pequena, 1-Média, 2-Grande) : ");
        int dimensao = scanner.nextInt();
        System.out.print("Indique a textura da mala (0-Pele, 1-Tecido, 2-Sintético) : ");
        int textura = scanner.nextInt();
        System.out.print("Indique o ano de coleção desta mala : ");
        int anoMala = scanner.nextInt();

        int premium = getPremiumOption(scanner);
        if(premium == 1){
            System.out.println(this.controller.listTransportadoras());
            System.out.print("Selecione o id duma destas transportadoras: ");
            int idTransportadoraPremium = scanner.nextInt();
            System.out.print("Indique o autor deste artigo premium: ");
            String autor = scanner.nextLine();
            this.controller.adicionaNovoMala(atual, usado, descricao, preco, desconto, estado, utilizadores, premium, idTransportadoraPremium, stock, autor, dimensao, textura, anoMala);
        }
        else{
            System.out.println(this.controller.listTransportadorasPremium());
            System.out.print("Selecione o id duma destas transportadoras: ");
            int idTransportadora = scanner.nextInt();
            this.controller.adicionaNovoMala(atual, usado, descricao, preco, desconto, estado, utilizadores, premium, idTransportadora, stock, "", dimensao, textura, anoMala);
        }
    }

    private int getPremiumOption(Scanner scanner) {
        System.out.print("O artigo é considerado Premium? (0-não, 1-sim): ");
        return scanner.nextInt();
    }

    public void alterarDadosTransportadora(int idTransportadora) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Introduza o novo email: ");
        String email5 = scanner.nextLine();
        System.out.print("Introduza o novo nome: ");
        String nome5 = scanner.nextLine();
        System.out.print("Introduza o novo nif: ");
        int nif5 = scanner.nextInt();
        System.out.print("Intoduza a nova margem de lucro: ");
        float margem2 = scanner.nextFloat();
        this.controller.atualizaTransportadora(idTransportadora, email5, nome5, nif5, margem2);
    }

    public void alterarFormula(int idTransportadora){
        Scanner scanner = new Scanner(System.in);
        this.controller.changeTipoFormula(idTransportadora);
        if(this.controller.getTransportadoraById(idTransportadora) instanceof TransportadoraPremium){
            System.out.println("A sua mergem de lucro é esta: " + this.controller.getMargem(idTransportadora));
            System.out.println("Se pretende alterar a margem de lucro introduza agora o novo valor, caso contrário introduza o valor anterior: ");
            float novaMargem = scanner.nextFloat();
            System.out.println("A sua fórmula atual é esta: " + this.controller.getFormula(idTransportadora));
            System.out.print("Introduza a nova fórmula (tem que ter as variaveis 'imposto', 'preco', 'margem' e 'impostoPremium'): ");
            String novaFormula = scanner.next();

            if(novaMargem != this.controller.getMargem(idTransportadora) || !Objects.equals(novaFormula, this.controller.getFormula(idTransportadora))){
                this.controller.atualizaFormula(idTransportadora, novaFormula);
                this.controller.atualizaMargem(idTransportadora, novaMargem);
            }
        }else{
            System.out.println("A sua mergem de lucro é esta: " + this.controller.getMargem(idTransportadora));
            System.out.println("Se pretende alterar a margem de lucro introduza agora o novo valor, caso contrário introduza o valor anterior: ");
            float novaMargem = scanner.nextFloat();
            System.out.println("A sua fórmula atual é esta: " + this.controller.getFormula(idTransportadora));
            System.out.print("Introduza a nova fórmula (tem que ter as variaveis 'imposto', 'margem' e 'preco'): ");
            String novaFormula = scanner.next();
            if(novaMargem != this.controller.getMargem(idTransportadora) || !Objects.equals(novaFormula, this.controller.getFormula(idTransportadora))){
                this.controller.atualizaFormula(idTransportadora, novaFormula);
                this.controller.atualizaMargem(idTransportadora, novaMargem);
            }
        }
    }

    public static void displayMessage(String message){
        System.out.println(message);
    }

    public void salvarEstado() throws IOException {
        this.controller.salvarEstado();
    }
}
