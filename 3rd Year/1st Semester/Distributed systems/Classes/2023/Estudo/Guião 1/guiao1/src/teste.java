public class teste{
    private static boolean[] flag = {false, false};


    public static void lock(int i){
        int j = 1-i;
        flag[i] = true;
        int victim = i;

        while(flag[j] && victim == i){}

        System.out.println("Sou o thread: " + i);
        unlock(i);
    }

    public static void unlock(int i){
        flag[i] = false;
    }
}
