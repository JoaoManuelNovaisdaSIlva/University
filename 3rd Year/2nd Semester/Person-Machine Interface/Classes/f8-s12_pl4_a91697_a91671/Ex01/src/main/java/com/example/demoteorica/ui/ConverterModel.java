/*
 *  DISCLAIMER: Este código foi criado para discussão e edição durante as aulas, representando uma solução em
 *  construção. Como tal, não deverá ser visto como uma solução canónica, ou mesmo acabada. É disponibilizado
 *  para auxiliar o processo de estudo. Os alunos são encorajados a testar adequadamente o código fornecido e
 *  a procurar soluções alternativas à medida que forem adquirindo mais conhecimentos.
 */
package com.example.demoteorica.ui;

import com.example.demoteorica.blogic.TempCalculator;
import com.example.demoteorica.blogic.TempConvRecord;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
/**
 * Modelo de dados para a janela de conversão de temperaturas.
 * Esta classe implementa o padrão Singleton.
 * A classe é instanciada e mantida pela classe MainWindowController.
 * O model poderia ser a classe TempCalculator, mas optou-se por criar uma classe específica para o model.
 * Uma limitação desta solução é que alterações na lógica de negócio não são refletidas automaticamente na GUI.
 * Para resolver este problema, poderia ser implementado o padrão Observer, mas isso é deixado como exercício.
 */
public class ConverterModel {

    // Variáveis de instância

    /**
     * Lógica de negócio
     */
    private final TempCalculator businessLogic = new TempCalculator();

    /**
     * Propriedade para o título da janela
     * A view irá observar esta propriedade para atualizar o título da janela (ver MainWindowController)
     */
    private final SimpleStringProperty titleProp = new SimpleStringProperty();
    public SimpleStringProperty getTitleProp() { return titleProp; }

    /**
     * Propriedades para os valores de entrada e saída
     * Os valores na View e nestas propriedades estão sempre sincronizados (ver MainWindowController)
     */
    private final SimpleStringProperty celsiusProp = new SimpleStringProperty();
    public SimpleStringProperty getCelsiusProp() { return celsiusProp; }
    private final SimpleStringProperty fahProp = new SimpleStringProperty();
    public SimpleStringProperty getFahProp() { return fahProp; }
    private final SimpleDoubleProperty kelvinPropSlider = new SimpleDoubleProperty();
    public SimpleDoubleProperty getKelvinPropSlider(){ return kelvinPropSlider; }
    private final SimpleStringProperty kelvinPropLabel = new SimpleStringProperty();
    public SimpleStringProperty getKelvinPropLabel(){return kelvinPropLabel;}

    /**
     * Lista observável com os registos de conversão.
     * Esta lista é apenas mantida a nível da GUI, não existe na lógica de negócio.
     * A View irá observar esta lista para atualizar a tabela de histórico (ver MainWindowController)
     */
    private final ObservableList<TempConvRecord> historyProp = FXCollections.observableArrayList();
    public ObservableList<TempConvRecord> getHistoryProp() { return historyProp; }

    // Implementação do padrão Singleton

    /**
     * Instância única da classe
     */
    private static ConverterModel instance = null;

    /**
     * Construtor privado para implementação do padrão Singleton
     */
    private ConverterModel() {
        // Inicialização das propriedades
        titleProp.set(businessLogic.getTitle());
        celsiusProp.set("0.0");
        updateForCelsius();
    }

    /**
     * Devolve a instância única desta classe
     * @return instância única desta classe
     */
    public static ConverterModel getInstance() {
        if (instance == null) {
            instance = new ConverterModel();
        }
        return instance;
    }

    // Métodos de instância - actualização das propriedades

    /**
     * Atualiza as propriedades com base no valor em Celsius
     */
    public void updateForCelsius() {
        try {
            TempConvRecord record = businessLogic.convertCelsius(Double.parseDouble(celsiusProp.get()));
            historyProp.add(0,record);

            fahProp.set(String.valueOf(record.getFahrenheit()));
            kelvinPropSlider.set(record.getKelvin());
            kelvinPropLabel.set(String.valueOf(record.getKelvin()));
        } catch (NumberFormatException e) {  // Em caso de erro, repõe os valores anteriores
            celsiusProp.set(String.valueOf(historyProp.get(0).getCelsius()));
        }
    }

    public void updateForFahrnheit(){
        try{
            TempConvRecord record = businessLogic.convertFahrenheit(Double.parseDouble(fahProp.get()));
            historyProp.add(0, record);

            celsiusProp.set(String.valueOf(record.getCelsius()));
            kelvinPropSlider.set(record.getKelvin());
            kelvinPropLabel.set(String.valueOf(record.getKelvin()));
        }catch (NumberFormatException e){
            fahProp.set(String.valueOf(historyProp.get(0).getFahrenheit()));
        }
    }

    public void updateForKelvin(){
        try{
            TempConvRecord record = businessLogic.convertKelvin(kelvinPropSlider.getValue());
            historyProp.add(0, record);
            fahProp.set(record.getFahrenheit() + "");
            celsiusProp.set(record.getCelsius() + "");
            kelvinPropLabel.set(record.getKelvin() + "");
        }catch (NumberFormatException e){
            fahProp.set(String.valueOf(historyProp.get(0).getFahrenheit()));
        }
    }
}