package Model;

import java.util.ArrayList;
import java.util.Objects;

public class Vendedor extends Utilizador implements Comparable<Vendedor>{
    private ArrayList<Artigos> emVenda;
    private ArrayList<Artigos> vendidos;
    private float valorFaturado;

    public Vendedor(){
        super();
        this.emVenda = new ArrayList<Artigos>();
        this.vendidos = new ArrayList<Artigos>();
    }

    public Vendedor(String email, String nome, String morada, int nif){
        super(email, nome, morada, nif);
        this.emVenda = new ArrayList<>();
        this.vendidos = new ArrayList<>();
    }

    public Vendedor(String email, String nome, String morada, int nif, ArrayList<Artigos> venda, ArrayList<Artigos> vendidos) {
        super(email, nome, morada, nif);
        this.emVenda = new ArrayList<>(venda);
        this.vendidos = new ArrayList<>(vendidos);
    }

    public Vendedor(Utilizador u, ArrayList<Artigos> venda, ArrayList<Artigos> vendidos){
        super(u);
        this.emVenda = new ArrayList<>(venda);
        this.vendidos = new ArrayList<>(vendidos);
    }
    public Vendedor(Vendedor u) {
        super(u);
        this.emVenda = new ArrayList<>(u.getEmVenda());
        this.vendidos = new ArrayList<>(u.getVendidos());
        this.valorFaturado = u.getValorFaturado();
    }

    public ArrayList<Artigos> getEmVenda() {
        return new ArrayList<>(emVenda);
    }

    public void setEmVenda(ArrayList<Artigos> emVenda) {
        this.emVenda = new ArrayList<>(emVenda);
    }

    public ArrayList<Artigos> getVendidos() {
        return new ArrayList<>(vendidos);
    }

    public void setVendidos(ArrayList<Artigos> vendidos) {
        this.vendidos = new ArrayList<>(vendidos);
    }

    public float getValorFaturado() {
        return valorFaturado;
    }

    public void setValorFaturado(float valorFaturado) {
        this.valorFaturado = valorFaturado;
    }

    public void adicionaArtigoEmVenda(Artigos art){
        this.emVenda.add(art);
    }

    public void removerArtigoEmVenda(Artigos art){
        this.emVenda.remove(art);
    }

    public void adicionaArtigoVendido(Artigos art){
        this.vendidos.add(art);
    }

    public void removerArtigoVendido(Artigos art){
        this.vendidos.remove(art);
    }

    @Override
    public int compareTo(Vendedor other){
        return Float.compare(other.getValorFaturado(), this.valorFaturado);
    }

    public Vendedor clone(){
        return new Vendedor(this);
    }

    @Override
    public String toString(){
        return super.toString() + "Comprador com " + "Items em venda: " + emVenda + " ; " + "Items vendidos: " + vendidos;
    }

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || o.getClass() != this.getClass()) return false;
        if(!super.equals(o)) return false;

        Vendedor v = (Vendedor) o;
        return super.equals(v) && Objects.equals(this.emVenda, v.getEmVenda()) && Objects.equals(this.vendidos, v.getVendidos());
    }
}
