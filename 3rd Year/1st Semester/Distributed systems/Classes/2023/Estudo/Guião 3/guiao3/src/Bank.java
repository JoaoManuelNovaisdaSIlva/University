import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

class Bank {

    private static class Account {
        private int balance;

        private final ReentrantLock acc_lock = new ReentrantLock();
        Account(int balance) { this.balance = balance; }
        int balance() {
            acc_lock.lock();
            try {
                return this.balance;
            }finally {
                acc_lock.unlock();
            }
        }
        boolean deposit(int value) {
            acc_lock.lock();
            try {
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

    private Map<Integer, Account> map = new HashMap<Integer, Account>();
    private int nextId = 0;

    private final ReentrantLock bank_lock = new ReentrantLock();
    private final ReentrantReadWriteLock bank_rwlock = new ReentrantReadWriteLock();
    private final Lock bank_rlock = bank_rwlock.readLock();
    private final Lock bank_wlock = bank_rwlock.writeLock();

    // create account and return account id
    public int createAccount(int balance) {
        Account c = new Account(balance);

        bank_wlock.lock();
        try{
            int id = nextId;
            nextId += 1;
            map.put(id, c);
            return id;
        }finally {
            bank_wlock.unlock();
        }
    }

    // close account and return balance, or 0 if no such account
    public int closeAccount(int id) {
        bank_wlock.lock();
        try{
            Account c = map.remove(id);
            if (c == null)
                return 0;
            return c.balance();
        }finally {
            bank_wlock.unlock();
        }
    }

    // account balance; 0 if no such account
    public int balance(int id) {
        Account c;

        bank_rlock.lock();
        try{
            c = map.get(id);
            if (c == null)
                return 0;
            return c.balance();
        }finally {
            bank_rlock.unlock();
        }
    }

    // deposit; fails if no such account
    public boolean deposit(int id, int value) {
        bank_rlock.lock();
        try{
            Account c = map.get(id);
            if (c == null)
                return false;
            return c.deposit(value);
        }finally {
            bank_rlock.unlock();
        }
    }

    // withdraw; fails if no such account or insufficient balance
    public boolean withdraw(int id, int value) {
        bank_rlock.lock();
        try{
            Account c = map.get(id);
            if (c == null)
                return false;
            return c.withdraw(value);
        }finally {
            bank_rlock.unlock();
        }
    }

    // transfer value between accounts;
    // fails if either account does not exist or insufficient balance
    public boolean transfer(int from, int to, int value) {
        bank_rlock.lock();
        try{
            Account f = map.get(from);
            Account t = map.get(to);
            if(f == null || t == null) return false;
            else return withdraw(from, value) && deposit(to, value);
        }finally {
            bank_rlock.unlock();
        }
    }

    public boolean transfer_towfl(int from, int to, int value){
        Account f = null,t = null;
        bank_lock.lock();
        try {
            f = map.get(from);
            t = map.get(to);
            if(f == null || t == null) return false;
        }finally {
            if(f != null && t != null){
                f.acc_lock.lock();
                t.acc_lock.lock();
            }
            bank_lock.unlock();
        }
        // Tem erro aqui, 2fl bloqueia o banco todo quando duas theard colidem
        try{
            return withdraw(from, value) && deposit(to, value);
        }finally {
            f.acc_lock.unlock();
            t.acc_lock.unlock();
        }
    }

    // sum of balances in set of accounts; 0 if some does not exist
    public int totalBalance(int[] ids) {
        int total = 0;

        bank_rlock.lock();
        try {
            for (int i : ids) {
                Account c = map.get(i);
                if (c != null)
                    total += c.balance();
            }
            return total;
        }finally {
            bank_rlock.unlock();
        }
    }

    public void print_accs(){
        for(Map.Entry<Integer, Account> entry : map.entrySet()){
            System.out.println("Conta: " + entry.getKey() + "->" + entry.getValue().balance);
        }
    }

}