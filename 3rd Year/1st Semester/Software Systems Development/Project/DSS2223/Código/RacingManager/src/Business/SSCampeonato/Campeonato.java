package Business.SSCampeonato;
/**
 * Write a description of class Business.SSCampeonato.Campeonato here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import Business.SSUtilizador.Utilizador;

import java.util.*;
import java.io.Serializable;

public class Campeonato implements Serializable
{
    private List<Circuito> circuitos;
    private Map<String,Integer> classificacao;

    private int corridaAtual;

    private String nome;
    private Map<String, Utilizador> participantes;

    private Map<String, Piloto> pilotos;

    private List<Carro> carros;

    private int nAfinacoes;

    public Campeonato()
    {
        this.nome = "#CAMP-" + new Random().nextInt(1000);
        this.circuitos = new ArrayList<Circuito>();
        this.classificacao = new HashMap<>();
        this.corridaAtual = 0;
        this.participantes = new HashMap<>();
        this.pilotos = new HashMap<>();
        this.nAfinacoes = 0;
        this.carros = new ArrayList<>();
    }
    
    public Campeonato(String nome, List<Circuito> cor, Map<String,Utilizador> part, Map<String,Piloto> pil, int nAfinacoes,
                      int corridaAtual, Map<String, Integer> classific, List<Carro> car)
    {
        this();
        ArrayList<Circuito> aux = new ArrayList<Circuito>();
        for(Circuito co : cor)
        {
            aux.add(co.clone());
        }

        Map<String, Utilizador> aux2 = new HashMap<>();
        for(String s : part.keySet()){
            aux2.put(s, part.get(s));
        }

        Map<String, Piloto> aux3 = new HashMap<>();
        for(String s : pil.keySet()){
            aux3.put(s, pil.get(s));
        }

        Map<String, Integer> aux4 = new HashMap<>();
        for(String s : classific.keySet()){
            aux4.put(s, classific.get(s));
        }

        List<Carro> aux5 = new ArrayList<>();
        for(Carro c : car){
            aux5.add(c.clone());
        }

        this.nome = nome;
        this.circuitos = aux;
        this.pilotos = aux3;
        this.participantes = aux2;
        this.classificacao = aux4;
        this.carros = aux5;
        this.nAfinacoes = nAfinacoes;
        this.corridaAtual = corridaAtual;
    }
    
    public Campeonato(Campeonato c)
    {
        this.nome = c.nome;
        this.circuitos = c.getCircuitos();
        this.classificacao = c.getClassificacao();
        this.corridaAtual = c.getCorridasAtual();
        this.participantes = c.getParticipantes();
        this.carros = c.getCarros();
        this.pilotos = c.getPilotos();
        this.nAfinacoes = c.getnAfinacoes();
    }

    public List<Circuito> getCircuitos(){
        return this.circuitos;
    }

    public Map<String,Integer> getClassificacao(){
        return this.classificacao;
    }
    public int getCorridasAtual()
    {
        return this.corridaAtual;
    }

    public Map<String, Utilizador> getParticipantes(){
        return this.participantes;
    }

    public Map<String, Piloto> getPilotos(){
        return this.pilotos;
    }

    public int getnAfinacoes(){
        return this.nAfinacoes;
    }

    public List<Carro> getCarros(){ return this.carros;}

    public void setCircuitos(List<Circuito> cir){
        this.circuitos = cir;
    }

    public void setClassificacao(Map<String, Integer> c){
        this.classificacao = c;
    }

    public void setCorridaAtual(int corridaAtual){
        this.corridaAtual = corridaAtual;
    }

    public void setParticipantes(Map<String, Utilizador> p){
        this.participantes = p;
    }

    public void setPilotos(Map<String, Piloto> p){
        this.pilotos = p;
    }

    public String getNome(){
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setnAfinacoes(int a){
        this.nAfinacoes = a;
    }

    public void simularCampeonato() {
        for(Circuito c: this.circuitos) {
            System.out.println("A simular circuito: " + c.getNome());
            c.simularCircuito();
        }
    }

}
