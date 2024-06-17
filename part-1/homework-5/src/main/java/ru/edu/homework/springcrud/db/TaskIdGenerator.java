package ru.edu.homework.springcrud.db;

public class TaskIdGenerator {

    private static final TaskIdGenerator INSTANCE = new TaskIdGenerator();
    private static int id = 1;

    private TaskIdGenerator() {}

    public static TaskIdGenerator getInstance() {
        return INSTANCE;
    }

    public long generateId() {
        return id++;
    }
}
