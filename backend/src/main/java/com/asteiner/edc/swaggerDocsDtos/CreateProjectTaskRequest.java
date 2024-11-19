package com.asteiner.edc.swaggerDocsDtos;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Schema(description = "Request body for task creation")
public class CreateProjectTaskRequest {
    @Schema(description = "Name of the task", example = "Task 1")
    private String name;

    @Schema(description = "Description of the task", example = "Task 1 is used to do this")
    private String description;

    @Schema(description = "Status of the task", example = "TODO")
    private String status;

    @Schema(description = "Priority of the task", example = "HIGH")
    private String priority;

    @Schema(description = "Due date of the task", example = "2024-11-19")
    private LocalDate dueDate;

    @Schema(description = "Ending date of the task", example = "2024-11-19")
    private LocalDate endedAt;

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
