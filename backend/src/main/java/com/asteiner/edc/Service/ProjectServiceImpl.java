package com.asteiner.edc.Service;

import com.asteiner.edc.Entity.*;
import com.asteiner.edc.Exception.NotFoundException;
import com.asteiner.edc.Others.GetProjectDto;
import com.asteiner.edc.Others.GetTaskDto;
import com.asteiner.edc.Others.GetTaskHistoryDto;
import com.asteiner.edc.Others.TaskDtoObject;
import com.asteiner.edc.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserProjectRoleRepository userProjectRoleRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskHistoryRepository taskHistoryRepository;

    @Autowired
    private EmailService emailService;

    @Override
    public void create(Project project, int userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));

        projectRepository.save(project);

        UserProjectRole userProjectRole = new UserProjectRole();
        userProjectRole.setUser(user);
        userProjectRole.setProject(project);
        userProjectRole.setRole("ADMIN");

        userProjectRoleRepository.save(userProjectRole);
    }

    @Override
    public List<GetProjectDto> getProjects(int userId) {
        List<Project> projects = projectRepository.findAllProjectsByUserId(userId);

        List<GetProjectDto> projectsDto = new ArrayList<>();

        for (Project project : projects) {
            GetProjectDto projectDto = new GetProjectDto();
            projectDto.setId(project.getId());
            projectDto.setName(project.getName());
            projectDto.setDescription(project.getDescription());
            projectDto.setStartedAt(project.getStartedAt());

            projectsDto.add(projectDto);
        }

        return projectsDto;
    }

    @Override
    public void addUser(int projectId, int userId, String email) {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new NotFoundException("Project not found"));

        checkUserAdminInProject(userId, project);

        User newUser = userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("No user was found with this email"));

        //if the new user is already in the project we don't do anything
        Optional<UserProjectRole> newUserAlreadyInProject = userProjectRoleRepository.findByUserAndProject(newUser, project);

        if (newUserAlreadyInProject.isPresent()) {
            throw new IllegalStateException("User already in project");
        }

        UserProjectRole newUserProjectRole = new UserProjectRole();
        newUserProjectRole.setUser(newUser);
        newUserProjectRole.setProject(project);
        newUserProjectRole.setRole("MEMBER");

        userProjectRoleRepository.save(newUserProjectRole);
    }

    @Override
    public void changeUserRole(int projectId, int userId, int userToChangeRoleId, String newRole) {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new NotFoundException("Project not found"));

        checkUserAdminInProject(userId, project);

        User userToChangeRole = userRepository.findById(userToChangeRoleId).orElseThrow(() -> new NotFoundException("User to change role not found"));
        UserProjectRole userToChangeRoleProjectRole = userProjectRoleRepository.findByUserAndProject(userToChangeRole, project).orElseThrow(() -> new NotFoundException("User to change role isn't in this project"));

        userToChangeRoleProjectRole.setRole(newRole);
        userProjectRoleRepository.save(userToChangeRoleProjectRole);
    }

    @Override
    public void createTask(int userId, int projectId, TaskDtoObject taskDtoObject) {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new NotFoundException("Project not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));

        checkUserMemberInProject(user, project);

        Task task = new Task();
        task.setProject(project);
        task.addUser(user);
        taskRepository.save(task);

        TaskHistory taskHistory = new TaskHistory();
        taskHistory.setTask(task);
        taskHistory.setName(taskDtoObject.getName());
        taskHistory.setDescription(taskDtoObject.getDescription());
        taskHistory.setPriority(taskDtoObject.getPriority());
        taskHistory.setStatus(taskDtoObject.getStatus());
        taskHistory.setDueDate(taskDtoObject.getDueDate());
        taskHistoryRepository.save(taskHistory);

        emailService.sendEmail(user.getEmail(), "New task assigned", "You were assigned to a new task.");
    }

    @Override
    public void addUserOnTask(int userId, int projectId, int taskId, int userToAddId) {
        //Check first if user making the request is in project
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new NotFoundException("Project not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
        checkUserMemberInProject(user, project);

        //Then check if user to add is in project
        User userToAdd = userRepository.findById(userToAddId).orElseThrow(() -> new NotFoundException("User to add not found"));
        checkUserInProject(userToAdd, project);

        //Then add user to task
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new NotFoundException("Task not found"));
        task.addUser(userToAdd);
        taskRepository.save(task);

        emailService.sendEmail(userToAdd.getEmail(), "New task assigned", "You were assigned to a new task.");
    }

    @Override
    public void editTask(int userId, int projectId, int taskId, TaskDtoObject taskDtoObject) {
        //Check first if user making the request is in project
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new NotFoundException("Project not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
        checkUserMemberInProject(user, project);

        Task task = taskRepository.findById(taskId).orElseThrow(() -> new NotFoundException("Task not found"));
        TaskHistory latestTaskHistory = taskHistoryRepository.findLatestTaskHistoryByTaskId(task.getId());
        TaskHistory taskHistory = new TaskHistory();
        taskHistory.setTask(task);

        if (taskDtoObject.getName() != null) {
            taskHistory.setName(taskDtoObject.getName());
        } else {
            taskHistory.setName(latestTaskHistory.getName());
        }

        if (taskDtoObject.getDescription() != null) {
            taskHistory.setDescription(taskDtoObject.getDescription());
        } else {
            taskHistory.setDescription(latestTaskHistory.getDescription());
        }

        if (taskDtoObject.getStatus() != null) {
            taskHistory.setStatus(taskDtoObject.getStatus());
        } else {
            taskHistory.setStatus(latestTaskHistory.getStatus());
        }

        if (taskDtoObject.getPriority() != null) {
            taskHistory.setPriority(taskDtoObject.getPriority());
        } else {
            taskHistory.setPriority(latestTaskHistory.getPriority());
        }

        if (taskDtoObject.getDueDate() != null) {
            taskHistory.setDueDate(taskDtoObject.getDueDate());
        } else {
            taskHistory.setDueDate(latestTaskHistory.getDueDate());
        }

        if (taskDtoObject.getEndedAt() != null) {
            taskHistory.setEndedAt(taskDtoObject.getEndedAt());
        } else {
            taskHistory.setEndedAt(latestTaskHistory.getEndedAt());
        }

        taskHistoryRepository.save(taskHistory);
    }

    @Override
    public GetTaskDto getTask(int userId, int projectId, int taskId) {
        //Check first if user making the request is in project
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new NotFoundException("Project not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
        checkUserInProject(user, project);

        Task task = taskRepository.findById(taskId).orElseThrow(() -> new NotFoundException("Task not found"));

        GetTaskDto taskDto = new GetTaskDto();
        taskDto.setId(task.getId());
        taskDto.setProjectId(task.getProject().getId());

        List<GetTaskHistoryDto> taskHistoryDtos = task.getTaskHistories().stream()
            .map(history -> {
                GetTaskHistoryDto dto = new GetTaskHistoryDto();
                dto.setId(history.getId());
                dto.setName(history.getName());
                dto.setDescription(history.getDescription());
                dto.setPriority(history.getPriority());
                dto.setStatus(history.getStatus());
                dto.setDueDate(history.getDueDate());
                dto.setEndedAt(history.getEndedAt());
                dto.setTaskId(history.getTask().getId());
                return dto;
            })
            .collect(Collectors.toList())
        ;

        taskDto.setTaskHistories(taskHistoryDtos);

        return taskDto;
    }

    @Override
    public List<GetTaskDto> getTasksByStatus(int userId, int projectId, String status) {
        //Check first if user making the request is in project
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new NotFoundException("Project not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
        checkUserInProject(user, project);

        List<Task> tasks = taskRepository.findTasksByStatus(status);

        List<GetTaskDto> tasksDto = new ArrayList<>();

        for (Task task : tasks) {
            GetTaskDto taskDto = new GetTaskDto();
            taskDto.setId(task.getId());
            taskDto.setProjectId(task.getProject().getId());

            List<GetTaskHistoryDto> taskHistoryDtos = task.getTaskHistories().stream()
                .map(history -> {
                    GetTaskHistoryDto dto = new GetTaskHistoryDto();
                    dto.setId(history.getId());
                    dto.setName(history.getName());
                    dto.setDescription(history.getDescription());
                    dto.setPriority(history.getPriority());
                    dto.setStatus(history.getStatus());
                    dto.setDueDate(history.getDueDate());
                    dto.setEndedAt(history.getEndedAt());
                    dto.setTaskId(history.getTask().getId());
                    return dto;
                })
                .collect(Collectors.toList())
            ;

            taskDto.setTaskHistories(taskHistoryDtos);

            tasksDto.add(taskDto);
        }

        return tasksDto;
    }

    private void checkUserAdminInProject(int userId, Project project) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));

        //if the user isn't in the project, we don't allow him to do anything
        UserProjectRole userProjectRole = userProjectRoleRepository.findByUserAndProject(user, project).orElseThrow(() -> new NotFoundException("This user isn't in this project"));

        //if the user isn't admin, he can't do something else
        if (!Objects.equals(userProjectRole.getRole(), "ADMIN")) throw new IllegalStateException("User is not admin in project");
    }

    private void checkUserMemberInProject(User user, Project project) {
        //if the user isn't in the project, we don't allow him to do anything
        UserProjectRole userProjectRole = userProjectRoleRepository.findByUserAndProject(user, project).orElseThrow(() -> new NotFoundException("This user isn't in this project"));
        //if the user isn't admin, he can't do something else
        boolean invalidRole = true;

        if (Objects.equals(userProjectRole.getRole(), "ADMIN") || Objects.equals(userProjectRole.getRole(), "MEMBER")) {
            invalidRole = false;
        }

        if (invalidRole) throw new IllegalStateException("User is not admin or member in project");
    }

    private void checkUserInProject(User user, Project project) {
        //if the user isn't in the project, we don't allow him to do anything
        userProjectRoleRepository.findByUserAndProject(user, project).orElseThrow(() -> new NotFoundException("This user isn't in this project"));
    }
}
