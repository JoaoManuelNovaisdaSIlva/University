class Increment implements Runnable {
    public void run() {
        final long I=100;

        for (long i = 0; i < I; i++)
            System.out.println("[THREAD " + Thread.currentThread().getName() + "] : " + i);
    }
}