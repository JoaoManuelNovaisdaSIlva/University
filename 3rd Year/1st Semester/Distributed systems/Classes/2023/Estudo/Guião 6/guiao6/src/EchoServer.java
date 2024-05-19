import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.locks.ReentrantLock;

public class EchoServer {

    public static void main(String[] args) {
        Somador soma = new Somador();
        try {
            ServerSocket ss = new ServerSocket(12345);

            while (true) {
                Socket socket = ss.accept();

                Thread t = new Thread(new worker(socket, soma));
                t.start();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

