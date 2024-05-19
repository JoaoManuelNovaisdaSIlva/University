import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Barrier {
    private int N;
    private final ReentrantLock barrier_lock = new ReentrantLock();
    private Condition cond = barrier_lock.newCondition();
    private int counter=0;
    private int epoch=0;

    private int turn=0;

    public Barrier (int N) {
        this.N = N;
    }

    public void await_fraco() throws InterruptedException {
        barrier_lock.lock();
        try {
            counter++;

            while (counter < N) {
                System.out.println("Thread: " + Thread.currentThread().getName() + " awaiting...");
                cond.await();
            }
            System.out.println("Thread: " + Thread.currentThread().getName() + " signaling...");
            cond.signalAll();
        } finally {
            barrier_lock.unlock();
        }
    }
    public void await() throws InterruptedException {
        barrier_lock.lock();
        try{
            int atual = epoch;
            counter++;
            if(counter<N){
                while(atual == epoch){
                    System.out.println("Thread: " + Thread.currentThread().getName() + " awaiting...");
                    cond.await();
                }
            }else{
                counter=0; epoch++;
                System.out.println("Thread: " + Thread.currentThread().getName() + " signaling...");
                cond.signalAll();
            }
            System.out.println("Thread: " + Thread.currentThread().getName() + " woke up...");
        }finally {
            barrier_lock.unlock();
        }
    }
}
