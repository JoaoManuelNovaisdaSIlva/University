/*
 *  DISCLAIMER: Este código foi criado para discussão e edição durante as aulas, representando uma solução em
 *  construção. Como tal, não deverá ser visto como uma solução canónica, ou mesmo acabada. É disponibilizado
 *  para auxiliar o processo de estudo. Os alunos são encorajados a testar adequadamente o código fornecido e
 *  a procurar soluções alternativas à medida que forem adquirindo mais conhecimentos.
 */
package com.example.demoteorica.ui;

import com.example.demoteorica.blogic.TempConvRecord;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import java.io.IOException;

/**
 * Controlador da janela principal
 */
public class MainWindowController {
    // Variáveis de instância - Model
    private final ConverterModel converterModel = ConverterModel.getInstance();

    // Variáveis de instância - Controlos da View
    @FXML
    private Label welcomeText;
    @FXML
    private TextField celsiusTextField;
    @FXML
    private TextField fahrnheitTextField;
    @FXML
    private Slider kelvinSLider;
    @FXML
    private Label kelvinLabel;
    @FXML
    private TableView<TempConvRecord> historyTable;
    @FXML
    private TableColumn<TempConvRecord,String> celsiusCol;
    @FXML
    private TableColumn<TempConvRecord,String> fahrenheitCol;
    @FXML
    private TableColumn<TempConvRecord,String> kelvinCol;

    /**
     * Inicialização do controlador
     *
     * Cria o modelo e faz as ligações (bindings) estre as propriedades deste e os controlos da View
     */
    @FXML
    protected void initialize() {
        // bindings de propriedades simples
        welcomeText.textProperty().bind((converterModel.getTitleProp())); // Binding unidireccional - o texto da label é atualizado sempre que o título muda no modelo
        converterModel.getCelsiusProp().bindBidirectional(celsiusTextField.textProperty()); // Binding bidireccional - o valor da propriedade celsiusProp (do modelo) e do TextField (da View) ficam sempre iguais

        // bindings da tabela
        historyTable.setItems(converterModel.getHistoryProp()); // A tabela é preenchida com os valores da propriedade historyProp do modelo (deve devolver uma ObservableList)

        celsiusCol.setCellValueFactory(new PropertyValueFactory<TempConvRecord, String>("celsius")); // A coluna é preenchida com valores dos TemConvRecord obtidos a partir de celsiusProperty (deve devolver uma Property), getCelsius() ou isCelsius() - no caso de o método devolver uma Property a ligação é bidireccional

    }

    /**
     * Acção do TestField dos graus Celsius (tecla Enter)
     * @param actionEvent
     */
    @FXML
    protected void onCelsiusAction(ActionEvent actionEvent) {
        converterModel.updateForCelsius();
    }

    /**
     * Acção do botão "Close" do menu "File"
     * @param actionEvent
     */
    @FXML
    protected void onCloseAction(ActionEvent actionEvent) {
        System.exit(0);
    }

    /**
     * Acção do botão "About" do menu "Help"
     * @param actionEvent
     * @throws IOException
     */
    @FXML
    protected void onAboutAction(ActionEvent actionEvent) throws IOException {
        WindowsFactory.aboutWindow(this.welcomeText.getScene().getWindow()).show();
    }

}