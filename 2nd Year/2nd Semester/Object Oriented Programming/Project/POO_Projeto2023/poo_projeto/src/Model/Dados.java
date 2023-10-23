package Model;

import Exceptions.FicheiroDeObjectosCorrumpido;

import java.io.IOException;
import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

public class Dados implements Serializable {
    private HashMap<String, Transportadora> transportadoras; // key é uma string com o nome do objecto mais o numero (diferente do id +/-)
    private HashMap<String, Utilizador> utilizadores;
    private HashMap<String, Artigos> artigos;
    private HashMap<String, Encomenda> encomendas;
    private LocalDateTime dateTimeDoSistema;
    private LoginParser loginParser;
    private HashMap<Integer, String> novasFormulas; // key é o id da transportadora
    private HashMap<Integer, Float> novasMargens; // key é o id da transportadora
    private List<Artigos> vendidos;
    private List<Encomenda> encomendasFinalizadas;
    private HashMap<Comprador, String> faturas;
    private static int idUtilizador;
    private static int idTransportadora;
    private static int idEncomenda;

    public Dados(){
        this.transportadoras = new HashMap<>();
        this.utilizadores = new HashMap<>();
        this.artigos = new HashMap<>();
        this.encomendas = new HashMap<>();
        this.dateTimeDoSistema = LocalDateTime.now();
        this.loginParser = new LoginParser();
        this.novasFormulas = new HashMap<>();
        this.novasMargens = new HashMap<>();
        this.vendidos = new ArrayList<>();
        this.encomendasFinalizadas = new ArrayList<>();
        this.faturas = new HashMap<>();
        idUtilizador = 0;
        idTransportadora = 0;
        idEncomenda = 0;
    }

    public Dados(String filepath) throws IOException, ClassNotFoundException {
        try {
            if(Parser.lerFicheiro(filepath) == null) throw new FicheiroDeObjectosCorrumpido("O ficheiro de objetos pode estar corrumpido ou não tem nada dentro (se a classe Dados for mudada, isto resulta no in.readObject() dar null)");
            else{
                Dados dados = Parser.lerFicheiro(filepath);
                this.transportadoras = dados.getTransportadoras();
                this.utilizadores = dados.getUtilizadores();
                this.artigos = dados.getArtigos();
                this.encomendas = dados.getEncomendas();
                this.dateTimeDoSistema = dados.getDateTimeDoSistema();
                this.loginParser = dados.getLoginParser();
                this.novasFormulas = dados.getNovasFormulas();
                this.novasMargens = dados.getNovasMargens();
                this.vendidos = dados.getVendidos();
                this.encomendasFinalizadas = dados.getEncomendasFinalizadas();
                this.faturas = dados.getFaturas();
                idUtilizador = dados.getIdUtilizador();
                idTransportadora = dados.getIdTransportadora();
                idEncomenda = dados.getIdEncomenda();
            }
        }catch (FicheiroDeObjectosCorrumpido e){
            e.printStackTrace();
            Dados dados = new Dados();
            this.transportadoras = dados.getTransportadoras();
            this.utilizadores = dados.getUtilizadores();
            this.artigos = dados.getArtigos();
            this.encomendas = dados.getEncomendas();
            this.dateTimeDoSistema = dados.getDateTimeDoSistema();
            this.loginParser = dados.getLoginParser();
            this.novasFormulas = dados.getNovasFormulas();
            this.novasMargens = dados.getNovasMargens();
            this.vendidos = dados.getVendidos();
            this.encomendasFinalizadas = dados.getEncomendasFinalizadas();
            this.faturas = dados.getFaturas();
            idUtilizador = dados.getIdUtilizador();
            idTransportadora = dados.getIdTransportadora();
            idEncomenda = dados.getIdEncomenda();
        }
    }

    public Dados(HashMap<String, Transportadora> transportadoras, HashMap<String, Utilizador> utilizadores, HashMap<String, Artigos> artigos, HashMap<String, Encomenda> encomendas, LocalDateTime data, LoginParser login, HashMap<Integer, String> formulas, HashMap<Integer, Float> margens, List<Artigos> vendidos, List<Encomenda> finalizadas, HashMap<Comprador, String> fatura, int utilizador, int transportadora, int encomenda){
        this.transportadoras = transportadoras;
        this.utilizadores = utilizadores;
        this.artigos = artigos;
        this.encomendas = encomendas;
        this.dateTimeDoSistema = data;
        this.loginParser = login;
        this.novasFormulas = formulas;
        this.novasMargens = margens;
        this.vendidos = vendidos;
        this.encomendasFinalizadas = finalizadas;
        this.faturas = fatura;
        idUtilizador = utilizador;
        idTransportadora = transportadora;
        idEncomenda = encomenda;
    }

    public Dados(Dados d){
        this.transportadoras = d.getTransportadoras();
        this.utilizadores = d.getUtilizadores();
        this.artigos = d.getArtigos();
        this.encomendas = d.getEncomendas();
        this.dateTimeDoSistema = d.getDateTimeDoSistema();
        this.loginParser = d.getLoginParser();
        this.novasFormulas = d.getNovasFormulas();
        this.novasMargens = d.getNovasMargens();
        this.vendidos = d.getVendidos();
        this.encomendasFinalizadas = d.getEncomendasFinalizadas();
        this.faturas = d.getFaturas();
        idUtilizador = d.getIdUtilizador();
        idTransportadora = d.getIdTransportadora();
        idEncomenda = d.getIdEncomenda();
    }

    public HashMap<String, Transportadora> getTransportadoras() {
        return transportadoras;
    }

    public void setTransportadoras(HashMap<String, Transportadora> transportadoras) {
        this.transportadoras = transportadoras;
    }

    public HashMap<String, Utilizador> getUtilizadores() {
        return utilizadores;
    }

    public void setUtilizadores(HashMap<String, Utilizador> utilizadores) {
        this.utilizadores = utilizadores;
    }

    public HashMap<String, Artigos> getArtigos() {
        return artigos;
    }

    public void setArtigos(HashMap<String, Artigos> artigos) {
        this.artigos = artigos;
    }

    public HashMap<String, Encomenda> getEncomendas() {
        return encomendas;
    }

    public void setEncomendas(HashMap<String, Encomenda> encomendas) {
        this.encomendas = encomendas;
    }

    public LocalDateTime getDateTimeDoSistema() {
        return dateTimeDoSistema;
    }

    public void setDateTimeDoSistema(LocalDateTime dateTimeDoSistema) {
        this.dateTimeDoSistema = dateTimeDoSistema;
    }

    public LoginParser getLoginParser() {
        return loginParser;
    }

    public void setLoginParser(LoginParser loginParser) {
        this.loginParser = loginParser;
    }

    public HashMap<Integer, String> getNovasFormulas() {
        return novasFormulas;
    }

    public void setNovasFormulas(HashMap<Integer, String> novasFormulas) {
        this.novasFormulas = novasFormulas;
    }

    public HashMap<Integer, Float> getNovasMargens() {
        return novasMargens;
    }

    public void setNovasMargens(HashMap<Integer, Float> novasMargens) {
        this.novasMargens = novasMargens;
    }

    public List<Artigos> getVendidos() {
        return vendidos;
    }

    public void setVendidos(List<Artigos> vendidos) {
        this.vendidos = vendidos;
    }

    public List<Encomenda> getEncomendasFinalizadas() {
        return encomendasFinalizadas;
    }

    public void setEncomendasFinalizadas(List<Encomenda> encomendasFinalizadas) {
        this.encomendasFinalizadas = encomendasFinalizadas;
    }

    public HashMap<Comprador, String> getFaturas() {
        return faturas;
    }

    public void setFaturas(HashMap<Comprador, String> faturas) {
        this.faturas = faturas;
    }

    public int getIdUtilizador() {
        return idUtilizador;
    }

    public void setIdUtilizador(int idUtilizador) {
        idUtilizador = idUtilizador;
    }

    public int getIdTransportadora() {
        return idTransportadora;
    }

    public void setIdTransportadora(int idTransportadora) {
        idTransportadora = idTransportadora;
    }

    public int getIdEncomenda() {
        return idEncomenda;
    }

    public void setIdEncomenda(int idEncomenda) {
        idEncomenda = idEncomenda;
    }

    public void salvarEstado() throws IOException {
        Parser.escreveFicheiro("objetos.ser", this);
    }

    public boolean verificaExistenciaComprador(String email){
        return this.loginParser.verificaExistenciaComprador(email);
    }

    public boolean verificaExistenciaVendedor(String email){
        return this.loginParser.verificaExistenciaVendedor(email);
    }

    public boolean verificaExistenciaTransportadora(String email){
        return this.loginParser.verificaExistenciaTransporadora(email);
    }

    public void criarNovoComprador(String email, String nome, String morada, int nif, String password){
        Comprador novoComprador = new Comprador(email, nome, morada, nif);
        this.utilizadores.put(String.valueOf(novoComprador.getId()), novoComprador);
        this.loginParser.adicionaConta(TipoConta.Comprador, novoComprador.getId(), email, password);
    }

    public void criarNovoVendedor(String email, String nome, String morada, int nif, String password){
        Vendedor novoVendedor = new Vendedor(email, nome, morada, nif);
        this.utilizadores.put(String.valueOf(novoVendedor.getId()), novoVendedor);
        this.loginParser.adicionaConta(TipoConta.Vendedor, novoVendedor.getId(), email, password);
    }

    public void criarNovaTransportadora(String email, String nome, int nif, float margem, TipoFormula tipo, String formula, String password){
        Transportadora novaTransportadora = new Transportadora(nome, nif, email, margem, tipo, formula);
        this.transportadoras.put(String.valueOf(novaTransportadora.getId()), novaTransportadora);
        this.loginParser.adicionaConta(TipoConta.Transportadora, novaTransportadora.getId(), email, password);
    }

    public void criarNovaTransportadoraPremium(String email, String nome, int nif, float margem, TipoFormula tipo, String formula, String formulaPremium, String password){
        TransportadoraPremium novaTransportadoraPremium = new TransportadoraPremium(nome, nif, email, margem, tipo, formula, formulaPremium);
        this.transportadoras.put(String.valueOf(novaTransportadoraPremium.getId()), novaTransportadoraPremium);
        this.loginParser.adicionaConta(TipoConta.Transportadora, novaTransportadoraPremium.getId(), email, password);
    }

    public void adicionaSapatilhaPremium(int idVendedor, boolean usado, String descricao, float preco, float desconto, Estado estado, int numUtilizadores, int idTransportadora, int stock, String autor, int tamanho, boolean atilhos, String cor, int anoColecao ){
        SapatilhasPremium novaSapatilhaPremium = new SapatilhasPremium(idVendedor, usado, descricao, preco, desconto, estado, numUtilizadores, getTransportadoraPremiumById(idTransportadora), stock, tamanho, atilhos, cor, anoColecao, autor);
        this.artigos.put(novaSapatilhaPremium.getBarCode(), novaSapatilhaPremium);
        getVendedorById(idVendedor).adicionaArtigoEmVenda(novaSapatilhaPremium);
        getTransportadoraPremiumById(idTransportadora).adicionaArtigoAssociado(novaSapatilhaPremium);
    }

    public void adicionaSapatilha(int idVendedor, boolean usado, String descricao, float preco, float desconto, Estado estado, int numUtilizadores, int idTransportadora, int stock, int tamanho, boolean atilhos, String cor, int anoColecao){
        Sapatilhas novaSapatilha = new Sapatilhas(idVendedor, usado, descricao, preco, desconto, estado, numUtilizadores, getTransportadoraById(idTransportadora), stock, tamanho, atilhos, cor, anoColecao);
        this.artigos.put(novaSapatilha.getBarCode(), novaSapatilha);
        getVendedorById(idVendedor).adicionaArtigoEmVenda(novaSapatilha);
        getTransportadoraById(idTransportadora).adicionaArtigoAssociado(novaSapatilha);
    }

    public void adicionaTshirt(int idVendedor, boolean usado, String descricao, float preco, float desconto, Estado estado, int numUtilizadores, int idTransportadora, int stock, TamanhoTShirt tamanho, PadraoTShirt padrao){
        TShirst novaTshirt = new TShirst(idVendedor, usado, descricao, preco, desconto, estado, numUtilizadores, getTransportadoraById(idTransportadora), stock, tamanho, padrao);
        this.artigos.put(novaTshirt.getBarCode(), novaTshirt);
        getVendedorById(idVendedor).adicionaArtigoEmVenda(novaTshirt);
        getTransportadoraById(idTransportadora).adicionaArtigoAssociado(novaTshirt);
    }

    public void adicionaMala(int idVendedor, boolean usado, String descricao, float preco, float desconto, Estado estado, int numUtilizadores, int idTransportadora, int stock, Dimenssao dimenssao, TexturaMala textura, int anoColecao){
        Malas novaMala = new Malas(idVendedor, usado, descricao, preco, desconto, estado, numUtilizadores, getTransportadoraById(idTransportadora), stock, dimenssao, textura, anoColecao);
        this.artigos.put(novaMala.getBarCode(), novaMala);
        getVendedorById(idVendedor).adicionaArtigoEmVenda(novaMala);
        getTransportadoraById(idTransportadora).adicionaArtigoAssociado(novaMala);
    }

    public void adicionaMalaPremium(int idVendedor, boolean usado, String descricao, float preco, float desconto, Estado estado, int numUtilizadores, Dimenssao dimenssao, TexturaMala textura, int anoColecao, int idTransportadora, int stock, String autor){
        MalasPremium novaMalaPremium = new MalasPremium(idVendedor, usado, descricao, preco, desconto, estado, numUtilizadores, dimenssao, textura, anoColecao, getTransportadoraPremiumById(idTransportadora), stock, autor);
        this.artigos.put(novaMalaPremium.getBarCode(), novaMalaPremium);
        getVendedorById(idVendedor).adicionaArtigoEmVenda(novaMalaPremium);
        getTransportadoraPremiumById(idTransportadora).adicionaArtigoAssociado(novaMalaPremium);
    }

    public static int getNovoIdUtilizador(){
        int id = idUtilizador;
        idUtilizador++;
        return id;
    }

    public static int getNovoIdTransportadora(){
        int id = idTransportadora;
        idTransportadora++;
        return id;
    }

    public static int getNovoIdEncomenda(){
        int id = idEncomenda;
        idEncomenda++;
        return id;
    }

    public Vendedor getVendedorById(int id){
        for(Utilizador utilizador : utilizadores.values()){
            if(utilizador instanceof Vendedor && utilizador.getId() == id){
                return (Vendedor) utilizador;
            }
        }
        return null;
    }

    public Comprador getCompradorById(int id){
        for(Utilizador utilizador : utilizadores.values()){
            if(utilizador instanceof Comprador && utilizador.getId() == id){
                return (Comprador) utilizador;
            }
        }
        return null;
    }

    public Encomenda getEncomendaById(int id){
        for(Encomenda enc : encomendas.values()){
            if(enc.getId() == id) return enc;
        }
        return null;
    }

    public TransportadoraPremium getTransportadoraPremiumById(int id){
        for(Transportadora premium : transportadoras.values()){
            if(premium instanceof TransportadoraPremium && premium.getId() == id){
                return (TransportadoraPremium) premium;
            }
        }
        return null;
    }

    public Transportadora getTransportadoraById(int id){
        for(Transportadora trans : transportadoras.values()){
            if(trans.getId() == id){
                return trans;
            }
        }
        return null;
    }

    public List<Encomenda> getEncomendasDoComprador(int id){
        List<Encomenda> encomendas = new ArrayList<>();
        for(Encomenda enc : this.encomendas.values()){
            if(enc.getComprador() == getCompradorById(id)){
                encomendas.add(enc);
            }
        }
        return encomendas;
    }

    public float getPrecosEncomenda(int idEncomenda){
        return getEncomendaById(idEncomenda).getPrecoProdutos();
    }

    public Artigos getArtigoByCode(String barcode){
        for(Artigos art : artigos.values()){
            if(Objects.equals(art.getBarCode(), barcode)){
                return art;
            }
        }
        return null;
    }

    public String adicionaArtigoAEncomenda(Encomenda enc, String barcode){
        Artigos copiaArt = getArtigoByCode(barcode);
        if(!enc.getArtigos().contains(copiaArt)) copiaArt.setPrice(copiaArt.getPrice() - copiaArt.getDicountPrice());
        if(copiaArt.getStock() <= 0){
            String sb = "Já não existe mais stock desse produto!";
            artigos.remove(barcode);
            int id = copiaArt.getIdVendedor();
            getVendedorById(id).removerArtigoEmVenda(copiaArt);
            //copiaArt.getTransportadoraAssociada().removeArtigoAssociado(copiaArt);

            return sb;
        }
        copiaArt.setStock(copiaArt.getStock()-1);
        return enc.adicionaArtigoAEncomenda(copiaArt);
    }

    public boolean checkEncomendasComprador(Comprador c){
        for(Encomenda enc : encomendas.values()){
            if(enc.getComprador() == c){
                return true;
            }
        }
        return false;
    }

    public Encomenda getEncomendaAtivaId(int id){
        Comprador copia = getCompradorById(id);
        if(encomendas.size() == 0 || !checkEncomendasComprador(copia)){
            ArrayList<Artigos> artigosEnc = new ArrayList<>();
            Encomenda novaEncomenda = new Encomenda(copia, artigosEnc);
            this.adicionaEncomenda(novaEncomenda);
            return novaEncomenda;
        }else{
            for(Encomenda enc : encomendas.values()){
                if(enc.getComprador() == copia && enc.getEstado() == EstadoEncomenda.Pendente) return enc;
            }
            ArrayList<Artigos> artigosEnc = new ArrayList<>();
            return new Encomenda(copia, artigosEnc);
        }
    }

    public TipoFormula getTipoFormulaTransportadora(int idTransportadora){
        return getTransportadoraById(idTransportadora).getTipoFormula();
    }

    public ArrayList<Artigos> getArtigosAssociados(int idTransportadora){
        return getTransportadoraById(idTransportadora).getArtigosAssociados();
    }

    public void changeTipoFormula(int idTransportadora){
        Transportadora temp = getTransportadoraById(idTransportadora);
        if(temp.getTipoFormula() == TipoFormula.Default) temp.setTipoFormula(TipoFormula.Customized);
        else temp.setTipoFormula(TipoFormula.Default);
    }

    public ArrayList<Artigos> getEmVenda(int idVendedor){
        return getVendedorById(idVendedor).getEmVenda();
    }

    public float getMargem(int idTransportadora){
        return getTransportadoraById(idTransportadora).getMargemLucro();
    }

    public String getFormula(int idTransportadora){
        return getTransportadoraById(idTransportadora).getFormula();
    }

    public void clearFaturas(){
        this.faturas.clear();
    }

    public int login(TipoConta tipo, String email, String password){
        return this.loginParser.validaLogin(tipo, email, password);
    }

    public void atualizaComprador(int idComprador, String email, String nome, String morada, int nif) throws IOException {
        Comprador c = getCompradorById(idComprador);
        this.loginParser.alteraEmail(TipoConta.Comprador, c.getEmail(), email, c.getId());
        c.setEmail(email);
        c.setNome(nome);
        c.setMorada(morada);
        c.setNif(nif);
    }

    public void atualizaVendedor(int idVendedor, String email, String nome, String morada, int nif) throws IOException {
        Vendedor v = getVendedorById(idVendedor);
        this.loginParser.alteraEmail(TipoConta.Vendedor, v.getEmail(), email, v.getId());
        v.setEmail(email);
        v.setNome(nome);
        v.setMorada(morada);
        v.setNif(nif);
    }

    public void atualizaTransportadora(int idTransportadora, String email, String nome, int nif, float margem) throws IOException{
        Transportadora t = getTransportadoraById(idTransportadora);
        this.loginParser.alteraEmail(TipoConta.Transportadora, t.getEmail(), email, t.getId());
        t.setEmail(email);
        t.setNome(nome);
        t.setNif(nif);
        t.setMargemLucro(margem);
    }

    public void atualizaFormula(int idTransportadora, String novaFormula){
        getTransportadoraById(idTransportadora).setFormula(novaFormula);
    }

    public void atualizaMargem(int idTransportadora, float novaMargem){
        getTransportadoraById(idTransportadora).setMargemLucro(novaMargem);
    }

    public void enviaFaturas(int idComprador){
        if(faturas.size() > 0){
            for(Map.Entry<Comprador, String> comp : faturas.entrySet()){
                if(comp.getKey() == getCompradorById(idComprador)){
                    System.out.println(comp.getValue());
                }
            }
        }
        clearFaturas();
    }

    public String pagarEncomenda(int idEncomenda){
        Encomenda enc = this.getEncomendaById(idEncomenda);
        String msg = enc.pagarEncomenda(dateTimeDoSistema);

        for(Artigos arts : enc.getArtigos()){
            for(Utilizador vendedor : utilizadores.values()){
                if(vendedor instanceof Vendedor){
                    if(((Vendedor) vendedor).getEmVenda().contains(arts)){
                        ((Vendedor) vendedor).adicionaArtigoVendido(arts);
                        ((Vendedor) vendedor).setValorFaturado(((Vendedor) vendedor).getValorFaturado() + (arts.getPrice()-arts.getDicountPrice()));
                    }
                }
            }
            for(Transportadora trans : transportadoras.values()){
                if(trans instanceof TransportadoraPremium) {
                    if (arts.getTransportadoraAssociada() == trans) {
                        trans.setValorFaturado(trans.getValorFaturado() + (((TransportadoraPremium) trans).calculaFormulaPremium(arts.getPrice()) - (arts.getPrice()-arts.getDicountPrice())));
                    }
                }else{
                    if(arts.getTransportadoraAssociada() == trans){
                        trans.setValorFaturado(trans.getValorFaturado() + (trans.calculaFormula(arts.getPrice()) - (arts.getPrice()-arts.getDicountPrice())));
                    }
                }
            }
            vendidos.add(arts);
        }
        encomendasFinalizadas.add(enc);
        return msg;
    }

    public void removerEncomenda(int idEncomenda){
        Iterator<Map.Entry<String, Encomenda>> iterator = encomendas.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry<String, Encomenda> entry = iterator.next();
            if(entry.getValue().getId() == idEncomenda){
                iterator.remove();
                break;
            }
        }
    }

    public String devolverEncomenda(int idEncomenda){
        Encomenda enc = getEncomendaById(idEncomenda);
        String msg = enc.devolucao(dateTimeDoSistema);

        for(Artigos arts : enc.getArtigos()){
            for(Utilizador vendedor : utilizadores.values()){
                if(vendedor instanceof Vendedor){
                    if(((Vendedor) vendedor).getEmVenda().contains(arts)){
                        ((Vendedor) vendedor).removerArtigoVendido(arts);
                        ((Vendedor) vendedor).setValorFaturado(((Vendedor) vendedor).getValorFaturado() - (arts.getPrice()-arts.getDicountPrice()));
                    }
                }
            }
            //this.dados.getVendidos().add(arts);
        }
        for(Artigos arts : enc.getArtigos()){
            for(Artigos artigos1 : artigos.values()){
                if(Objects.equals(arts.getBarCode(), artigos1.getBarCode())){
                    artigos1.aumentaStock();
                }
            }
        }
        removerEncomenda(idEncomenda);
        return msg;
    }

    public void adicionaNovaFormula(int idTransportadora, String novaFormula, float novaMargem){
        this.novasFormulas.put(idTransportadora, novaFormula);
        this.novasMargens.put(idTransportadora, novaMargem);
    }

    public String listTransportadoras(){
        StringBuilder sb = new StringBuilder();
        for(Transportadora trans : transportadoras.values()){
            if(!(trans instanceof TransportadoraPremium)) {
                sb.append(trans.toString());
                sb.append("\n");
            }
        }
        if(sb.length() > 2){
            sb.setLength(sb.length() - 2);
        }
        return sb.toString();
    }

    public String listTransportadorasPremium(){
        StringBuilder sb = new StringBuilder();
        for(Transportadora trans : transportadoras.values()){
            if(trans instanceof TransportadoraPremium copia) {
                sb.append(copia.toString());
                sb.append("\n");
            }
        }
        if(sb.length() > 2){
            sb.setLength(sb.length() - 2);
        }
        return sb.toString();
    }

    public String listArtigosAssociadosTransportadora(int idTransportadora){
        StringBuilder sb = new StringBuilder();
        for(Artigos arts : this.getArtigosAssociados(idTransportadora)){
            sb.append(arts.toString());
            sb.append("\n");
        }
        if(sb.length() > 2){
            sb.setLength(sb.length()-2);
        }
        return sb.toString();
    }

    public String listArtigosEmVenda(int idVendedor){
        StringBuilder sb = new StringBuilder();
        for(Artigos arts : getVendedorById(idVendedor).getEmVenda()){
            sb.append(arts.toString());
            sb.append("\n");
        }
        if(sb.length() > 2){
            sb.setLength(sb.length()-2);
        }
        return sb.toString();
    }

    public String listArtigos(){
        StringBuilder sb = new StringBuilder();
        for(Artigos arts : artigos.values()){
            sb.append(arts.toString());
            sb.append("\n");
        }
        if(sb.length()>2){
            sb.setLength(sb.length()-2);
        }
        return sb.toString();
    }

    public String listEncomendasComprador(List<Encomenda> encs){
        StringBuilder sb = new StringBuilder();
        for(Encomenda enc : encs){
            sb.append(enc.toString());
            sb.append("\n");
        }
        if(sb.length()>2) sb.setLength(sb.length()-2);
        return sb.toString();
    }

    public String listVendedores(){
        StringBuilder sb = new StringBuilder();
        for(Utilizador users : utilizadores.values()){
            if(users instanceof Vendedor){
                sb.append(users.toString());
                sb.append("\n");
            }
        }
        if(sb.length()>2)sb.setLength(sb.length()-2);
        return sb.toString();
    }

    public String listCompradores(){
        StringBuilder sb = new StringBuilder();
        for(Utilizador users : utilizadores.values()){
            if(users instanceof Comprador){
                sb.append(users.toString());
                sb.append("\n");
            }
        }
        if(sb.length()>2)sb.setLength(sb.length()-2);
        return sb.toString();
    }

    public String listTopRankingVendedores(){
        ArrayList<Vendedor> topVendedores = new ArrayList<>();
        for(Utilizador u : utilizadores.values()){
            if(u instanceof Vendedor){
                topVendedores.add((Vendedor) u);
            }
        }
        Collections.sort(topVendedores);
        int lugar = 1;
        StringBuilder sb = new StringBuilder();
        for(Vendedor v : topVendedores){
            sb.append(lugar).append(" --> ").append(v.toString());
            sb.append("\n");
            lugar++;
        }
        return sb.toString();
    }

    public String listTopRankingTransportadorasPremium(){
        ArrayList<TransportadoraPremium> topTransportadorasPremium = new ArrayList<>();
        for(Transportadora t : transportadoras.values()){
            if(t instanceof TransportadoraPremium){
                topTransportadorasPremium.add((TransportadoraPremium) t);
            }
        }
        Collections.sort(topTransportadorasPremium);
        int lugar = 1;
        StringBuilder sb = new StringBuilder();
        for(TransportadoraPremium t : topTransportadorasPremium){
            sb.append(lugar).append(" --> ").append(t.toString());
            sb.append("\n");
            lugar++;
        }
        return sb.toString();
    }

    public String listTopRankingTransportadoras(){
        ArrayList<Transportadora> topTransportadoras = new ArrayList<>();
        for(Transportadora t : transportadoras.values()){
            if(!(t instanceof TransportadoraPremium)){
                topTransportadoras.add(t);
            }
        }
        Collections.sort(topTransportadoras);
        int lugar = 1;
        StringBuilder sb = new StringBuilder();
        for(Transportadora t : topTransportadoras){
            sb.append(lugar).append(" --> ").append(t.toString());
            sb.append("\n");
            lugar++;
        }
        return sb.toString();
    }

    public void avancaTempo(int dias){
        this.dateTimeDoSistema = dateTimeDoSistema.plusDays(dias);
    }

    public void expedirEncomendas(){
        for(Encomenda encs : encomendasFinalizadas){ // Expedir encomendas pagas
            encs.setEstado(EstadoEncomenda.Expedida);
            encs.setUltimaAlteracao(dateTimeDoSistema);
        }
        encomendasFinalizadas.clear();
    }

    public void atualizarMargens(){
        for(Map.Entry<Integer, Float> entrada : novasMargens.entrySet()){ // Atualizar margens
            int id = entrada.getKey();
            float margem = entrada.getValue();
            atualizaMargem(id, margem);
        }
        this.novasMargens.clear();
    }

    public void atualizaFormulas(){
        for(Map.Entry<Integer, String> entrada : novasFormulas.entrySet()){ // Atualizar formulas
            int id = entrada.getKey();
            String formula = entrada.getValue();
            atualizaFormula(id, formula);
        }
        this.novasFormulas.clear();
    }

    public void atualizaPrecosDescontos(){
        int anoAtual = dateTimeDoSistema.getYear();
        for(Artigos arts : artigos.values()){ // Atualizar preços de descontos
            if(arts instanceof SapatilhasPremium){
                if(((SapatilhasPremium) arts).getCollectionYear() > anoAtual) ((SapatilhasPremium) arts).atualizarPrecoSapatilhasPremium(anoAtual);
            } else if (arts instanceof Sapatilhas) {
                if(((Sapatilhas) arts).getCollectionYear() > anoAtual) ((Sapatilhas) arts).atualizaPrecoDesconto(anoAtual);
            } else if (arts instanceof MalasPremium) {
                if(((MalasPremium) arts).getCollectionYear() > anoAtual) ((MalasPremium) arts).atualizaPrecoMalasPremium(anoAtual);
            } else if (arts instanceof Malas) {
                if(((Malas) arts).getCollectionYear() > anoAtual) ((Malas) arts).atualizaPrecoDesconto(anoAtual);
            }
        }
    }

    public void entregarEncomenda(){
        for(Encomenda encs : encomendas.values()){ // Entregar a Encomenda
            Duration duration = Duration.between(encs.getUltimaAlteracao(), dateTimeDoSistema);
            long horasPassadas = duration.toHours();
            if(encs.getEstado() == EstadoEncomenda.Expedida && horasPassadas >= 24){
                encs.setEstado(EstadoEncomenda.Entregue);
                encs.setDataEntrega(dateTimeDoSistema);
                String fatura = "Foi entregue a encomenda: " + encs.getId() + " ; " + "Artigos: " + encs.getArtigos() + " ; " + "Preço dos produtos: " + encs.getPrecoProdutos() + " ; " + "Preço final: " + encs.getCustosExpedicao();
                faturas.put(encs.getComprador(), fatura);
            }
        }
    }

    public void adicionaEncomenda(Encomenda encomenda){
        this.encomendas.put(String.valueOf(encomenda.getId()), encomenda);
    }

    public Dados clone(Dados d){
        return new Dados(this);
    }

    @Override
    public String toString(){
        return "Transportadoras: " + this.transportadoras + ";\nUtilizadores: " + this.utilizadores + ";\nArtigos: " + this.artigos + ";\nEncomendas: " + this.encomendas;
    }

    @Override
    public boolean equals(Object o){
        if(this == o)return true;
        if(o == null || this.getClass() != o.getClass()) return false;

        Dados d = (Dados) o;
        return this.transportadoras.equals(d.getTransportadoras()) && this.utilizadores.equals(d.getUtilizadores()) && this.artigos.equals(d.getArtigos())
                && this.encomendas.equals(d.getEncomendas()) && this.dateTimeDoSistema == d.getDateTimeDoSistema() && this.loginParser.equals(d.getLoginParser())
                && this.novasFormulas.equals(d.getNovasFormulas()) && this.novasMargens.equals(d.getNovasMargens()) && this.vendidos.equals(d.getVendidos())
                && this.encomendasFinalizadas.equals(d.getEncomendasFinalizadas()) && this.faturas.equals(d.getFaturas());
    }
}
