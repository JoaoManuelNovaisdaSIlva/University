import java.lang.Math;
/**
 * Write a description of class Circulo here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Circulo
{
    
    //private double x;
    //private double y;
    private Ponto centro;
    private double raio;
    
    /**
     * Construtor por omissao
     * tem que ser criadas coisas validas, como um cirulo com raio>0
     */
    public Circulo(){
        this.centro = new Ponto();
        //this.x = 0;
        //this.y = 0;
        this.raio = 1;
    }
    
    /**
     * Contrutor parametrizado
     */
    public Circulo(int x, int y, double raio){
        this.centro = new Ponto(x,y);
        //this.x = x;
        //this.y = y;
        this.raio = raio; // o raio tem que ser positivo
    }
    
    public Circulo(Ponto p, double raio){
        this.centro = p.clone();
        //this.centro = new Ponto(p);
        this.raio = raio;
    }
    
    /**
     * Construtor de copia
     */
    public Circulo(Circulo umCirc){
        this.centro = umCirc.getCentro();
        //this.x = umCirc.getX();
        //this.y = umCirc.getY();
        this.raio = umCirc.getRaio();
    }
    
    // Gets
    // a)
    /**
    * public double getX(){
    *    return this.x;
    * }
    // b)
    * public double getY(){
    *    return this.y;
    * }
    **/
    
    public Ponto getCentro(){
        return this.centro.clone();
    }
    // c)
    public double getRaio(){
        return this.raio;
    }
    
    // Sets
    // d)
    /**
    * public void setX(double novoX){
    *    this.x = novoX;
    * }
    *
    * public void setY(double novoY){
    *    this.y = novoY;
    * }
    **/
    
    public void setCentro(Ponto p){
        this.centro = p.clone();
    }
    
    public void setRaio(double novoRaio){
        this.raio = novoRaio;
    }
    
    // e)
    public void alteraCentro(Ponto p){
        this.setCentro(p);
        //this.x = x;
        //this.y = y;
    }
    
    // f)
    public double calculaArea(){
        return Math.PI * Math.pow(this.raio,2);
    }
    
    // g)
    public double calculaPerimetro(){
        return 2  * Math.PI * this.raio;
    }
    
    // Clone
    public Circulo clone(){
        return new Circulo(this);
    }
    
    //Equals
    public boolean equals(Object o){
        if (this == o)
            return true;
        if((o == null) || (this.getClass() != o.getClass()))
            return false;
        Circulo p = (Circulo) o;
        //return (this.x == p.getX() && this.y == p.getY() && this.raio == p.getRaio());
        return (this.centro.equals(p.getCentro()) && this.raio == p.getRaio());
    }
    
    // toString
    public String toString(){
        //return "x = " + this.x + " | y = " + this.y + " | raio = " + this.raio; 
        
        // forma mais eficiente no caso de haver varios utilizadores
        StringBuilder sb = new StringBuilder();
        //sb.append("X = ");
        //sb.append(this.x);
        //sb.append("Y = ");
        //sb.append(this.y);
        sb.append("Ponto = ");
        sb.append(this.centro);
        sb.append("Raio = ");
        sb.append(this.raio);
        
        return sb.toString();
    }
}
