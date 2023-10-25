package Controller;

import Model.*;
import Exceptions.EmailAlreadyUsedException;
import Exceptions.FicheiroDeObjectosCorrumpido;
import Exceptions.LoginInvalidoException;
import View.*;

import java.io.IOException;
import java.util.*;

public class Controller {
    private Dados dados;

    public Controller() throws IOException, ClassNotFoundException {
        try {
            if (Parser.lerFicheiro("objetos.ser") == null) {
                throw new FicheiroDeObjectosCorrumpido("O ficheiro de objetos pode estar corrumpido ou não tem nada dentro (se a classe Dados for mudada, isto resulta no in.readObject() dar null)");
            } else this.dados = Parser.lerFicheiro("objetos.ser");
        }catch (FicheiroDeObjectosCorrumpido e){
            View.displayMessage(e.getMessage());
            this.dados = new Dados();
        }
    }

    public Controller(Dados dados){
        this.dados = dados;
    }

    public static void run() throws IOException, ClassNotFoundException {
        Dados dados = Parser.lerFicheiro("objetos.ser");
    }

    public Dados getDados() {
        return dados;
    }

    public void setDados(Dados dados) {
        this.dados = dados;
    }

    public void salvarEstado() throws IOException {
        this.dados.salvarEstado();
    }

    public void adicionaCompradorNovo(String email, String nome, String morada, int nif, String passwaord){
        try{
            if(this.dados.verificaExistenciaComprador(email)) throw new EmailAlreadyUsedException("\n" + "Já existe um comprador com esse email!");

            this.dados.criarNovoComprador(email, nome, morada, nif, passwaord);
        }catch (EmailAlreadyUsedException e){
            View.displayMessage(e.getMessage());
        }
    }

    public void adicionaVendedorNovo(String email, String nome, String morada, int nif, String password){
        try{
            if(this.dados.verificaExistenciaVendedor(email)) throw new EmailAlreadyUsedException("\n" + "Já existe um vendedor com esse email!");

            this.dados.criarNovoVendedor(email, nome, morada, nif, password);
        }catch (EmailAlreadyUsedException e){
            View.displayMessage(e.getMessage());
        }
    }

    public void adicionaTransportadoraNova(String email, String nome, int nif, float margem, TipoFormula tipo, String formula, String password){
        try{
            if(this.dados.verificaExistenciaTransportadora(email)) throw new EmailAlreadyUsedException("\n" + "Já existe uma transportadora com esse email!");

            this.dados.criarNovaTransportadora(email, nome, nif, margem, tipo, formula, password);
        } catch (EmailAlreadyUsedException e){
            View.displayMessage(e.getMessage());
        }
    }

    public void adicionaTransportadoraPremiumNova(String email, String nome, int nif, float margem, TipoFormula tipo, String formula, String formulaPremium, String password){
        try{
            if(this.dados.verificaExistenciaTransportadora(email)) throw new EmailAlreadyUsedException("\n" + "Já existe uma transportadora com esse email!");

            this.dados.criarNovaTransportadoraPremium(email, nome, nif, margem, tipo, formula, formulaPremium, password);
        }catch (EmailAlreadyUsedException e){
            View.displayMessage(e.getMessage());
        }
    }

    public void adicionaNovaSapatilhaNova(int id, int usado, String descricao, float preco, float desconto, int estado, int numUtilizadores, int premium, int idTransportadora, int stock, String autor, int tamanho, int atilhos, String cor, int anoColecao){
        boolean usadoB;
        usadoB = usado == 1;

        Estado estadoS = null;
        if(estado == 0) estadoS = Estado.Mau;
        else if(estado == 1) estadoS = Estado.Medio;
        else if(estado == 2) estadoS = Estado.Bom;

        boolean atilhosB;
        atilhosB = atilhos != 0;

        if(premium == 1) this.dados.adicionaSapatilhaPremium(id, usadoB, descricao, preco, desconto, estadoS, numUtilizadores, idTransportadora, stock, autor, tamanho, atilhosB, cor, anoColecao);
        else this.dados.adicionaSapatilha(id, usadoB, descricao, preco, desconto, estadoS, numUtilizadores, idTransportadora, stock, tamanho, atilhosB, cor, anoColecao);
    }


    public void adicionaNovoTshirt(int idVendedor, int usado, String descricao, float preco, float desconto, int estado, int numUtilizadores, int idTransportadora, int stock, int tamanho, int padrao){
        boolean usadoB;
        usadoB = usado == 1;

        Estado estadoT = null;
        if(estado == 0) estadoT = Estado.Mau;
        else if(estado == 1) estadoT = Estado.Medio;
        else if(estado == 2) estadoT = Estado.Bom;

        TamanhoTShirt tamanhoT = null;
        if(tamanho == 0) tamanhoT = TamanhoTShirt.S;
        else if(tamanho == 1) tamanhoT = TamanhoTShirt.M;
        else if(tamanho == 2) tamanhoT = TamanhoTShirt.L;
        else if(tamanho == 3) tamanhoT = TamanhoTShirt.XL;

        PadraoTShirt padraoT = null;
        if(padrao == 0) padraoT = PadraoTShirt.Liso;
        else if(padrao == 1) padraoT = PadraoTShirt.Riscas;
        else if(padrao == 2) padraoT = PadraoTShirt.Palmeiras;

        this.dados.adicionaTshirt(idVendedor, usadoB, descricao, preco, desconto, estadoT, numUtilizadores, idTransportadora, stock, tamanhoT, padraoT);
    }

    public void adicionaNovoMala(int idVendedor, int usado, String descricao, float preco, float desconto, int estado, int numUtilizadores, int premium, int idTransportadora, int stock, String autor, int dimensao, int textura, int anoColecao){
        boolean usadoB;
        usadoB = usado == 1;

        Estado estadoS = null;
        if(estado == 0) estadoS = Estado.Mau;
        else if(estado == 1) estadoS = Estado.Medio;
        else if(estado == 2) estadoS = Estado.Bom;

        Dimenssao dimensaoM = null;
        if(dimensao == 0) dimensaoM = Dimenssao.Pequeno;
        else if(dimensao == 1) dimensaoM = Dimenssao.Medio;
        else if(dimensao == 2) dimensaoM = Dimenssao.Grande;

        TexturaMala texturaM = null;
        if(textura == 0) texturaM = TexturaMala.Pele;
        else if(textura == 1) texturaM = TexturaMala.Tecido;
        else if(textura == 2) texturaM = TexturaMala.Sintetico;

        if(premium == 1) this.dados.adicionaMalaPremium(idVendedor, usadoB, descricao, preco, desconto, estadoS, numUtilizadores, dimensaoM, texturaM, anoColecao, idTransportadora, stock, autor);
        else this.dados.adicionaMala(idVendedor , usadoB, descricao, preco, desconto, estadoS, numUtilizadores, idTransportadora, stock, dimensaoM, texturaM, anoColecao);
    }

    public void enviaFatura(int idComprador){
        this.dados.enviaFaturas(idComprador);
    }

    public int login(TipoConta tipo, String email, String password){ // Devolve false se id retornado for -1
         try {
             int res = this.dados.login(tipo, email, password);
             if (res == -1) throw new LoginInvalidoException("As credências introduzidas estão incorretas!");
             return res;
         }catch (LoginInvalidoException e){
             View.displayMessage(e.getMessage());
             return -1;
         }
    }

    public Comprador getCompradorById(int id){
        return this.dados.getCompradorById(id);
    }

    public Vendedor getVendedorById(int id){
        return this.dados.getVendedorById(id);
    }

    public List<Encomenda> getEncomendasDoComprador(int id){
        return this.dados.getEncomendasDoComprador(id);
    }

    public float getPrecosEncomenda(int idEncomenda){
        return this.dados.getPrecosEncomenda(idEncomenda);
    }

    public Artigos getArtigoByCode(String barcode){
        return this.dados.getArtigoByCode(barcode);
    }

    public void adicionaArtigoAEncomenda(int idComrador, String codbarras) {
        String msg = this.dados.adicionaArtigoAEncomenda(getEncomendaAtivaId(idComrador), codbarras);
        View.displayMessage(msg);
    }

    public Encomenda getEncomendaAtivaId(int id){
        return this.dados.getEncomendaAtivaId(id);
    }

    public void atualizaComprador(int idComprador, String email, String nome, String morada, int nif) throws IOException {
        this.dados.atualizaComprador(idComprador, email, nome, morada, nif);
    }

    public void atualizaVendedor(int idVendedor, String email, String nome, String morada, int nif) throws IOException {
        this.dados.atualizaVendedor(idVendedor, email, nome, morada, nif);
    }

    public void atualizaTransportadora(int idTransportadora, String email, String nome, int nif, float margem) throws IOException{
        this.dados.atualizaTransportadora(idTransportadora, email, nome, nif, margem);
    }

    public void atualizaFormula(int idTransportadora, String novaFormula){
        this.dados.atualizaFormula(idTransportadora, novaFormula);
    }

    public void atualizaMargem(int idTransportadora, float novaMargem){
        this.dados.atualizaMargem(idTransportadora, novaMargem);
    }

    public Encomenda getEncomendaById(int id){
        return this.dados.getEncomendaById(id);
    }

    public Transportadora getTransportadoraById(int id){
        return this.dados.getTransportadoraById(id);
    }

    public TransportadoraPremium getTransportadoraPremiumById(int id){
        return this.dados.getTransportadoraPremiumById(id);
    }

    public ArrayList<Artigos> getArtigosAssociados(int idTransportadora){
        return this.dados.getArtigosAssociados(idTransportadora);
    }

    public HashMap<String, Transportadora> getTransportadoras(){
        return this.dados.getTransportadoras();
    }

    public ArrayList<Artigos> getEmVenda(int idVendedor){
        return this.dados.getEmVenda(idVendedor);
    }

    public TipoFormula getTipoFormulaTransportadora(int idTransportadora){
        return this.dados.getTipoFormulaTransportadora(idTransportadora);
    }

    public float getMargem(int idTransportadora){
        return this.dados.getMargem(idTransportadora);
    }

    public String getFormula(int idTransportadora){
        return this.dados.getFormula(idTransportadora);
    }

    public void changeTipoFormula(int idTransportadora){
        this.dados.changeTipoFormula(idTransportadora);
    }

    public void pagarEncomenda(int idEncomenda){
        String msg = this.dados.pagarEncomenda(idEncomenda);
        View.displayMessage(msg);
    }

    public void devolverEncomenda(int idEncomenda){
        String msg = this.dados.devolverEncomenda(idEncomenda);
        View.displayMessage(msg);
    }

    public void adicionaNovaFormula(int idTransportadora, String novaFormula, float novaMargem){
        this.dados.adicionaNovaFormula(idTransportadora, novaFormula, novaMargem);
    }

    public String listTransportadoras(){
        return this.dados.listTransportadoras();
    }

    public String listTransportadorasPremium(){
        return this.dados.listTransportadorasPremium();
    }

    public String listArtigosAssociadosTransportadora(int idTransportadora){
        return this.dados.listArtigosAssociadosTransportadora(idTransportadora);
    }

    public String listArtigosEmVenda(int idVendedor){
        return this.dados.listArtigosEmVenda(idVendedor);
    }

    public String listaArtigos(){
        return this.dados.listArtigos();
    }

    public String listEncomendasComprador(int idComrpador){
        List<Encomenda> encs = getEncomendasDoComprador(idComrpador);
        return this.dados.listEncomendasComprador(encs);
    }

    public String listVendedores(){
        return this.dados.listVendedores();
    }

    public String listCompradores(){
        return this.dados.listCompradores();
    }

    public String listTopRankingVendedores(){
        return this.dados.listTopRankingVendedores();
    }

    public String listTopRankingTransportadorasPremium(){
        return this.dados.listTopRankingTransportadorasPremium();
    }

    public String listTopRankingTransportadoras(){
        return this.dados.listTopRankingTransportadoras();
    }

    public void avancaTempo(int dias){
        this.dados.avancaTempo(dias);
        this.dados.expedirEncomendas();
        this.dados.atualizarMargens();
        this.dados.atualizaFormulas();
        this.dados.atualizaPrecosDescontos();
        this.dados.entregarEncomenda();
    }

}
