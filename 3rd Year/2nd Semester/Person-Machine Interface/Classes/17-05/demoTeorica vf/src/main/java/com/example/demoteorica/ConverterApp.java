/*
 *  DISCLAIMER: Este código foi criado para discussão e edição durante as aulas, representando uma solução em
 *  construção. Como tal, não deverá ser visto como uma solução canónica, ou mesmo acabada. É disponibilizado
 *  para auxiliar o processo de estudo. Os alunos são encorajados a testar adequadamente o código fornecido e
 *  a procurar soluções alternativas à medida que forem adquirindo mais conhecimentos.
 */
package com.example.demoteorica;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class ConverterApp extends Application {
    /**
     * Iniciar a aplicação
     *
     * @param stage stage principal
     * @throws IOException exceção de I/O
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ConverterApp.class.getResource("mainwindow-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Método principal
     *
     * @param args argumentos de linha de comandos - neste casoe stão a ser ignorados
     */
    public static void main(String[] args) {
        launch();
    }
}