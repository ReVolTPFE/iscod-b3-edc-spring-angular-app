package com.asteiner.edc.Controller;

import com.asteiner.edc.Entity.Project;
import com.asteiner.edc.Others.GetProjectDto;
import com.asteiner.edc.Others.GetTaskDto;
import com.asteiner.edc.Others.TaskDtoObject;
import com.asteiner.edc.Service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user/{userId}/project")
public class ProjectController
{
    @Autowired
    private ProjectService projectService;

    @PostMapping("/create")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void createProject(@RequestBody Project project, @PathVariable("userId") int userId) {
        projectService.create(project, userId);
    }

    @PostMapping("/{projectId}/task/create")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void createTask(@RequestBody TaskDtoObject taskDtoObject, @PathVariable("userId") int userId, @PathVariable("projectId") int projectId) {
        projectService.createTask(userId, projectId, taskDtoObject);
    }

    @PostMapping("/{projectId}/addUser")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void addUserToProject(@RequestBody String email, @PathVariable("userId") int userId, @PathVariable("projectId") int projectId) {
        projectService.addUser(projectId, userId, email);
    }

    @GetMapping("/{projectId}/task/status/{status}")
    @ResponseStatus(code = HttpStatus.OK)
    public List<GetTaskDto> getTasksByStatus(@PathVariable("userId") int userId, @PathVariable("projectId") int projectId, @PathVariable("status") String status) {
        return projectService.getTasksByStatus(userId, projectId, status);
    }

    @PostMapping("/{projectId}/task/{taskId}/addUser")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void addUserToTask(@RequestBody String userToAddId, @PathVariable("userId") int userId, @PathVariable("projectId") int projectId, @PathVariable("taskId") int taskId) {
        projectService.addUserOnTask(userId, projectId, taskId, Integer.parseInt(userToAddId));
    }

    @PatchMapping("/{projectId}/task/{taskId}/edit")
    @ResponseStatus(code = HttpStatus.OK)
    public void editTask(@RequestBody TaskDtoObject taskDtoObject, @PathVariable("userId") int userId, @PathVariable("projectId") int projectId, @PathVariable("taskId") int taskId) {
        projectService.editTask(userId, projectId, taskId, taskDtoObject);
    }

    @GetMapping("/{projectId}/task/{taskId}")
    @ResponseStatus(code = HttpStatus.OK)
    public GetTaskDto getTask(@PathVariable("userId") int userId, @PathVariable("projectId") int projectId, @PathVariable("taskId") int taskId) {
        return projectService.getTask(userId, projectId, taskId);
    }

    @PutMapping("/{projectId}/changeUserRole/{userToChangeRoleId}")
    @ResponseStatus(code = HttpStatus.OK)
    public void changeUserRoleInProject(@RequestBody String newRole, @PathVariable("userId") int userId, @PathVariable("projectId") int projectId, @PathVariable("userToChangeRoleId") int userToChangeRoleId) {
        projectService.changeUserRole(projectId, userId, userToChangeRoleId, newRole);
    }

    @GetMapping("")
    @ResponseStatus(code = HttpStatus.OK)
    public List<GetProjectDto> getProjects(@PathVariable("userId") int userId) {
        return projectService.getProjects(userId);
    }
}
