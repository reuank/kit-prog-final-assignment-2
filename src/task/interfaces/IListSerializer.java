package task.interfaces;

import java.util.List;

/**
 * An interface for list serializer.
 *
 * @param <T> The type.
 * @param <R> The return list type (normally string).
 */
public interface IListSerializer<T, R> {
    /**
     * Serializes a T-List to a R-List (R = String mostly)
     * @param list the T-list that shall be serialized.
     * @return returns the serialized list.
     */
    List<R> serialize(List<T> list);
}
