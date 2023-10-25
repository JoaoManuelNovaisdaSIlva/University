package Model;

import java.io.*;

public class Parser {
    public static void escreveFicheiro(String filename, Dados dados) throws IOException {
        try {
            FileOutputStream fileOut = new FileOutputStream(filename);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(dados);
            out.close();
            fileOut.close();
            System.out.println("Os objetos foram gravados para: " + filename);

        } catch (IOException e){
            e.printStackTrace();
        }

    }

    public static Dados lerFicheiro(String filename) throws IOException, ClassNotFoundException {
        Dados dados = null;


        try {
            FileInputStream fineIn = new FileInputStream(filename);
            ObjectInputStream in = new ObjectInputStream(fineIn);
            dados = (Dados) in.readObject();
            in.close();
            fineIn.close();

        } catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return dados;
    }
}
