package task.constructs.database;

import java.util.HashMap;

/**
 * The construct that works is used as a database.
 */
public class Database {
    private HashMap<Class, Table<? extends Model>> tables;

    /**
     * Instantiates a new Database.
     */
    public Database() {
        this.tables = new HashMap<>();
    }

    /**
     * Creates new Tables that hold model data.
     *
     * @param tables The tables that shall be added.
     */
    public void createTables(Table<?>... tables) {
        for (Table table : tables) {
            this.createTable(table);
        }
    }

    private void createTable(Table<?> table) {
        this.tables.put(table.getType(), table);
    }

    /**
     * Returns a specific table.
     *
     * @param tableClass The table class that functions as the key to the table here.
     * @param <T> The model class.
     * @return Returns the requested table.
     */
    @SuppressWarnings("unchecked")
    public <T extends Model> Table<T> getTable(Class<T> tableClass) {
        /* Note that, as the tables only get generated within the app itself and with no user interaction whatsoever,
        this cast is always safe! Only Model-Tables are being instantiated in the app. */
        return (Table<T>) this.tables.get(tableClass);
    }
}