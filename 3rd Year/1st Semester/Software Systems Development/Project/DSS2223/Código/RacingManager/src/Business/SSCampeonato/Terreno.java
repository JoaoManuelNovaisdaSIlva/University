package Business.SSCampeonato;

import static java.lang.Integer.*;

public class Terreno {
    private String tipo; // 0-reta, 1-curva, 2-chincane
    private int dificuldade; // 0-facil, 1-medio ...

    public Terreno(){
        this.tipo = "";
        this.dificuldade = 0;
    }

    public Terreno(String t, int d){
        this.tipo = t;
        this.dificuldade = d;
    }

    public Terreno(String t){
        String[] sep = t.split(":");
        this.tipo = sep[0];
        this.dificuldade = parseInt(sep[1]);
    }

    public String getTipo(){
        return this.tipo;
    }

    public int getDificuldade(){
        return this.dificuldade;
    }

    public void setTipo(String t){
        this.tipo = t;
    }

    public void setDificuldade(int d){
        this.dificuldade = d;
    }
}
