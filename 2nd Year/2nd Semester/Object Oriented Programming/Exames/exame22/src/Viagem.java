import java.io.Serializable;

public class Viagem implements Serializable {
    private String origem;
    private String destino;
    private float distancia;
    private float custo;

    public Viagem(String origem, String destino, float distancia, float custo){
        this.origem = origem;
        this.destino = destino;
        this.distancia = distancia;
        this.custo = custo;
    }

    public Viagem(Viagem v){
        this.origem = v.getOrigem();
        this.destino = v.getDestino();
        this.distancia = v.getDistancia();
        this.custo = v.getCusto();
    }

    @Override
    public Viagem clone(){
        return new Viagem(this);
    }

    public String getOrigem() {
        return origem;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public float getDistancia() {
        return distancia;
    }

    public void setDistancia(float distancia) {
        this.distancia = distancia;
    }

    public float getCusto() {
        return custo;
    }

    public void setCusto(float custo) {
        this.custo = custo;
    }
}
