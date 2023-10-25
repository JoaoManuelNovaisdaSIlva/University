import Business.Database.CarroDAO;
import Business.Database.DAOConfig;
import Business.Database.UtilizadorDAO;
import Business.SSUtilizador.Jogador;
import Exceptions.CampeonatoNaoExistente;
import UI.TextUI;


public class Main {
    public static void main(String[] args) {
        try {
            new DAOConfig().createTables(DAOConfig.getConnection());
        } catch(Exception e) {}

        CarroDAO.buildInstance();
        UtilizadorDAO.buildInstance();

        /*
        UtilizadorDAO j = new UtilizadorDAO();
        Jogador jog = j.get(2);*/

        try {
            new TextUI();
        } catch(Exception e){
            System.out.println("Impossible to run UI.TextUI");
        } catch (CampeonatoNaoExistente e) {
            throw new RuntimeException(e);
        }

        //j.remove(3);
        //System.out.println(jog.printInfo());
    }
}