import java.lang.reflect.AccessibleObject;
import java.util.Random;

public class BankTest {

    private static class Mover implements Runnable {
        Bank b;
        int s; // Number of accounts

        public Mover(Bank b, int s) { this.b=b; this.s=s; }

        public void run() {
            final int moves=10000;
            int from, to;
            Random rand = new Random();

            b.createAccount(1234);

            for (int m=0; m<moves; m++)
            {
                from=rand.nextInt(s+1); // Get one
                while ((to=rand.nextInt(s+1))==from); // Slow way to get distinct
                b.transfer(from,to,1);
            }
        }
    }

    private static class Observer implements Runnable {
        private Bank b;
        private int[] ids;
        public Observer(Bank b, int[] ids) {
            this.b = b;
            this.ids = ids;
        }

        @Override
        public void run() {
            final int balanceOperations = 10000;
            int counter=0;
            for (int i = 0; i < balanceOperations; i++) {
                int currentBalance = b.totalBalance(ids);
                if (currentBalance < 4000) counter++;
                System.out.println("Saldo Atual: " + currentBalance);
                }
            System.out.println(counter);
            }
        }


    public static void main(String[] args) throws InterruptedException {
        final int N = 10;

        Bank b = new Bank();
        int[] ids = {0,1,2,3};

        for (int i = 0; i < N; i++)
            b.createAccount(1000);

        int initialBalance = b.totalBalance(ids);
        System.out.println(initialBalance);

        b.print_accs();

        Thread t1 = new Thread(new Mover(b, 3));
        Thread t2 = new Thread(new Mover(b, 3));
        Thread t3 = new Thread(new Observer(b, ids));
        Thread t4 = new Thread(() -> { int r = b.closeAccount(0); System.out.println(r);});

        t1.start();
        t2.start();
        t3.start();
        t4.start();

        t1.join();
        t2.join();
        t3.join();
        t4.join();

        b.print_accs();

        System.out.println(b.totalBalance(ids));
    }
}
