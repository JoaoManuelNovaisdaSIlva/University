import java.util.concurrent.locks.ReentrantLock;

public class Bank {

    private static class Account {
        private int balance;

        private final ReentrantLock acc_lock = new ReentrantLock();
        Account(int balance) { this.balance = balance; }
        int balance() {
            acc_lock.lock();
            try {
                return balance;
            }finally {
                acc_lock.unlock();
            }
        }
        boolean deposit(int value) {
            acc_lock.lock();
            try{
                balance += value;
                return true;
            }finally {
                acc_lock.unlock();
            }

        }
        boolean withdraw(int value) {
            acc_lock.lock();
            try {
                if (value > balance)
                    return false;
                balance -= value;
                return true;
            }finally {
                acc_lock.unlock();
            }
        }
    }

    // Bank slots and vector of accounts
    private int slots;
    private Account[] av;

    private final ReentrantLock bank_lock = new ReentrantLock();

    public Bank(int n) {
        slots=n;
        av=new Account[slots];
        for (int i=0; i<slots; i++) av[i]=new Account(0);
    }

    // Account balance
    public int balance(int id) {
        if (id < 0 || id >= slots)
            return 0;
        return av[id].balance();
    }

    // Deposit
    public boolean deposit(int id, int value) {
        if (id < 0 || id >= slots)
            return false;
        return av[id].deposit(value);
    }

    // Withdraw; fails if no such account or insufficient balance
    public boolean withdraw(int id, int value) {
        if (id < 0 || id >= slots)
            return false;
        return av[id].withdraw(value);
    }

    public boolean transfer(int from, int to, int value){
        return withdraw(from, value) && deposit(to, value);
    }

    public int totalBalance(){
        int balance=0;
        for(Account acc : av){
            balance += acc.balance();
        }
        return balance;
    }
}