import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ReservasTrotinete {
    
    private int code;
    private Localizacao start;
    private Localizacao end;
    private String timeStart;
    private String timeEnd;

    public ReservasTrotinete(int code, Localizacao start) {
        this.code = code;
        this.start = start;
        this.timeStart = getDateTimeString();
        this.end = null;
        this.timeEnd = null;
    }

    public ReservasTrotinete(int code, Localizacao start, String datetimeStart) {
        this.code = code;
        this.start = start;
        this.timeStart = datetimeStart;
        this.end = null;
        this.timeEnd = null;
    }
    
    public ReservasTrotinete(int code, Localizacao start, String datetimeStart, Localizacao end, String datetimeEnd) {
        this.code = code;
        this.start = start;
        this.timeStart = datetimeStart;
        this.end = end;
        this.timeEnd = datetimeEnd;
    }


    public String getDateTimeString(){
        LocalTime localTime = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return localTime.format(formatter);
    }
    public void setEnd(Localizacao end){
        this.end = end;
        this.timeEnd = getDateTimeString();
    }

    public void setEnd(Localizacao end, String datetimeEnd){
        this.end = end;
        this.timeEnd = datetimeEnd;
    }

    public int getCode() {
        return code;
    }

    public Localizacao getStart() {
        return start;
    }

    public Localizacao getEnd() {
        return end;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public long getDuration(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalTime t1 = LocalTime.parse(timeStart, formatter);
        LocalTime t2 = LocalTime.parse(timeEnd, formatter);
        long hours = Duration.between(t1, t2).toSeconds();
        return hours;
    }

    public boolean isFinished(){
        return end != null;
    }

    public int getDistance(){
        return Math.abs(start.getX() - end.getX()) + Math.abs(start.getY() - end.getY());
    }

    public String toString() {
        String s = "Reserve: \n";
        s += "   Code: " + code + "\n";
        s += "   Start: " + start + "\n";
        s += "   End: " + end + "\n";
        s += "   Initial time: " + timeStart + "\n";
        s += "   End time: " + timeEnd + "\n";
        return s;
    }

    public static void serialize(ReservasTrotinete r, DataOutputStream out) throws IOException {
        out.writeInt(r.getCode());
        Localizacao p1 = r.getStart();
        out.writeInt(p1.getX());
        out.writeInt(p1.getY());
        Localizacao p2 = r.getEnd();
        if (p2 == null){
            out.writeInt(-1);
            out.writeInt(-1);
        } else {
            out.writeInt(p2.getX());
            out.writeInt(p2.getY());
        }
        out.writeUTF(r.getTimeStart());
        if(r.getTimeEnd() != null)
            out.writeUTF(r.getTimeEnd());
        else
            out.writeUTF("");
    } 


	public static ReservasTrotinete deserialize(DataInputStream in) throws IOException {
        int code = in.readInt();
        int x1 = in.readInt();
        int y1 = in.readInt();
        int x2 = in.readInt();
        int y2 = in.readInt();
        String datetimeStart = in.readUTF();
        String datetimeEnd = in.readUTF();
        ReservasTrotinete r = new ReservasTrotinete(code, new Localizacao(x1, y1), datetimeStart);
        if (x2 != -1 && y2 != -1){
            r.setEnd(new Localizacao(x2, y2), datetimeEnd);
        }
        return r;
    }
}
