package com.asteiner.edc.unit.Others;

import com.asteiner.edc.Others.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OthersTest {
    @Test
    void getTaskDto() {
        int id = 1;
        int projectId = 1;
        List<GetTaskHistoryDto> taskHistories = new ArrayList<>();

        GetTaskDto getTaskDto = new GetTaskDto();
        getTaskDto.setId(id);
        getTaskDto.setProjectId(projectId);
        getTaskDto.setTaskHistories(taskHistories);

        assertEquals(id, getTaskDto.getId());
        assertEquals(projectId, getTaskDto.getProjectId());
        assertEquals(taskHistories, getTaskDto.getTaskHistories());
    }

    @Test
    void getTaskHistoryDto() {
        int id = 1;
        int taskId = 1;
        String name = "Task";
        String description = "Description";
        String status = "ONGOING";
        String priority = "HIGH";
        LocalDate dueDate = LocalDate.now();
        LocalDate endedAt = LocalDate.now();

        GetTaskHistoryDto getTaskHistoryDto = new GetTaskHistoryDto();
        getTaskHistoryDto.setId(id);
        getTaskHistoryDto.setTaskId(taskId);
        getTaskHistoryDto.setName(name);
        getTaskHistoryDto.setDescription(description);
        getTaskHistoryDto.setStatus(status);
        getTaskHistoryDto.setPriority(priority);
        getTaskHistoryDto.setDueDate(dueDate);
        getTaskHistoryDto.setEndedAt(endedAt);

        assertEquals(id, getTaskHistoryDto.getId());
        assertEquals(taskId, getTaskHistoryDto.getTaskId());
        assertEquals(name, getTaskHistoryDto.getName());
        assertEquals(description, getTaskHistoryDto.getDescription());
        assertEquals(status, getTaskHistoryDto.getStatus());
        assertEquals(priority, getTaskHistoryDto.getPriority());
        assertEquals(dueDate, getTaskHistoryDto.getDueDate());
        assertEquals(endedAt, getTaskHistoryDto.getEndedAt());
    }

    @Test
    void taskDtoObject() {
        String name = "Task Name";
        String description = "Task Description";
        String status = "ONGOING";
        String priority = "HIGH";
        LocalDate dueDate = LocalDate.now();
        LocalDate endedAt = LocalDate.now();

        TaskDtoObject taskDtoObject = new TaskDtoObject();
        taskDtoObject.setName(name);
        taskDtoObject.setDescription(description);
        taskDtoObject.setStatus(status);
        taskDtoObject.setPriority(priority);
        taskDtoObject.setDueDate(dueDate);
        taskDtoObject.setEndedAt(endedAt);

        assertEquals(name, taskDtoObject.getName());
        assertEquals(description, taskDtoObject.getDescription());
        assertEquals(status, taskDtoObject.getStatus());
        assertEquals(priority, taskDtoObject.getPriority());
        assertEquals(dueDate, taskDtoObject.getDueDate());
        assertEquals(endedAt, taskDtoObject.getEndedAt());
    }

    @Test
    void getUserWithProjectRoleDto() {
        int userId = 1;
        String username = "Username";
        String email = "a@b.c";
        String role = "ADMIN";

        GetUserWithProjectRoleDto dto = new GetUserWithProjectRoleDto();
        dto.setUserId(1);
        dto.setUsername("Username");
        dto.setEmail("a@b.c");
        dto.setRole("ADMIN");

        assertEquals(userId, dto.getUserId());
        assertEquals(username, dto.getUsername());
        assertEquals(email, dto.getEmail());
        assertEquals(role, dto.getRole());
    }

    @Test
    void getUserDto() {
        int userId = 1;
        String username = "Username";
        String email = "a@b.c";

        GetUserDto dto = new GetUserDto();
        dto.setId(1);
        dto.setUsername("Username");
        dto.setEmail("a@b.c");

        assertEquals(userId, dto.getId());
        assertEquals(username, dto.getUsername());
        assertEquals(email, dto.getEmail());
    }

    @Test
    void getProjectDto() {
        GetUserWithProjectRoleDto userDto = new GetUserWithProjectRoleDto();
        userDto.setUserId(1);
        userDto.setUsername("Username");
        userDto.setEmail("a@b.c");
        userDto.setRole("ADMIN");

        int id = 1;
        String name = "Project 1";
        String description = "Project description";
        LocalDate startedAt = LocalDate.now();
        Set<GetUserWithProjectRoleDto> users = new HashSet<>();
        users.add(userDto);

        GetProjectDto dto = new GetProjectDto();
        dto.setId(1);
        dto.setName("Project 1");
        dto.setDescription("Project description");
        dto.setStartedAt(LocalDate.now());
        dto.addUser(userDto);

        assertEquals(id, dto.getId());
        assertEquals(name, dto.getName());
        assertEquals(description, dto.getDescription());
        assertEquals(startedAt, dto.getStartedAt());
        assertEquals(users, dto.getUsers());
    }
}
