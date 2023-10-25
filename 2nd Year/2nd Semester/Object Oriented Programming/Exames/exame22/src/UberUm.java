import java.util.HashMap;
import java.util.Map;

public class UberUm {
    HashMap<String, Veiculo> veiculos;

    public UberUm(){
        this.veiculos = new HashMap<>();
    }

    public UberUm(HashMap<String, Veiculo> veiculos){
        this.veiculos = new HashMap<>();
        for(Map.Entry<String, Veiculo> entry : veiculos.entrySet()) {
            this.veiculos.put(entry.getKey(), entry.getValue().clone());
        }
    }

    public Viagem viagemMaisAntiga(String matricula) throws VeiculoNaoExisteException, VeiculoSemViagens{
        Veiculo temp = veiculos.get(matricula);
        if(temp == null){
            throw new VeiculoNaoExisteException();
        }
        else if(temp.getViagens().size() == 0){
            throw new VeiculoSemViagens();
        } else return temp.getViagens().get(0).clone();
    }
}
