import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.*;

class Contact {
    private String name;
    private int age;
    private long phoneNumber;
    private String company;     // Pode ser null
    private ArrayList<String> emails;

    public Contact(String name, int age, long phoneNumber, String company, List<String> emails) {
        this.name = name;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.company = company;
        this.emails = new ArrayList<>(emails);
    }

    public String name() { return name; }
    public int age() { return age; }
    public long phoneNumber() { return phoneNumber; }
    public String company() { return company; }
    public List<String> emails() { return new ArrayList(emails); }

    // @TODO
    public void serialize(DataOutputStream out) throws IOException {
        out.writeUTF(this.name);
        out.writeInt(this.age);
        out.writeLong(this.phoneNumber);
        out.writeUTF(company != null ? company : "");
        out.writeInt(this.emails.size());
        for(String s : this.emails) out.writeUTF(s);

    }

    // @TODO
    public static Contact deserialize(DataInputStream in) throws IOException {
        String name = in.readUTF();
        int age = in.readInt();
        long phone = in.readLong();
        String company = in.readUTF();
        if(company.isEmpty()) company=null;
        int size = in.readInt();
        ArrayList<String> emails = new ArrayList<>();
        for(int i=0; i<size; i++){
            emails.add(in.readUTF());
        }

        return new Contact(name, age, phone, company, emails);
    }

    public void update(Contact c){
        this.age = c.age;
        this.phoneNumber = c.phoneNumber;
        this.company = c.company;
        this.emails = c.emails;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(this.name).append(";");
        builder.append(this.age).append(";");
        builder.append(this.phoneNumber).append(";");
        builder.append(this.company).append(";");
        builder.append(this.emails.toString());
        builder.append("}");
        return builder.toString();
    }

}
