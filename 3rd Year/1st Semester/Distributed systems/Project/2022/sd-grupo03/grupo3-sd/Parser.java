
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Parser {
    private Map <String, Utilizador> utilizadores;
    private int nextId;
    private ReadWriteLock lock;

    public Parser() throws IOException {
        this.utilizadores = new HashMap <String, Utilizador>();
        this.nextId = 0;
        this.lock = new ReentrantReadWriteLock();
        this.readFile();
    }

    public void readFile() {
        try {
            Scanner scanner = new Scanner(new File("contas.txt"));

            while (scanner.hasNextLine()) {
                String[] line = scanner.nextLine().split(";");
                if(line.length != 2) continue;
                Utilizador u = new Utilizador(line[0], line[1]);

                this.inicializeUser(u);

            }

            scanner.close();
        } catch(Exception e) { System.err.println("Erru ao ler o ficheiro"); }
    }

    public int inicializeUser(Utilizador u) {
        String username = u.getName();
        lock.writeLock().lock();
        if (utilizadores.containsKey(username)) {
            lock.writeLock().unlock();
            return -2;
        }
        int id = nextId++;
        u.setId(id);
        u.setLoggedIn(false);
        utilizadores.put(u.getName(),u);
        lock.writeLock().unlock();
        return id;
    }

    public int addUser(Utilizador u) {
        String username = u.getName();
        lock.writeLock().lock();
        if (utilizadores.containsKey(username)) {
            lock.writeLock().unlock();
            return -1;
        }
        int id = nextId++;
        u.setId(id);
        u.setLoggedIn(true);
        utilizadores.put(u.getName(),u);
        lock.writeLock().unlock();
        writeInFile(u.getName(),u.getPassword(),u.getId());
        return id;
    }

    public Utilizador getUser(String username) {
        lock.readLock().lock();
        try {
            return utilizadores.get(username);
        } finally {
            lock.readLock().unlock();
        }
    }

    public void logout(String username) {
        lock.readLock().lock();
        if (!utilizadores.containsKey(username)) {
            lock.readLock().unlock();
            return;
        }
        Utilizador u = utilizadores.get(username);
        lock.readLock().unlock();
        u.setLoggedIn(false);
    }

    public boolean login(String username, String pass) {
        lock.readLock().lock();
        if (!utilizadores.containsKey(username)) {
            lock.readLock().unlock();
            return false;
        }
        Utilizador u = utilizadores.get(username);
        lock.readLock().unlock();
        return u.login(pass);
    }

    public void writeInFile(String name, String pw, int id){
        BufferedWriter writer;
        try{
            writer = new BufferedWriter(new FileWriter("contas.txt",true));
            String newAccount = '\n' + name + ';' + pw + ';' + id;
            writer.write(newAccount);
            writer.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

}
