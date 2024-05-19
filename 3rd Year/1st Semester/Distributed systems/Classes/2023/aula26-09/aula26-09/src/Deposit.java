
public class Deposit implements Runnable{
    private Bank bank;

    public Deposit(Bank bank){
        this.bank = bank;
    }

    @Override
    public void run(){
        int I = 1000;
        int v = 100;

        for(int i=0; i<I; i++){
            bank.deposit(v);
        }
    }
}
