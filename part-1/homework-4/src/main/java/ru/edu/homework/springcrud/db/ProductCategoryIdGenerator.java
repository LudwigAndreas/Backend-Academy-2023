package ru.edu.homework.springcrud.db;

public class ProductCategoryIdGenerator {
    private static final ProductCategoryIdGenerator INSTANCE = new ProductCategoryIdGenerator();
    private static int id = 1;

    private ProductCategoryIdGenerator() {}

    public static ProductCategoryIdGenerator getInstance() {
        return INSTANCE;
    }

    public long generateId() {
        return id++;
    }
}
