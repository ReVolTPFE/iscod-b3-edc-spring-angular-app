package com.asteiner.edc.integration.Service;

import com.asteiner.edc.Entity.*;
import com.asteiner.edc.Others.*;
import com.asteiner.edc.Repository.TaskHistoryRepository;
import com.asteiner.edc.Repository.TaskRepository;
import com.asteiner.edc.Repository.UserProjectRoleRepository;
import com.asteiner.edc.Repository.UserRepository;
import com.asteiner.edc.Service.ProjectServiceImpl;
import com.asteiner.edc.Service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
@ExtendWith(SpringExtension.class)
public class ProjectServiceImplIT {
    @Autowired
    private ProjectServiceImpl projectService;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private UserProjectRoleRepository userProjectRoleRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskHistoryRepository taskHistoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    @DirtiesContext
    public void testCreateAndGetProject() {
        User user = new User();
        user.setUsername("Username");
        user.setEmail("user@example.com");
        user.setPassword("password");
        userService.create(user);

        String name = "Project";
        String description = "Description";
        LocalDate startedAt = LocalDate.now();

        Project project = new Project();
        project.setName(name);
        project.setDescription(description);
        project.setStartedAt(startedAt);
        projectService.create(project, 1);

        GetProjectDto savedProject = projectService.getProject(1, 1);
        assertEquals(1, savedProject.getId());
        assertEquals(name, savedProject.getName());
        assertEquals(description, savedProject.getDescription());
        assertEquals(startedAt, savedProject.getStartedAt());
        assertNotNull(savedProject.getUsers());
    }

    @Test
    @DirtiesContext
    public void testGetProjects() {
        User user = new User();
        user.setUsername("Username");
        user.setEmail("user@example.com");
        user.setPassword("password");
        userService.create(user);

        String name = "Project";
        String description = "Description";
        LocalDate startedAt = LocalDate.now();

        Project project = new Project();
        project.setName(name);
        project.setDescription(description);
        project.setStartedAt(startedAt);
        projectService.create(project, 1);

        List<GetProjectDto> savedProjects = projectService.getProjects(1);
        assertEquals(1, savedProjects.getFirst().getId());
        assertEquals(name, savedProjects.getFirst().getName());
        assertEquals(description, savedProjects.getFirst().getDescription());
        assertEquals(startedAt, savedProjects.getFirst().getStartedAt());
        assertNotNull(savedProjects.getFirst().getUsers());
    }

    @Test
    @DirtiesContext
    public void testAddUserToProject() {
        User user = new User();
        user.setUsername("Username");
        user.setEmail("user@example.com");
        user.setPassword("password");
        userService.create(user);

        String name = "Project";
        String description = "Description";
        LocalDate startedAt = LocalDate.now();

        Project project = new Project();
        project.setName(name);
        project.setDescription(description);
        project.setStartedAt(startedAt);
        projectService.create(project, 1);

        User userToAdd = new User();
        userToAdd.setUsername("Username2");
        userToAdd.setEmail("user2@example.com");
        userToAdd.setPassword("password");
        userService.create(userToAdd);

        projectService.addUser(1, 1, "user2@example.com");

        List<UserProjectRole> userProjectRoles = new ArrayList<>();
        userProjectRoleRepository.findAll().forEach(userProjectRoles::add);

        assertEquals(2, userProjectRoles.get(1).getUser().getId());
    }

    @Test
    @DirtiesContext
    public void testChangeUserRoleInProject() {
        User user = new User();
        user.setUsername("Username");
        user.setEmail("user@example.com");
        user.setPassword("password");
        userService.create(user);

        String name = "Project";
        String description = "Description";
        LocalDate startedAt = LocalDate.now();

        Project project = new Project();
        project.setName(name);
        project.setDescription(description);
        project.setStartedAt(startedAt);
        projectService.create(project, 1);

        projectService.changeUserRole(1, 1, 1, "MEMBER");

        List<UserProjectRole> userProjectRoles = new ArrayList<>();
        userProjectRoleRepository.findAll().forEach(userProjectRoles::add);

        assertEquals("MEMBER", userProjectRoles.getFirst().getRole());
    }

    @Test
    @DirtiesContext
    public void testCreateAndGetTask() {
        User user = new User();
        user.setUsername("Username");
        user.setEmail("user@example.com");
        user.setPassword("password");
        userService.create(user);

        Project project = new Project();
        project.setName("Project");
        project.setDescription("Description");
        project.setStartedAt(LocalDate.now());
        projectService.create(project, 1);

        Task task = new Task();
        task.setProject(project);
        task.addUser(user);
        task.setTaskHistories(new HashSet<>());
        taskRepository.save(task);

        GetTaskDto getTaskDto = projectService.getTask(1, 1, 1);

        assertNotNull(getTaskDto);
        assertEquals(1, getTaskDto.getId());
    }

    @Test
    @DirtiesContext
    public void testEditTask() {
        User user = new User();
        user.setUsername("Username");
        user.setEmail("user@example.com");
        user.setPassword("password");
        userService.create(user);

        Project project = new Project();
        project.setName("Project");
        project.setDescription("Description");
        project.setStartedAt(LocalDate.now());
        projectService.create(project, 1);

        Task task = new Task();
        task.setProject(project);
        task.addUser(user);
        task.setTaskHistories(new HashSet<>());
        taskRepository.save(task);

        TaskDtoObject dto = new TaskDtoObject();
        dto.setName("Name2");
        dto.setDescription("Description2");
        dto.setStatus("TODO");
        dto.setPriority("HIGH");
        dto.setDueDate(LocalDate.now());
        dto.setEndedAt(LocalDate.now());

        projectService.editTask(1, 1, 1, dto);

        List<TaskHistory> taskHistories = new ArrayList<>();
        taskHistoryRepository.findAll().forEach(taskHistories::add);

        assertEquals("Name2", taskHistories.getFirst().getName());
    }

    @Test
    @DirtiesContext
    public void testEditAnotherTask() {
        User user = new User();
        user.setUsername("Username");
        user.setEmail("user@example.com");
        user.setPassword("password");
        userService.create(user);

        Project project = new Project();
        project.setName("Project");
        project.setDescription("Description");
        project.setStartedAt(LocalDate.now());
        projectService.create(project, 1);

        Task task = new Task();
        task.setProject(project);
        task.addUser(user);
        task.setTaskHistories(new HashSet<>());
        taskRepository.save(task);

        TaskHistory history = new TaskHistory();
        history.setName("Name");
        history.setDescription("Description");
        history.setStatus("TODO");
        history.setPriority("HIGH");
        history.setDueDate(LocalDate.now());
        history.setEndedAt(LocalDate.now());
        history.setTask(task);
        taskHistoryRepository.save(history);

        TaskDtoObject dto = new TaskDtoObject();
        dto.setName(null);
        dto.setDescription(null);
        dto.setStatus(null);
        dto.setPriority(null);
        dto.setDueDate(null);
        dto.setEndedAt(null);

        projectService.editTask(1, 1, 1, dto);

        List<TaskHistory> taskHistories = new ArrayList<>();
        taskHistoryRepository.findAll().forEach(taskHistories::add);

        assertEquals("Name", taskHistories.getFirst().getName());
    }

    @Test
    @DirtiesContext
    public void testGetTasks() {
        User user = new User();
        user.setUsername("Username");
        user.setEmail("user@example.com");
        user.setPassword("password");
        userService.create(user);

        Project project = new Project();
        project.setName("Project");
        project.setDescription("Description");
        project.setStartedAt(LocalDate.now());
        projectService.create(project, 1);

        Task task = new Task();
        task.setProject(project);
        task.addUser(user);
        task.setTaskHistories(new HashSet<>());
        taskRepository.save(task);

        TaskHistory history = new TaskHistory();
        history.setName("Name");
        history.setDescription("Description");
        history.setStatus("TODO");
        history.setPriority("HIGH");
        history.setDueDate(LocalDate.now());
        history.setEndedAt(LocalDate.now());
        history.setTask(task);
        taskHistoryRepository.save(history);

        TaskDtoObject dto = new TaskDtoObject();
        dto.setName("Name2");
        dto.setDescription("Description2");
        dto.setStatus("TODO");
        dto.setPriority("HIGH");
        dto.setDueDate(LocalDate.now());
        dto.setEndedAt(null);

        List<GetTaskDto> getTaskDtos = projectService.getTasks(1, 1);

        assertEquals(1, getTaskDtos.getFirst().getId());
    }

    @Test
    @DirtiesContext
    public void testGetTasksByStatus() {
        User user = new User();
        user.setUsername("Username");
        user.setEmail("user@example.com");
        user.setPassword("password");
        userService.create(user);

        Project project = new Project();
        project.setName("Project");
        project.setDescription("Description");
        project.setStartedAt(LocalDate.now());
        projectService.create(project, 1);

        Task task = new Task();
        task.setProject(project);
        task.addUser(user);
        task.setTaskHistories(new HashSet<>());
        taskRepository.save(task);

        TaskHistory history = new TaskHistory();
        history.setName("Name");
        history.setDescription("Description");
        history.setStatus("TODO");
        history.setPriority("HIGH");
        history.setDueDate(LocalDate.now());
        history.setEndedAt(LocalDate.now());
        history.setTask(task);
        taskHistoryRepository.save(history);

        List<GetTaskDto> getTaskDtos = projectService.getTasksByStatus(1, 1, "TODO");

        assertEquals(1, getTaskDtos.getFirst().getId());
    }
}
