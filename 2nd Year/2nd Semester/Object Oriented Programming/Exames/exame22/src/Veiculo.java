import java.io.Serializable;
import java.util.List;

public abstract class Veiculo implements Serializable {
    private String matricula;
    private String marca;
    private String modelo;
    private double precoKm;
    private List<Viagem> viagens;


    public abstract float custoViagem(float distancia);

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public double getPrecoKm() {
        return precoKm;
    }

    public void setPrecoKm(double precoKm) {
        this.precoKm = precoKm;
    }

    public List<Viagem> getViagens() {
        return viagens;
    }

    public void setViagens(List<Viagem> viagens) {
        this.viagens = viagens;
    }

    public Viagem getViagemMaisAntiga() throws  VeiculoSemViagens{
        List<Viagem> v = this.getViagens();
        if(v.size() == 0) throw new VeiculoSemViagens();
        else return v.get(0).clone();
    }

    @Override
    public Veiculo clone() {
        try {
            return (Veiculo) super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }
}
