package com.asteiner.edc.unit.Others;

import com.asteiner.edc.Others.GetTaskDto;
import com.asteiner.edc.Others.GetTaskHistoryDto;
import com.asteiner.edc.Others.TaskDtoObject;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
}
