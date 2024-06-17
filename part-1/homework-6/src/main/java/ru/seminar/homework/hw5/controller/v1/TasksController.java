package ru.seminar.homework.hw5.controller.v1;

import io.reflectoring.api.TasksApi;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.seminar.homework.hw5.model.StatusEnumEntity;
import ru.seminar.homework.hw5.model.TaskEntity;
import ru.seminar.homework.hw5.service.TaskService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("${openapi.electronicQueuingProjectOpenAPI30.base-path:/api/v1}")
public class TasksController implements TasksApi {

    private final TaskService taskService;

    public TasksController(TaskService taskService) {
        this.taskService = taskService;
    }

    @Override
    public ResponseEntity<Map<String, List<String>>> getTasks() {
        Map<StatusEnumEntity, List<TaskEntity>> tasks = taskService.getAllIncompleteTasks();
        Map<String, List<String>> response = new HashMap<>();
        for (StatusEnumEntity status : StatusEnumEntity.values()) {
            response.put(status.toString(), tasks.getOrDefault(status, new ArrayList<>()).stream().map(TaskEntity::getId).toList());
        }
        return ResponseEntity.ok(response);
    }
}
