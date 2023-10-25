package Business.SSCampeonato;
/**
 * Write a description of class Business.SSCampeonato.Piloto here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.io.Serializable;

public class Piloto implements Serializable
{
    //Variaveis de instancia
    private String id;
    private String nome;
    private Double sva;
    
    //Construtores
    public Piloto()
    {
        this.id = "";
        this.nome = "";
        this.sva = 0.0;
    }
    
    public Piloto(String i, String name, Double s)
    {
        this.id = i;
        this.nome = name;
        this.sva = s;
    }
    
    public Piloto(Piloto p)
    {
        this.id = p.getId();
        this.nome = p.getNome();
        this.sva = p.getSva();
    }
    
    //Gets e Sets

    public String getId(){ return this.id; }
    public String getNome()
    {
        return this.nome;
    }

    public Double getSva(){ return this.sva; }

    public void setId(String i){ this.id = i; }
    public void setNome(String nome)
    {
        this.nome = nome;
    }

    public void setSva(Double s) { this.sva = s; }
    //Metodos usuais
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("\nId: "); sb.append(this.id);
        sb.append("\nNome: "); sb.append(this.nome);
        String n = Double.toString(this.sva);
        sb.append("\nSva: "); sb.append(n);
        return sb.toString();
    }
    
    
    public Piloto clone()
    {
        return new Piloto(this);
    }
    
    public boolean equals(Object o)
    {
        if(this == o)
        return true;
        
        if((o == null) || (this.getClass() != o.getClass()))
        return false;
        
        Piloto p = (Piloto) o;
        return (this.id.equals(p.getId()) &&
                this.nome==p.getNome() &&
                this.sva==p.getSva());
    }

    public int getQualidadeChuva() {
        return 1;
    }

    public int getQualidade() {
        return 1;
    }
}
