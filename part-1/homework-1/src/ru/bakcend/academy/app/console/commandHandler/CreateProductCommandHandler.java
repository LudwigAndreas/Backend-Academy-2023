package ru.bakcend.academy.app.console.commandHandler;

import ru.bakcend.academy.app.console.Command;
import ru.bakcend.academy.controller.ProductController;
import ru.bakcend.academy.exception.command.WrongArgumentsException;

import java.io.OutputStream;
import java.io.PrintWriter;

public class CreateProductCommandHandler extends BaseCommandHandler {
    public CreateProductCommandHandler(ProductController productController) {
        super(productController);
    }

    @Override
    public int handle(Command command, OutputStream outputStream) {
        if (command.getName().equalsIgnoreCase("create")) {
            if (command.hasArgs()) {
                try {
                    String partNumber = command.getArg(0);
                    String name = command.getArg(1);
                    double price = command.getArgAsDouble(2);
                    int count = command.getArgAsInt(3);
                    String output = productController.createProduct(partNumber, name, price, count);
                    PrintWriter printWriter = new PrintWriter(outputStream);
                    printWriter.printf(output);
                    printWriter.flush();
                    return 0;
                } catch (IndexOutOfBoundsException | IllegalArgumentException e) {
                    throw new WrongArgumentsException("Error: Argument error. Use: create <partNumber> <name> <price> <count>");
                }
            } else {
                throw new WrongArgumentsException("Error: Wrong number of arguments. Use: create <partNumber> <name> <price> <count>");
            }
        } else {
            return -1;
        }
    }
}
