package ru.bakcend.academy.app.console.commandHandler;

import ru.bakcend.academy.app.console.Command;
import ru.bakcend.academy.controller.ProductController;
import ru.bakcend.academy.exception.command.CommandException;
import ru.bakcend.academy.exception.command.WrongArgumentsException;

import java.io.OutputStream;
import java.io.PrintWriter;

public class ExitCommandHandler extends BaseCommandHandler{
    public ExitCommandHandler(ProductController productController) {
        super(productController);
    }

    @Override
    public int handle(Command command, OutputStream outputStream) {
        if (command.getName().equalsIgnoreCase("exit")) {
            if (!command.hasArgs()) {
                try {
                    PrintWriter printWriter = new PrintWriter(outputStream);
                    printWriter.printf("Goodbye!");
                    printWriter.flush();
                    return 2;
                } catch (IndexOutOfBoundsException | NumberFormatException e) {
                    throw new CommandException("Wrong arguments.");
                }
            } else {
                throw new WrongArgumentsException("Wrong number of arguments. Expected 0.");
            }
        } else {
            return -1;
        }
    }
}
