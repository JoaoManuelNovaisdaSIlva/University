package Model;

import org.mvel.MVEL;

import java.util.*;

public class TransportadoraPremium extends Transportadora{

    final static float impostoPremium = 5.00f;

    private String formulaPremium;

    public TransportadoraPremium(String nome, int nif, String email, float margem, TipoFormula tipo, String formula, String formulaPremium){
        super(nome, nif, email, margem, tipo, formula);
        this.formulaPremium = formulaPremium;
    }

    public TransportadoraPremium(String nome, int nif, String email, ArrayList<Artigos> artigos, float margem, TipoFormula tipo, String formula, String formulaPremium){
        super(nome, nif, email, artigos, margem, tipo, formula);
        this.formulaPremium = formulaPremium;
    }

    public TransportadoraPremium(TransportadoraPremium tp){
        super(tp);
        this.formulaPremium = tp.getFormulaPremium();
    }

    public String getFormulaPremium(){
        return this.formulaPremium;
    }

    public void setFormulaPremium(String formula){
        this.formulaPremium = formula;
    }

    public float calculaFormulaPremium(float preco){
        if(super.getTipoFormula() == TipoFormula.Default){
            return calculaFormulaDefualtPremium(preco);
        } else{
            return  calculaFormulaCusomizedPremium(this.formulaPremium, preco);
        }
    }

    public float calculaFormulaDefualtPremium(float preco){
        return (preco*(super.getMargemLucro())*(1+imposto))+impostoPremium;
    }

    public static float evaluateFormulaPremium(String formula, Map<String, Float> variables) {
        Object result = MVEL.eval(formula, variables);
        return Float.parseFloat(result.toString());
    }

    public float calculaFormulaCusomizedPremium(String line, float preco){
        Map<String, Float> variables = new HashMap<>();

        variables.put("preco", preco);
        variables.put("imposto", imposto);
        variables.put("margem", getMargemLucro());
        variables.put("impostoPremium", impostoPremium);
        return evaluateFormulaPremium(line, variables);

    }

    public Transportadora clone(){
        return new TransportadoraPremium(this);
    }

    @Override
    public String toString(){
        return super.toString() + "Transportadora Premium com formula: " + formulaPremium;
    }

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || o.getClass() != this.getClass()) return false;
        if(!super.equals(o)) return false;

        TransportadoraPremium t1 = (TransportadoraPremium) o;
        return super.equals(t1) && Objects.equals(this.formulaPremium, t1.formulaPremium);
    }

}
