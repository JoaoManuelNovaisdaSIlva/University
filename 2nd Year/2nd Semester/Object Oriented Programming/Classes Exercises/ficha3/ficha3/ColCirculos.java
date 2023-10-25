import java.util.List;
import java.util.ArrayList;
/**
 * Gere uma colecçao de Circulos
 * 
 * @author (your name)
 * @version (a version number or a date)
 */
public class ColCirculos{
    private String nome;
    private String nomeDono;
    private List<Circulo> circulos;
    
    public ColCirculos(){
        this.nome = "";
        this.nomeDono = "";
        this.circulos = new ArrayList();
    }
    
    public ColCirculos(String nome, String dono){
        this.nome = nome;
        this.nomeDono = dono;
        this.circulos = new ArrayList();
    }
    
    public void adicionaCirculos(Circulo c){
        this.circulos.add(c.clone());
    }
    
    // verificar se um circulo existe
    
    public boolean existeCirculo(Circulo c){
        return this.circulos.contains(c);
    }
    
    // qual a posiçao do circulo
    
    public int posicao(Circulo c){
        return this.circulos.indexOf(c);
    }
    
    // quantos circulos
    
    public int quantosCirculos(){
        return this.circulos.size();
    }
}
