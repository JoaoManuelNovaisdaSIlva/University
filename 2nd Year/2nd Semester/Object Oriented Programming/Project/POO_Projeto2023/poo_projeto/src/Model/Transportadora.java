package Model;

import org.mvel.MVEL;

import java.io.Serializable;
import java.util.Map;

import java.util.*;

public class Transportadora implements Serializable, Comparable<Transportadora> {
    final static float valorExpedicaoPequena = 2.00f;
    final static float valorExpedicaoMedia = 4.20f;
    final static float getValorExpedicaoGrande = 6.00f;
    final static float imposto = 0.23f;

    //private static int counter=0;

    private int id;
    private String nome;
    private int nif;
    private String email;
    private ArrayList<Artigos> artigosAssociados;
    private float margemLucro;
    private TipoFormula tipoFormula;
    private String formula;
    private float valorFaturado;

    public Transportadora(){
        this.id = Dados.getNovoIdTransportadora();
        this.nome = "";
        this.nif = 0;
        this.email = "";
        this.artigosAssociados = new ArrayList<>();

        this.margemLucro = Math.round((1.01f + new Random().nextFloat() * (1.50f-1.01f)) * 100) / 100f; // numero aleatorio entre 1.01 e 1.50
        this.tipoFormula = TipoFormula.Default;
        this.formula = "";
    }

    public Transportadora(String nome, int nif, String email, float margemLucro, TipoFormula tipo, String formula){ // "" para default
        this.id = Dados.getNovoIdTransportadora();
        this.nome = nome;
        this.nif = nif;
        this.email = email;
        this.artigosAssociados = new ArrayList<>();
        this.margemLucro = margemLucro;
        this.tipoFormula = tipo;
        this.formula = formula;
    }

    public Transportadora(String nome, int nif, String email, ArrayList<Artigos> artigos, float margemLucro, TipoFormula tipo, String formula){ // "" para default
        this.id = Dados.getNovoIdTransportadora();
        this.nome = nome;
        this.nif = nif;
        this.email = email;
        this.artigosAssociados = new ArrayList<>(artigos);
        this.margemLucro = margemLucro;
        this.tipoFormula = tipo;
        this.formula = formula;
    }

    public Transportadora(Transportadora t){
        this.id = Dados.getNovoIdTransportadora();
        this.nome = t.getNome();
        this.nif = t.getNif();
        this.email = t.getEmail();
        this.artigosAssociados = new ArrayList<>(t.getArtigosAssociados());
        this.margemLucro = t.getMargemLucro();
        this.tipoFormula = t.getTipoFormula();
        this.formula = t.getFormula();
        this.valorFaturado = t.getValorFaturado();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getNif() {
        return nif;
    }

    public void setNif(int nif) {
        this.nif = nif;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<Artigos> getArtigosAssociados() {
        return new ArrayList<>(artigosAssociados);
    }

    public void setArtigosAssociados(ArrayList<Artigos> artigosAssociados) {
        this.artigosAssociados = new ArrayList<>(artigosAssociados);
    }

    public float getMargemLucro(){
        return this.margemLucro;
    }

    public void setMargemLucro(float margem){
        this.margemLucro = margem;
    }

    public TipoFormula getTipoFormula(){
        return this.tipoFormula;
    }

    public void setTipoFormula(TipoFormula t){
        this.tipoFormula = t;
    }

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public float getValorFaturado() {
        return valorFaturado;
    }

    public void setValorFaturado(float valorFaturado) {
        this.valorFaturado = valorFaturado;
    }

    public float calculaFormula(float preco){
        if(this.tipoFormula == TipoFormula.Default){
            return calculaFormulaDefualt(preco);
        } else{
            return  calculaFormulaCusomized(this.formula, preco);
        }
    }

    public float calculaFormulaDefualt(float preco){
        return (preco*(margemLucro)*(1+imposto));
    }

    public static float evaluateFormula(String formula, Map<String, Float> variables) {
        Object result = MVEL.eval(formula, variables);
        return Float.parseFloat(result.toString());
    }

    public float calculaFormulaCusomized(String line, float preco){
        Map<String, Float> variables = new HashMap<>();


        variables.put("preco", preco);
        variables.put("imposto", imposto);
        variables.put("margem", margemLucro);
        return evaluateFormula(line, variables);

    }

    public void adicionaArtigoAssociado(Artigos art){
        this.artigosAssociados.add(art);
    }

    public void removeArtigoAssociado(Artigos art){
        this.artigosAssociados.remove(art);
    }

    @Override
    public int compareTo(Transportadora other){
        return Float.compare(other.valorFaturado, this.valorFaturado);
    }

    @Override
    public int hashCode(){
        return Objects.hash(id, nome, nif, email, margemLucro, tipoFormula, formula);
    }

    public Transportadora clone(){
        return new Transportadora(this);
    }

    @Override
    public String toString(){
        return "Transportadora: " + nome + " ; " + "Id: " + id + " ; " + "Formula: " + formula;
    }

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || o.getClass() != this.getClass()) return false;

        Transportadora t = (Transportadora) o;

        return Objects.equals(this.nome, t.getNome()) && Objects.equals(this.id, t.getId()) && Objects.equals(this.nif, t.getNif())
                && Objects.equals(this.email, t.getEmail()) && Objects.equals(this.artigosAssociados, t.getArtigosAssociados())
                && Objects.equals(this.margemLucro, t.getMargemLucro()) && Objects.equals(this.tipoFormula, t.getTipoFormula())
                && Objects.equals(this.formula, t.getFormula());
    }
}
