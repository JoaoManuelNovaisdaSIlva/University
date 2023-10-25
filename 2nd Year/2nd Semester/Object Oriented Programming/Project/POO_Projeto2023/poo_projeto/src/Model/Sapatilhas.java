package Model;


import java.util.Objects;

public class Sapatilhas extends Artigos {
    private int shoeSize;
    private boolean hasLaces;
    private String color;
    private int collectionYear;

    public Sapatilhas(int idVendedor, boolean used, String desc, float price, float discount, Estado state, int numUsers, Transportadora t, int stock, int size, boolean laces, String colour, int year){
        super(idVendedor, used, desc, price, discount, state, numUsers, t, stock);
        this.shoeSize = size;
        this.hasLaces = laces;
        this.color = colour;
        this.collectionYear = year;
    }

    public Sapatilhas(Sapatilhas s){
        super(s);
        this.shoeSize = s.getShoeSize();
        this.hasLaces = s.getHasLaces();
        this.color = s.getColor();
        this.collectionYear = s.getCollectionYear();
    }

    public int getShoeSize() {
        return shoeSize;
    }

    public void setShoeSize(int shoeSize) {
        this.shoeSize = shoeSize;
    }

    public boolean getHasLaces() {
        return hasLaces;
    }

    public void setHasLaces(boolean hasLaces) {
        this.hasLaces = hasLaces;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getCollectionYear() {
        return collectionYear;
    }

    public void setCollectionYear(int collectionYear) {
        this.collectionYear = collectionYear;
    }

    public void atualizaPrecoDesconto(int currentYear){
        if(this.getIsUsed()) {
            float newDiscount = (this.getDicountPrice() + (this.getDicountPrice() * 0.05f * (currentYear - this.collectionYear)));
            if (newDiscount > this.getPrice()) return;
            else this.setDicountPrice(newDiscount);
        }
    }

    @Override
    public int hashCode(){
        return Objects.hash(super.hashCode(), shoeSize, hasLaces, color, collectionYear);
    }
    @Override
    public Sapatilhas clone(){
        return new Sapatilhas(this);
    }
    @Override
    public String toString(){
        return super.toString() + "Sapatilha do ano de coleção: " + this.collectionYear + " --> ";
    }

    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || o.getClass() != this.getClass()) return false;
        if(!super.equals(o)) return false;

        Sapatilhas s = (Sapatilhas) o;
        return Objects.equals(this.shoeSize, s.getShoeSize()) && Objects.equals(this.hasLaces, s.getHasLaces()) && Objects.equals(this.color, s.getColor())
                && Objects.equals(this.collectionYear, s.getCollectionYear()) && super.equals(s);
    }
}
