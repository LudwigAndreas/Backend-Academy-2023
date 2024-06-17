package ru.edu.homework.springcrud.service;

import ru.edu.homework.springcrud.exception.repository.AlreadyExistsException;
import ru.edu.homework.springcrud.exception.repository.NotFoundException;
import ru.edu.homework.springcrud.model.TaskStatus;
import ru.edu.homework.springcrud.model.TriggeredTask;

public interface ProductMoveService {

    TriggeredTask moveProductsAsync(Long sourceCategoryId, Long targetCategoryId) throws AlreadyExistsException, NotFoundException;

    TaskStatus getTaskStatus(Long taskId) throws NotFoundException;
}
