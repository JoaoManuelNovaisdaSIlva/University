import java.util.concurrent.locks.ReentrantLock;

public class Somador {
    private int soma=0, counter=0;
    private ReentrantLock lock = new ReentrantLock();

    public double getMedia(){
        lock.lock();
        try{
            return (double) soma/counter;
        }finally {
            lock.unlock();
        }

    }

    public void new_soma(int soma){
        lock.lock();
        try{
            this.counter++;
            this.soma += soma;
        }finally {
            lock.unlock();
        }

     }
}
