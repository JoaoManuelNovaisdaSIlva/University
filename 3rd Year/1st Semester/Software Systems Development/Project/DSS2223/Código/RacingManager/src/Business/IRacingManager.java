package Business;

import Business.SSCampeonato.Campeonato;
import Business.SSCampeonato.Carro;
import Business.SSCampeonato.Corrida;
import Business.SSCampeonato.Piloto;
import Business.SSUtilizador.Utilizador;
import Exceptions.AdicionaItemError;
import Exceptions.RemoveItemError;
import Exceptions.SetItemFailed;
import Exceptions.UtilizadorNaoEncontrado;

import java.util.List;
import java.util.Map;

public interface IRacingManager {

    Map<String, Utilizador> getUtilizadores();
    Map<Integer, Carro> getCarros();
    List<Campeonato> getCampeonatos();
    List<Corrida> getCorridas();
    List<Piloto> getPilotos();

    void setUtilizadores(Map<String, Utilizador> u) throws SetItemFailed;
    void setCarros(Map<Integer, Carro> c) throws SetItemFailed;
    void setCampeonatos(List<Campeonato> c) throws SetItemFailed;
    void setCorridas(List<Corrida> c) throws SetItemFailed;
    void setPilotos(List<Piloto> p) throws SetItemFailed;

    void adicionaUtilizador(Utilizador u) throws AdicionaItemError;
    void adicionaCarro(Carro u) throws AdicionaItemError;
    void adicionaCampeonato(Campeonato u) throws AdicionaItemError;
    void adicionaCorrida(Corrida u) throws AdicionaItemError;
    void adicionaPiloto(Piloto u) throws AdicionaItemError;

    void removerUtilizador(Utilizador u) throws RemoveItemError;
    void removerCarro(Carro u) throws RemoveItemError;
    void removerCampeonato(Campeonato u) throws RemoveItemError;
    void removerCorrida(Corrida u) throws RemoveItemError;
    void removerPiloto(Piloto u) throws RemoveItemError;

    Utilizador verificaCredenciais(String username, String password) throws UtilizadorNaoEncontrado;
}
