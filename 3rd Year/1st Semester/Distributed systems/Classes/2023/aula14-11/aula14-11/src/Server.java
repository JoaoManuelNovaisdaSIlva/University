import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import static java.util.Arrays.asList;

class ContactManager {
    private HashMap<String, Contact> contacts = new HashMap<>();
    private ReentrantLock managerLock = new ReentrantLock();

    // @TODO
    public void update(Contact c) {
        try{
            managerLock.lock();
            contacts.put(c.name(), c);
        }finally {
            managerLock.unlock();
        }
    }

    // @TODO
    public ContactList getContacts() {
        try{
            managerLock.lock();
            ContactList contactsList = new ContactList();
            contactsList.addAll(this.contacts.values());
            return contactsList;
        }finally {
            managerLock.unlock();
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
        try {
            DataInputStream inputStream = new DataInputStream(new DataInputStream(socket.getInputStream()));

            while(true){
                Contact c = Contact.deserialize(inputStream);
            }
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