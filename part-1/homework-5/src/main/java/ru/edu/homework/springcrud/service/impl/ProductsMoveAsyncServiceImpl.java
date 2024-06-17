package ru.edu.homework.springcrud.service.impl;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ru.edu.homework.springcrud.exception.repository.AlreadyExistsException;
import ru.edu.homework.springcrud.exception.repository.NotFoundException;
import ru.edu.homework.springcrud.model.TaskStatus;
import ru.edu.homework.springcrud.model.TriggeredTask;
import ru.edu.homework.springcrud.repository.TaskRepository;
import ru.edu.homework.springcrud.service.ProductCategoryService;
import ru.edu.homework.springcrud.service.ProductMoveService;

@Service
public class ProductsMoveAsyncServiceImpl implements ProductMoveService {

    private final TaskRepository taskRepository;

    private final ProductCategoryService productCategoryService;

    public ProductsMoveAsyncServiceImpl(TaskRepository taskRepository, ProductCategoryService productCategoryService) {
        this.taskRepository = taskRepository;
        this.productCategoryService = productCategoryService;
    }

    public TriggeredTask moveProductsAsync(Long sourceCategoryId, Long targetCategoryId) throws AlreadyExistsException, NotFoundException {
        TriggeredTask task = new TriggeredTask(sourceCategoryId, targetCategoryId);
        taskRepository.save(task);
        startTask(task);
        return task;
    }

    @Async("threadPoolTaskExecutor")
    protected void startTask(TriggeredTask task) throws AlreadyExistsException, NotFoundException {
        try {
            task.setStatus(TaskStatus.IN_PROGRESS);
            taskRepository.save(task);
            productCategoryService.moveProducts(task.getSourceCategoryId(), task.getTargetCategoryId());
            task.setStatus(TaskStatus.DONE);
            taskRepository.save(task);
        } catch (NotFoundException | AlreadyExistsException e) {
//          may check source and target categories before task creation
            task.setStatus(TaskStatus.ERROR);
            taskRepository.save(task);
        }
    }

    @Override
    public TaskStatus getTaskStatus(Long taskId) throws NotFoundException {
        return taskRepository.findById(taskId).orElseThrow(NotFoundException::new).getStatus();
    }
}
