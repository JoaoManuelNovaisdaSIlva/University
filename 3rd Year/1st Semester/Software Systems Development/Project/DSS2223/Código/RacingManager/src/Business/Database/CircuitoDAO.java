package Business.Database;

import Business.SSCampeonato.Circuito;

import java.sql.*;
import java.util.Collection;
import java.util.Map;
import java.util.Set;


public class CircuitoDAO implements Map {


    public static CircuitoDAO singleton;

    public CircuitoDAO(){ }


    public static CircuitoDAO buildInstance(){
        if(CircuitoDAO.singleton == null)
            CircuitoDAO.singleton = new CircuitoDAO();
        return CircuitoDAO.singleton;
    }

    public Circuito get(String id){
        String sql = "SELECT * FROM circuito WHERE id = ? ";
        try {
            Connection con = DAOConfig.getConnection();
            PreparedStatement select = con.prepareStatement(sql);
            select.setString(1, id);
            ResultSet rs = select.executeQuery();

            if(rs.next())
                return new Circuito(rs.getString("id"), rs.getInt("voltas"),
                        rs.getString("mapa"));


        } catch (SQLException e){
            System.out.println("Impossible to find circuito with id = " + id + ": " + e.getMessage());
            return null;
        }


        return null;
    }

    public Circuito get(int id){
        String sql = "SELECT * FROM circuito WHERE id = ? ";
        try {
            Connection con = DAOConfig.getConnection();
            PreparedStatement select = con.prepareStatement(sql);
            select.setInt(1, id);
            ResultSet rs = select.executeQuery();

            if(rs.next())
                return new  Circuito(rs.getString("id"), rs.getInt("voltas"),
                        rs.getString("mapa"));

        } catch (SQLException e){
            System.out.println("Impossible to find circuito with nome = " + id + ": " + e.getMessage());
            return null;
        }


        return null;
    }

    public int put(String nome, String password, int admin){
        if(this.get(nome) != null){
            return -1; // VERIFICAR NA AULA
        }

        String sql = "INSERT INTO jogador (`nome`, `password`, `admin`)" +
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
            System.out.println("Impossible to add ciruito: " + e.getMessage());
            return -1;
        }

        return -1;
    }


    @Override
    public int size() {
        int size = 0;
        String query = "SELECT count(*)  from circuitos";
        try {
            Connection con = DAOConfig.getConnection();
            PreparedStatement preparedStmt = con.prepareStatement(query);

            ResultSet rs = preparedStmt.executeQuery();

            if(rs.next())
                size = rs.getInt(1);

        } catch (SQLException e){
            System.out.println("Impossible to delete circuito: " + e.getMessage());
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
        String query = "DELETE from circuito WHERE id = ?";
        try {
            Connection con = DAOConfig.getConnection();
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setInt(1, (Integer) key);

            if(preparedStmt.execute())
                return true;
        } catch (SQLException e){
            System.out.println("Impossible to delete circuito: " + e.getMessage());
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
        return null;
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
