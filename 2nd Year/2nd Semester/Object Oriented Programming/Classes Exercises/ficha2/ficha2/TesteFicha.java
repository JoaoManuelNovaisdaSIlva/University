import java.util.Scanner;
import java.lang.Math;
/**
 * Write a description of class TesteFicha here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class TesteFicha
{
   public static void main(String[] args){
       Ficha2 f = new Ficha2();
       Scanner sc = new Scanner(System.in);
       
       // Pergunta 1
       // a)
       System.out.print("Introduza o tamanho do array: ");
       int tamanho = sc.nextInt();
       
       int[] numeros = new int[tamanho];
       System.out.print("Introduza" + " " + tamanho + " " + "valores inteiros: \n");
       for(int i = 0; i<tamanho; i++){
           numeros[i] = sc.nextInt();
       }
       System.out.print("O array e o seguinte: \n");
       for(int n : numeros){
           System.out.println(n);
       }
       //Calcular o minimo
       int min = f.calcMinimo(numeros);
       System.out.println("O elemento minimo e: " + " " + min);
       
       // b)
       int[] lista = {1,2,3,4,5,6};
       int[] sub = f.arrayEntreIndices(lista, 2, 4);
       System.out.println("O array subconjunto e o seguinte: \n");
       for(int n : sub){
           System.out.println(n);
       }
   }
   
   
}
