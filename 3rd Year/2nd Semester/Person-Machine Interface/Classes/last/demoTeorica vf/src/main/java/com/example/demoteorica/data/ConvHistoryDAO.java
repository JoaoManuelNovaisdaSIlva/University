/*
 *  DISCLAIMER: Este código foi criado para discussão e edição durante as aulas, representando uma solução em
 *  construção. Como tal, não deverá ser visto como uma solução canónica, ou mesmo acabada. É disponibilizado
 *  para auxiliar o processo de estudo. Os alunos são encorajados a testar adequadamente o código fornecido e
 *  a procurar soluções alternativas à medida que forem adquirindo mais conhecimentos.
 */
package com.example.demoteorica.data;

import com.example.demoteorica.blogic.TempConvRecord;

import java.io.*;
import java.util.List;

/**
 * DAO para guardar e carregar a lista de registos de conversão
 */
public class ConvHistoryDAO {
    // Variáveis de classe
    /**
     * Nome do ficheiro de dados
     */
    public static final String HISTORY_DAT = "history.dat";
    /**
     * Instância única do DAO - para Singleton
     */
    public static ConvHistoryDAO instance = null;

    // Construtores
    /**
     * Tornar o construtor privado
     * @see #getInstance()
     */
    private ConvHistoryDAO() {
    }

    // Métodos de classe
    /**
     * Obter a instância única do DAO
     * @return instância única do DAO
     */
    public static ConvHistoryDAO getInstance() {
        if (instance == null) {
            instance = new ConvHistoryDAO();
        }
        return instance;
    }

    // Métodos de instância
    /**
     * Guardar uma lista de registos
     *
     * @param records registos a guardar
     * @throws IOException se houver um erro de I/O
     */
    public void saveRecords(List<TempConvRecord> records) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(HISTORY_DAT));
        oos.writeObject(records);
        oos.close();
    }

    /**
     * Carregar uma lista de registos
     * @return lista de registos
     * @throws IOException se houver um erro de I/O
     * @throws ClassNotFoundException se a classe lida não existir
     */
    public List<TempConvRecord> loadRecords() throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(HISTORY_DAT));
        List<TempConvRecord> records = (List<TempConvRecord>) ois.readObject();
        ois.close();
        return records;
    }
}
