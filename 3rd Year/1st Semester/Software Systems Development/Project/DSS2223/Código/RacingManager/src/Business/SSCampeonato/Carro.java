package Business.SSCampeonato;
/**
 * Write a description of class Business.Carros.Carro here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.Map;
import java.io.Serializable;
import java.util.Random;

public abstract class Carro implements Comparable<Carro>,Serializable
{
    //Variaveis de instancia

    public enum tipoMotor{
        Hibrido, Combustao
    }

    public enum tipoPenu{
        Chuva, Duro, Macio
    }
    private int id;
    private String marca;
    private String modelo;
    private int cilindrada;
    private int potencia;
    private int fiabilidade;

    private Double pac;

    private Double downforce;
    private boolean dnf;
    
    /* Construtores */
    public Carro()
    {
        this.id = new Random().nextInt(1000000);
        this.marca = "";
        this.modelo = "";
        this.cilindrada = 0;
        this.potencia = 0;
        this.fiabilidade = 0;
        this.pac = 0.0;
        this.downforce = 0.0;
        this.dnf = false;
    }
    
    public Carro(String marca, String modelo, int cilindrada, int potencia, int fiabilidade, double pac, double downforce)
    {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.cilindrada = cilindrada;
        this.potencia = potencia;
        this.fiabilidade = fiabilidade;
        this.pac = pac;
        this.downforce = downforce;
        this.dnf = false;
    }
    
    public Carro(Carro c)
    {
       this.id = new Random().nextInt(1000000);
       this.marca = c.getMarca();
       this.modelo = c.getModelo();
       this.cilindrada = c.getCilindrada();
       this.potencia = c.getPotencia();
       this.fiabilidade = c.getFiabilidade();
       this.pac = c.getPac();
       this.downforce = c.getDownforce();
       this.dnf = c.getDNF();
    }
    
    /* Gets e sets */
    public double getPac()
    {
        return this.pac;
    }

    public double getDownforce(){ return this.downforce; }

    public int getId() { return this.id; }
    
    public String getMarca()
    {
        return this.marca;
    }
    
    public String getModelo()
    {
        return this.modelo;
    }
    
    public int getCilindrada()
    {
        return this.cilindrada;
    }
    
    public int getPotencia()
    {
        return this.potencia;
    }
    

    public int getFiabilidade()
    {
        return this.fiabilidade;
    }
    
    public boolean getDNF()
    {
        return this.dnf;
    }
    
    public void setMarca(String marca)
    {
        this.marca = marca;
    }
    
    public void setModelo(String modelo)
    {
        this.modelo = modelo;
    }
    
    public void setCilindrada(int cilindrada)
    {
        this.cilindrada = cilindrada;
    }
    
    public void setPotencia(int potencia)
    {
        this.potencia = potencia;
    }
    

    public void setPac(double t)
    {
        this.pac = t;
    }

    public void setDownforce(double d){ this.downforce = d; }
    
    public void setDNF(boolean b)
    {
        this.dnf = b;
    }
    /* Metodos usuais */
    public abstract Carro clone();
    
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("\nMarca: ");sb.append(this.marca);
        sb.append("\nModelo: ");sb.append(this.modelo);
        sb.append("\nCilindrada: ");sb.append(this.cilindrada);
        sb.append("\nPotencia: ");sb.append(this.potencia);
        sb.append("\nFiabiliade: ");sb.append(this.fiabilidade);
        sb.append("\nPac: ");sb.append(this.pac);
        sb.append("\nDownforce: ");sb.append(this.downforce);
        sb.append("\nDNF: ");sb.append(this.dnf);
        return sb.toString();
    }

    public abstract boolean DNF(int volta,int totalvoltas,int clima);
    public boolean equals(Object o)
    {
        if(this==o)
        return true;
        
        if(o==null || this.getClass()!=o.getClass())
        return false;
        
        Carro c = (Carro) o;
        return( this.marca.equals(c.getMarca()) &&
                this.modelo.equals(c.getModelo()) &&
                this.cilindrada == c.getCilindrada() &&
                this.potencia == c.getPotencia() &&
                this.fiabilidade == c.getFiabilidade() &&
                this.pac == c.getPac() &&
                this.downforce == c.getDownforce() &&
                this.dnf == c.getDNF());
    }
}
