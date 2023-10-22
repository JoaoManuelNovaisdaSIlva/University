
/**
 * Write a description of class Ficha1 here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Ficha1
{
   public double celsiusParaFarenheit(double graus){
       return graus * 1.8 + 32;
   }
   
   public String criaDescricaoConta(String nome, double saldo){
       return nome + "->" + saldo;
   }
   
   public double eurosParaLibras(double valor, double taxaConversao){
       return valor * taxaConversao;
   }
}
