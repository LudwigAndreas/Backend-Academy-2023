package ru.edu.homework.springcrud.repository.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import ru.edu.homework.springcrud.exception.repository.FileRepositoryException;
import ru.edu.homework.springcrud.model.Product;
import ru.edu.homework.springcrud.repository.ProductRepository;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public class FileProductRepositoryImpl implements ProductRepository {

    @Value("${product.repository.file.path:src/main/resources/products.txt}")
    private String fileName;

    private File file;

    private final Logger logger = LoggerFactory.getLogger(FileProductRepositoryImpl.class);

    @Override
    public List<Product> findAll() {
        return readProductsFromFile();
    }

    @Override
    public Optional<Product> findById(String partNumber) {
        List<Product> products = readProductsFromFile();
        logger.debug("Searching for product with part number {}", partNumber);
        return products.stream()
                .filter(product -> product.getPartNumber().equals(partNumber))
                .findFirst();
    }

    @Override
    public void delete(Product product) {
        List<Product> products = readProductsFromFile();
        products.remove(product);
        saveAll(products);
        logger.debug("Product with part number {} deleted", product.getPartNumber());
    }

    @Override
    public void delete(String partNumber) {
        List<Product> products = readProductsFromFile();
        products.removeIf(product -> product.getPartNumber().equals(partNumber));
        saveAll(products);
        logger.debug("Product with part number {} deleted", partNumber);
    }

    @Override
    public Product save(Product product) {
        List<Product> products = readProductsFromFile();
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getPartNumber().equals(product.getPartNumber())) {
                products.set(i, product);
                saveAll(products);
                logger.debug("Product with part number {} saved", product.getPartNumber());
                return product;
            }
        }
        products.add(product);
        saveAll(products);
        logger.debug("Product with part number {} saved", product.getPartNumber());
        return product;
    }

    @Override
    public Iterable<Product> saveAll(Iterable<Product> products) {
        clearFile();
        writeProductsToFile((List<Product>) products);
        return products;
    }

    @Override
    public boolean existsById(String partNumber) {
        List<Product> products = readProductsFromFile();
        logger.debug("Searching for product with part number {}", partNumber);
        return products.stream().anyMatch(product -> product.getPartNumber().equals(partNumber));
    }

    @Override
    public long count() {
        return readProductsFromFile().size();
    }

    private boolean createFileIfNotExists() {
        if (file == null) {
            file = new File(fileName);
        }
        try {
            if (!file.exists()) {
                return file.createNewFile();
            }
        } catch (IOException e) {
            logger.error("Error while creating file", e);
            throw new FileRepositoryException("Error while creating " + fileName + " file");
        }
        return true;
    }

    private void clearFile() {
        checkFile();
        try (PrintWriter pw = new PrintWriter(file)) {
            pw.print("");
        } catch (FileNotFoundException e) {
            logger.error("Error while clearing file", e);
            throw new FileRepositoryException("Error while clearing " + fileName + " file");
        }
    }

    private void writeProductsToFile(List<Product> products) {
        clearFile();
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(products);
        } catch (IOException e) {
            logger.error("Error while writing to file " + e.getMessage(), e);
            throw new FileRepositoryException("Error while writing to " + fileName + " file");
        }
    }

    private List<Product> readProductsFromFile() {
        if (!checkFile()) {
            return new ArrayList<>();
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            Collection<?> rawCollection = (Collection<?>) ois.readObject();
            List<Product> productList = new ArrayList<>();
            for (Object o : rawCollection) {
                try {
                    productList.add((Product) o);
                } catch (ClassCastException e) {
                    throw new FileRepositoryException("File contains not products");
                }
            }
            logger.debug("File read successfully, {} products found", productList.size());
            return productList;
        } catch (IOException | ClassNotFoundException e) {
            logger.error("Error while reading file", e);
            throw new FileRepositoryException("Error while reading " + fileName + " file");
        }
    }

    private boolean checkFile() {
        return createFileIfNotExists();
    }
}
