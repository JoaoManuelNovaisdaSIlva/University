/*
 *  DISCLAIMER: Este código foi criado para discussão e edição durante as aulas, representando uma solução em
 *  construção. Como tal, não deverá ser visto como uma solução canónica, ou mesmo acabada. É disponibilizado
 *  para auxiliar o processo de estudo. Os alunos são encorajados a testar adequadamente o código fornecido e
 *  a procurar soluções alternativas à medida que forem adquirindo mais conhecimentos.
 */
package com.example.demoteorica.blogic;

/**
 * Calculadora de temperaturas
 */
public class TempCalculator {

    // Variáveis de instância
    private final String title = "Calculadora de temperaturas";

    // Métodos de instância
    /**
     * Obter o título da aplicação
     *
     * @return título da aplicação
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Converter um valor em Celsius
     *
     * @param celsius valor a converter
     * @return registo com os valores da temperatura em C, F e K
     */
    public TempConvRecord convertCelsius(double celsius) {
        return new TempConvRecord(celsius, celsius2fahrenheit(celsius), celsius2kelvin(celsius));
    }

    /**
     * Converter um valor em Fahrenheit
     *
     * @param fahrenheit valor a converter
     * @return registo com os valores da temperatura em C, F e K
     */
    public TempConvRecord convertFahrenheit(double fahrenheit) {
        return new TempConvRecord(fahrenheit2celsius(fahrenheit), fahrenheit, fahrenheit2kelvin(fahrenheit));
    }

    /**
     * Converter um valor em Kelvin
     *
     * @param kelvin
     * @return
     */
    public TempConvRecord convertKelvin(double kelvin) {
        return new TempConvRecord(kelvin2celsius(kelvin), kelvin2fahrenheit(kelvin), kelvin);
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
