import java.util.LinkedList;

public class chainedHash {

    private static class Node {
        String key;
        String value;

        Node(String key, String value) {
            this.key = key;
            this.value = value;
        }
    }

    private LinkedList<Node>[] table;
    private int m;
    private int count;


    @SuppressWarnings("unchecked")
    public chainedHash(int m) {
        this.m = m;
        this.table = new LinkedList[m];
        for (int i = 0; i < m; i++) {
            table[i] = new LinkedList<>();
        }
        this.count = 0;
    }


    private int hash(String key) {
        return (key.hashCode() & 0x7fffffff) % m;
    }


    public void insert(String key, String value) {
        int i = hash(key);
        for (Node node : table[i]) {
            if (node.key.equals(key)) {
                node.value = value; // update existing
                return;
            }
        }
        table[i].add(new Node(key, value));
        count++;
    }


    public String lookup(String key) {
        int i = hash(key);
        for (Node node : table[i]) {
            if (node.key.equals(key)) {
                return node.value;
            }
        }
        return null;
    }


    public String remove(String key) {
        int i = hash(key);
        for (Node node : table[i]) {
            if (node.key.equals(key)) {
                String val = node.value;
                table[i].remove(node);
                count--;
                return val;
            }
        }
        return null;
    }


    public boolean isInTable(String key) {
        return lookup(key) != null;
    }

    public boolean isEmpty() {
        return count == 0;
    }

    public boolean isFull() {

        return false;
    }


    public void printTable() {
        for (int i = 0; i < m; i++) {
            if (!table[i].isEmpty()) {
                System.out.print(i + ": ");
                for (Node node : table[i]) {
                    System.out.print("(" + node.key + ", " + node.value + ") ");
                }
                System.out.println();
            }
        }
    }
}
