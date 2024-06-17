package ru.bakcend.academy.app.console.commandHandler;

import ru.bakcend.academy.app.console.Command;
import ru.bakcend.academy.controller.ProductController;

import java.io.OutputStream;

public abstract class BaseCommandHandler {
    ProductController productController;

    BaseCommandHandler(ProductController productController) {
        this.productController = productController;
    }

    public abstract int handle(Command command, OutputStream outputStream);


}
