import java.util.Random;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        //await_test();
        agreement_test();
    }

    public static void await_test() throws InterruptedException {
        int numThreads = 6; // Increase the number of threads for better demonstration
        Barrier barrier = new Barrier(numThreads);
        Thread[] threads = new Thread[numThreads+3];

        for (int i = 0; i < numThreads+3; i++) {
            final int threadId = i;
            threads[i] = new Thread(() -> {
                System.out.println("Thread " + threadId + " was created.");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                try {
                    barrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println("Thread " + threadId + " passed the barrier.");
            });
            threads[i].setName(String.valueOf(threadId));
        }

        for(int i=0; i<numThreads+3; i++){
            threads[i].start();
        }

        for(int i=0; i<numThreads+3; i++){
            threads[i].join();
        }
    }

    public static void agreement_test() throws InterruptedException{
        int numThreads = 6; // Increase the number of threads for better demonstration
        Agreement agreement = new Agreement(numThreads);
        Thread[] threads = new Thread[numThreads+3];
        final int[] r = {-1};

        for (int i = 0; i < numThreads+3; i++) {
            final int threadId = i;
            threads[i] = new Thread(() -> {
                System.out.println("Thread " + threadId + " was created.");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                try {
                    Random random = new Random();
                    int prop = random.nextInt(10);
                    System.out.println("Thread " + threadId + " is running its code with: " + prop);
                    r[0] = agreement.propose(prop);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println("Thread " + threadId + " passed the barrier with value: " + r[0]);
            });
            threads[i].setName(String.valueOf(threadId));
        }

        for(int i=0; i<numThreads+3; i++){
            threads[i].start();
        }

        for(int i=0; i<numThreads+3; i++){
            threads[i].join();
        }
    }
}