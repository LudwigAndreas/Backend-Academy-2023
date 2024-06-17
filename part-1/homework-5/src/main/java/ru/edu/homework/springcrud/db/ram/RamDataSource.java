package ru.edu.homework.springcrud.db.ram;

import lombok.Getter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import ru.edu.homework.springcrud.db.DataSource;
import ru.edu.homework.springcrud.model.Product;
import ru.edu.homework.springcrud.model.ProductCategory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Getter
@Component
@ConditionalOnProperty(name = "repository.type", havingValue = "ram")
public class RamDataSource implements DataSource {

    private final Map<String, Product> products = new HashMap<>();

    private final Map<Long, ProductCategory> productCategories = new HashMap<>();

    @Override
    public void saveProducts(Map<String, Product> products) {
        this.products.putAll(products);
    }

    @Override
    public void saveProductCategories(Map<Long, ProductCategory> productCategories) {
        this.productCategories.putAll(productCategories);
    }
}
