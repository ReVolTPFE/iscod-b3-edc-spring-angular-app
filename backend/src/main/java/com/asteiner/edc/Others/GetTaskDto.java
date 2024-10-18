package com.asteiner.edc.Others;

import java.util.List;

public class GetTaskDto {
    private int id;
    private int projectId;
    private List<GetTaskHistoryDto> taskHistories;
    private List<GetUserDto> users;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public List<GetTaskHistoryDto> getTaskHistories() {
        return taskHistories;
    }

    public void setTaskHistories(List<GetTaskHistoryDto> taskHistories) {
        this.taskHistories = taskHistories;
    }

    public List<GetUserDto> getUsers() {
        return users;
    }

    public void setUsers(List<GetUserDto> users) {
        this.users = users;
    }
}