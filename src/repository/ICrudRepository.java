package repository;

import java.util.List;

public interface ICrudRepository <T> {

    T create(T obj);

    List<T> getAll();

    T update(T obj);

    void delete(T obj);
}
