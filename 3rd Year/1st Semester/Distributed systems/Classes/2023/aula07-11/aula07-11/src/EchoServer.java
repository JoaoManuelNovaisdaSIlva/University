import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {

    public static void main(String[] args) {
        Somador s = new Somador();
        try {
            ServerSocket ss = new ServerSocket(12345);

            while (true) {
                Socket socket = ss.accept();

                Thread a = new Thread(new worker(socket, s));
                a.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class worker implements Runnable{
    private Socket socket;
    private Somador s;

    public worker(Socket s, Somador som){ this.socket = s; this.s = som; }

    @Override
    public void run(){
        try{
            int soma=0;
            int counter=0;


            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream());

            String line;
            while ((line = in.readLine()) != null) {
                try{
                    System.out.println("Incoming: " + line);
                    int v = Integer.parseInt(line);
                    s.soma(v);
                    soma += v;
                }catch (NumberFormatException e){
                    e.printStackTrace();
                }

                out.println(this.s.valorSoma());
                out.flush();
            }
            System.out.println("Sai do while");
            float media = this.s.media();
            out.println(Float.toString(media));
            out.flush();

            soma=0;
            counter=0;

            socket.shutdownOutput();
            socket.shutdownInput();
            socket.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}