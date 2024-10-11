package com.asteiner.edc.Service;

import com.asteiner.edc.Entity.Project;
import com.asteiner.edc.Others.GetProjectDto;
import com.asteiner.edc.Others.GetTaskDto;
import com.asteiner.edc.Others.TaskDtoObject;

import java.util.List;

public interface ProjectService {
    void create(Project project, int userId);

    List<GetProjectDto> getProjects(int userId);

    GetProjectDto getProject(int userId, int projectId);

    void addUser(int projectId, int userId, String email);

    void changeUserRole(int projectId, int userId, int userToChangeRoleId, String newRole);

    void createTask(int userId, int projectId, TaskDtoObject taskDtoObject);

    void addUserOnTask(int userId, int projectId, int taskId, int userToAddId);

    void editTask(int userId, int projectId, int taskId, TaskDtoObject taskDtoObject);

    List<GetTaskDto> getTasks(int userId, int projectId);

    GetTaskDto getTask(int userId, int projectId, int taskId);

    List<GetTaskDto> getTasksByStatus(int userId, int projectId, String status);
}
