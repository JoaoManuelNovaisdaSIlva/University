import java.io.IOException;
import java.net.*;
import java.util.Random;
import java.util.Scanner;

public class Client {

    private static int ServerPort = 12345;

    public static void main(String[] args) throws IOException, InterruptedException {
        String[] x = args[0].split(":");
        String ServerIP = x[0];
        int ServerPort = Integer.parseInt(x[1]);
        String dom = args[1];
        String typeValue = args[2];
        boolean recursive = false;
        if (args.length > 3) {
            recursive = true;
        }

        System.out.println("IP: " + ServerIP + " porta: " + ServerPort);

        // Generate query
        Random random = new Random();
        String query = random.nextInt(65535) + ";Q";
        if (recursive) {
            query += "R";
        }
        query += ";0;0;0;0;" + dom + ";" + typeValue + ";";

        // Send query
        byte[] sendData = query.getBytes();
        DatagramSocket socket = new DatagramSocket();
        InetAddress address = InetAddress.getByName(ServerIP);
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, address, ServerPort);
        socket.send(sendPacket);

        // Receive response
        byte[] receiveData = new byte[1024];
        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
        socket.receive(receivePacket);
        String response = new String(receivePacket.getData());
        System.out.println("Resposta:");
        System.out.println(response);
    }

}