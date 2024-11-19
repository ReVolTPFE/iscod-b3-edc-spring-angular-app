package com.asteiner.edc.swaggerDocsDtos;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Schema(description = "Request body for project creation")
public class CreateProjectRequest {
    @Schema(description = "Name of the project", example = "Super great project name")
    private String name;

    @Schema(description = "Description of the project", example = "This project is aimed for this, this and this.")
    private String description;

    @Schema(description = "Starting date of the project", example = "2024-11-19")
    private LocalDate startedAt;

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

    public LocalDate getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(LocalDate startedAt) {
        this.startedAt = startedAt;
    }
}
