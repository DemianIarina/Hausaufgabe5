package repository;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Represents a repository of same type objects, in a file, but also in a list, implementing ICrudRepository
 * @param <T> template parameter for the Objects in the list
 */
public abstract class FileRepository<T> implements ICrudRepository<T>{
    protected String fileName;
    protected List<T> repoList;

    public FileRepository(String fileName) throws IOException {
        this.repoList = new ArrayList<>();
        this.fileName = fileName;
        repoList = readFromFile();
    }

    /**
     * Gives a list of same type objects from the file
     * @return list of ojects
     * @throws IOException if there occurs an error with the ObjectOutputStream
     */
    public abstract List<T>  readFromFile() throws IOException;

    /**
     * Writes a list of same type objects into a given file, serialised from json
     * @param repoList the object list to write
     * @throws IOException if there occurs an error with the ObjectOutputStream
     */
    public void writeToFile(Iterable<T> repoList) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter writer = objectMapper.writer(new DefaultPrettyPrinter());
        writer.writeValue(new File(fileName), repoList);
    }

    /**
     * Gives all the object from the repository
     * @return list with al object
     */
    public List<T> getAll(){
        return repoList;
    }

    /**
     * Adds a new object to the repository
     * @param obj a new Object of type T
     * @return the added object
     * @throws IOException if there occurs an error with the ObjectOutputStream
     */
    public T create(T obj) throws IOException {
        repoList.add(obj);
        writeToFile(repoList);

        return obj;
    }

    /**
     * Updates an existing object from the repo
     * @param obj the object with the new information
     * @return the modified object
     * @throws IOException if there occurs an error with the ObjectOutputStream
     */
    public abstract T update(T obj) throws IOException;

    /**
     * delete an existing object from the repo
     * @param obj the object to be deleted
     */
    public void delete(T obj) throws IOException {
        this.repoList.remove(obj);
        writeToFile(repoList);

    }


}
