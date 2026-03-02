import java.util.Arrays;

public class openHash {
    private String[] keys;
    private String[] values;
    private int m;
    private int count;


    public openHash(int m) {
        this.m = m;
        this.keys = new String[m];
        this.values = new String[m];
        this.count = 0;
    }


    private int hash(String key) {

        return (key.hashCode() & 0x7fffffff) % m;
    }


    public void insert(String key, String value) {
        if (isFull()) {
            throw new RuntimeException("Hash table is full");
        }

        int i = hash(key);
        while (keys[i] != null) {
            if (keys[i].equals(key)) {
                values[i] = value;
                return;
            }
            i = (i + 1) % m;
        }
        keys[i] = key;
        values[i] = value;
        count++;
    }


    public String lookup(String key) {
        int i = hash(key);
        while (keys[i] != null) {
            if (keys[i].equals(key)) {
                return values[i];
            }
            i = (i + 1) % m;
        }
        return null;
    }


    public String remove(String key) {
        int i = hash(key);
        while (keys[i] != null) {
            if (keys[i].equals(key)) {
                String val = values[i];
                keys[i] = null;
                values[i] = null;
                count--;


                i = (i + 1) % m;
                while (keys[i] != null) {
                    String reKey = keys[i];
                    String reVal = values[i];
                    keys[i] = null;
                    values[i] = null;
                    count--;
                    insert(reKey, reVal);
                    i = (i + 1) % m;
                }
                return val;
            }
            i = (i + 1) % m;
        }
        return null;
    }

    // (e) Predicates
    public boolean isInTable(String key) {
        return lookup(key) != null;
    }

    public boolean isFull() {
        return count == m;
    }

    public boolean isEmpty() {
        return count == 0;
    }

    // For debugging
    public void printTable() {
        for (int i = 0; i < m; i++) {
            if (keys[i] != null) {
                System.out.println(i + ": (" + keys[i] + ", " + values[i] + ")");
            }
        }
    }
}
