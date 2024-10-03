package com.asteiner.edc.Others;

import java.time.LocalDate;

public class GetTaskHistoryDto {
    private int id;
    private int taskId;
    private String name;
    private String description;
    private String status;
    private String priority;
    private LocalDate dueDate;
    private LocalDate endedAt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDate getEndedAt() {
        return endedAt;
    }

    public void setEndedAt(LocalDate endedAt) {
        this.endedAt = endedAt;
    }
}
