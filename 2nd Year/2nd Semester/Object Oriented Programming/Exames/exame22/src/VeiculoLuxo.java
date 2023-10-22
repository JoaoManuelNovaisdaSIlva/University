public class VeiculoLuxo extends Veiculo implements Comparable<VeiculoLuxo>{
    private float taxaLucro;

    public float custoViagem(float distancia){
        return (float) (distancia* getPrecoKm() * (1.1+this.taxaLucro));
    }

    @Override
    public int compareTo(VeiculoLuxo o) {
        return 0;
    }
}
