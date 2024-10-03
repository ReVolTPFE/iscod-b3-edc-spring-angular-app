package com.asteiner.edc.Service;

import com.asteiner.edc.Entity.Project;
import com.asteiner.edc.Others.TaskDtoObject;

public interface ProjectService {
    void create(Project project, int userId);

    void addUser(int projectId, int userId, String email);

    void changeUserRole(int projectId, int userId, int userToChangeRoleId, String newRole);

    void createTask(int userId, int projectId, TaskDtoObject taskDtoObject);
}
