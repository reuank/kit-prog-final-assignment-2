package task.constructs.database;

import java.util.ArrayList;
import java.util.HashMap;

public class Database {
    private String name;
    private HashMap<Class, Table<? extends Model>> tables;

    public Database(String name) {
        this.name = name;
        this.tables = new HashMap<>();
    }

    public void createTable(Table<? extends Model> table) {
        this.tables.put(table.getType(), table);
    }

    public void createTables(Table<?>... tables) {
        for (Table table : tables) {
            this.createTable(table);
        }
    }

    public boolean isEmpty() {
        return tables.isEmpty();
    }

    public <T extends Model> Table<T> getTable(Class<T> tableClass) {
        return (Table<T>) this.tables.get(tableClass);
    }
}