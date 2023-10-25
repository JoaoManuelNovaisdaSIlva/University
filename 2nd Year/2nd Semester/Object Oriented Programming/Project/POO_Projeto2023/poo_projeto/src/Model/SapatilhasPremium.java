package Model;


import java.util.Objects;

public class SapatilhasPremium extends Sapatilhas{
    private String authors;

    public SapatilhasPremium(int idVendedor, boolean used, String desc, float price, float discount, Estado state, int numUsers, TransportadoraPremium t, int stock, int size, boolean laces, String colour, int year, String authors) {
        super(idVendedor, used, desc, price, discount, state, numUsers, t, stock, size, laces, colour, year);
        this.authors = authors;
    }

    public SapatilhasPremium(SapatilhasPremium s) {
        super(s);
        this.authors = s.getAuthors();
    }

    public String getAuthors(){
        return this.authors;
    }

    public void setAuthors(String au){
        this.authors = au;
    }

    public void atualizarPrecoSapatilhasPremium(int currentYear){
        float newPrice = (this.getPrice() + (this.getPrice()*0.10f*(currentYear- this.getCollectionYear())));
        this.setPrice(newPrice);
    }

    @Override
    public int hashCode(){
        return Objects.hash(super.hashCode(), authors);
    }
    @Override
    public SapatilhasPremium clone(){
        return new SapatilhasPremium(this);
    }
    @Override
    public String toString(){
        return super.toString() + "Sapatilha premium com autor: " + authors;
    }

    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || o.getClass() != this.getClass()) return false;
        if(!super.equals(o)) return false;

        SapatilhasPremium s = (SapatilhasPremium) o;
        return Objects.equals(this.authors, s.getAuthors()) && super.equals(s);
    }
}
