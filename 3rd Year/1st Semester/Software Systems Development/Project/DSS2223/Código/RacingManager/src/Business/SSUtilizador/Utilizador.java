package Business.SSUtilizador;

import java.util.Random;

public abstract class Utilizador {

    private String id;

    public Utilizador(){
        this("#" + new Random().nextInt(10000));
    }
    public Utilizador(String id){
        this.id = id;
    }

    public String getId(){
        return this.id;
    }


    public void setId(String id){ this.id = id; }

    public abstract boolean isAdmin();

}
