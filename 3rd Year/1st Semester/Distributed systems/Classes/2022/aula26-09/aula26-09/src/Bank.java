import java.util.concurrent.locks.ReentrantLock;

class Bank{

    private static class Account {
        private int balance;
        Account(int balance) { this.balance = balance; }
        int balance() { return balance; }
        boolean deposit(int value) {
            balance += value;
            return true;
        }
    }

    // Our single account, for now
    private Account savings = new Account(0);

    private ReentrantLock lock = new ReentrantLock();

    // Account balance
    public int balance() {
        return savings.balance();
    }

    // Deposit
    boolean deposit(int value) {
        try{
            lock.lock();
            return savings.deposit(value);
        }finally {
            lock.unlock();
        }
    }
}