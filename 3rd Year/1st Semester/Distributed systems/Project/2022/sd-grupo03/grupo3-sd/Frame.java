import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

public class Frame{
    private int tag;
    private boolean response;
    private Object data;

    public Frame(int tipoFrame, boolean response, Object data) {
        this.tag = tipoFrame;
        this.response = response;
        this.data = data;
    }

    public int getTag() {
        return tag;
    }

    public Object getData() {
        return data;
    }

    public void serialize(DataOutputStream out) throws IOException {
        out.writeInt(tag);
        out.writeBoolean(response);
        switch (tag) {
            case 0:
                if (response) {
                    out.writeInt((int) data);
                } else {
                    Utilizador.serialize((Utilizador)data, out);
                }
                break;
            case 1:
                if (response) {
                    out.writeBoolean((boolean) data);
                } else {
                    Utilizador.serialize((Utilizador) data, out);
                }
                break;
            case 2:
                if (response) {
                    List<?> list = (List<?>) data;
                    out.writeInt(list.size());
                    for (Object o : list) {
                        Localizacao.serialize((Localizacao)o, out);
                    }
                } else {
                    Localizacao.serialize((Localizacao) data, out);
                }
                break;
            case 3:
                if (response) {
                    ReservasTrotinete.serialize((ReservasTrotinete) data, out);
                } else {
                    Localizacao.serialize((Localizacao) data, out);
                }
                break;
            case 4:
                if (response) {
                    out.writeDouble((double) data);
                } else {
                    ReservasTrotinete.serialize((ReservasTrotinete) data, out);
                }
                break;
        }
    }

    public static Frame deserialize(DataInputStream in) throws IOException{
        int tipoFrame = in.readInt();
        boolean response = in.readBoolean();
        Object data = null;
        switch (tipoFrame) {
            case 0:
                if (response) {
                    data = in.readInt();
                } else {
                    data = Utilizador.deserialize(in);
                }
                break;
            case 1:
                if (response) {
                    data = in.readBoolean();
                } else {
                    data = Utilizador.deserialize(in);
                }
                break;
            case 2:
                if (response) {
                    int size = in.readInt();
                    List<Localizacao> l = new ArrayList<Localizacao>();
                    for (int i = 0; i < size; i++) {
                        l.add(Localizacao.deserialize(in));
                    }
                    data=l;
                } else {
                    data = Localizacao.deserialize(in);
                }
                break;
            case 3:
                if (response) {
                    data = ReservasTrotinete.deserialize(in);
                } else {
                    data = Localizacao.deserialize(in);
                }
                break;
            case 4:
                if (response) {
                    data = in.readDouble();
                } else {
                    data = ReservasTrotinete.deserialize(in);
                }
                break;
        }
        return new Frame(tipoFrame, response, data);
    }
}