package ru.bakcend.academy.app.console;

import java.util.ArrayList;
import java.util.List;

public class Command {
    String name;

    List<String> args;

    public Command(String name, List<String> args) {
        this.name = name;
        this.args = args;
    }

    public Command(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<String> getArgs() {
        return args;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setArgs(List<String> args) {
        this.args = args;
    }

    public void addArg(String arg) {
        if (args == null) {
            args = new ArrayList<>();
        }
        args.add(arg);
    }

    public String getArg(int index) {
        return args.get(index);
    }

    public int getArgAsInt(int index) {
        return Integer.parseInt(args.get(index));
    }

    public double getArgAsDouble(int index) {
        return Double.parseDouble(args.get(index));
    }

    public boolean hasArgs() {
        return args.size() > 0;
    }

}
