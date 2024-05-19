class Bank {

    private static class Account {
        private int balance;
        Account(int balance) { this.balance = balance; }
        int balance() { return balance; }
        boolean deposit(int value) {
            balance += value;
            return true;
        }

        void reset(){
            balance=0;
        }
    }

    // Our single account, for now
    private static Account savings = new Account(0);

    // Account balance
    public static int balance() {
        return savings.balance();
    }

    public static void reset(){
        savings.reset();
    }

    // Deposit
    static boolean deposit(int value) {
        //System.out.println("[THREAD " + Thread.currentThread().getName() + "] : " + value);
        return savings.deposit(value);
    }
}