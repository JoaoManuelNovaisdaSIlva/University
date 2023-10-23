import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Localizacao {
    
    private int x;
    private int y;

    public Localizacao(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Localizacao) {
            Localizacao p = (Localizacao) o;
            return this.x == p.x && this.y == p.y;
        }
        return false;
    }

    public static void serialize(Localizacao p, DataOutputStream out) throws IOException {
        out.writeInt(p.getX());
        out.writeInt(p.getY());
    }

    public static Localizacao deserialize(DataInputStream in) throws IOException {
        int x = in.readInt();
        int y = in.readInt();
        return new Localizacao(x, y);
    }

    public String toString() {
        return "Localizacao " + "(" + x + "," + y + ")";
    }


    @Override
    public int hashCode() {
        return (int) x*1000 + y;
    }
}