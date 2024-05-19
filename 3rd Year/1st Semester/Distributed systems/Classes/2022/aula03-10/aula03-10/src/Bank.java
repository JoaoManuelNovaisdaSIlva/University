import java.util.concurrent.locks.ReentrantLock;

public class Bank {

    private static class Account { // A solução do prof envolvia fazer locks nos metodos do bank mas utilizando a coleção de contas para os fazer, ex: av[id].lock.lock()
        private int balance;

        private ReentrantLock lock = new ReentrantLock();
        Account(int balance) { this.balance = balance; }
        int balance() { return balance; }
        boolean deposit(int value) {
            try{
                lock.lock();
                balance += value;
                return true;
            }finally {
                lock.unlock();
            }
        }
        boolean withdraw(int value) {
            if (value > balance)
                return false;

            try{
                lock.lock();
                balance -= value;
                return true;
            }finally {
                lock.unlock();
            }
        }
    }

    // Bank slots and vector of accounts
    private int slots;
    private Account[] av;

    private ReentrantLock lockBanco = new ReentrantLock();

    public Bank(int n) {
        slots=n;
        av=new Account[slots];
        for (int i=0; i<slots; i++) av[i]=new Account(0);
    }

    // Account balance
    public int balance(int id) {
        if (id < 0 || id >= slots)
            return 0;
        return av[id].balance(); // balance() apenas lê, pode ser necessário lock se queremos que seja visivel o balance atualizado a toda a altura
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

    public int totalBalance(){
        try{
            lockBanco.lock();
            int soma=0;
            for(Account a : av){
                soma += a.balance();
            }
            return soma;
        }finally {
            lockBanco.unlock();
        }
    }

    public int totalBalanceEficiente(){
        for(Account a : av){
            a.lock.lock();
        }
        int soma=0;
        for(Account a : av){
            soma += a.balance;
            a.lock.unlock();
        }
        return soma;
    }

    // Esta solução funciona se não tiver locks na class Conta
    public boolean transferNoLock(int from, int to, int amount){
        int a1 = Math.min(from, to);
        int a2 = Math.max(from, to);
        try{
            av[a1].lock.lock();
            av[a2].lock.lock();
            if(from < 0 || to < 0 || from >= slots || to >= slots || from == to) return false;
            return this.withdraw(from, amount) && this.deposit(to, amount);

        } finally {
            av[a1].lock.unlock();
            av[a2].lock.unlock();
        }
    }

    public boolean transfer(int from, int to, int amount){
        try{
            this.lockBanco.lock();
            if(from < 0 || to < 0 || from >= slots || to >= slots || from == to) return false;
            return this.withdraw(from, amount) && this.deposit(to, amount);

        } finally {
            this.lockBanco.unlock();
        }
    }

    public boolean tranferEficiente(int from, int to, int amount){ // Reduzir ao máximo a secção crítica
        if(from < 0 || to < 0 || from >= slots || to >= slots || from == to) return false;

        if(from < to){
            av[from].lock.lock();
            if(av[from].balance < amount){
                av[from].lock.unlock();
                return false;
            }

            av[from].withdraw(amount);

            av[to].lock.lock();
            av[from].lock.unlock();

            av[to].deposit(amount);
            av[to].lock.unlock();
            return true;
        }else{
            av[to].lock.lock();
            av[to].deposit(amount);

            av[from].lock.lock();
            av[to].lock.unlock();

            if(av[from].balance < amount){
                av[from].lock.unlock();
                return false; // Temos problema aqui, provavelmente resolver com uma withdraw no dinheiro anteriormente depositado na conta to,
                              // talvez fazer o bloco else com 2 locks
            }

            av[from].withdraw(amount);
            av[from].lock.unlock();
            return true;
        }

    }
}