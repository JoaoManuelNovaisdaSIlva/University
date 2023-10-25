package Business.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DAOConfig {
    static final String USERNAME = "root";                       // Actualizar
    static final String PASSWORD = "";                       // Actualizar
    private static final String DATABASE = "racingmanager";          // Actualizar
    private static final String DRIVER = "jdbc:mysql";        // Usar para MySQL
    static final String URL = DRIVER+"://localhost:3306/";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL + DATABASE, USERNAME, PASSWORD);
    }

    public static void endConnection(Connection con) throws SQLException {
        con.close();
    }

    public void createTables(Connection con){
        try {
            Statement stmt = con.createStatement();

            String sql_user = "CREATE TABLE `jogador` (\n" +
                    "  `id` int(11) NOT NULL,\n" +
                    "  `nome` varchar(255) NOT NULL,\n" +
                    "  `password` varchar(255) NOT NULL,\n" +
                    "  `classificacao` int(11) NOT NULL\n" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;";
            stmt.executeQuery(sql_user);

            String sql_campeonato = "CREATE TABLE `campeonato` (\n" +
                    "  `participantes` varchar(2555) NOT NULL,\n" +
                    "  `nome` varchar(255) NOT NULL,\n" +
                    "  `lista_corridas` varchar(255) NOT NULL,\n" +
                    "  `pilotos` varchar(255) NOT NULL\n" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;";
            stmt.executeQuery(sql_campeonato);

            String sql_carro = "CREATE TABLE `carro` (\n" +
                    "  `id` int(11) NOT NULL,\n" +
                    "  `tipo_carro` enum('SC','PC2H','PC2','PC1H','PC1','GT','GTH') NOT NULL,\n" +
                    "  `marca` varchar(255) NOT NULL,\n" +
                    "  `modelo` varchar(255) NOT NULL,\n" +
                    "  `cilindrada` int(11) NOT NULL,\n" +
                    "  `potencia` int(11) NOT NULL,\n" +
                    "  `potencia_eletrica` int(11) NOT NULL,\n" +
                    "  `numero_voltas` int(11) NOT NULL,\n" +
                    "  `dnf` tinyint(1) NOT NULL,\n" +
                    "  `pneu` enum('CHUVA','MACIO','DURO') NOT NULL,\n" +
                    "  `motor` enum('CONSERVADOR','NORMAL','AGRESSIVO') NOT NULL,\n" +
                    "  `pac` double NOT NULL,\n" +
                    "  `downforce` double NOT NULL\n" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;";
            stmt.executeQuery(sql_carro);

            String sql_piloto = "CREATE TABLE `piloto` (\n" +
                    "  `id` int(11) NOT NULL,\n" +
                    "  `nome` varchar(255) NOT NULL,\n" +
                    "  `sva` double NOT NULL\n" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;\n";

            stmt.executeQuery(sql_piloto);

            String sql_circuito = "CREATE TABLE `circuito` (\n" +
                    "  `id` varchar(50) NOT NULL,\n" +
                    "  `nome` varchar(250) NOT NULL\n" +
                    "  `chicane` int(11) NOT NULL,\n" +
                    "  `retas` int(11) NOT NULL,\n" +
                    "  `curvas` int(11) NOT NULL,\n" +
                    "  `distancia` int(11) NOT NULL,\n" +
                    "  `voltas` int(11) NOT NULL,\n" +
                    "  `record` double NOT NULL,\n" +
                    "  `tempo_medio` double NOT NULL,\n" +
                    "  `dificuldades_curvas` varchar(2555) NOT NULL,\n" +
                    "  `dificuldade_retas` varchar(2555) NOT NULL\n" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;";

            stmt.executeQuery(sql_circuito);

            System.out.println("Created all tables successfully ! ");


        } catch (SQLException e) {
            //System.out.println("Error while creating: " + e.getMessage());
        }

    }


}
