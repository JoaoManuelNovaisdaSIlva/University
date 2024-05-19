import java.util.Random;

public class Main {

    private static class Mover implements Runnable {
        Bank b;
        int s; // Number of accounts

        public Mover(Bank b, int s) { this.b=b; this.s=s; }

        public void run() {
            for(int i=0; i<s; i++){
                b.deposit(1, 10);
            }


        }
    }
/**
    private static class Observer implements Runnable {
        private Bank b;
        private int expectedBalance;

        public Observer(Bank b, int expectedBalance) {
            this.b = b;
            this.expectedBalance = expectedBalance;
        }

        @Override
        public void run() {
            final int balanceOperations = 100000;

            for (int i = 0; i < balanceOperations; i++) {
                int currentBalance = b.totalBalance();
                if (currentBalance != this.expectedBalance) {
                    throw new RuntimeException("Unexpected balance: " + currentBalance);
                }
            }
        }
    }**/

    public static void main(String[] args) throws InterruptedException {
        final int N = 10;

        Bank b = new Bank();
        b.createAccount(10);
        int[] ids = {0};
        int initialBalance = b.totalBalance(ids);
        System.out.println("Initial Balance: " + initialBalance);

        Thread t1 = new Thread(new Mover(b, 100));
        Thread t2 = new Thread(new Mover(b, 100));
        Thread t3 = new Thread(new Mover(b, 100));


        t1.start();
        t2.start();
        t3.start();

        t1.join();
        t2.join();
        t3.join();

        System.out.println("Final Balance: " + b.totalBalance(ids));
    }
}
