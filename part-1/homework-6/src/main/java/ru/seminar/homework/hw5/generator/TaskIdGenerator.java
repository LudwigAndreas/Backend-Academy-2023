package ru.seminar.homework.hw5.generator;

import java.util.UUID;

public class TaskIdGenerator {
    private TaskIdGenerator() {
    }

    private static TaskIdGenerator instance;

    public static TaskIdGenerator getInstance() {
        if (instance == null) {
            instance = new TaskIdGenerator();
        }
        return instance;
    }

    public String generateId() {
        return UUID.randomUUID().toString().substring(0, 6).toUpperCase();
    }
}
