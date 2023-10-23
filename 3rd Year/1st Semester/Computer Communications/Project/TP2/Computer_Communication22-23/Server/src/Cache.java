import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;

public class Cache {
    private int size;
    private int index;
    private ArrayList<String[]> cache;

    public Cache() {
        this.size = 0;
        this.index = 0;
        this.cache = new ArrayList<>();
    }

    public void adicionaLinhaCache(String name, String type, String value, int ttl, int prio, String origin, Date time, String state) {
        String[] newLine = {name, type, value, String.valueOf(ttl), String.valueOf(prio), origin, String.valueOf(time), String.valueOf(this.index), state};
        this.cache.add(newLine);
        this.index++;
        this.size++;
    }

    public int verifica(String dom, String typeValue) {
        for (int i = 0; i < this.index; i++) {
            if (dom.equals(this.cache.get(i)[0]) && typeValue.equals(this.cache.get(i)[1])) {
                return i;
            }
        }

        return -1;
    }

    public int checkNumberOfValues(String dom, String typeValue) {
        int c = 0;
        for (int i = 0; i < this.index; i++) {
            if (dom.equals(this.cache.get(i)[0]) && typeValue.equals(this.cache.get(i)[1])) {
                c++;
            }
        }

        return c;
    }

    public ArrayList<String[]> devolveFileItems() {
        ArrayList<String[]> array  = new ArrayList<>();
        for(int i = 0; i < this.index; i++){
            if(this.cache.get(i)[5].equals("FILE")){
                array.add(this.cache.get(i));
            }
        }

        return array;
    }

    public String getLineFormated(String[] arr){
        StringBuilder out = new StringBuilder();
        for(int i = 0; i<arr.length; i++){
            out.append(arr[i]);
        }

        return out.toString();
    }

    public ArrayList<String[]> devolveAuthoritiesValues(String dom) {
        ArrayList<String[]> arrayAV = new ArrayList<>();
        for (int i = 0; i < this.index; i++) {
            if (dom.equals(this.cache.get(i)[0]) && "NS".equals(this.cache.get(i)[1])) {
                arrayAV.add(this.cache.get(i));
            }
        }
        return arrayAV;
    }

    public ArrayList<String[]> devolveValues(String dom, String typeValue) {
        ArrayList<String[]> arrayV = new ArrayList<>();
        for (int i = 0; i < this.index; i++) {
            if (dom.equals(this.cache.get(i)[0]) && typeValue.equals(this.cache.get(i)[1])) {
                arrayV.add(this.cache.get(i));
            }
        }
        return arrayV;
    }

    public String[] extraValue(String dom, String comp) {
        for (int i = 0; i < this.index; i++) {
            if (comp.equals(this.cache.get(i)[0]) && "A".equals(this.cache.get(i)[1])) {
                return this.cache.get(i);
            }
        }

        return null;
    }
}


