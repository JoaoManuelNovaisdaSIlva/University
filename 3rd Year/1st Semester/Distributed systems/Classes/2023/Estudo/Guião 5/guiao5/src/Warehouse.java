import java.util.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

class Warehouse {
    private Map<String, Product> map =  new HashMap<String, Product>();
    private ReentrantLock warehouse_lock = new ReentrantLock(true);
    private Condition cond = warehouse_lock.newCondition();

    private int N;
    private int counter=0;
    private int turn=1;

    private class Product {
        private ReentrantLock product_lock = new ReentrantLock();
        int quantity = 0;
    }

    public Warehouse(int N){
        this.N = N;
    }

    private Product get(String item) {
        warehouse_lock.lock();
        try{
            Product p = map.get(item);
            if (p != null) return p;
            p = new Product();
            map.put(item, p);
            return p;
        }finally {
            warehouse_lock.unlock();
        }

    }

    public void supply(String item, int quantity) {
        Product p = null;
        warehouse_lock.lock();
        try {
            p = get(item);
        }finally {
            if(p!=null) {
                p.product_lock.lock();
                System.out.println("DEI SIGNAL");
                p.quantity += quantity;
                cond.signalAll();
                p.product_lock.unlock();
            }
            warehouse_lock.unlock();
        }
    }

    public void consume(Set<String> items) throws InterruptedException {
        Product p=null;
        warehouse_lock.lock();
        try {
            for (String s : items) {
                p = get(s);
                while (p.quantity <= 0) {
                    System.out.println("Thread: " + Thread.currentThread().getName() + " awaiting...");
                    cond.await();
                }
                p.product_lock.lock();
                try {
                    p.quantity--;
                }finally {
                    p.product_lock.unlock();
                }
            }

        }finally {
            warehouse_lock.unlock();
        }
    }

    public void consume_coop(Set<String> items) throws InterruptedException {
        Product p=null;
        warehouse_lock.lock();
        try {
            for (String s : items) {
                p = get(s);
                counter++;
                while (p.quantity <= 0 && counter <= N) {
                    System.out.println("Thread: " + Thread.currentThread().getName() + " awaiting...");
                    cond.await();
                }
                p.product_lock.lock();
                try {
                    p.quantity--;
                    System.out.println("Thread: " + Thread.currentThread().getName() + " consumed, current quantity: " + p.quantity);
                }finally {
                    p.product_lock.unlock();
                }
            }

        }finally {
            warehouse_lock.unlock();
        }
    }

    public void cosume_coop_starvation(Set<String> items) throws InterruptedException{
        Product p=null;
        warehouse_lock.lock();
        try {
            for (String s : items) {
                p = get(s);
                int current = counter++;
                while (p.quantity <= 0 || counter <= N || current == turn) {
                    System.out.println("Thread: " + Thread.currentThread().getName() + " awaiting...");
                    cond.await();
                }
                p.product_lock.lock();
                try {
                    p.quantity--;
                    turn++;
                    if(current == N) counter=0;
                    System.out.println("Thread: " + Thread.currentThread().getName() + " consumed, current quantity: " + p.quantity);
                }finally {
                    p.product_lock.unlock();
                }
            }

        }finally {
            warehouse_lock.unlock();
        }
    }

    public void consume_one(Product p){
        p.product_lock.lock();
        try {
            p.quantity--;
            System.out.println("Thread: " + Thread.currentThread().getName() + " consumed, current quantity: " + p.quantity);
        }finally {
            p.product_lock.unlock();
        }
    }

}