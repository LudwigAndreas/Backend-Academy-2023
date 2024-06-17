package ru.seminar.homework.hw5.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Data
@AllArgsConstructor
public class TaskEntity {

    @Setter
    private String id;

    private StatusEnumEntity status;

    private Map<StatusEnumEntity, Long> times;

    public TaskEntity() {
        this.id = UUID.randomUUID().toString().substring(0, 6).toUpperCase();
        this.status = StatusEnumEntity.NEW;
        this.times = new HashMap<>();
    }

    public TaskEntity(TaskEntity task) {
        this.id = task.getId();
        this.status = task.getStatus();
        this.times = task.getTimes();
    }

    public void setStatus(StatusEnumEntity status) {
        if (status == this.status.nextStatus() || status == this.status.prevStatus() || status == StatusEnumEntity.CANCEL) {
            this.status = status;
            this.times.put(status, System.currentTimeMillis());
        } else {
            throw new IllegalArgumentException("Unable to change status" + this.status + " to " + status);
        }
    }

    public void nextStatus() {
        this.status = this.status.nextStatus();
        this.times.put(status, System.currentTimeMillis());
    }

    public void prevStatus() {
        this.status = this.status.prevStatus();
        this.times.put(status, System.currentTimeMillis());
    }

}
