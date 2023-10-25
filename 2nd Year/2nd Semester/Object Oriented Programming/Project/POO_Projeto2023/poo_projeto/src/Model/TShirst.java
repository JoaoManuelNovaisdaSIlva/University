package Model;


import java.util.Objects;

public class TShirst extends Artigos {
    private TamanhoTShirt size;
    private PadraoTShirt pattern;
    public TShirst(int idVendedor, boolean used, String desc, float price, float discount, Estado state, int numUsers, Transportadora t, int stock, TamanhoTShirt size, PadraoTShirt pattern) {
        super(idVendedor, used, desc, price, discount, state, numUsers, t, stock);
        this.size = size;
        this.pattern = pattern;
    }

    public TShirst(TShirst a) {
        super(a);
        this.size = a.getSize();
        this.pattern = a.getPattern();
    }

    public TamanhoTShirt getSize() {
        return size;
    }

    public void setSize(TamanhoTShirt size) {
        this.size = size;
    }

    public PadraoTShirt getPattern() {
        return pattern;
    }

    public void setPattern(PadraoTShirt pattern) {
        this.pattern = pattern;
    }

    @Override
    public int hashCode(){
        return Objects.hash(super.hashCode(), size, pattern);
    }
    @Override
    public TShirst clone(){
        return new TShirst(this);
    }
    @Override
    public String toString(){
        return super.toString() + "T-shirt de tamanho: " + size;
    }

    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || o.getClass() != this.getClass()) return false;

        if(!super.equals(o)) return false;
        TShirst t = (TShirst) o;

        return Objects.equals(this.size, t.getSize()) && Objects.equals(this.pattern, t.getPattern()) && super.equals(t);
    }
}
