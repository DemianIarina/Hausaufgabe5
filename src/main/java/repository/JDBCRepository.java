package repository;

import java.util.List;

public abstract class JDBCRepository<T> implements ICrudRepository<T> {
    protected List<T> repoList;

    public JDBCRepository(List<T> repoList) {
        this.repoList = repoList;
    }


    /**
     * Gives a list of same type objects from the file
     * @return list of ojects
     */
    public abstract List<T>  read();

    //TODO write(Iterable<T> repoList) ?

    /**
     * Adds a new object to the repository
     * @param obj a new Object of type T
     * @return the added object
     */
    public abstract T create(T obj);

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
    public abstract T update(T obj);

    /**
     * delete an existing object from the repo
     * @param obj the object to be deleted
     */
    public abstract void delete(T obj);
}
