package ru.bakcend.academy;


import ru.bakcend.academy.app.Application;

public class Main {
    public static void main(String[] args) {
        Application app = Application.getInstance(args);
        app.exec();
    }
}