
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    
    private Parser userDB;
    private ServerSocket serverSocket;
    private Mapa mapa;
    private boolean running;

    public Server() {
        try {
            this.serverSocket = new ServerSocket(8888);
            this.running = true;
            this.userDB = new Parser();
            this.mapa = new Mapa();
            System.out.println(this.mapa);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(-1);
        } catch (Exception e) {
            System.err.println("Could not listen on port: "+ 8888);
            System.exit(1);
        }
    }

    public void acceptConnections() {
        while (running) {
            try {
                Socket clientSocket = serverSocket.accept();
                Connection con = new Connection(clientSocket);
                System.out.println("Connection from client accepted");
                ClientHandler client= new ClientHandler(con, userDB, mapa);
                new Thread(client).start();
            } catch (Exception e) {
                e.printStackTrace();
                running = false;
            }
        }
        try {
            this.serverSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
