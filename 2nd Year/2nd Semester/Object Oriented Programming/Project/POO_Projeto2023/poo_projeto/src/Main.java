import Model.Dados;
import Exceptions.InputInvalidoException;
import Controller.*;
import View.View;

import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InputInvalidoException {
        Dados dados = new Dados("objetos.ser");
        Controller controller = new Controller(dados);
        View view = new View(controller);
        view.run();
    }
}
