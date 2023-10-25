import javax.xml.crypto.Data;
import java.io.*;
import java.net.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;

public class Server {

    private String serverType;
    private Config config;

    private Log logger;

    private int TTL;
    private int UDP_port = 53;
    private int TCP_port = 99;

    private Database database;

    private DatagramSocket UDP_Server;

    public static String role;


    public Server(String type, String configLocation) {
        serverType = type;
        config = new Config(configLocation);
        logger = new Log(config.getLogLocation());

    }



    public void start() throws IOException {
        if(this.serverType.equals("SP")){
            this.role = "SP";
            database = new Database(config.getDatabaseLocation());
            new Thread(new ZoneTransfer(database.getCache())).start();
            this.startUDP();
        }

        if(this.serverType.equals("SS")){
            this.role = "SS";
            this.startZoneTransfer();
            this.startUDP();
        }
    }

    private void startUDP() {
        try {
            UDP_Server = new DatagramSocket(UDP_port);
            byte[] receive = new byte[1000];

            System.out.println("UDP Server started in port " + UDP_port + ".");

            DatagramPacket DpReceive = null;

            while (true)
            {
                DpReceive = new DatagramPacket(receive, receive.length);
                UDP_Server.receive(DpReceive);

                System.out.println("Client ["+DpReceive.getAddress() + ":"+ DpReceive.getPort() + "]+: " + data(receive));

                /*
                DatagramSocket clientSocket = new DatagramSocket();
                new DatagramPacket(send, send.length, DpReceive.getAddress(), DpReceive.getPort());
                clientSocket.send(new DatagramPacket(send, send.length));
                clientSocket.close();
                //receive = new byte[65535]; */

                InetAddress add = DpReceive.getAddress();
                int portaResposta = DpReceive.getPort();
                String mensagemRecebida = new String(receive);
                System.out.println("Canhos");
                logger.QR(true, add.toString(), mensagemRecebida);
                logger.QE(true, add.toString(), mensagemRecebida);
                System.out.println("Recebi uma mensagem do cliente " + add.toString());


                new Thread(() -> responderCliente(mensagemRecebida, add, portaResposta)).start();
                logger.RP(true, add.toString(), mensagemRecebida);
                logger.RR(true, add.toString(), mensagemRecebida);
            }
        } catch (SocketException e){
            logger.message("Socket error..." + e.getMessage());
        } catch (Exception e) {
            logger.message("Error while opening server..." + e.getMessage());
        }

    }

    public static StringBuilder data(byte[] a)
    {
        if (a == null)
            return null;
        StringBuilder ret = new StringBuilder();
        int i = 0;
        while (a[i] != 0)
        {
            ret.append((char) a[i]);
            i++;
        }
        return ret;
    }

    public void responderCliente(String msgStr, InetAddress add, int port) {
        String flags = "";
        String response_code = "0";

        String[] x = msgStr.split(";");
        String message_id = x[0];
        String segunda = x[1];

        String[] lista2 = {x[6], x[7]};
        if (segunda.contains("R")) {
            flags += "R+";
        }
        flags += "A";
        if (this.database.getCache().verifica(lista2[0], lista2[1]) == -1) {
            response_code = "1";
        }
        String numberOfValues = Integer.toString(this.database.getCache().checkNumberOfValues(lista2[0], lista2[1]));
        ArrayList<String[]> values = this.database.getCache().devolveValues(lista2[0], lista2[1]);
        ArrayList<String[]> authorities = this.database.getCache().devolveAuthoritiesValues(lista2[0]);
        String numberOfAuthoritiesValues = Integer.toString(authorities.size());

        String[][] extraValues = new String[values.size() + authorities.size()][];
        for (int i = 0; i < values.size(); i++) {
            String comp = values.get(i)[2];
            extraValues[i] = this.database.getCache().extraValue(lista2[0], comp);
        }
        for (int i = 0; i < authorities.size(); i++) {
            String comp = authorities.get(i)[2];
            extraValues[values.size() + i] = this.database.getCache().extraValue(lista2[0], comp);
        }

        String nrExtraValues = Integer.toString(extraValues.length);
        String resposta = message_id + "," + flags + "," + response_code + "," + numberOfValues + "," + numberOfAuthoritiesValues + "," + nrExtraValues + ";";
        resposta += segunda + ";";
        for (String[] value : values) {
            if (value.length > 4) {
                resposta += value[0] + " " + value[1] + " " + value[2] + " " + value[3] + " " + value[4];
            } else {
                resposta += value[0] + " " + value[1] + " " + value[2] + " " + value[3];
            }
        }
        for (String[] authority : authorities) {
            if (authority.length > 4) {
                resposta += authority[0] + " " + authority[1] + " " + authority[2] + " " + authority[3] + " " + authority[4];
            } else {
                resposta += authority[0] + " " + authority[1] + " " + authority[2] + " " + authority[3];
            }
        }
        for (String[] extra : extraValues) {
            if (extra != null) {
                if (extra.length > 4) {
                    resposta += extra[0] + " " + extra[1] + " " + extra[2] + " " + extra[3] + " " + extra[4];
                } else {
                    resposta += extra[0] + " " + extra[1] + " " + extra[2] + " " + extra[3];
                }
            }
        }

        try {
            DatagramSocket clientSocket = new DatagramSocket();
            DatagramPacket dt = new DatagramPacket(resposta.getBytes(), resposta.getBytes().length, add, port);
            clientSocket.send(dt);
        } catch(Exception e){
            System.out.println("OI");
        }


    }

    private void startZoneTransfer(){
        try {
            DatagramSocket ds = new DatagramSocket();
            InetAddress ip = InetAddress.getByName(config.getAddress("SP").get(0));
            String request = config.getMainDomain() + ".";
            byte buf[] = null;

            Socket client = new Socket(config.getAddress("SP").get(0),TCP_port);
            PrintWriter writer = new PrintWriter(client.getOutputStream(), true);
            BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
            writer.println(request);

            Thread.sleep(400);

            int numEntries = parseInt(reader.readLine().split(":")[1]);
            logger.message("I'm going to read " + numEntries + " entries of database.");
            Thread.sleep(500);
            writer.println("OK");
            Thread.sleep(90);
            for(int i = 0; i < numEntries; i++){
                logger.message("Recieved #" + i + " line of database.");
                //database.add(reader.readLine());
            }

            client.close();

            logger.message("Database transfered");
        } catch (Exception e) {
            logger.message("Error requesting database: " + e.getMessage());
        }


    }

    private class ZoneTransfer implements Runnable {

        private Cache c;
        public ZoneTransfer(Cache c){
            this.c = c;
        }

        @Override
        public void run() {
            while(true){
                try {
                    ServerSocket sc = new ServerSocket(TCP_port);

                    logger.message("Waiting for TCP Zone Transfer");

                    Socket socket = sc.accept();


                    boolean end = false;
                    InputStream input = socket.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
                    String line = reader.readLine();
                    //logger.message("Recieved zone transfer: " + line);
                    ArrayList<String[]> items = c.devolveFileItems();

                    long start = System.currentTimeMillis();
                    writer.println("NumLines:" + items.size());
                    Thread.sleep(200);
                    line = reader.readLine();
                    if(line.equals("OK")){
                        Thread.sleep(400);
                        for(int i = 0; i<items.size(); i++){
                            writer.write(c.getLineFormated(items.get(i)));
                            Thread.sleep(300);
                        }
                    }
                    socket.close();
                    sc.close();
                    long timeend = System.currentTimeMillis();

                    logger.ZT(socket.getInetAddress().getHostAddress(), socket.getPort(), Server.role, null, timeend-start);


                    Thread.sleep(400);


                } catch (IOException e){
                    logger.message("An error occurred while opening TCP Server: " + e.getMessage());
                } catch (InterruptedException e){
                    logger.message("Impossible to sleep: " + e.getMessage());
                }
            }

        }
    }

}
