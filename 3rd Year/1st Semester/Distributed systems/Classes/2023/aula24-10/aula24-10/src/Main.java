import javax.swing.plaf.TableHeaderUI;

public class Main{
    public static void main(String[] args) throws InterruptedException {
        int N = 5;
        Thread[] threads = new Thread[N];
        Barrier b = new Barrier(N);
        for(int i=0; i<N; i++){
            int fI = i;
            threads[i] = new Thread(() ->{
                try{
                    Thread.sleep(1000*fI);
                    System.out.println("Estou na T" + fI + " 1 vez");
                    b.espera();
                }catch (Exception ignored){}
                System.out.println("o espera() retomou na T" + fI + " 1 vez");

                try{
                    Thread.sleep(1000*fI);
                    System.out.println("Estou na T" + fI + " 2 vez");
                    b.espera();
                }catch (Exception ignored){}
                System.out.println("O espera() retomou na T" + fI + " 2 vez");
            });
        }

        for(int i=0; i<N; i++){
            threads[i].start();
        }

        for(int i=0; i<N; i++){
            threads[i].join();
        }
    }
}