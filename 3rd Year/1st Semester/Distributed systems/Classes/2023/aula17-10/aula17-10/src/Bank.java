import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

class Bank {

    private static class Account {
        private int balance;
        private ReentrantLock accountLock = new ReentrantLock();
        Account(int balance) { this.balance = balance; }
        int balance() { return balance; }
        boolean deposit(int value) {
            balance += value;
            return true;
        }
        boolean withdraw(int value) {
            if (value > balance)
                return false;
            balance -= value;
            return true;
        }
    }

    private Map<Integer, Account> map = new HashMap<Integer, Account>();
    private int nextId = 0;
    private ReentrantLock bankLock = new ReentrantLock();

    // create account and return account id
    public int createAccount(int balance) {
        Account c = new Account(balance);
        try{
            bankLock.lock();
            int id = nextId;
            nextId += 1;
            map.put(id, c);
            return id;
        }finally {
            bankLock.unlock();
        }
    }

    // close account and return balance, or 0 if no such account
    public int closeAccount(int id) {
        this.bankLock.lock();
        Account c = map.remove(id);
        if (c == null){
            this.bankLock.unlock();
            return 0;
        }

        c.accountLock.lock();
        this.bankLock.unlock();

        int r = c.balance();
        c.accountLock.unlock();

        return r;
    }

    // account balance; 0 if no such account
    public int balance(int id) {
        bankLock.lock();
        Account c = map.get(id);
        if (c == null) {
            this.bankLock.unlock();
            return 0;
        }

        c.accountLock.lock();
        bankLock.unlock();

        int r = c.balance();
        c.accountLock.unlock();

        return r;
    }

    // deposit; fails if no such account
    public boolean deposit(int id, int value) {
        // Aqui tenho que ter um momento em que tenhos os 2 locks na mão, caso contrário entre estes pode acontecer qualquer coisa (2 phase locking)
        //                         _______                                  _____________                                      __________
        // lock banco:             |     |                                  |           |                                      |        |
        //                      ______          em vez de                   _____________    há problemas se:    _________
        // lock cliente:        |    |          (para melhor performance)   |           |                        |       |


        bankLock.lock();
        Account c = map.get(id); // Este get e o put do createAccount não podem correr ao mesmo tempo, logo este bloco precisa adequirir o lock do banco
        if (c == null) {
            bankLock.unlock(); // Não esquecer deste caso
            return false;
        }

        c.accountLock.lock();
        bankLock.unlock();
        // Se tivesse uma operação sobre o map não poderia dar unlock ao banco antes dessa alteração
        boolean r = c.deposit(value);
        c.accountLock.unlock();

        return r;
    }

    // withdraw; fails if no such account or insufficient balance
    public boolean withdraw(int id, int value) {
        bankLock.lock();
        Account c = map.get(id);
        if (c == null) {
            bankLock.unlock();
            return false;
        }

        c.accountLock.lock();
        bankLock.unlock();
        boolean r = c.withdraw(value);
        c.accountLock.unlock();

        return r;
    }

    // transfer value between accounts;
    // fails if either account does not exist or insufficient balance
    public boolean transfer(int from, int to, int value) {
        Account cfrom, cto;

        bankLock.lock();
        cfrom = map.get(from);
        cto = map.get(to);

        if (cfrom == null || cto ==  null) {
            bankLock.unlock();
            return false;
        }

        cfrom.accountLock.lock();
        cto.accountLock.lock();
        bankLock.unlock();
        boolean r = cfrom.withdraw(value) && cto.deposit(value);
        cfrom.accountLock.unlock();
        cto.accountLock.unlock();

        return r;
    }

    private final ReentrantReadWriteLock l = new ReentrantReadWriteLock();
    private final Lock readLock = l.readLock();

    /** Fazer dps
     * public boolean tranfer1(int from, int to, int value){
        Account cfrom, cto;
        readLock.lock();
        try{
            cfrom = map.get(from);
            cto = map.get(to);
            if(cfrom == null | cto == null){
                readLock.unlock();
                return false;
            }
        }
    }**/

    // sum of balances in set of accounts; 0 if some does not exist
    public int totalBalance(int[] ids) {
        int total = 0;
        for (int i : ids) {

            bankLock.lock();
            Account c = map.get(i);
            if (c == null) {
                bankLock.unlock();
                return 0;
            }

            c.accountLock.lock();
            bankLock.unlock();
            total += c.balance();
            c.accountLock.unlock();
        }
        return total;
    }

}