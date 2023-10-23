import java.io.FileWriter;

public class Log {

    private String fileLocation;
    private boolean debug;

    public Log(String fileLocation){
        this.fileLocation = fileLocation;
        writeToFile("# Log file for DNS server/resolver");
        this.debug = debug;
    }

    private void writeToFile(String message){
        try {
            if(this.fileLocation == null) return;
            FileWriter myWriter = new FileWriter(this.fileLocation, true);
            myWriter.write(message + "\n");
            myWriter.close();
        } catch (Exception e){
            System.out.println("Impossible to write to file: " + this.fileLocation + " : " + e.getMessage());
        }
    }
    public void message(String message){
        System.out.println("[MESSAGE] " + message);
        this.writeToFile("[MESSAGE] " + message);
    }

    public void error(String message){
        System.out.println("[ERROR] " + message);
        this.writeToFile("[ERROR] " + message);
    }

    public void QR(boolean query, String address, String info){
        writeToFile("Query log started");

        String write = "QR " + address + " " + info;

        writeToFile(write);

        if(this.debug) System.out.println(write);
    }

    public void QE(boolean query, String address, String info){
        writeToFile("Query log started");

        String write = "QE " + address + " " + info;

        writeToFile(write);

        if(this.debug) System.out.println(write);
    }

    public void RP(boolean query, String address, String info){
        String write = "RP " + address + " " + info;

        writeToFile(write);

        if(this.debug) System.out.println(write);
    }

    public void RR(boolean query, String address, String info){
        String write = "RR " + address + " " + info;

        writeToFile(write);

        if(this.debug) System.out.println(write);
    }

    public void ZT(String ip, long port, String role, String bytes, long time){
        String write = "ZT " + ip + ":" + port + " " + " time=" + time + "ms " +
                (role.equals("SP") ? "sent " : "received ") + bytes + " bytes";

        writeToFile(write);

        if(this.debug) System.out.println(write);
    }

    public void EV(String tipoEvento, String msg){
        String write = "EV " + "127.0.0.1" + " " + tipoEvento + (!msg.isEmpty() ? " "+msg : "");

        writeToFile(write);

        if(this.debug) System.out.println(write);
    }

    public void ER(String ip){
        String write = "ER " + ip;

        writeToFile(write);

        if(this.debug) System.out.println(write);
    }

    public void EZ(String ip, String port, String role){
        String write = "EZ " + ip + ":" + port + " " + role ;

        writeToFile(write);

        if(this.debug) System.out.println(write);
    }

    public void FL(String tipoErro){
        String write = "FL " + "127.0.0.1" + tipoErro;

        writeToFile(write);

        if(this.debug) System.out.println(write);
    }

    public void TO(String tipoTimeout){
        String write = "TO " + tipoTimeout;

        writeToFile(write);

        if(this.debug) System.out.println(write);
    }

    public void SP(String motivo){
        String write = "SP " + "127.0.0.1" + motivo;

        writeToFile(write);

        if(this.debug) System.out.println(write);
    }

    public void ST(int porta, boolean mode){
        String write = "ST 127.0.0.1" + porta + " " + mode;

        writeToFile(write);

        if(this.debug) System.out.println(write);
    }
}
