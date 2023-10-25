package Model;


import java.util.Objects;

public class MalasPremium extends Malas{
    private String author;

    public MalasPremium(int idVendedor, boolean used, String desc, float price, float discount, Estado state, int numUsers, Dimenssao dimensions, TexturaMala texture, int collectionYear, TransportadoraPremium t, int stock, String author) {
        super(idVendedor, used, desc, price, discount, state, numUsers, t, stock, dimensions, texture, collectionYear);
        this.author = author;
    }

    public MalasPremium(MalasPremium a) {
        super(a);
        this.author = a.getAuthor();
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void atualizaPrecoMalasPremium(int currentYear){
        float newPrice = this.getPrice();
        if(this.getDimensions() == Dimenssao.Pequeno){
            newPrice = (this.getPrice() + (this.getPrice()*0.05f*(currentYear- this.getCollectionYear())));
        }else if (this.getDimensions() == Dimenssao.Medio){
            newPrice = (this.getPrice() + (this.getPrice()*0.10f*(currentYear- this.getCollectionYear())));
        }else if (this.getDimensions() == Dimenssao.Grande){
            newPrice = (this.getPrice() + (this.getPrice()*0.20f*(currentYear- this.getCollectionYear())));
        }
        this.setPrice(newPrice);
    }

    @Override
    public int hashCode(){
        return Objects.hash(super.hashCode(), author);
    }
    @Override
    public MalasPremium clone(){
        return new MalasPremium(this);
    }
    @Override
    public String toString(){
        return super.toString() + "Mala Premium com autor: " + author;
    }

    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || o.getClass() != this.getClass()) return false;
        if(!super.equals(o)) return false;

        MalasPremium m = (MalasPremium) o;
        return Objects.equals(this.author, m.getAuthor()) && super.equals(m);
    }
}
