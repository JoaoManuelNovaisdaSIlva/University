import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ClientHandler implements Runnable{
    private Connection con;
    private boolean running;
    private Parser utilizadores;
    private Mapa mapa;
    private String current;
    private ReadWriteLock lock;

    public ClientHandler(Connection con, Parser userDB, Mapa m) {
        try {
            this.con = con;
            this.utilizadores = userDB;
            this.mapa = m;
            this.running = true;
            this.current = null;
            this.lock = new ReentrantReadWriteLock();
        } catch (Exception e) {
            System.out.println("Erro ao contruir o clientehandler: " + e.getMessage());
        }
    }


    @Override
    public void run() {
        while(this.isRunning()){
            try {
                Frame msg = con.receive();
                if (msg.getTag() == -1) {
                    this.lock.writeLock().lock();
                    running = false;
                    this.lock.writeLock().unlock();
                    if (this.current != null) {
                        this.utilizadores.logout(this.current);
                        System.out.println("O utilizador " + this.current + " disconectou-se");
                    }
                    System.out.println("O cliente fechou a conexao");
                    return;
                }else if(msg.getTag() >= 0 && msg.getTag() <= 7){
                    this.handleQuery(msg);
                }
            }catch (Exception e) {
                con.close();
                if (this.current != null) {
                    this.utilizadores.logout(this.current);
                    System.out.println("O utilizador " + this.current + " disconectou-se");
                }
                this.lock.writeLock().lock();
                this.running = false;
                this.lock.writeLock().unlock();
                System.out.println("Concexao fechada pelo cliente");
                break;
            }
        }
        this.con.close();
        System.out.println("Servidor fechado");

    }

    public void handleQuery(Frame f){
        System.out.println("Recebi a tag: " + f.getTag());
        try {
            switch (f.getTag()){
                case 0:
                    Utilizador u1= (Utilizador) f.getData();
                    int newID = utilizadores.addUser(u1);
                    if (newID!=-1){
                        System.out.println("Novo utilizador: " + u1.getName()+ "\ncom o seguinte id: " + u1.getId());
                        this.current=u1.getName();
                    }
                    this.con.send(new Frame(0,true,newID));
                    break;
                case 1:
                    Utilizador u=(Utilizador)f.getData();
                    boolean login=utilizadores.login(u.getName(),u.getPassword());
                    if (login){
                        System.out.println("O utilizador: " + u.getName() + " Fez login");
                        this.current=u.getName();
                    }
                    this.con.send(new Frame(1,true,login));
                    break;
                case 2:
                    Localizacao p=(Localizacao)f.getData();
                    System.out.println("O utilizador: " + this.current + " quer saber do dispor de trotinetes em: " + "(" + p.getX() + "," + p.getY() + ")");
                    this.con.send(new Frame(2,true,mapa.avaiable(p)));
                    break;
                case 3:
                    Localizacao p1=(Localizacao)f.getData();
                    System.out.println("O utilizador: " + this.current + " quer alugar um trotinete em: " + "(" + p1.getX() + ":" + p1.getY() + ")");
                    ReservasTrotinete r = mapa.aluga(p1);
                    this.con.send(new Frame(3,true,r));
                    break;
                case 4:
                    ReservasTrotinete r1=(ReservasTrotinete)f.getData();
                    System.out.println("O utilizador:  " + this.current + " quer terminar o aluguer em: " + "(" + r1.getEnd().getX() + "," + r1.getEnd().getY() + ")");
                    double price = mapa.arruma(r1);
                    this.con.send(new Frame(4,true,price));
                    System.out.println("O utilizador " + this.current + " deve " + price + " euros");
                    break;
            }

        } catch (Exception e) {
            System.out.println("Erro no handle query: " + e.getMessage());
        }
        //System.out.println("Enviei: " + f.getTipoFrame());
    }

    public boolean isRunning(){
        this.lock.readLock().lock();
        boolean runs=this.running;
        this.lock.readLock().unlock();
        return runs;
    }
}

