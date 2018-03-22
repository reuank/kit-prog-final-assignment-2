package task.interfaces;

import java.util.List;

public interface IListSerializer<T, D> {
    List<D> serialize(List<T> list);
}
