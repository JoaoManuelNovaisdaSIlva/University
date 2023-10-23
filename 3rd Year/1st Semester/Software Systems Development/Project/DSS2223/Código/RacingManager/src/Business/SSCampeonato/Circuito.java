package Business.SSCampeonato;
/**
 * Write a description of class Business.SSCampeonato.Circuito here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import Business.Database.CircuitoDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.io.Serializable;

public class Circuito implements Serializable
{
    /* Variaveis instancia */

    private String id;
    private String nome;
    private int voltas;
    private List<Terreno> caminho;

    /* Construtores */
    public Circuito()
    {
        this.nome = "";
        this.voltas = 0;

    }


    public Circuito(String nome,int nvoltas,String mapa){
        this.nome = nome;
        this.voltas = nvoltas;
        String[] mapaArr = mapa.split(",");
        for(String pecaMapa: mapaArr){
            Terreno t = new Terreno(pecaMapa);
            caminho.add(t);
        }
    }
    
    public Circuito(Circuito c)
    {
        this.nome = c.getNome();
        this.voltas = c.getVoltas();
        this.caminho = c.getCaminho();
    }
    
    /* Gets e Sets */
    public String getNome()
    {
        return this.nome;
    }

    public int getVoltas()
    {
        return this.voltas;
    }

    public List<Terreno> getCaminho()
    {
        return this.caminho;
    }

    
    public void setNome(String n)
    {
        this.nome = n;
    }

    public void setVoltas(int v)
    {
        this.voltas = v;
    }

    public int getNum(String tipo){
        int soma = 0;
        for(Terreno t : this.getCaminho()) {
            if(t.getTipo().equals(tipo)){
                soma++;
            }
        }

        return soma;
    }

    public void setDificuldadeCaminho(List<Terreno> t)
    {
        this.caminho = t;
    }

    /* Metodos usuais */
    public Circuito clone()
    {
        return new Circuito(this);
    }
    
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("\nNome: ");sb.append(this.nome);
        sb.append("\nNumero de voltas: ");sb.append(this.voltas);
        //sb.append("\nTempo Medio: ");sb.append(TimeConverter.toTimeFormat(this.tempoMedio));
        return sb.toString();
    }
    
    public boolean equals(Object o)
    {
       if(this == o)
       return true;
       
       if(o == null || this.getClass() != o.getClass())
       return false;
       
       Circuito c = (Circuito) o;
       return ( this.nome.equals(c.getNome()) &&
               this.voltas == c.getVoltas() &&
                this.caminho == c.getCaminho());
    }

    public void simularCircuito() {

    }
}
