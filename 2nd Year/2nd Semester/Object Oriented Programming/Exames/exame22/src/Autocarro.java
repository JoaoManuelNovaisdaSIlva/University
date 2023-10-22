public class Autocarro extends Veiculo implements Comparable<Autocarro>{
    private int lotacao;

    public Autocarro(Autocarro a){
        this.lotacao = a.lotacao;
    }

    public float custoViagem(float distancia){
        if(this.lotacao > 10){
            return (float) (distancia*getPrecoKm()*0.5f/this.lotacao);
        }else{
            return distancia*0.75f*this.lotacao;
        }
    }

    @Override
    public Autocarro clone(){
        return new Autocarro(this);
    }

    @Override
    public int compareTo(Autocarro o) {
        return 0;
    }
}
