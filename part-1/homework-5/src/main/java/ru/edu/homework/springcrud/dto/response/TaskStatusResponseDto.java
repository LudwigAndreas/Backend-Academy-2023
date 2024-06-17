package ru.edu.homework.springcrud.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TaskStatusResponseDto {
    private long taskId;

    private String status;

}
