
/**
 * Write a description of class LinhadeEncomenda here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class LinhadeEncomenda
{
    // 7.
    // a)
    private String idProduto;
    private String descricaoProduto;
    private double precoProdutoSemImpostos;
    private int quantidadeEncomenda;
    private double impostoPercentagem;
    private double desconto;
    
    public LinhadeEncomenda(){
        this.idProduto = "vazia";
        this.descricaoProduto = "vazia";
        this.precoProdutoSemImpostos = 0;
        this.quantidadeEncomenda = 0;
        this.impostoPercentagem = 0;
        this.desconto = 0;
    }
    
    public LinhadeEncomenda(String id, String desc, double precoSemImp, int quantidade, double imposto, double desconto){
        this.idProduto = id;
        this.descricaoProduto = desc;
        this.precoProdutoSemImpostos = precoSemImp;
        this.quantidadeEncomenda = quantidade;
        this.impostoPercentagem = imposto;
        this.desconto = desconto;
    }
    
    public LinhadeEncomenda(LinhadeEncomenda linha){
        this.idProduto = linha.getId();
        this.descricaoProduto = linha.getDesc();
        this.precoProdutoSemImpostos = linha.getPreco();
        this.quantidadeEncomenda = linha.getQuantidade();
        this.impostoPercentagem = linha.getImposto();
        this.desconto = linha.getDesconto();
    }
    
    // Gets
    
    public String getId(){
        return this.idProduto;
    }
    
    public String getDesc(){
        return this.descricaoProduto;
    }
    
    public double getPreco(){
        return this.precoProdutoSemImpostos;
    }
    
    public int getQuantidade(){
        return this.quantidadeEncomenda;
    }
    
    public double getImposto(){
        return this.impostoPercentagem;
    }
    
    public double getDesconto(){
        return this.desconto;
    }
    
    // Sets
    
    public void setId(String novoID){
        this.idProduto = novoID;
    }
    
    public void setDesc(String novaDesc){
        this.descricaoProduto = novaDesc;
    }
    
    public void setPreco(double novoPreco){
        this.precoProdutoSemImpostos = novoPreco;
    }
    
    public void setQuantidade(int novaQuantidade){
        this.quantidadeEncomenda = novaQuantidade;
    }
    
    public void setImposto(double novoImposto){
        this.impostoPercentagem = novoImposto;
    }
    
    public void setDesconto(double novoDesconto){
        this.desconto = novoDesconto;
    }
    
    // Clone
    
    public LinhadeEncomenda clone(){
        return new LinhadeEncomenda(this);
    }
    
    // toString
    
    public String toString(){
        return "A linha de encomenda tem as seguintes caracteristicas: \n" + "Referencia do produto: " + this.idProduto + "\n" + "Descricao do produto: " + this.descricaoProduto + "\n"
        + "Preco do produto antes de impostos: " + this.precoProdutoSemImpostos + "\n" + "Quantidade da encomenda: " + this.quantidadeEncomenda + "\n" + "Regime de Imposto em percentagem: " 
        + this.impostoPercentagem + "\n" + "Percentagem de desconto: " + this.desconto + "\n";
    }
    
    // Equals
    
    public boolean equals(Object o){
        if(this == o)
            return true;
        if((o == null) || (this.getClass() != o.getClass()))
            return false;
            
        LinhadeEncomenda p = (LinhadeEncomenda) o;
        return (this.idProduto == p.getId() && this.descricaoProduto == p.getDesc() && this.precoProdutoSemImpostos == p.getPreco() && this.quantidadeEncomenda == p.getQuantidade()
        && this.impostoPercentagem == p.getImposto() && this.desconto == p.getDesconto());
    }
    
    // b) 
    
    public double calculaValorLinhaEnc(){
        double precoComDesconto = (this.precoProdutoSemImpostos * this.quantidadeEncomenda) - ((this.precoProdutoSemImpostos * this.quantidadeEncomenda) * this.desconto/100);
        double precoFinal = precoComDesconto - (precoComDesconto * this.impostoPercentagem/100);
        return precoFinal;
    }
    
    // c) 
    
    public double calculaValorDesconto(){
        return ((this.precoProdutoSemImpostos * this.quantidadeEncomenda) * this.desconto/100);
    }
    

    
}

