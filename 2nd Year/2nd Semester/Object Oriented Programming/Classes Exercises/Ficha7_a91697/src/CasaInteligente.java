import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Stream;

public class CasaInteligente {

    private String nomeCasa;
    private Map<String, List<String>> divisao;
    private List<SmartDevice> dispositivos;

    public CasaInteligente()
    {
        this("Casa#" + new Random().nextInt());
    }


    public CasaInteligente(String nomeCasa)
    {
        this.dispositivos = new ArrayList<>();
        this.nomeCasa = nomeCasa;
        this.divisao = new HashMap<>();
    }

    public void addRoom(String nomeDivisao)
    {
        this.divisao.put(nomeDivisao, new ArrayList<>());
    }

    public void addDevice(SmartDevice dev)
    {
        this.dispositivos.add(dev.clone());
    }

    public SmartDevice getDevice(String ID)
    {
        for (SmartDevice d: this.dispositivos)
        {
            if (d.getID().equals(ID))
                return d;
        }
        return null;
    }

    public void setAllOn(boolean newState)
    {
        this.dispositivos.forEach(d -> d.setOn(newState));
    }


    public boolean hasRoom(String nomeDivisao)
    {
        return this.divisao.containsKey(nomeDivisao);
    }

    public boolean existsDevice(String ID)
    {
        return this.dispositivos.stream().anyMatch(d -> d.getID().equals(ID));
    }

    public void addToRoom(String sala, String ID)
    {
        List<String> novo = new ArrayList<>();
        novo.add(ID);
        if (this.divisao.containsKey(sala) )
            this.divisao.get(sala).add(ID);
        else
            this.divisao.put(sala, novo);
    }

    public boolean roomHasDevice(String sala, String ID)
    {
        if (this.divisao.get(sala).contains(ID))
            return true;
        return false;
    }
}
