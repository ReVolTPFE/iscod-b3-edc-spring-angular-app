package com.asteiner.edc.Others;

import com.asteiner.edc.Entity.User;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class GetProjectDto {
    private int id;
    private String name;
    private String description;
    private LocalDate startedAt;
    private Set<GetUserWithProjectRoleDto> users = new HashSet<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public LocalDate getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(LocalDate startedAt) {
        this.startedAt = startedAt;
    }

    public Set<GetUserWithProjectRoleDto> getUsers() {
        return users;
    }

    public void addUser(GetUserWithProjectRoleDto user) {
        if (users == null) {
            users = new HashSet<>();
        }
        users.add(user);
    }
}
