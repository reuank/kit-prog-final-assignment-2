package task.constructs.database.queries;

import task.constructs.database.Model;
import task.constructs.database.Table;
import task.exceptions.InvalidQueryException;
import task.exceptions.Message;

import java.lang.reflect.Field;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class Select<T extends Model> {
    private Table<T> table;
    private Predicate<T> filter;

    public Select(Table table) {
        this.table = table;
    }

    public Predicate<T> getFilter() {
        return filter;
    }

    public void setFilter(Predicate<T> filter) {
        this.filter = filter;
    }

    /*public Select whereEquals(String fieldName, Object value) throws InvalidQueryException {
        try {
            Stream<T> stream = this.table.getRows()
                    .stream()
                    .filter(row -> row.getClass().getDeclaredField(fieldName).equals(value));
        } catch (NoSuchFieldException exception) {
            throw new InvalidQueryException(Message.LOGIN_FAILED);
        }
    }*/
}
