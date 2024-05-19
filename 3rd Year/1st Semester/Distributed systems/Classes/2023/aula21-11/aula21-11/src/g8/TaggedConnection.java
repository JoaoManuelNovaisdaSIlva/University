package g8;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.locks.ReentrantLock;

public class TaggedConnection implements AutoCloseable{
    private Socket socket;
    private DataOutputStream dataOut;
    private DataInputStream dataInput;
    private ReentrantLock readLock = new ReentrantLock();
    private ReentrantLock writeLock = new ReentrantLock();
    public static class Frame{
        public final int tag;
        public final byte[] data;

        public Frame(int tag, byte[] data){
            this.tag = tag;
            this.data = data;
        }
    }

    public TaggedConnection(Socket socket) throws IOException {
        this.socket = socket;
        this.dataOut = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        this.dataInput = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
    }

    public void send(Frame frame) throws IOException{
        writeLock.lock();
        try{
            dataOut.writeInt(frame.tag);
            dataOut.writeInt(frame.data.length);
            dataOut.write(frame.data);
            dataOut.flush();
        }finally {
            writeLock.unlock();
        }
    }

    public void send(int tag, byte[] data) throws IOException{
        writeLock.lock();
        try{
            dataOut.writeInt(tag);
            dataOut.writeInt(data.length);
            dataOut.write(data);
            dataOut.flush();
        }finally {
            writeLock.unlock();
        }

    }

    public Frame receive() throws IOException{
        readLock.lock();
        try {
            int tag = dataInput.readInt();
            int len = dataInput.readInt();
            return new Frame(tag, dataInput.readNBytes(len));
        }finally {
            readLock.lock();
        }

    }

    @Override
    public void close() throws Exception {
        readLock.lock();
        writeLock.lock();
        try{
            socket.close();
        }finally {
            readLock.unlock();
            writeLock.unlock();
        }
    }
}
