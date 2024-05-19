import java.util.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

class Warehouse {
    private Map<String, Product> map =  new HashMap<String, Product>();
    ReentrantLock warehouseLock = new ReentrantLock();
    Condition cond = warehouseLock.newCondition();
    private int threads;
    private int epoch=1;

    public Warehouse(int n){
        this.threads = n;
    }

    private class Product { int quantity = 0; }

    private Product get(String item) {
        warehouseLock.lock();
        try{
            Product p = map.get(item);
            if (p != null) return p;
            p = new Product();
            map.put(item, p);
            return p;
        }finally {
            warehouseLock.unlock();
        }

    }

    public void supply(String item, int quantity) {
        warehouseLock.lock();
        try{
            Product p = get(item);
            p.quantity += quantity;
            cond.signalAll();
        }finally {
            warehouseLock.unlock();
        }
    }

    // Errado se faltar algum produto...
    public void consume(Set<String> items) throws InterruptedException{ // Greedy
        warehouseLock.lock();
        try {
            int e = this.epoch;
            for (String s : items) {
                if (e == this.epoch) {
                    while(get(s).quantity <= this.threads) cond.await();
                }else {
                    //cond.signalAll();
                    get(s).quantity--;
                    this.epoch++;
                }
            }

        }finally {
            warehouseLock.unlock();
        }
    }

    public void consumeCoop(Set<String> items) throws InterruptedException{ // Funciona mas não sei se está "correto", quantity = -1??
        warehouseLock.lock();
        try {
            for(String s : items){
                while(get(s).quantity < this.threads && get(s).quantity >= 0){
                    cond.await();
                }
                System.out.println("Consumi");
                get(s).quantity -= this.threads+1;
            }
        }finally {
            warehouseLock.unlock();
        }
    }
}