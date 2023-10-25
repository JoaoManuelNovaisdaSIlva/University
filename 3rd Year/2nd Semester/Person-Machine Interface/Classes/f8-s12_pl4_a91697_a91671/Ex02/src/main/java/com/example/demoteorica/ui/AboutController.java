/*
 *  DISCLAIMER: Este código foi criado para discussão e edição durante as aulas, representando uma solução em
 *  construção. Como tal, não deverá ser visto como uma solução canónica, ou mesmo acabada. É disponibilizado
 *  para auxiliar o processo de estudo. Os alunos são encorajados a testar adequadamente o código fornecido e
 *  a procurar soluções alternativas à medida que forem adquirindo mais conhecimentos.
 */
package com.example.demoteorica.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 * Controlador da janela "About"
 */
public class AboutController {
    // Variáveis de instância - Controlos da janela
    @FXML
    private Button okButton;

    // Métodos de instância - handlers de eventos
    /**
     * Ação do botão "OK"
     *
     * @param actionEvent
     */
    @FXML
    protected void onAboutOKAction(ActionEvent actionEvent) {
        okButton.getScene().getWindow().hide();
    }
}
