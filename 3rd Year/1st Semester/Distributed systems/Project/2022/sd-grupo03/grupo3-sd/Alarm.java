import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Alarm {
    private final ReentrantLock lock;
    private final Condition alarme;
    private final Queue<Frame> queue;

    public Alarm(){
        this.lock = new ReentrantLock();
        this.alarme = this.lock.newCondition();
        this.queue = new LinkedList<>();
    }

    public Frame poll() throws InterruptedException{
        this.lock.lock();
        this.alarme.await();
        Frame f = this.queue.poll();
        this.lock.unlock();
        return f;
    }

    public void push(Frame f){
        this.lock.lock();
        this.queue.add(f);
        this.alarme.signal();
        this.lock.unlock();
    }

    public void clear(){
        this.lock.lock();
        if (this.queue.isEmpty() == false) {
            this.alarme.signalAll();
        }
        this.lock.unlock();
    }
}
