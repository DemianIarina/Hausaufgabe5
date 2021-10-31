package repository;

import java.util.List;

/**
 * Represents the general behavior of a CrudRrepoitory
 * @param <T> template parameter for the Objects of the Repo
 */
public interface ICrudRepository <T> {

    T create(T obj);

    List<T> getAll();

    T update(T obj);

    void delete(T obj);
}
