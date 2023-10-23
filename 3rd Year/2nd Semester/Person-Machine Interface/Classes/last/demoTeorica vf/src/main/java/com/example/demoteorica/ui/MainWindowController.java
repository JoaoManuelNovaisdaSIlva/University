/*
 *  DISCLAIMER: Este código foi criado para discussão e edição durante as aulas, representando uma solução em
 *  construção. Como tal, não deverá ser visto como uma solução canónica, ou mesmo acabada. É disponibilizado
 *  para auxiliar o processo de estudo. Os alunos são encorajados a testar adequadamente o código fornecido e
 *  a procurar soluções alternativas à medida que forem adquirindo mais conhecimentos.
 */
package com.example.demoteorica.ui;

import com.example.demoteorica.blogic.TempCalculator;
import com.example.demoteorica.blogic.TempConvRecord;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

import java.io.IOException;
import java.util.function.UnaryOperator;

/**
 * Controlador da janela principal
 */
public class MainWindowController {
    // Variáveis de instância - Model
    private final TempCalculator model = new TempCalculator();

    // Variáveis de instância - propriedades auxiliares para controlar a View
    private final DoubleProperty celsiusProp = new SimpleDoubleProperty();
    private final DoubleProperty fahrenheitProp = new SimpleDoubleProperty();
    private final DoubleProperty kelvinProp = new SimpleDoubleProperty();

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
    @FXML
    private Label messagesTextField;

    /**
     * Inicialização do controlador
     *
     * Cria o modelo e faz as ligações (bindings) estre as propriedades deste e os controlos da View
     */
    @FXML
    protected void initialize() {
        // bindings do título
        welcomeText.textProperty().bind((model.titleProperty())); // Binding unidireccional - o texto da label é atualizado sempre que o título muda no modelo

        Platform.runLater(() -> {  // Usamos runLater para este código correr após o método initialize terminar
            ((Stage)welcomeText.getScene().getWindow()).titleProperty().bind(model.titleProperty());
        }); // Binding unidireccional - o título da janela é atualizado sempre que o título muda no modelo

        // bindings das propriedades auxiliares

        // Filtro para garantir que celsiusTextField e farhrenheitTextField contêm valores numéricos
        UnaryOperator<TextFormatter.Change> numberFilter = change -> { // Filtro para o texto do TextField - o valor introduzido deve ser um número real
            String newText = change.getControlNewText();
            // se a alteração é um valor válido, retornar a alteração (correspone a aceitá-la)
            if (newText.matches("-?([1-9][0-9]*\\.?[0-9]*)?")) {
                return change;
            }
            // se a alteração é um valor inválido, rejeitá-la retornando null
            return null;
        };

        // celsiusTextField e celsiusProp devem estar sempre sincronizados
        TextFormatter<Number> formatter = new TextFormatter<>(new NumberStringConverter(),0, numberFilter);
        celsiusTextField.setTextFormatter(formatter); // Formatação do texto do TextField - o valor introduzido deve ser um número real
        celsiusProp.bindBidirectional(formatter.valueProperty()); // Binding bidireccional - o valor da propriedade celsiusProp e *o valor no formatter* do TextField ficam sempre iguais

        // Listener para o foco do TextField - quando o foco é perdido, o valor do TextField é processado
        celsiusTextField.focusedProperty().addListener((observable, oldVal, newValue) -> {
            if (!newValue && celsiusProp.get()!=historyTable.getItems().get(0).getCelsius()) {
                // Se o foco foi perdido e o valor do TextField é diferente do valor na tabela
                onCelsiusAction(null);
            }
        });

        // fahrnheitTextField e fahrenheitProp devem estar sempre sincronizados
        TextFormatter<Number> formatter2 = new TextFormatter<>(new NumberStringConverter(),0, numberFilter);
        fahrnheitTextField.setTextFormatter(formatter2);
        fahrenheitProp.bindBidirectional(formatter2.valueProperty());

        // Listener para o foco do TextField - quando o foco é perdido, o valor do TextField é processado
        fahrnheitTextField.focusedProperty().addListener((observable, oldVal, newValue) -> {
            if (!newValue && fahrenheitProp.get()!=historyTable.getItems().get(0).getFahrenheit()) {
                onFahrenheitAction(null);
            }
        });

        // bindings do Slider
        kelvinProp.bindBidirectional(kelvinSLider.valueProperty()); // Binding bidireccional - o valor da propriedade kelvinProp (do modelo) e do Slider (da View) ficam sempre iguais
        kelvinLabel.textProperty().bind(kelvinProp.asString()); // Binding unidireccional - o texto da label é atualizado sempre que o valor da propriedade kelvinProp muda no modelo

        // Listener para o foco do Slider - quando o foco é perdido, o valor do Slider é processado
        kelvinSLider.focusedProperty().addListener((observable, oldVal, newValue) -> {
            if (!newValue && kelvinProp.get()!=historyTable.getItems().get(0).getKelvin()) {
                onKelvinMouseReleased(null);
            }
        });

        // bindings da tabela
        historyTable.setItems(model.historyProperty()); // A tabela é preenchida com os valores da propriedade historyProp do modelo (deve devolver uma ObservableList)

        celsiusCol.setCellValueFactory(new PropertyValueFactory<TempConvRecord, String>("celsius")); // A coluna é preenchida com valores dos TemConvRecord obtidos a partir de celsiusProperty (deve devolver uma Property), getCelsius() ou isCelsius() - no caso de o método devolver uma Property a ligação é bidireccional
        fahrenheitCol.setCellValueFactory(new PropertyValueFactory<TempConvRecord, String>("fahrenheit")); // O mesmo para a coluna dos graus Fahrenheit
        kelvinCol.setCellValueFactory(new PropertyValueFactory<TempConvRecord, String>("kelvin")); // O mesmo para a coluna dos graus Kelvin

        // A label messagesTextField é atualizada com o número de registos na tabela - solução com event handler
        /* historyTable.getItems().addListener((InvalidationListener) observable -> {
            messagesTextField.setText("Total de registos: " + historyTable.getItems().size());
        });*/

        // A label messagesTextField é atualizada com o número de registos na tabela - solução com bindings
        /* messagesTextField.textProperty()
                         .bind(Bindings.size(historyTable.getItems())
                                       .asString("Total de registos: %d"));*/

        // A label messagesTextField é atualizada com o número de registos na tabela - solução com bindings e expressão condicional
        messagesTextField.textProperty()
                .bind(Bindings.when(Bindings.equal(Bindings.size(historyTable.getItems()),1))
                              .then("Sem histórico de registos!")
                              .otherwise(Bindings.size(historyTable.getItems()).asString("Total de registos: %d")));

        // Definições alternativa do event handler acima - com classes anónimas (InvalidationListener e ChangeListener)
        /* historyTable.getItems().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                messagesTextField.setText("Total de registos: " + historyTable.getItems().size());
            }
        });

        historyTable.getItems().addListener(new ListChangeListener<TempConvRecord>() {
            @Override
            public void onChanged(Change<? extends TempConvRecord> change) {
                messagesTextField.setText("Total de registos: " + historyTable.getItems().size());
            }
        });*/

        // event handler para o fecho da janela - usa runLater para garantir que o controlador
        // está inicializado quando o código for executado
        Platform.runLater(() -> {
            welcomeText.getScene().getWindow().setOnCloseRequest(event -> {
                this.saveHistory();
            });
        });

        // Event handler para o fecho da janela - alternativa com classe anónima
        /* welcomeText.getScene().getWindow().setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                saveHistory();
            }
        });*/

        // Inicialização das propriedades auxiliares com os valores do último registo do histórico
        try { // tenta inicializar as propriedades com o último registo do histórico
            TempConvRecord record = model.historyProperty().get(0);
            celsiusProp.set(record.getCelsius());
            fahrenheitProp.set(record.getFahrenheit());
            kelvinProp.set(record.getKelvin());
        }
        catch (Exception e) { // se não for possível, inicializa com valores por omissão
            celsiusProp.set(0);
            onCelsiusAction(null);
        }
    }

    /**
     * Acção do TestField dos graus Celsius (tecla Enter)
     * @param actionEvent the event
     */
    @FXML
    protected void onCelsiusAction(ActionEvent actionEvent) {
        TempConvRecord record = model.convertCelsius(celsiusProp.get());
        fahrenheitProp.set(record.getFahrenheit());
        kelvinProp.set(record.getKelvin());
    }

    /**
     * Acção do TestField dos graus Fahrenheit (tecla Enter)
     * @param actionEvent the event
     */
    @FXML
    protected void onFahrenheitAction(ActionEvent actionEvent) {
        TempConvRecord record =  model.convertFahrenheit(fahrenheitProp.get());
        celsiusProp.set(record.getCelsius());
        kelvinProp.set(record.getKelvin());
    }

    /**
     * Acção do Slider dos graus Kelvin (mouse released)
     * @param mouseEvent the event
     */
    @FXML
    protected void onKelvinMouseReleased(MouseEvent mouseEvent) {
        TempConvRecord record = model.convertKelvin(kelvinProp.get());
        celsiusProp.set(record.getCelsius());
        fahrenheitProp.set(record.getFahrenheit());
    }

    /**
     * Acção do botão "Close" do menu "File"
     * @param actionEvent the event
     */
    @FXML
    protected void onCloseAction(ActionEvent actionEvent) {
        saveHistory();
        Platform.exit();
    }

    /**
     * Guarda o histórico.
     * Chamado quando a aplicação é fechada (quando o utilizador clica no botão "Close" do menu "File" - via
     * método onCloseAction() - ou quando clica no botão de fechar da janela - via onCloseRequest()).
     * @see #initialize()
     */
    private void saveHistory() {
        try {
            model.saveHistory();
            System.err.println("Histórico gravado!");
        } catch (Exception e) {
            System.err.println("Erro ao gravar o histórico: " + e.getMessage());
        }
    }

    /**
     * Acção do botão "About" do menu "Help"
     * @param actionEvent o evento
     * @throws IOException se ocorrer um erro de I/O
     */
    @FXML
    protected void onAboutAction(ActionEvent actionEvent) throws IOException {
        WindowsFactory.aboutWindow(this.welcomeText.getScene().getWindow()).show();
    }

}