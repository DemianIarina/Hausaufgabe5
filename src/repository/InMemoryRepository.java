package repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a repository of same type objects, implementing ICrudRepository
 * @param <T> template parameter for the Objects in the list
 */
public abstract class InMemoryRepository <T> implements ICrudRepository<T> {
    protected List<T> repoList;

    public InMemoryRepository() {
        this.repoList = new ArrayList<>();

    }

    /**
     * Adds a new object to the list
     * @param obj a new Object of type T
     * @return the added object
     */
    @Override
    public T create(T obj) {
        this.repoList.add(obj);
        return obj;
    }

    /**
     * Returns all the object from the repository
     * @return list with al object
     */
    @Override
    public List<T> getAll() {
        return this.repoList;
    }

    /**
     * Removes an already existing object from the list
     * @param obj the object wanted to be deleted
     */
    @Override
    public void delete(T obj) {
        this.repoList.remove(obj);
    }

}
