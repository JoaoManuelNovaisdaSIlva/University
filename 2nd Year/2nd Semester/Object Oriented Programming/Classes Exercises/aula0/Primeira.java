/**
 * Write a description of class Primeira here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
import java.util.Scanner;

public class Primeira
{
    /**
     * Metodo auxiliar que devolve um String
     */
    public static String geraSaudacao(String nome){
        return "Boas camarada" + " " + nome + "!";
    }
    // num concat (+) de duas string o java aloca memoria para uma nova string cujo tamanha e a acumulacao das outras 2 e copia para la cada uma das strings e 
    // depois altera o pointer da primeira string para a final. Nao e possivel alterar strings em java. Pedacos de memoria que nao tenham apontadores
    // sao libertados pelo java (garbage colector)
    /**
     * Defeniçao do metodo main
     */
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        System.out.println("Nome a saudar: ");
        String valorLido = sc.next();
        
        String nome = geraSaudacao(valorLido);
        System.out.println(nome);
    }
}
