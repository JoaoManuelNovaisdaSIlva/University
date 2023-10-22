import java.util.*;

public class PolyAsMap implements  Poly{
    Map<Integer, Double> polinomio;

    public PolyAsMap(){
        this.polinomio = new HashMap<>();
    }

    public void addMonomio(int grau, double coeficiente){
        this.polinomio.put(grau, coeficiente);
    }

    public double calcula(double x){
        double soma = 0;
        for(Map.Entry<Integer, Double> entry : this.getPolinomio().entrySet()){
            soma += entry.getKey() * Math.pow(x, entry.getValue());
        }
        return soma;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        List<Integer> sorted = new ArrayList<>(this.polinomio.keySet());
        Collections.sort(sorted, Comparator.reverseOrder());
        for (Integer integer : sorted) {
            sb.append(this.getPolinomio().get(integer));
            sb.append("x^");
            sb.append(integer);
            sb.append("  ");
        }
        return sb.toString();
    }

    public Map<Integer, Double> getPolinomio() {
        return polinomio;
    }

    public void setPolinomio(Map<Integer, Double> polinomio) {
        this.polinomio = polinomio;
    }
}
