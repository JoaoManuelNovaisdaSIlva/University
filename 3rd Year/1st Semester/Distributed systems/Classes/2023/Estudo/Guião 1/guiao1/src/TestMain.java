public class TestMain {
    public static void main(String[] args) throws InterruptedException {
        Thread alice = new Thread(() -> {
            teste.lock(0);
        });
        Thread bob = new Thread(() -> {
            teste.lock(1);
        });

        alice.start();
        bob.start();

        alice.join();
        bob.join();

    }
}
