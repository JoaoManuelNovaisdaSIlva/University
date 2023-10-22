import java.util.Scanner;
import java.lang.Math;
/**
 * Write a description of class Ficha2 here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Ficha2
{
    public int calcMinimo(int[] numeros){
        int r = numeros[0];
        for(int n : numeros){
            if (n < r) r = n;
        }
        return r;
    }
    
    public int[] arrayEntreIndices(int[] arr, int a, int b){
        int tam = b - a + 1;
        int[] subconjunto = new int[tam];
        System.arraycopy(arr, a, subconjunto, 0,tam);
        return subconjunto;
    }
    
    public int[] comuns(int[] a, int[] b){
        int[] res = new int[a.length];
        int elems = 0;
        
        for(int elem1:a){
            boolean enc = this.existe(res
        }
    }
}
