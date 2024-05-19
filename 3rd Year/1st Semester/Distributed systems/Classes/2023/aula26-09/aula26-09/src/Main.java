
public class Main {
    public static void Incrementmain(String[] args) throws InterruptedException {
        int N=10;
        Thread[] threads = new Thread[N];

        for(int i=0;i<N;i++){
            threads[i] = new Thread(new Increment());
            threads[i].setName(String.valueOf(i));
        }

        for(int i=0;i<N;i++){
            threads[i].start();
        }

        for(int i=0;i<N;i++){
            threads[i].join();
        }

        System.out.println("Fim!");

    }

    public static void main(String[] args) throws InterruptedException{
        int N=10;
        Thread[] threads = new Thread[N];
        Bank bank = new Bank();

        for(int i=0;i<N;i++){
            threads[i] = new Thread(new Deposit(bank));
            threads[i].setName(String.valueOf(i));
        }

        for(int i=0;i<N;i++){
            threads[i].start();
        }

        for(int i=0;i<N;i++){
            threads[i].join();
        }

        System.out.println("Conta: " + bank.balance());
    }
}