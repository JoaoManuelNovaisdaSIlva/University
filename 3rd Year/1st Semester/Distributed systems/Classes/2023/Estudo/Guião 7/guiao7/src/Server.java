import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.locks.ReentrantLock;

import static java.util.Arrays.asList;

class ContactManager {
    private HashMap<String, Contact> contacts = new HashMap<>();
    private ReentrantLock manager_lock = new ReentrantLock();

    // @TODO
    public void update(Contact c) {
        manager_lock.lock();
        try{
            for(Contact conts : contacts.values()){
                if(conts.name() == c.name()){
                    conts.update(c);
                    return;
                }
            }
            contacts.put(c.name(), c);
        } finally {
            manager_lock.unlock();
        }

    }

    // @TODO
    public ContactList getContacts() {
        manager_lock.lock();
        try{
            ContactList list = new ContactList();
            list.addAll(contacts.values());
            return list;
        }finally {
            manager_lock.unlock();
        }

    }
}

class ServerWorker implements Runnable {
    private Socket socket;
    private ContactManager manager;

    public ServerWorker(Socket socket, ContactManager manager) {
        this.socket = socket;
        this.manager = manager;
    }

    // @TODO
    @Override
    public void run() {
        try{
            DataInputStream fi = new DataInputStream(socket.getInputStream());
            DataOutputStream fo = new DataOutputStream(socket.getOutputStream());

            ContactList list = manager.getContacts();
            list.serialize(fo);

            //String line;
            while(true){
                System.out.println("Entrei no while!!");
                Contact c = Contact.deserialize(fi);
                if(c==null) break;
                manager.update(c);
                System.out.println(manager.getContacts().toString());
            }

            /**Thread.sleep(2000);
            System.out.println(manager.getContacts().toString());**/

        }catch (IOException e){
            e.printStackTrace();
        }
        try {
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}



public class Server {

    public static void main (String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(12345);
        ContactManager manager = new ContactManager();
        // example pre-population
        manager.update(new Contact("John", 20, 253123321, null, asList("john@mail.com")));
        manager.update(new Contact("Alice", 30, 253987654, "CompanyInc.", asList("alice.personal@mail.com", "alice.business@mail.com")));
        manager.update(new Contact("Bob", 40, 253123456, "Comp.Ld", asList("bob@mail.com", "bob.work@mail.com")));

        while (true) {
            Socket socket = serverSocket.accept();
            Thread worker = new Thread(new ServerWorker(socket, manager));
            worker.start();
        }
    }

}
