import java.io.IOException;


public class Main {

    static final int SERVER_PORT = 53;


    public static void main(String[] args) {
        if(args.length < 2) {
            System.out.println("Please run with <serverType> <configLocation>");
            return;
        }

        String serverType = args[0];
        String configLocation = args[1];

        Server s = new Server(serverType, configLocation);
        try {
            s.start();
        } catch (IOException e){

        }

    }
}