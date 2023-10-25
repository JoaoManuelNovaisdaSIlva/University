
/**
 * Write a description of class TesteCirculos here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class TesteCirculos
{
    public static void main(String[] args){
        Ponto p1,p2;
        p1 = new Ponto(4,8);
        p2 = new Ponto(5,6);
        
        Circulo c1, c2, c3, c4;
        c1 = new Circulo();
        c2 = new Circulo(p1,12);
        c3 = new Circulo(p2,50);
        c4 = new Circulo(5,5,15);
        
        Ponto y = c2.getCentro();
        System.out.println("p1 == y ?" + (p1==y));
        System.out.println("p1 == y ?" + (p1.equals(y)));
       
        System.out.println(c1.toString());
        System.out.println(c2.toString());
        System.out.println(c3); 
        /**
         * o println sempre que e chamado envia ao 
         * argumento um toString caso toString nao esteja emplementado ele usa um toString 
         * que devolve o hashcode do objeto
         */
        
        System.out.println(c3.calculaArea());        
    }
}
