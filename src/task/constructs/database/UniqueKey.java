package task.constructs.database;

public class UniqueKey<K> {
    private String name;
    private K uniqueKey;

    public UniqueKey(String name, K uniqueKey) {
        this.name = name;
        this.uniqueKey = uniqueKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public K getUniqueKey() {
        return uniqueKey;
    }

    public void setUniqueKey(K uniqueKey) {
        this.uniqueKey = uniqueKey;
    }
}