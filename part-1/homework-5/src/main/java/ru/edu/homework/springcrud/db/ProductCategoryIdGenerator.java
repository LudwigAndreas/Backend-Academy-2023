package ru.edu.homework.springcrud.db;

public class ProductCategoryIdGenerator {
    private static final ProductCategoryIdGenerator INSTANCE = new ProductCategoryIdGenerator();
    private static long id = 1;

    private ProductCategoryIdGenerator() {}

    public static ProductCategoryIdGenerator getInstance() {
        return INSTANCE;
    }

    public static ProductCategoryIdGenerator getInstance(long id) {
        if (id > ProductCategoryIdGenerator.id) {
            ProductCategoryIdGenerator.id = id;
        }
        return INSTANCE;
    }

    public long generateId() {
        return id++;
    }
}
