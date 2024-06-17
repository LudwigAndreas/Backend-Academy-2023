package ru.bakcend.academy.model;

import java.util.Objects;

public class Product {
    String partNumber;
    String name;
    Double cost;
    Integer number;

    public Product() {
    }

    public Product(String partNumber, String name, Double cost, Integer number) {
        this.partNumber = partNumber;
        this.name = name;
        if (cost < 0)
            throw new IllegalArgumentException("Cost can't be negative");
        this.cost = cost;
        if (number < 0)
            throw new IllegalArgumentException("Number can't be negative");
        this.number = number;
    }

    public String getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        if (cost < 0)
            throw new IllegalArgumentException("Cost can't be negative");
        this.cost = cost;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        if (number < 0)
            throw new IllegalArgumentException("Number can't be negative");
        this.number = number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(partNumber, product.partNumber) && Objects.equals(name, product.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(partNumber, name);
    }

    @Override
    public String toString() {
        return "Product{" +
                "partNumber='" + partNumber + '\'' +
                ", name='" + name + '\'' +
                ", cost=" + cost +
                ", number=" + number +
                '}';
    }
}
