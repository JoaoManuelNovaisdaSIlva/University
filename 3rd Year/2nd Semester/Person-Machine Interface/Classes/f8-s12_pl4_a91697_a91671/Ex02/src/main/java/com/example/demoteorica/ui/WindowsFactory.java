/*
 *  DISCLAIMER: Este código foi criado para discussão e edição durante as aulas, representando uma solução em
 *  construção. Como tal, não deverá ser visto como uma solução canónica, ou mesmo acabada. É disponibilizado
 *  para auxiliar o processo de estudo. Os alunos são encorajados a testar adequadamente o código fornecido e
 *  a procurar soluções alternativas à medida que forem adquirindo mais conhecimentos.
 */
package com.example.demoteorica.ui;

import com.example.demoteorica.ConverterApp;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

/**
 * Factory de janelas
 *
 * Esta classe contém métodos para criar janelas auxiliares.
 * Os métodos são utilizados pelos controladores de outras janelas.
 */
public class WindowsFactory {
    /**
     * Criar a janela "About"
     *
     * @param parent janela pai
     * @return janela "About"
     * @throws IOException
     */
    static public Stage newGameWindow(Window parent) throws IOException {
        Stage aboutStage = new Stage();  //
        FXMLLoader fxmlLoader = new FXMLLoader(ConverterApp.class.getResource("new-game.fxml")); // Definir o ficheiro FXML (View)
        Scene scene = new Scene(fxmlLoader.load(), 490, 580);
        aboutStage.setScene(scene);
        aboutStage.setTitle("RacingManager");
        aboutStage.initOwner(parent);
        aboutStage.setAlwaysOnTop(true);
        aboutStage.setMaxHeight(490); // Pode ser definido no FXML - colocado aqui para demonstrar a possibilidade
        aboutStage.setMaxWidth(580);  // Pode ser definido no FXML - estes valores têm precedência sobre os definidos no FXML
        return aboutStage; // Retornar a janela criada (sem a mostrar)
    }
}
