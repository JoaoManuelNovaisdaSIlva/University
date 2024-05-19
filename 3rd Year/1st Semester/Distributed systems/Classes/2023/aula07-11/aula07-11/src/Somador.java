import java.util.concurrent.locks.ReentrantLock;

public class Somador {

    public ReentrantLock l = new ReentrantLock();
    private int v = 0;
    private int nV = 0;
    public Somador(){

    }

    public void soma(int x){
        this.l.lock();
        this.v += x;
        this.nV++;
        this.l.unlock();
    }

    public int media(){
        System.out.println("V = " + this.v + " / " + this.nV);
        return this.v/this.nV;
    }

    public int valorSoma(){
        this.l.lock();
        int s = this.v;
        this.l.unlock();
        return s;
    }
}
