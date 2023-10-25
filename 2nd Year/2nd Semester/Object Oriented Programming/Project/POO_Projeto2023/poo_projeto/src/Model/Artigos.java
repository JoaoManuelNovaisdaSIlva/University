package Model;


import java.io.Serializable;
import java.util.Objects;

public abstract class Artigos implements Serializable{
    private int idVendedor;
    private boolean isUsed; // Sim, é possivel haver um Artigo que seja Novo mas tenha os valores de usedState e numOfUsers, mas não importa porque depois nao vao ser usados
    private String description;
    private String barCode;
    private float price;
    private float discountPrice;
    private Estado usedState;
    private int numOfUsers;
    private Transportadora transportadoraAssociada;
    private int stock;

    public Artigos(int idVendedor, boolean used, String desc, float price, float discount, Estado state, int numUsers, Transportadora t, int stock){
        this.idVendedor = idVendedor;
        this.isUsed = used;
        this.description = desc;
        this.barCode = Integer.toString(this.hashCode()); // Gerar uma string unica a partir do hascode do objeto
        this.price = price;
        this.discountPrice = discount;

        //if(!used) this.discountPrice = 0;
        //else this.discountPrice = price - (price/(numUsers*state));

        this.usedState = state;
        this.numOfUsers = numUsers;
        this.transportadoraAssociada = t;
        this.stock = stock;
    }

    public Artigos(Artigos a){
        this.idVendedor = a.getIdVendedor();
        this.isUsed = a.getIsUsed();
        this.description = a.getDescription();
        this.barCode = a.getBarCode();
        this.price = a.getPrice();
        this.discountPrice = a.getDicountPrice();
        this.usedState = a.getUsedState();
        this.numOfUsers = a.getNumOfUseres();
        this.transportadoraAssociada = a.getTransportadoraAssociada();
        this.stock = a.getStock();
    }

    public int getIdVendedor() {
        return idVendedor;
    }

    public void setIdVendedor(int idVendedor) {
        this.idVendedor = idVendedor;
    }

    public boolean getIsUsed(){
        return this.isUsed;
    }

    public void setUsed(boolean u){
        this.isUsed = u;
    }

    public Estado getUsedState(){
        return this.usedState;
    }

    public void setUsedState(Estado u){
        this.usedState = u;
    }

    public int getNumOfUseres(){
        return this.numOfUsers;
    }

    public void setNumOfUseres(int u){
        this.numOfUsers = u;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getDicountPrice() {
        return discountPrice;
    }

    public void setDicountPrice(float dicountPrice) {
        this.discountPrice = dicountPrice;
    }

    public Transportadora getTransportadoraAssociada(){
        return this.transportadoraAssociada;
    }

    public void setTransportadoraAssociada(Transportadora t){
        this.transportadoraAssociada = t;
    }

    public int getStock() {
        return stock;
    }

    public void aumentaStock(){
        this.stock++;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
    @Override
    public int hashCode(){
        return Objects.hash(isUsed, description, barCode, price, discountPrice, usedState, numOfUsers);
    }
    public abstract Artigos clone();
    @Override
    public String toString(){
        return "Id do vendedor: " + this.idVendedor + " ; " + "Codigo de barras: " + this.barCode + " ; " + "Preço: " + this.price + " ; " + "Transportadora : " + this.transportadoraAssociada + " ; " + "Stock: " + this.stock + " --> ";
    }
    @Override
    public boolean equals(Object o){
        if(this == o) return true;

        if(o == null || o.getClass() != this.getClass()) return false;

        Artigos a = (Artigos) o;
        return Objects.equals(this.isUsed, a.getIsUsed()) && Objects.equals(this.description, a.getDescription()) && Objects.equals(this.barCode, a.getBarCode())
                && Objects.equals(this.price, a.getPrice()) && Objects.equals(this.discountPrice, a.getDicountPrice()) && Objects.equals(this.usedState, a.getUsedState())
                && Objects.equals(this.numOfUsers, a.getNumOfUseres()) && Objects.equals(this.transportadoraAssociada, a.getTransportadoraAssociada())
                && Objects.equals(this.stock, a.getStock()) && Objects.equals(this.idVendedor, a.getIdVendedor());
    }
}
