/*
 *  DISCLAIMER: Este código foi criado para discussão e edição durante as aulas, representando uma solução em
 *  construção. Como tal, não deverá ser visto como uma solução canónica, ou mesmo acabada. É disponibilizado
 *  para auxiliar o processo de estudo. Os alunos são encorajados a testar adequadamente o código fornecido e
 *  a procurar soluções alternativas à medida que forem adquirindo mais conhecimentos.
 */
package com.example.demoteorica.blogic;

import com.example.demoteorica.data.ConvHistoryDAO;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.util.stream.Collectors;

/**
 * Calculadora de temperaturas
 */
public class TempCalculator {

    // Variáveis de instância

    /**
     * Título da aplicação - Esta variável é usada para definir o título da janela principal da
     * aplicação. É uma propriedade, para que possa ser usada diretamente na definição do título
     * da janela.
     */
    private final StringProperty title = new SimpleStringProperty("Conversor de Temperaturas");
    /** Histórico de conversões */
    private ObservableList<TempConvRecord> history;
    /** DAO para guardar o histórico de conversões */
    private final ConvHistoryDAO dao = ConvHistoryDAO.getInstance();

    // Construtores

    /**
     * Construtor por omissão
     */
    public TempCalculator() {
        try {
            this.history = FXCollections.observableArrayList(dao.loadRecords());
        }
        catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro a carregar o histórico de conversões"); // Escreve no canal de erro (consola)
            this.history = FXCollections.observableArrayList();
            // Poderíamos também lançar uma exceção para indicar que o histórico não foi carregado
        }
    }

    // Métodos de instância

    /**
     * Obter o título da aplicação como propriedade
     *
     * @return Propriedade com o título da aplicação
     */
    public StringProperty titleProperty() {
        return this.title;
    }

    /**
     * Obter o título da aplicação como String
     *
     * @return String com o título da aplicação
     */
    public String getTitle() {
        return this.title.get();
    }

    /**
     * Obter o histórico de conversões
     *
     * @return histórico de conversões
     */
    public ObservableList<TempConvRecord> historyProperty() {
        return FXCollections.unmodifiableObservableList(this.history);
    }

    /**
     * Converter um valor em Celsius
     *
     * @param celsius valor a converter em Celsius
     * @return registo com os valores da temperatura em C, F e K
     */
    public TempConvRecord convertCelsius(double celsius) {
        TempConvRecord record = new TempConvRecord(celsius, celsius2fahrenheit(celsius), celsius2kelvin(celsius));
        history.add(0, record);
        return record;
    }

    /**
     * Converter um valor em Fahrenheit
     *
     * @param fahrenheit valor a converter em Fahrenheit
     * @return registo com os valores da temperatura em C, F e K
     */
    public TempConvRecord convertFahrenheit(double fahrenheit) {
        TempConvRecord record = new TempConvRecord(fahrenheit2celsius(fahrenheit), fahrenheit, fahrenheit2kelvin(fahrenheit));
        history.add(0, record);
        return record;
    }

    /**
     * Converter um valor em Kelvin
     *
     * @param kelvin  valor a converter em Kelvin
     * @return registo com os valores da temperatura em C, F e K
     */
    public TempConvRecord convertKelvin(double kelvin) {
        TempConvRecord record = new TempConvRecord(kelvin2celsius(kelvin), kelvin2fahrenheit(kelvin), kelvin);
        history.add(0, record);
        return record;
    }

    /**
     * Guardar o histórico de conversões
     * @throws IOException se ocorrer um erro ao guardar o histórico
     */
    public void saveHistory() throws IOException {
        dao.saveRecords(this.history.stream().collect(Collectors.toList()));
    }

    // Métodos auxiliares

    /**
     * Converter um valor em Celsius para Fahrenheit
     *
     * @param clesius valor a converter
     * @return valor convertido
     */
    private double celsius2fahrenheit(double clesius) {
        return clesius * 1.8 + 32;
    }

    /**
     * Converter um valor em Fahrenheit para Celsius
     *
     * @param fahrenheit valor a converter
     * @return valor convertido
     */
    private double fahrenheit2celsius(double fahrenheit) {
        return (fahrenheit - 32) / 1.8;
    }

    /**
     * Converter um valor em Celsius para Kelvin
     *
     * @param celsius valor a converter
     * @return valor convertido
     */
    private double celsius2kelvin(double celsius) {
        return celsius + 273.15;
    }

    /**
     * Converter um valor em Kelvin para Celsius
     *
     * @param kelvin valor a converter
     * @return valor convertido
     */
    private double kelvin2celsius(double kelvin) {
        return kelvin - 273.15;
    }

    /**
     * Converter um valor em Fahrenheit para Kelvin
     *
     * @param fahrenheit valor a converter
     * @return valor convertido
     */
    private double fahrenheit2kelvin(double fahrenheit) {
        return (fahrenheit + 459.67) * 5/9;
    }

    /**
     * Converter um valor em Kelvin para Fahrenheit
     *
     * @param kelvin valor a converter
     * @return valor convertido
     */
    private double kelvin2fahrenheit(double kelvin) {
        return kelvin * 9/5 - 459.67;
    }

}
