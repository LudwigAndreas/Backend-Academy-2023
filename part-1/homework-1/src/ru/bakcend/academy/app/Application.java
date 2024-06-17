package ru.bakcend.academy.app;



import ru.bakcend.academy.app.console.ConsoleApplication;

import java.util.HashMap;
import java.util.Map;

public class Application {
    private static Application instance;
    private final Map<String, String> optionValueMap = new HashMap<>();
    private String downloadFolder = "./downloads";
    private String urlsFile = "files_urls.txt";
    private int threadsCount = 1;

    public Application(String[] args) {
        boolean done = false;

        parseOptions(args);

        for (Map.Entry<String, String> entry : optionValueMap.entrySet()) {
            if (entry.getKey().equals("help") || entry.getKey().equals("h")) {
                printHelpMessage();
                done = true;
            } else if (entry.getKey().equals("version") || entry.getKey().equals("v")) {
                System.out.println("Version 1.0");
                done = true;
            } else {
                System.err.println("Unknown option: " + entry.getKey());
                System.exit(1);
            }
        }

        if (done) {
            System.exit(0);
        }

    }

    public static Application getInstance(String[] args) {
        if (instance == null) {
            instance = new Application(args);
        }
        return instance;
    }

    public void exec() {
        initConsoleMain();
    }

    private void parseOptions(String[] args) {
        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            boolean twoDashes = arg.startsWith("--");
            if (twoDashes || arg.startsWith("-")) {
                String[] parts = arg.split("=");
                String option;
                if (twoDashes) {
                    option = parts[0].substring(2);
                } else {
                    option = parts[0].substring(1);
                }
                String value = null;
                if (arg.contains("=")) {
                    value = parts[1];
                }
                optionValueMap.put(option, value);
            }
        }
    }

    private void initConsoleMain() {
        ConsoleApplication application = new ConsoleApplication();
        application.exec();
    }

    private void printHelpMessage() {
        System.out.println("Usage: java Program [options]");
        System.out.println("Options:");
        System.out.println("  --help, -h\t\t\tDisplay this help message.");
    }
}