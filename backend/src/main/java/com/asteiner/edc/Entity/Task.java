package com.asteiner.edc.Entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Table(name = "task")
@Entity
public class Task
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL)
    @OrderBy("id ASC")
    private Set<TaskHistory> taskHistories;

    @ManyToMany(mappedBy = "tasks")
    private Set<User> users = new HashSet<>();

    public int getId() {
        return id;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Set<TaskHistory> getTaskHistories() {
        return taskHistories;
    }

    public void setTaskHistories(Set<TaskHistory> taskHistories) {
        this.taskHistories = taskHistories;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void addUser(User user) {
        if (users == null) {
            users = new HashSet<>();
        }
        users.add(user);
        user.getTasks().add(this);
    }
}
