package com.asteiner.edc.Service;

import com.asteiner.edc.Entity.Project;

public interface ProjectService {
    void create(Project project, int userId);

    void addUser(int projectId, int userId, String email);
}
