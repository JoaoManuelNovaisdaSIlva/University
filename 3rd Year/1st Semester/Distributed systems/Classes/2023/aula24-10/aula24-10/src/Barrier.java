import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Barrier implements Runnable{
    private ReentrantLock barrierLock = new ReentrantLock();
    private Condition cond = barrierLock.newCondition();
    private int invocacoes=1;
    private final int threads;

    private int epoch=0;

    Barrier(int N){
        this.threads = N;
    }

    void espera() throws InterruptedException{
        barrierLock.lock();
        try{
            int e = this.epoch;
            invocacoes += 1;
            if(invocacoes <= threads){ // Condição de espera
                while(epoch == e){
                    cond.await();
                }
            }else {
                cond.signalAll();
                invocacoes = 0;
                epoch += 1;
            }
        }finally {
            barrierLock.unlock();
        }
    }

    @Override
    public void run() {
        try {
            espera();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
