package task.constructs.database;

import java.util.ArrayList;
import java.util.function.Predicate;

/**
 * The construct that represents tables in a database and holds rows of data.
 * @param <T> The class of the data that is stored in the table.
 */
public class Table<T extends Model> {
    private ArrayList<T> rows;
    private Class<T> type;

    /**
     * Instantiates a new table.
     * @param type The type of the table.
     */
    public Table(Class<T> type) {
        this.type = type;
        this.rows = new ArrayList<>();
    }

    /**
     * @return - returns the rows.
     **/
    public ArrayList<T> getRows() {
        return rows;
    }

    /**
     * @return - returns the type.
     **/
    public Class<T> getType() {
        return type;
    }

    /**
     * Inserts a new row into the table.
     *
     * @param item the new row.
     */
    public void insert(T item) {
        this.rows.add(item);
    }

    /**
     * CHecks if there are any rows that match the requirements.
     *
     * @param predicate The requirements, represented by a predicate.
     * @return - returns true if there is a match.
     */
    public boolean anyMatch(Predicate<T> predicate) {
        return !this.isEmpty() && this.rows.stream().anyMatch(predicate);
    }

    private boolean isEmpty() {
        return this.rows.isEmpty();
    }
}