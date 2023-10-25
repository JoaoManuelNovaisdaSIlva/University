import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.stream.Collectors;

/**
 * Write a description of class Encomenda here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Encomenda{
    private String nome;
    private int nFiscal;
    private String morada;
    private int nEncomenda;
    private LocalDate data;
    private List<LinhadeEncomenda> linhaEnco;
    
    public Encomenda(){
        this.nome = "";
        this.nFiscal = 0;
        this.morada = "";
        this.nEncomenda = 0;
        this.data = LocalDate.now();
        this.linhaEnco = new ArrayList();
    }
    
    public Encomenda(String nome, int fiscal, String morada, int encomenda, LocalDate data, List<LinhadeEncomenda> linha){
        this.nome = nome;
        this.nFiscal = fiscal;
        this.morada = morada;
        this.nEncomenda = encomenda;
        this.data = data;
        this.linhaEnco = linha;
    }
    
    public Encomenda(Encomenda e){
        this.nome = e.getNome();
        this.nFiscal = e.getNFiscal();
        this.morada = e.getMorada();
        this.nEncomenda = e.getNEncomenda();
        this.data = e.getData();
        this.linhaEnco = e.getLinha();
    }
    //gets 
    
    public String getNome(){
        return this.nome;
    }
    
    public int getNFiscal(){
        return this.nFiscal;
    }
    
    public String getMorada(){
        return this.morada;
    }
    
    public int getNEncomenda(){
        return this.nEncomenda;
    }
    
    public LocalDate getData(){
        return this.data;
    }
    
    public List<LinhadeEncomenda> getLinha(){
        ArrayList<LinhadeEncomenda> res = new ArrayList();
        for(LinhadeEncomenda c: this.linhaEnco)
            res.add(c.clone());
        return res;
    }
    
    // sets
    
    public void setNome(String novoNome){
        this.nome = novoNome;
    }
    
    public void setNFiscal(int nfisc){
        this.nFiscal = nfisc;
    }
    
    public void setMorada(String novaMorada){
        this.morada = novaMorada;
    }
    
    public void setNEncomenda(int novonEncomenda){
        this.nEncomenda = novonEncomenda;
    }
    
    public void setData(LocalDate novaData){
        this.data = novaData;
    }
    
    //clone
    
    public Encomenda clone(){
        return new Encomenda(this);
    }
    
    //Equals
    
    public boolean equals(Object o){
        if(this == o)
            return true;
        if((o == null) || (this.getClass() != o.getClass()))
            return false;
        Encomenda p = (Encomenda) o;
        return (this.nome == p.getNome() && this.nFiscal == p.getNFiscal() && this.morada == p.getMorada()
        && this.nEncomenda == p.getNEncomenda() && this.data == p.getData() && this.linhaEnco.equals(p.getLinha()));
    }
    
    // toString
    
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Nome do Cliente = ");
        sb.append(this.nome);
        sb.append("Numero fiscal = ");
        sb.append(this.nFiscal);
        sb.append("Morada do Cliente = ");
        sb.append(this.morada);
        sb.append("Numero da Encomenda = ");
        sb.append(this.nEncomenda);
        sb.append("Data da Encomenda = ");
        sb.append(this.data);
        // array?
        
        return sb.toString();
    }
    
    // b)
    
    public double calculaValorTotal(){
        double r = 0;
        for(LinhadeEncomenda l: this.linhaEnco)
            r += l.calculaValorLinhaEnc();
        
        return r;
    }
    
    // com recurso a iteradores internos
    
    public double calculaValorToral_I(){
        return this.linhaEnco.stream().mapToDouble(LinhadeEncomenda::calculaValorLinhaEnc).sum();
    }
    
    // c)
    
    public double calculaValorDesconto(){
        return this.linhaEnco.stream().mapToDouble(LinhadeEncomenda::calculaValorDesconto).sum();
    }
    
    /*
     * Método que implementa C) com recurso a iteradores externos 
     */
    
    public double calculaValorDesconto_E() {
      double r = 0;
      for(LinhadeEncomenda le : this.linhaEnco) {
        r += le.calculaValorDesconto();
      }
      return r;
    }
    
    
    /**
     * D)
     */
    public int numeroTotalProdutos() {
        int r = 0;
        for(LinhadeEncomenda le : this.linhaEnco) {
            r += le.getQuantidade();
        }
        return r;
    }

    /**
     * E)
     */
    public boolean existeProdutoEncomenda(String codProd) {
      boolean existe = false;
      int i=0;
         
      while(!existe && i< this.linhaEnco.size()) {
        if(this.linhaEnco.get(i).getId().equals(codProd)) 
           existe = true;
        i++;
      }
      return existe;
    }
     
      
    // codificação alternativa de E) com recurso a iteradores internos
    public boolean existeNaEncomenda(String codProd) {
      return this.linhaEnco.stream().anyMatch(e -> (e.getId()).equals(codProd));
    }
    
    
    /**
     * F)
     */
    public void adicionaLinha(LinhadeEncomenda linha) {
        this.linhaEnco.add(linha.clone());
    }

    /**
     * G)
     */
    
    public void removeProduto(String codProd){
      for(Iterator<LinhadeEncomenda> it = this.linhaEnco.iterator() ; it.hasNext();){
          LinhadeEncomenda le = it.next();
          if(le.getId().equals(codProd)){
              it.remove();
          }
      }
    }

    //calcula o número de produtos encomendados nesta encomenda
    public int numProdutos() {
     
      return this.linhaEnco.stream().mapToInt(LinhadeEncomenda::getQuantidade).sum();
    }    
    
    
    
    
    
    //calcula a lista dos produtos de uma encomenda
    public List<String> getListaProdutos() {
      return this.linhaEnco.stream().map(LinhadeEncomenda::getId).distinct().collect(Collectors.toList());    
    }
}
