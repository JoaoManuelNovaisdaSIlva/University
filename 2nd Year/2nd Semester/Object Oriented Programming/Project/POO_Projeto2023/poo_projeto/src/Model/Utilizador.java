package Model;

import java.io.Serializable;
import java.util.Objects;

public abstract class Utilizador implements Serializable {
    //private static int counter=0;
    private int id;
    private String email;
    private String nome;
    private String morada;
    private int nif;

    public Utilizador(){
        this.id = Dados.getNovoIdUtilizador();
        this.email = "";
        this.nome = "";
        this.morada = "";
        this.nif = 0;
    }

    public Utilizador(String email, String nome, String morada, int nif){
        this.id = Dados.getNovoIdUtilizador();
        this.email = email;
        this.nome = nome;
        this.morada = morada;
        this.nif = nif;
    }

    public Utilizador(Utilizador u){
        this.id = Dados.getNovoIdUtilizador();
        this.email = u.getEmail();
        this.nome = u.getNome();
        this.morada = u.getMorada();
        this.nif = u.getNif();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMorada() {
        return morada;
    }

    public void setMorada(String morada) {
        this.morada = morada;
    }

    public int getNif() {
        return nif;
    }

    public void setNif(int nif) {
        this.nif = nif;
    }

    public abstract Utilizador clone();

    @Override
    public int hashCode(){
        return Objects.hash(id, email, nome, morada, nif);
    }

    @Override
    public String toString(){
        return "Utilizador id: " + this.id + " ; " + "Nome: " + this.nome + " ; ";
    }

    @Override
    public boolean equals(Object o){
        if(this == o) return true;

        if(o == null || o.getClass() != this.getClass()) return false;

        Utilizador u = (Utilizador) o;
        return Objects.equals(this.id, u.getId()) && Objects.equals(this.email, u.getEmail()) && Objects.equals(this.nome, u.getNome())
                && Objects.equals(this.morada, u.getMorada()) && Objects.equals(this.nif, u.getNif());
    }
}
