package repository;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class FileRepository<T> implements ICrudRepository<T>{
    protected String fileName;
    protected List<T> repoList;

    public FileRepository(String fileName) throws IOException {
        repoList = new ArrayList<>(){};
        this.fileName = fileName;
        repoList = readFromFile();
    }
    public abstract List<T>  readFromFile() throws IOException;

    public void writeToFile(Iterable<T> repoList) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter writer = objectMapper.writer(new DefaultPrettyPrinter());
        writer.writeValue(new File(fileName), repoList);
    }

    public List<T> getAll(){
        return repoList;
    }


    public T create(T obj) throws IOException {
        this.repoList.add(obj);
        writeToFile(repoList);   //TODO check daca functioneaza sa adaugi obiect nou

        return obj;
    }

    public abstract T update(T obj) throws IOException;

    public void delete(T obj){
        this.repoList.remove(obj);

        //TODO: remove from json

    }


}
