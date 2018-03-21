package task.constructs.database;

import java.util.ArrayList;

public class Table<T extends Model> {
    private ArrayList<T> rows;
    private Class<T> type;


    public Table(Class<T> type) {
        this.type = type;
        this.rows = new ArrayList<>();
    }

    public ArrayList<T> getRows() {
        return rows;
    }

    public Class<T> getType() {
        return this.type;
    }

    public void insert(T item) {
        this.rows.add(item);
    }
}