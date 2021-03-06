package repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Represent an abstract Repository working with a database
 * @param <T> the typo of objects in the repository
 */
public abstract class JDBCRepository<T> implements ICrudRepository<T> {
    protected List<T> repoList;
    protected Connection conn;

    public JDBCRepository(Connection conn) throws SQLException {
        this.repoList = new ArrayList<>();
        this.conn = conn;
        repoList = read();
    }


    /**
     * Gives a list of same type objects from the file
     * @return list of ojects
     */
    public abstract List<T>  read() throws SQLException;

    /**
     * Adds a new object to the repository
     * @param obj a new Object of type T
     * @return the added object
     */
    public abstract T create(T obj) throws SQLException;

    /**
     * Gives all the object from the repository
     * @return list with al object
     */
    public List<T> getAll(){
        return repoList;
    }

    /**
     * Updates an existing object from the repo
     * @param obj the object with the new information
     * @return the modified object
     */
    public abstract T update(T obj) throws SQLException;

    /**
     * delete an existing object from the repo
     * @param obj the object to be deleted
     */
    public abstract void delete(T obj) throws SQLException;
}