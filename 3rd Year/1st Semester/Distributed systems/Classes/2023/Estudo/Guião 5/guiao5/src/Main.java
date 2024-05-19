import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        int N=5;
        Thread[] threads = new Thread[N+1];
        Warehouse w = new Warehouse(N);
        Set<String> s = new HashSet<>();
        s.add("Tilhas");

        for(int i=0; i<=N; i++){
            int fI=i;
            threads[i] = new Thread(()->{
                try{
                    //Thread.sleep(1000*fI);
                    System.out.println("Criei a thread: " + fI);
                    w.consume(s);
                    System.out.println("Consegui consumir! [Thread " + fI + "]");

                } catch (Exception ignored) {}
            });
            threads[i].setName(String.valueOf(i));
        }

        for(int i=0; i<=N; i++){
            threads[i].start();
        }

        Thread.sleep(10000);
        w.supply("Tilhas", 5);
        System.out.println("DEI SUPPLY");

        for(int i=0; i<=N; i++){
            threads[i].join();
        }
    }
}