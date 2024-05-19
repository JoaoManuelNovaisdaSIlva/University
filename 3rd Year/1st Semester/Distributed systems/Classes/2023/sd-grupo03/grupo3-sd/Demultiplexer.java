import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

public class Demultiplexer implements Runnable{

    private final Connection con;
    private final ReentrantLock lock = new ReentrantLock();
    private Map <Integer, Alarm> alarms = new HashMap<>();
    private boolean running = true;

    public Demultiplexer(Connection con) {
        this.con = con;
    }

    @Override
    public void run() {
        System.out.println("Demultiplexer comecou");
        boolean running = true;
        while (running) {
            try {
                Frame frame = con.receive();
                this.lock.lock();
                Alarm a = this.alarms.get(frame.getTag());
                this.lock.unlock();
                a.push(frame);
            } catch (Exception e) {
                if (running == false) return;
                System.err.println("Erro no demultiplexer!");
                this.close();
                return;
            }
        }
    }

    public int send(Frame f){
        if(this.isRunning()==false) return 1;
        this.lock.lock();
        if (this.alarms.containsKey(f.getTag())==false) {
            this.alarms.put(f.getTag(), new Alarm());
        }
        this.lock.unlock();
        try {
            con.send(f);
            return 0;
        } catch (IOException e) {
            System.err.println("Erro ao enviar a frame pelo demultiplexer!");
            this.close();
            return 1;
        }
    }

    public Frame receive(int tag) throws InterruptedException {
        this.lock.lock();
        if (this.alarms.containsKey(tag)==false) {
            this.alarms.put(tag, new Alarm());
        }
        Alarm al = this.alarms.get(tag);
        this.lock.unlock();
        Frame f = al.poll();
        return f;
    }

    public void close() {
        this.lock.lock();
        this.con.close();
        this.running = false;
        this.lock.unlock();
    }

    public boolean isRunning() {
        this.lock.lock();
        boolean r = running;
        this.lock.unlock();
        return r;
    }
}
