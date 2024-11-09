package com.asteiner.edc.unit.Entity;

import com.asteiner.edc.Entity.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EntityTest {
    @Test
    void user() {
        String email = "a@b.c";
        String password = "azerty";
        String username = "ABC";

        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setUsername(username);

        Set<Task> listTasks = new HashSet<>();
        Task task = new Task();
        task.addUser(user);
        listTasks.add(task);

        assertEquals(password, user.getEmail());
        assertEquals(password, user.getPassword());
        assertEquals(username, user.getUsername());
        assertEquals(listTasks, user.getTasks());
    }

    @Test
    void userProjectRole() {
        User user = new User();
        Project project = new Project();
        String role = "MEMBER";

        UserProjectRole userProjectRole = new UserProjectRole();
        userProjectRole.setUser(user);
        userProjectRole.setProject(project);
        userProjectRole.setRole(role);

        assertEquals(user, userProjectRole.getUser());
        assertEquals(project, userProjectRole.getProject());
        assertEquals(role, userProjectRole.getRole());
    }

    @Test
    void project() {
        String name = "Project";
        String description = "Description";
        LocalDate startedAt = LocalDate.now();

        Set<UserProjectRole> listUserProjectRole = new HashSet<>();
        UserProjectRole userProjectRole = new UserProjectRole();
        listUserProjectRole.add(userProjectRole);

        Set<Task> listTasks = new HashSet<>();
        Task task = new Task();
        listTasks.add(task);

        Project project = new Project();
        project.setName(name);
        project.setDescription(description);
        project.setStartedAt(startedAt);
        project.setUserProjectRoles(listUserProjectRole);
        project.setTasks(listTasks);

        assertEquals(name, project.getName());
        assertEquals(description, project.getDescription());
        assertEquals(startedAt, project.getStartedAt());
        assertEquals(listUserProjectRole, project.getUserProjectRoles());
        assertEquals(listTasks, project.getTasks());
    }

    @Test
    void task() {
        Set<TaskHistory> listTaskHistories = new HashSet<>();
        TaskHistory taskHistory = new TaskHistory();
        listTaskHistories.add(taskHistory);

        Set<User> listUsers = new HashSet<>();
        User user = new User();
        listUsers.add(user);

        Project project = new Project();

        Task task = new Task();
        task.setTaskHistories(listTaskHistories);
        task.addUser(user);
        task.setProject(project);

        assertEquals(listTaskHistories, task.getTaskHistories());
        assertEquals(listUsers, task.getUsers());
        assertEquals(project, task.getProject());
    }

    @Test
    void taskHistory() {
        String name = "Task Name";
        String description = "Task Description";
        String status = "ONGOING";
        String priority = "HIGH";
        LocalDate dueDate = LocalDate.now();
        LocalDate endedAt = LocalDate.now();
        Task task = new Task();

        TaskHistory taskHistory = new TaskHistory();
        taskHistory.setName(name);
        taskHistory.setDescription(description);
        taskHistory.setStatus(status);
        taskHistory.setPriority(priority);
        taskHistory.setDueDate(dueDate);
        taskHistory.setEndedAt(endedAt);
        taskHistory.setTask(task);

        assertEquals(name, taskHistory.getName());
        assertEquals(description, taskHistory.getDescription());
        assertEquals(status, taskHistory.getStatus());
        assertEquals(priority, taskHistory.getPriority());
        assertEquals(dueDate, taskHistory.getDueDate());
        assertEquals(endedAt, taskHistory.getEndedAt());
        assertEquals(task, taskHistory.getTask());
    }
}
