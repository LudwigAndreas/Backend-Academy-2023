package ru.seminar.homework.hw5.controller.v1;

import io.reflectoring.api.TimesApi;
import io.reflectoring.model.GetTaskStatusAverageTimes200Response;
import io.reflectoring.model.GetTaskStatusTimes200Response;
import io.reflectoring.model.TaskEnum;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.seminar.homework.hw5.mapper.StatusEnumMapper;
import ru.seminar.homework.hw5.mapper.TaskMapper;
import ru.seminar.homework.hw5.service.TaskService;
import ru.seminar.homework.hw5.service.TimesService;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Map;


@RestController
@RequestMapping("${openapi.electronicQueuingProjectOpenAPI30.base-path:/api/v1}")
public class TimesController implements TimesApi {
    
    private final TimesService timesService;
    private final TaskService taskService;

    private final TaskMapper taskMapper;

    private final StatusEnumMapper statusEnumMapper;

    public TimesController(TimesService timesService, TaskService taskService, TaskMapper taskMapper, StatusEnumMapper statusEnumMapper) {
        this.timesService = timesService;
        this.taskService = taskService;
        this.taskMapper = taskMapper;
        this.statusEnumMapper = statusEnumMapper;
    }


    @Override
    public ResponseEntity<Map<String, OffsetDateTime>> getTaskAverageTimes() {
        return ResponseEntity.ok(taskMapper.offsetDateTimeToStringMap(timesService.getAverageTime()));
    }

    @Override
    public ResponseEntity<GetTaskStatusAverageTimes200Response> getTaskStatusAverageTimes(TaskEnum status) {
        GetTaskStatusAverageTimes200Response response = new GetTaskStatusAverageTimes200Response();
        OffsetDateTime offsetDateTime = OffsetDateTime.ofInstant(
                Instant.ofEpochMilli(timesService.getAverageTimeByStatus(statusEnumMapper.taskEnumToStatusEnumEntity(status)))
                , ZoneId.systemDefault()
        );
        response.setAverageTime(offsetDateTime);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<GetTaskStatusTimes200Response> getTaskStatusTimes(String taskId, TaskEnum status) {
        GetTaskStatusTimes200Response response = new GetTaskStatusTimes200Response();
        OffsetDateTime offsetDateTime = null;
        offsetDateTime = OffsetDateTime.ofInstant(
                Instant.ofEpochMilli(timesService.getTimeByTaskAndStatus(taskId, statusEnumMapper.taskEnumToStatusEnumEntity(status))),
                ZoneId.systemDefault()
        );
        response.time(offsetDateTime);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Map<String, OffsetDateTime>> getTaskTimes(String taskId) {
        return ResponseEntity.ok(taskMapper.offsetDateTimeToStringMap(timesService.getTimeByTask(taskId)));
    }
}
