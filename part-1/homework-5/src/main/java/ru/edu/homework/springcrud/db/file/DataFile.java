package ru.edu.homework.springcrud.db.file;

import ru.edu.homework.springcrud.db.annotation.AnnotationProcessor;
import ru.edu.homework.springcrud.exception.db.DataSourceException;
import ru.edu.homework.springcrud.exception.db.DataSourceReadException;

import java.io.*;
import java.util.*;

public class DataFile<T, ID> {

    File file;

    public DataFile(String fileName) {
        file = new File(fileName);
    }

    public void setFile(File file) {
        this.file = file;
    }

    public void save(Map<ID, T> data) throws DataSourceReadException {
        clearFile();
        Map<ID, T> loadedData = load();
        loadedData.putAll(data);
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(new ArrayList<>(loadedData.values()));
        } catch (FileNotFoundException e) {
            throw new DataSourceException("File not found");
        } catch (IOException  e) {
            e.printStackTrace();
            throw new DataSourceException("Error while writing to file");
        }
    }

    public Map<ID, T> load() throws DataSourceReadException {
        checkFile();
        if (file.length() == 0) {
            return new HashMap<>();
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            List<T> list = (List<T>) ois.readObject();
            Map<ID, T> map = new HashMap<>();
            for (T t : list) {
                ID id = AnnotationProcessor.getIdFieldValue(t);
                map.put(id, t);
            }
            return map;
        } catch (FileNotFoundException e) {
            throw new DataSourceException("File not found");
        } catch (IOException e) {
            throw new DataSourceException("Error while reading file");
        } catch (ClassNotFoundException | ClassCastException e) {
            throw new DataSourceReadException("File contains invalid data");
        }
    }



    private void clearFile() {
        checkFile();
        try (PrintWriter pw = new PrintWriter(file)) {
            pw.print("");
        } catch (FileNotFoundException e) {
            throw new DataSourceException("Error while clearing file");
        }
    }

    private boolean checkFile() {
        return createFileIfNotExists();
    }

    private boolean createFileIfNotExists() {
        if (file == null) {
            return false;
        }
        try {
            if (!file.exists()) {
                return file.createNewFile();
            }
        } catch (IOException e) {
            throw new DataSourceException("Error while creating file");
        }
        return true;
    }
}
