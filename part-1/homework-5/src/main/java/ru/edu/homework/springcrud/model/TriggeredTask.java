package ru.edu.homework.springcrud.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TriggeredTask {

    private Long id;
    private TaskStatus status;
    private Long sourceCategoryId;
    private Long targetCategoryId;

    public TriggeredTask(Long sourceCategoryId, Long targetCategoryId) {
        this.id = null;
        this.status = TaskStatus.WAITING;
        this.sourceCategoryId = sourceCategoryId;
        this.targetCategoryId = targetCategoryId;
    }

}
