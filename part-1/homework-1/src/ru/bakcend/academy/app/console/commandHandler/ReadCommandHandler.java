package ru.bakcend.academy.app.console.commandHandler;

import ru.bakcend.academy.app.console.Command;
import ru.bakcend.academy.controller.ProductController;
import ru.bakcend.academy.exception.command.WrongArgumentsException;

import java.io.OutputStream;
import java.io.PrintWriter;

public class ReadCommandHandler extends BaseCommandHandler {

    public ReadCommandHandler(ProductController productController) {
        super(productController);
    }

    @Override
    public int handle(Command command, OutputStream outputStream) {
        if (command.getName().equalsIgnoreCase("read")) {
            if (!command.hasArgs()) {
                String output = productController.getAllProducts();
                PrintWriter printWriter = new PrintWriter(outputStream);
                printWriter.printf(output);
                printWriter.flush();
                return 0;
            } else {
                throw new WrongArgumentsException("Wrong number of arguments. Expected 0.");
            }
        } else {
            return -1;
        }
    }
}
