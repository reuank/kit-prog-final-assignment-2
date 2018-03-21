package task.interfaces;

import java.util.List;

public interface IListSerializer<T, D> {
    D serialize(List<T> list);
    D serializeSingle(T item);
}
