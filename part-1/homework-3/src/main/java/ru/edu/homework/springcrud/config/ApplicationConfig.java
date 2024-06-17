package ru.edu.homework.springcrud.config;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import ru.edu.homework.springcrud.repository.ProductRepository;
import ru.edu.homework.springcrud.repository.impl.FileProductRepositoryImpl;
import ru.edu.homework.springcrud.repository.impl.RamProductRepositoryImpl;

@Configuration
public class ApplicationConfig {

    @Value("${product.repository.class.name}")
    private String repositoryClassName;

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    @Primary
    @Qualifier("productRepository")
    public ProductRepository productRepository() {
        try {
            Class<?> repositoryClass = Class.forName(repositoryClassName);
            return (ProductRepository) repositoryClass.getDeclaredConstructor().newInstance();
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Failed to create repository instance with name " + repositoryClassName, e);
        }
    }
}
