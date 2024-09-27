package com.asteiner.edc.Entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Set;

@Table(name = "project")
@Entity
public class Project
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String description;

    @Column
    private LocalDate startedAt;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private Set<UserProjectRole> userProjectRoles;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private Set<Task> tasks;

    public int getId() {
        return id;
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

    public Set<UserProjectRole> getUserProjectRoles() {
        return userProjectRoles;
    }

    public void setUserProjectRoles(Set<UserProjectRole> userProjectRoles) {
        this.userProjectRoles = userProjectRoles;
    }

    public Set<Task> getTasks() {
        return tasks;
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }
}
