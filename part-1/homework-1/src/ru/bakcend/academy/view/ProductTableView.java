package ru.bakcend.academy.view;

import ru.bakcend.academy.model.Product;

public class ProductTableView {
    private ProductTableView() {
    }


    public static String productView(Product product) {
        int maxFieldLength = 25;
        if (product.getPartNumber().length() > maxFieldLength) {
            maxFieldLength = product.getPartNumber().length();
        }
        if (product.getName().length() > maxFieldLength) {
            maxFieldLength = product.getName().length();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(productHeaderView(maxFieldLength));
        stringBuilder.append(generateProductLine(product, maxFieldLength));
        stringBuilder.append(generateDelimiter(maxFieldLength));
        return stringBuilder.toString();
    }

    public static String productsView(Iterable<Product> products) {
        int maxFieldLength = 25;
        for (Product product : products) {
            if (product.getPartNumber().length() > maxFieldLength) {
                maxFieldLength = product.getPartNumber().length();
            }
            if (product.getName().length() > maxFieldLength) {
                maxFieldLength = product.getName().length();
            }
        }

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(productHeaderView(maxFieldLength));
        for (Product product : products) {
            stringBuilder.append(generateProductLine(product, maxFieldLength));
        }
        stringBuilder.append(generateDelimiter(maxFieldLength));
        return stringBuilder.toString();
    }

    private static String generateProductLine(Product product, int maxFieldLength) {
        return String.format("| %-" + maxFieldLength + "s | %-" + maxFieldLength + "s | %25s | %25s |\n",
                product.getPartNumber(),
                product.getName(),
                product.getCost(),
                product.getNumber());
    }

    private static String generateHeader(int maxFieldLength) {
        return String.format("| %-" + maxFieldLength + "s | %-" + maxFieldLength + "s | %25s | %25s |\n",
                "Part Number",
                "Name",
                "Cost",
                "Number");
    }

    private static String generateDelimiter(int maxFieldLength) {
        return String.format("+-%-" + maxFieldLength + "s-+-%-" + maxFieldLength + "s-+-%25s-+-%25s-+\n",
                "-".repeat(maxFieldLength),
                "-".repeat(maxFieldLength),
                "-".repeat(25),
                "-".repeat(25));
    }

    private static String productHeaderView(int maxFieldLength) {
        return generateDelimiter(maxFieldLength) + generateHeader(maxFieldLength) + generateDelimiter(maxFieldLength);
    }
}
