public class Main {
    static int N=10;

    public static void main_inc(String[] args) throws InterruptedException {
        Thread[] threads = new Thread[N];

        for(int i=0; i<N; i++){
            threads[i] = new Thread(new Increment());
            threads[i].setName(String.valueOf(i));
        }

        for(int i=0; i<N; i++){
            threads[i].start();
        }

        for(int i=0; i<N; i++){
            threads[i].join();
        }
        System.out.println("FIM!");
    }

    public static void main_bank(String[] args) throws InterruptedException {
        int I = 1000;
        int V = 100;
        Thread[] threads = new Thread[N];

        for(int i=0; i<N; i++){
            threads[i] = Bank.deposit(V*I);
            threads[i].setName(String.valueOf(i));
        }

        for(int i=0; i<N; i++){
            threads[i].start();
        }

        for(int i=0; i<N; i++){
            threads[i].join();
        }
        System.out.println(Bank.balance());
        Bank.reset();
        System.out.println("FIM!");
    }

    public static void main(String[] args) throws InterruptedException {
        while(true){
            String[] argss = new String[0];
            main_bank(argss);
        }

    }
}


