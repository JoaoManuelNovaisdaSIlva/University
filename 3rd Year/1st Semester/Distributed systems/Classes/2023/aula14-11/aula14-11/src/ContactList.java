import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

class ContactList extends ArrayList<Contact> {

    // @TODO
    public void serialize(DataOutputStream out) throws IOException {
        out.writeInt(this.size());
        for(Contact c : this){
            c.serialize(out);
        }
    }

    // @TODO
    public static ContactList deserialize(DataInputStream in) throws IOException {
        ContactList contactList = new ContactList();
        int size = in.readInt();

        if(size > 0){
            Contact c = Contact.deserialize(in);
            contactList.add(c);
        }
        return contactList;
    }

}
