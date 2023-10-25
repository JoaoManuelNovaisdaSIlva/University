/*
 *  DISCLAIMER: Este código foi criado para discussão e edição durante as aulas, representando uma solução em
 *  construção. Como tal, não deverá ser visto como uma solução canónica, ou mesmo acabada. É disponibilizado
 *  para auxiliar o processo de estudo. Os alunos são encorajados a testar adequadamente o código fornecido e
 *  a procurar soluções alternativas à medida que forem adquirindo mais conhecimentos.
 */
package com.example.demoteorica.blogic;

/**
 * Registo com os valores da temperatura em C, F e K
 */
public class TempConvRecord {
    // Variáveis de instância
    private final double celsius;
    private final double fahrenheit;
    private final double kelvin;

    // Construtores
    /**
     * Construtor completo
     */
    public TempConvRecord(double celsius, double fahrenheit, double kelvin) {
        this.celsius = celsius;
        this.fahrenheit = fahrenheit;
        this.kelvin = kelvin;
    }

    // Métodos de instância
    /**
     * Obter o valor em Celsius
     *
     * @return valor em Celsius
     */
    public double getCelsius() {
        return roundAvoid(celsius, 2);
    }

    public static double roundAvoid(double value, int places) {
        double scale = Math.pow(10, places);
        return Math.round(value * scale) / scale;
    }


    /**
     * Obter o valor em Fahrenheit
     *
     * @return valor em Fahrenheit
     */
    public double getFahrenheit() {
        return roundAvoid(fahrenheit, 2);
    }

    /**
     * Obter o valor em Kelvin
     *
     * @return valor em Kelvin
     */
    public double getKelvin() {
        return roundAvoid(kelvin, 2);
    }

}
