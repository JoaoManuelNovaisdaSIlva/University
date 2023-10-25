package Business.Database;

import Business.SSCampeonato.Campeonato;
import Business.SSCampeonato.Circuito;
import Business.SSCampeonato.Piloto;
import Business.SSUtilizador.Administrador;
import Business.SSUtilizador.Utilizador;

import java.sql.*;
import java.util.*;


public class CampeonatoDAO implements Map {

    public static CampeonatoDAO singleton;

    public CampeonatoDAO(){

    }

    public static CampeonatoDAO buildInstance(){
        if(CampeonatoDAO.singleton == null)
            CampeonatoDAO.singleton = new CampeonatoDAO();
        return CampeonatoDAO.singleton;
    }

    public Campeonato get(int id){
        String sql = "SELECT * FROM campeonato WHERE nome = ? ";
        try {
            Connection con = DAOConfig.getConnection();
            PreparedStatement select = con.prepareStatement(sql);
            select.setInt(1, id);
            ResultSet rs = select.executeQuery();

            if(rs.next()){
                String[] circ = rs.getString("lista_corridas").split(",");
                List<Circuito> circ_final = new ArrayList<Circuito>();
                //for(String c : circ) { circ_final.add(c); }

                Map<String, Utilizador> part = new HashMap<>();
                for(String p : rs.getString("participantes").split(",")){
                    part.put( p, new UtilizadorDAO().get(p) );
                }

                Map<String, Piloto> pil = new HashMap<>();
                for(String p : rs.getString("pilotos").split(",")){
                    // PILOTOS DAO
                    // part.put( p, new Piloto().get(p) );
                }


                return new Campeonato(rs.getString("nome"), circ_final, part, pil,
                        rs.getInt("nAfinacoes"), 0, new HashMap<>(), new ArrayList<>());
            }



        } catch (SQLException e){
            System.out.println("Impossible to find user with id = " + id + ": " + e.getMessage());
            return null;
        }


        return null;
    }

    public Campeonato get(String nome){
        String sql = "SELECT * FROM Campeonato WHERE nome = ? ";
        try {
            Connection con = DAOConfig.getConnection();
            PreparedStatement select = con.prepareStatement(sql);
            select.setString(1, nome);
            ResultSet rs = select.executeQuery();

            if(rs.next())
                return null;
                //return new Campeonato(rs.getString("id"), rs.getString(2), rs.getString(3));


        } catch (SQLException e){
            System.out.println("Impossible to find user with nome = " + nome + ": " + e.getMessage());
            return null;
        }


        return null;
    }

    public Utilizador get(String nome, String password){
        String sql = "SELECT * FROM Campeonato WHERE nome = ? AND password = ? ";
        try {
            Connection con = DAOConfig.getConnection();
            PreparedStatement select = con.prepareStatement(sql);
            select.setString(1, nome);
            select.setString(2, password);
            ResultSet rs = select.executeQuery();

            if(rs.next()){
                //FALTA
            }


        } catch (SQLException e){
            System.out.println("Impossible to find user with nome = " + nome + ": " + e.getMessage());
            return null;
        }


        return null;
    }
    public int put(String nome, String password, int admin){
        if(this.get(nome) != null){
            return -1; // VERIFICAR NA AULA
        }

        String sql = "INSERT INTO Campeonato (`nome`, `password`, `admin`)" +
                "                      VALUES (?, ?, ?)";
        try {
            Connection con = DAOConfig.getConnection();
            PreparedStatement insert = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            insert.setString(1, nome);
            insert.setString(2, password);
            int error = insert.executeUpdate();

            if(error != 0) {
                try (ResultSet rs = insert.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1);
                    }
                }
            }


        } catch (SQLException e){
            System.out.println("Impossible to add user: " + e.getMessage());
            return -1;
        }

        return -1;
    }


    @Override
    public int size() {
        int size = 0;
        String query = "SELECT count(*)  from Campeonato";
        try {
            Connection con = DAOConfig.getConnection();
            PreparedStatement preparedStmt = con.prepareStatement(query);

            ResultSet rs = preparedStmt.executeQuery();

            if(rs.next())
                size = rs.getInt(1);

        } catch (SQLException e){
            System.out.println("Impossible to delete user: " + e.getMessage());
        }
        return size;
    }

    @Override
    public boolean isEmpty() {
        if (size() == 0) return true; else return false;
    }

    @Override
    public boolean containsKey(Object key) {
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        return false;
    }

    @Override
    public Object get(Object key) {
        if(key instanceof String) {
            return get((String) key);
        }
        if(key instanceof Integer) {
            return get((Integer) key);
        }
        return null;
    }

    @Override
    public Object put(Object key, Object value) {
        return null;
    }

    @Override
    public Object remove(Object key) {
        if(!(key instanceof Integer)) return null;
        String query = "DELETE from Campeonato WHERE id = ?";
        try {
            Connection con = DAOConfig.getConnection();
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setInt(1, (Integer) key);

            if(preparedStmt.execute())
                return true;
        } catch (SQLException e){
            System.out.println("Impossible to delete user: " + e.getMessage());
        }

        return false;
    }


    /* Do I need to define */
    @Override
    public void putAll(Map m) {

    }

    @Override
    public void clear() {

    }

    @Override
    public Set keySet() {

        Set<String> set = new HashSet<>();

        try {
            Connection con = DAOConfig.getConnection();
            Statement stm = con.createStatement();
            String sql = "SELECT * FROM Carros";
            ResultSet rs = stm.executeQuery(sql);
            while (rs.next()) {
                set.add(String.valueOf(rs.getInt("id")));
            }
        }catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return set;
    }

    @Override
    public Collection values() {
        return null;
    }

    @Override
    public Set<Entry> entrySet() {
        return null;
    }
}
