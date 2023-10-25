package Business.SSUtilizador;

import java.util.Random;

public class Administrador extends Utilizador {

    private String username;
    private String password;
    public Administrador()
    {
        super();
    }

    public Administrador(String id, String nome, String password)
    {
        super(id);
        this.username = nome;
        this.password = password;
    }

    public Administrador(Administrador a)
    {
        super(a.getId());
    }
    public boolean isAdmin(){
        return true;
    }
}
