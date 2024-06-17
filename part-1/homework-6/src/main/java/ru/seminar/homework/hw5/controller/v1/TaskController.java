package ru.seminar.homework.hw5.controller.v1;

import io.reflectoring.api.TaskApi;
import io.reflectoring.model.PatchTaskByIdRequest;
import io.reflectoring.model.Task;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.seminar.homework.hw5.exception.NotFoundException;
import ru.seminar.homework.hw5.mapper.StatusEnumMapper;
import ru.seminar.homework.hw5.mapper.TaskMapper;
import ru.seminar.homework.hw5.service.TaskService;

@RestController
@RequestMapping("${openapi.electronicQueuingProjectOpenAPI30.base-path:/api/v1}")
public class TaskController implements TaskApi {

    private final TaskService taskService;

    private final TaskMapper taskMapper;

    private final StatusEnumMapper statusEnumMapper;

    public TaskController(TaskService taskService, TaskMapper taskMapper, StatusEnumMapper statusEnumMapper) {
        this.taskService = taskService;
        this.taskMapper = taskMapper;
        this.statusEnumMapper = statusEnumMapper;
    }

    @Override
    public ResponseEntity<Task> addTask(){
            return ok(taskMapper.map(taskService.createTask()));
    }

    @Override
    public ResponseEntity<Void> deleteTask(String taskId){
        try {
            taskService.deleteTaskById(taskId);
        } catch (NotFoundException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Task> getTask() {
        return ok(taskMapper.map(taskService.getFirstTask()));
    }

    @Override
    public ResponseEntity<Task> getTaskById(String taskId) {
        return ok(taskMapper.map(taskService.getTask(taskId)));
    }

    @Override
    public ResponseEntity<Task> patchTaskById(String taskId, PatchTaskByIdRequest patchTaskByIdRequest){
        return ok(taskMapper.map(taskService.updateTask(taskId, statusEnumMapper.taskEnumToStatusEnumEntity(patchTaskByIdRequest.getStatus()))));
    }

    private ResponseEntity<Task> ok(Task task) {
        return ResponseEntity.ok(task);
    }

}
