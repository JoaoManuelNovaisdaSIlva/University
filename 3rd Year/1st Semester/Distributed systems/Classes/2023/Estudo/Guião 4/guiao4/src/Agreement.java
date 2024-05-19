import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Agreement {

    private int N;
    private ReentrantLock lock = new ReentrantLock();
    private Condition cond = lock.newCondition();
    private int max=0;
    private int counter=0;
    private int epoch=0;

    public Agreement (int N) {
        this.N = N;
    }

    public int propose(int choice) throws InterruptedException {
        lock.lock();
        try {
            counter++;
            max = Math.max(max, choice);
            int current = epoch;
            if(counter < N){
                while(current == epoch){
                    System.out.println("Thread: " + Thread.currentThread().getName() + " awaiting...");
                    cond.await();
                }
            }else{
                epoch++; counter=0;
                System.out.println("Thread: " + Thread.currentThread().getName() + " signaling...");
                cond.signalAll();
            }
            return max;
        }finally {
            lock.unlock();
        }
    }

}
