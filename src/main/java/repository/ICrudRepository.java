package repository;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Represents the general behavior of a CrudRrepoitory
 * @param <T> template parameter for the Objects of the Repo
 */
public interface ICrudRepository <T> {

    T create(T obj) throws IOException, SQLException;

    List<T> getAll();

    T update(T obj) throws IOException;

    void delete(T obj) throws IOException, SQLException;
}
