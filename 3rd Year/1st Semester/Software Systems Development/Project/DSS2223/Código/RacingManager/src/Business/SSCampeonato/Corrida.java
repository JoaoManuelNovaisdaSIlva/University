package Business.SSCampeonato;
/**
 * Write a description of class Business.SSCampeonato.Corrida here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import Business.Database.CircuitoDAO;
import Business.Database.UtilizadorDAO;
import Business.SSUtilizador.Utilizador;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;
import java.util.Collections;
import java.util.Random;
import java.util.Iterator;
import java.io.Serializable;

public class Corrida implements Serializable {
    //variaveis de instancia
    private String id_Corrida;
    private List<Carro> listaCarros;
    private Circuito circuito;
    private Set<Carro> resultados;
    //private Map<Business.Carros.Carro,Long> bestLap;
    private List<Carro> primeiro_da_Volta;

    private Map<Utilizador, Carro> participantes;
    private Map<Carro, Integer> dnf;
    private int clima; //1-chove 0-sol

    //Construtores
    public Corrida() {
        this.id_Corrida = "#" + Math.random();
        this.listaCarros = new ArrayList<Carro>();
        this.circuito = new Circuito();
        this.resultados = new TreeSet<Carro>();
        //this.bestLap = new HashMap<Business.Carros.Carro,Long>();
        this.primeiro_da_Volta = new ArrayList<Carro>();
        this.dnf = new HashMap<Carro, Integer>();
        Random rand = new Random();
        int x = rand.nextInt(2);
        this.clima = x;
    }


    public Corrida(Circuito c, Map<Utilizador, Carro> participantes) {
        this();
        for (Utilizador u : participantes.keySet()) {
            Carro car = participantes.get(u);
            this.participantes.put(u, car);
            this.listaCarros.add(car);
            this.resultados.add(car.clone());
            this.primeiro_da_Volta.add(car.clone());
        }
        this.circuito = c.clone();
        //this.clima = clima;
    }


    public Corrida(Corrida c) {
        this.id_Corrida = c.id_Corrida;
        this.listaCarros = c.getCarros();
        this.circuito = c.getCircuito();
        this.resultados = c.getResultados();
        this.primeiro_da_Volta = c.getPrimeiroVolta();
        this.dnf = c.getDNF();
        this.clima = c.getClima();
        this.participantes = c.participantes;
    }


    //Gets e sets
    public List<Carro> getCarros() {
        ArrayList<Carro> aux = new ArrayList<Carro>();
        for (Carro c : this.listaCarros) {
            aux.add(c.clone());
        }
        return aux;
    }

    public Circuito getCircuito() {
        return this.circuito.clone();
    }


    public Set<Carro> getResultados() {
        TreeSet<Carro> aux = new TreeSet<Carro>();
        for (Carro c : this.resultados) {
            aux.add(c.clone());
        }
        return aux;
    }

    public Map<Carro, Integer> getDNF() {
        HashMap<Carro, Integer> aux = new HashMap<Carro, Integer>();
        for (Carro c : this.dnf.keySet()) {
            aux.put(c.clone(), this.dnf.get(c));
        }
        return aux;
    }

    public int getClima() {
        return this.clima;
    }


    public List<Carro> getPrimeiroVolta() {
        ArrayList<Carro> aux = new ArrayList<Carro>();
        for (Carro c : this.primeiro_da_Volta) {
            aux.add(c.clone());
        }
        return aux;
    }


    public void setCircuito(Circuito c) {
        this.circuito = c.clone();
    }

    //Metodos

    public Corrida clone() {
        return new Corrida(this);
    }

    /**
     * Adicionar um carro á lista
     */
    public void adicionarCarro(Carro c) {
        this.listaCarros.add(c.clone());
    }

    /**
     * adicionar lista de carros
     */
    public void adicionarCarro(List<Carro> l) {
        for (Carro c : l) {
            this.listaCarros.add(c.clone());
        }
    }

    /**
     * Numero total de carros
     */
    public int totalCarros() {
        return this.listaCarros.size();
    }

    /**
     * Remover um carro
     */
    public void removerCarro(Carro c) {
        this.listaCarros.remove(c);
    }

    /**
     * Limpa a lista de carros
     */
    public void limpaListaCarros() {
        this.listaCarros.clear();
    }

    /**
     * Simula a corrida
     */
    public void simulaCorrida() {
        int voltas = this.circuito.getVoltas();
        long t_aux, t_volta;
        ArrayList<Carro> aux = new ArrayList<Carro>();
        HashMap<Carro, Integer> temp = new HashMap<Carro, Integer>();
        for (Carro c : this.listaCarros) {
            aux.add(c.clone());
        }
        for (int i = 0; i < voltas; i++) {
            for (Carro c : aux) {
                if (c.getDNF() == false) //verifica se o carro esta acidentado
                {
                    if (c.DNF(i, voltas, this.clima) == true) //verifica se o carro tem acidente na volta
                    {
                        c.setDNF(true);
                        temp.put(c.clone(), i);
                    }
                }
            }
        }
        for (Carro c : aux) {
            if (c.getDNF() == false) {
                this.resultados.add(c);
            }
        }
        this.dnf = temp;
    }


    /**
     * Metodo auxiliar privado para determinar o carro que vai em 1o a cada volta
     */

    /*
    public List<String> simularCorrida() {
        Circuito c = CircuitoDAO.buildInstance().get(this.circuito);
        int nvoltas = c.getVoltas();
        int nretas = c.getNum("RETA");


        List<Utilizador> posicoes = new ArrayList<>();
        posicoes.addAll(this.participantes);

        for (int i=0;i<nvoltas;i++){
            for (Utilizador user:posicoes){
                Carro p = participantes.get(user);
                if (this.dnf.containsKey(p) && this.listaCarros.get(p).despistar(i)){
                    this.dNF.put(p,1);
                    posicoes.remove(p);
                }
            }
            for (int x=0;x<nretas;x++){
                int dificuldadereta = parseInt(c.getListaDificuldadeRetas().get(x));
                if (dificuldadereta != 0){

                    String player_ant =posicoes.get(0);
                    PlayerSet playerset_ant = sets.get(player_ant);
                    int n = 0;
                    for (i=n+1;i<posicoes.size();i++){
                        String player=posicoes.get(i);
                        PlayerSet playerset = sets.get(player);
                        if(!this.dNF.containsKey(posicoes.get(i))) {
                            if (ultrapassa(playerset_ant,playerset,dificuldadereta,i)) {
                                posicoes.set(n, player);
                                posicoes.set(i, player_ant);
                            }else {
                                n = i;
                                player_ant = player;
                                playerset_ant = playerset;
                            }
                        }
                    }
                }

                if (c.getListaDificuldadeCurvas().size()>x) {
                    int dificuldadecurva = parseInt(c.getListaDificuldadeCurvas().get(x));
                    if (dificuldadecurva != 0) {
                        int n= 0;
                        String player_ant =posicoes.get(n);
                        PlayerSet playerset_ant = sets.get(player_ant);

                        for (i=n+1;i<posicoes.size();i++){
                            String player=posicoes.get(i);
                            PlayerSet playerset = sets.get(player);
                            if(!this.dNF.containsKey(posicoes.get(i))) {
                                if (ultrapassa(playerset_ant,playerset,dificuldadecurva,i)) {
                                    posicoes.set(n, player);
                                    posicoes.set(i, player_ant);
                                }else {
                                    n = i;
                                    player_ant = player;
                                    playerset_ant = playerset;
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    */

}
