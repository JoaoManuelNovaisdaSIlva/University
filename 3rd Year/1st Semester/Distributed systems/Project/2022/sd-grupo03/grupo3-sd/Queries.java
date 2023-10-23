import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


public class Queries {

    private Connection con;
    private Demultiplexer demultiplexer;

    public Queries(Socket s) throws IOException{
        this.con = new Connection(s);
        this.demultiplexer=new Demultiplexer(this.con);
        new Thread(this.demultiplexer).start();
        System.out.println("Conexao Estabelicida");
    }

    public int register(String username, String password) {
        
        Utilizador utilizador = new Utilizador(username, password);
        Frame request = new Frame(0, false, utilizador);
        if (demultiplexer.send(request) == 1){
            return -1;
        }
        
        try {
            Frame response = demultiplexer.receive(0);
            System.out.println(response.getTag());

            return (Integer)response.getData();
        } catch (Exception e) {
            return -1;
        }
    }

    public Boolean login(String username, String password) {
        
        Utilizador utilizador = new Utilizador(username, password);
        Frame request = new Frame(1, false, utilizador);
        if (1==demultiplexer.send(request)){
            return false;
        }

        try {
            Frame response = demultiplexer.receive(1);

            return (Boolean)response.getData();
        } catch (Exception e) {
            return false;
        }
    }

    public List<Localizacao> get_available(Localizacao current){
        
        Frame request = new Frame(2, false, current);
        if (1==demultiplexer.send(request)){
            return null;
        }

        try {
            Frame response = demultiplexer.receive(2);

            List<?> avail =(List<?>)response.getData();
            List<Localizacao> list = new ArrayList<Localizacao>();
            for (Object o : avail) {
                list.add((Localizacao)o);
            }
            return list;
        } catch (Exception e) {
            return null;
        }

    }


    public ReservasTrotinete reserve_scooter(Localizacao start){
        
        Frame request = new Frame(3, false, start);
        if (1==demultiplexer.send(request)){
            return null;
        }

        try {
            Frame response = demultiplexer.receive(3);

            return (ReservasTrotinete)response.getData();
        } catch (Exception e) {
            return null;
        }
    }

    public double park_scooter(ReservasTrotinete res){
        
        Frame request = new Frame(4, false, res);
        if (1 == demultiplexer.send(request)) {
            return 0;
        }

        try {
            Frame response = demultiplexer.receive(4);
            return (Double) response.getData();
        } catch (Exception e) {
            return 0;
        }
    }



    public void close() throws IOException{
        demultiplexer.send(new Frame(-1, false, null));
        demultiplexer.close();
    }

    public boolean isRunning() {
        return demultiplexer.isRunning();
    }
}