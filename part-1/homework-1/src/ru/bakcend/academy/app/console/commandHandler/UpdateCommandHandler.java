package ru.bakcend.academy.app.console.commandHandler;

import ru.bakcend.academy.app.console.Command;
import ru.bakcend.academy.controller.ProductController;
import ru.bakcend.academy.exception.command.CommandException;
import ru.bakcend.academy.exception.command.WrongArgumentsException;

import java.io.OutputStream;
import java.io.PrintWriter;

public class UpdateCommandHandler extends BaseCommandHandler{
    public UpdateCommandHandler(ProductController productController) {
        super(productController);
    }

    @Override
    public int handle(Command command, OutputStream outputStream) {
        if (command.getName().equalsIgnoreCase("update")) {
            if (command.hasArgs()) {
                try {
                    String partNumber = command.getArg(0);
                    String name = command.getArg(1);
                    double price = command.getArgAsDouble(2);
                    int count = command.getArgAsInt(3);
                    String output = productController.updateProduct(partNumber, name, price, count);
                    PrintWriter printWriter = new PrintWriter(outputStream);
                    printWriter.printf(output);
                    printWriter.flush();
                    return 0;
                } catch (IndexOutOfBoundsException | NumberFormatException e) {
                    throw new CommandException("Wrong arguments. Use: update <partNumber> <name> <price> <count>");
                }
            } else {
                throw new WrongArgumentsException("Wrong number of arguments. Use: update <partNumber> <name> <price> <count>");
            }
        } else {
            return -1;
        }
    }
}
