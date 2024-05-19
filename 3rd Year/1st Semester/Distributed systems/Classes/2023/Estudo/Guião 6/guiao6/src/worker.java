import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class worker implements Runnable {
    Socket socket;
    Somador somador;

    public worker(Socket s, Somador soma) {
        this.socket = s;
        this.somador = soma;

    }

    @Override
    public void run() {

        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream());

            double media;
            int soma = 0, counter = 0;
            String line;
            while ((line = in.readLine()) != null) {
                somador.new_soma(Integer.parseInt(line));
                soma += Integer.parseInt(line);
                counter++;
                out.println(soma);
                out.flush();
            }
            media = (double) soma / counter;
            out.println(media);
            out.flush();

            out.println(somador.getMedia());
            out.flush();

            socket.shutdownOutput();
            socket.shutdownInput();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
