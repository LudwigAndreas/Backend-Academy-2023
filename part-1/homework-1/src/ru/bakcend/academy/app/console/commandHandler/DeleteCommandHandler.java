package ru.bakcend.academy.app.console.commandHandler;

import ru.bakcend.academy.app.console.Command;
import ru.bakcend.academy.controller.ProductController;
import ru.bakcend.academy.exception.command.CommandException;

import java.io.OutputStream;
import java.io.PrintWriter;

public class DeleteCommandHandler extends BaseCommandHandler {
    public DeleteCommandHandler(ProductController productController) {
        super(productController);
    }

    @Override
    public int handle(Command command, OutputStream outputStream) {
        if (command.getName().equalsIgnoreCase("delete")) {
            if (command.hasArgs() && command.getArgs().size() == 1) {
                try {
                    String partNumber = command.getArg(0);
                    String output = productController.deleteProduct(partNumber);
                    PrintWriter printWriter = new PrintWriter(outputStream);
                    printWriter.printf(output);
                    printWriter.flush();
                    return 0;
                } catch (IndexOutOfBoundsException | NumberFormatException e) {
                    throw new CommandException("Wrong arguments. Use: delete <partNumber>");
                }
            } else {
                throw new CommandException("Wrong number of arguments. Use: delete <partNumber>");
            }
        } else {
            return -1;
        }
    }
}
