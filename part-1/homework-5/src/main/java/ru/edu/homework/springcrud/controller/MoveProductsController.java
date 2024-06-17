package ru.edu.homework.springcrud.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import ru.edu.homework.springcrud.dto.request.MoveTaskRequestDto;
import ru.edu.homework.springcrud.dto.response.MoveTaskResponseDto;
import ru.edu.homework.springcrud.dto.response.TaskStatusResponseDto;
import ru.edu.homework.springcrud.exception.repository.AlreadyExistsException;
import ru.edu.homework.springcrud.exception.repository.NotFoundException;
import ru.edu.homework.springcrud.model.TriggeredTask;
import ru.edu.homework.springcrud.service.ProductMoveService;

@RestController
public class MoveProductsController {
    private final ProductMoveService productMoveService;

    public MoveProductsController(ProductMoveService productMoveService) {
        this.productMoveService = productMoveService;
    }

//    TODO: add mapper
    @PostMapping("/move-products-task")
    public MoveTaskResponseDto moveProductsAsync(@Valid @RequestBody MoveTaskRequestDto taskRequest) throws AlreadyExistsException, NotFoundException {
        TriggeredTask task = productMoveService.moveProductsAsync(taskRequest.getSourceCategoryId(), taskRequest.getTargetCategoryId());
        return new MoveTaskResponseDto(task.getId());
    }

    @GetMapping("/move-products")
    public TaskStatusResponseDto getTaskStatus(@RequestParam Long taskId) throws NotFoundException {
        return new TaskStatusResponseDto(taskId, productMoveService.getTaskStatus(taskId).toString());
    }
}
