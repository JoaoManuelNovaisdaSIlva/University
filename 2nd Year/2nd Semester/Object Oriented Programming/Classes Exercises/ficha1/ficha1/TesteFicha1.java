
/**
 * Write a description of class TesteFicha1 here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
import java.util.Scanner;
import java.lang.Math;

public class TesteFicha1
{
    public static void main(String[] args){
        Ficha1 f = new Ficha1();
        Scanner sc = new Scanner(System.in);
        
        // Pergunta 1
        System.out.print("Valor em graus Celsius?: ");
        
        double graus = sc.nextDouble();
        System.out.println(f.celsiusParaFarenheit(graus));
        
        // Pergunta 2
        
        System.out.print("Introduza o primeiro numero: ");
        int x = sc.nextInt();
        System.out.print("Introduza o segndo numero: ");
        int y = sc.nextInt();
        
        int r = Math.max(x,y);
        System.out.printf("O maior valor e: %d\n", r);
        
        // Pergunta 3
        
        Ficha1 f3 = new Ficha1();
        
        System.out.print("Introduza o nome:");
        String nome = sc.next();
        System.out.print("Introduza o saldo:");
        double a = sc.nextDouble();
        System.out.println(f3.criaDescricaoConta(nome,a));
        
        // Pergunta 4 
        Ficha1 f4 = new Ficha1();
        
        System.out.print("Introduza o valor em euros:");
        double euro = sc.nextDouble();
        System.out.print("Introduza a taxa de convercao para libras:");
        double taxa = sc.nextDouble();
        
        System.out.println(f4.eurosParaLibras(euro, taxa));
        
        // Pergunta 5
        
        System.out.print("Introduza o primeiro numero: ");
        int x1 = sc.nextInt();
        System.out.print("Introduza o segndo numero: ");
        int y1 = sc.nextInt();
        
        System.out.printf("%d %d", Math.min(x1,y1), Math.max(x1,y1));
    }
}
