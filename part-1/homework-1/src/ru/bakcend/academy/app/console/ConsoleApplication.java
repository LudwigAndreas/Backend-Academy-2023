package ru.bakcend.academy.app.console;

import ru.bakcend.academy.app.console.commandHandler.*;
import ru.bakcend.academy.controller.ProductController;
import ru.bakcend.academy.exception.command.CommandException;
import ru.bakcend.academy.repository.ProductRepository;
import ru.bakcend.academy.repository.ProductRepositoryImpl;
import ru.bakcend.academy.service.ProductService;
import ru.bakcend.academy.service.ProductServiceImpl;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class ConsoleApplication {

    boolean done;
    ProductController productController;
    List<BaseCommandHandler> commandHandlers;

    public ConsoleApplication() {
        done = false;
        ProductRepository productRepository = new ProductRepositoryImpl();
        ProductService productService = new ProductServiceImpl(productRepository);
        productController = new ProductController(productService);
        commandHandlers = CreateCommandHandlers(productController);
    }

    private List<BaseCommandHandler> CreateCommandHandlers(ProductController productController) {
        return List.of(
                new CreateProductCommandHandler(productController),
                new ReadCommandHandler(productController),
                new UpdateCommandHandler(productController),
                new DeleteCommandHandler(productController),
                new ExitCommandHandler(productController));
    }

    public void exec() {
        Scanner in = new Scanner(System.in);
        while (!done) {
            System.out.print("> ");
            String input = in.nextLine();
            Optional<Command> command = parse(input);
            if (command.isEmpty())
                continue;
            boolean handled = false;

            for (BaseCommandHandler commandHandler : commandHandlers) {
                try {
                    int result = commandHandler.handle(command.get(), System.out);
                    if (result == 0) {
                        handled = true;
                        break;
                    } else if (result == -1) {
                        continue;
                    } else if (result == 2) {
                        handled = true;
                        done = true;
                        break;
                    }
                } catch (CommandException e) {
                    System.out.println("Error: " + e.getMessage());
                    handled = true;
                } catch (IllegalArgumentException e) {
                    System.out.println("Syntax error: " + e.getMessage());
                    handled = true;
                }
            }
            if (!handled) {
                System.out.println("Unknown command: " + command.get().getName());
            }
        }
    }

    private Optional<Command> parse(String input) {
        return CommandParser.parse(input);
    }
}
