package g8;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.locks.ReentrantLock;

public class FramedConnection implements AutoCloseable{
    private Socket socket;
    private DataInputStream inputStream;
    private DataOutputStream outputStream;
    private ReentrantLock readLock = new ReentrantLock();
    private ReentrantLock writeLock = new ReentrantLock();

    public FramedConnection(Socket socket) throws IOException {
        this.inputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        this.outputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        this.socket = socket;
    }

    public void send(byte[] data) throws IOException{
        writeLock.lock();
        try{
            outputStream.writeInt(data.length);
            outputStream.write(data);
            outputStream.flush();
        }finally {
            writeLock.unlock();
        }
    }

    public byte[] receive() throws IOException{
        readLock.lock();
        try{
            int nBytes = inputStream.readInt();
            return inputStream.readNBytes(nBytes);
        }finally {
            readLock.unlock();
        }
    }

    @Override
    public void close() throws Exception {
        try{
            writeLock.lock();
            readLock.lock();
            socket.close();
        }finally {
            writeLock.unlock();
            readLock.unlock();
        }
    }
}
