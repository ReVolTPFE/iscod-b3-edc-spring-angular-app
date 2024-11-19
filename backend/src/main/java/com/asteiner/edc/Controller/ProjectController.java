package com.asteiner.edc.Controller;

import com.asteiner.edc.Entity.Project;
import com.asteiner.edc.Others.GetProjectDto;
import com.asteiner.edc.Others.GetTaskDto;
import com.asteiner.edc.Others.TaskDtoObject;
import com.asteiner.edc.Service.ProjectService;
import com.asteiner.edc.swaggerDocsDtos.CreateProjectRequest;
import com.asteiner.edc.swaggerDocsDtos.CreateProjectTaskRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Create a new project for a user",
            description = "Creates a new project associated to a user id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Project created successfully"),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    })
    @PostMapping("/create")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void createProject(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Project details", required = true,
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CreateProjectRequest.class)))
            @RequestBody Project project,

            @Parameter(description = "Id of the user creating the project", example = "1", required = true)
            @PathVariable("userId") int userId) {
        projectService.create(project, userId);
    }

    @Operation(summary = "Create a new task in a project",
            description = "Creates a new task associated to a project")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Task created successfully"),
            @ApiResponse(responseCode = "404", description = "User or project not found", content = @Content)
    })
    @PostMapping("/{projectId}/task/create")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void createTask(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Task details", required = true,
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CreateProjectTaskRequest.class)))
            @RequestBody TaskDtoObject taskDtoObject,

            @Parameter(description = "Id of the user creating the task", example = "1", required = true)
            @PathVariable("userId") int userId,

            @Parameter(description = "Id of the project of the task", example = "1", required = true)
            @PathVariable("projectId") int projectId) {
        projectService.createTask(userId, projectId, taskDtoObject);
    }

    @Operation(summary = "Add a user in a project",
            description = "Adds a new user to a project")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User added successfully"),
            @ApiResponse(responseCode = "404", description = "User or project not found", content = @Content)
    })
    @PostMapping("/{projectId}/addUser")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void addUserToProject(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "User to add email", required = true,
                    content = @Content(mediaType = "application/json", schema = @Schema(type = "string", example = "user@example.com")))
            @RequestBody String email,

            @Parameter(description = "Id of the user adding another user", example = "1", required = true)
            @PathVariable("userId") int userId,

            @Parameter(description = "Id of the project", example = "1", required = true)
            @PathVariable("projectId") int projectId) {
        projectService.addUser(projectId, userId, email);
    }

    @Operation(summary = "Retrieve all tasks with conditions",
            description = "Retrieves all tasks of projects where the user is in")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All tasks for this status successfully retrieved", content = {
                    @Content(mediaType = "application/json", schema = @Schema(
                            type = "array"),
                            examples = @ExampleObject(value = """
                                [
                                    {
                                        "id": 1,
                                        "projectId": 1,
                                        "taskHistories": [
                                            {
                                                "id": 1,
                                                "taskId": 1,
                                                "name": "Develop User Stories Module",
                                                "description": "Design and implement a module where users can create, view, and prioritize user stories for each sprint.",
                                                "status": "TODO",
                                                "priority": "LOW",
                                                "dueDate": "2024-11-30",
                                                "endedAt": null
                                            }
                                        ]
                                    },
                                    {
                                        "id": 2,
                                        "projectId": 2,
                                        "taskHistories": [
                                            {
                                                "id": 2,
                                                "taskId": 2,
                                                "name": "Set Up Code Quality Report Generation",
                                                "description": "Develop a feature to generate and export code quality reports for each project repository.",
                                                "status": "TODO",
                                                "priority": "MEDIUM",
                                                "dueDate": "2024-11-23",
                                                "endedAt": null
                                            }
                                        ]
                                    }
                                ]
                    """))
            }),
            @ApiResponse(responseCode = "404", description = "User, project or status not found", content = @Content)
    })
    @GetMapping("/{projectId}/task/status/{status}")
    @ResponseStatus(code = HttpStatus.OK)
    public List<GetTaskDto> getTasksByStatus(
            @Parameter(description = "Id of the user", example = "1", required = true)
            @PathVariable("userId") int userId,

            @Parameter(description = "Id of the project", example = "1", required = true)
            @PathVariable("projectId") int projectId,

            @Parameter(description = "Status of tasks to retrieve", example = "TODO", required = true)
            @PathVariable("status") String status) {
        return projectService.getTasksByStatus(userId, projectId, status);
    }

    @Operation(summary = "Add user to a task",
            description = "Assigns user and send a mail to the user when he is assigned to a task")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User assigned successfully"),
            @ApiResponse(responseCode = "404", description = "User, project or task not found", content = @Content)
    })
    @PostMapping("/{projectId}/task/{taskId}/addUser")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void addUserToTask(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "User id to add to the task", required = true,
                    content = @Content(mediaType = "application/json", schema = @Schema(type = "string", example = "1")))
            @RequestBody String userToAddId,

            @Parameter(description = "Id of the user", example = "1", required = true)
            @PathVariable("userId") int userId,

            @Parameter(description = "Id of the project", example = "1", required = true)
            @PathVariable("projectId") int projectId,

            @Parameter(description = "Id of the task", example = "1", required = true)
            @PathVariable("taskId") int taskId) {
        projectService.addUserOnTask(userId, projectId, taskId, Integer.parseInt(userToAddId));
    }

    @Operation(summary = "Edit a task",
            description = "Edits a task details and creates a new task history as current task data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task edited successfully"),
            @ApiResponse(responseCode = "404", description = "User, project or task not found", content = @Content)
    })
    @PatchMapping("/{projectId}/task/{taskId}/edit")
    @ResponseStatus(code = HttpStatus.OK)
    public void editTask(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Task details", required = true,
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CreateProjectTaskRequest.class)))
            @RequestBody TaskDtoObject taskDtoObject,

            @Parameter(description = "Id of the user", example = "1", required = true)
            @PathVariable("userId") int userId,

            @Parameter(description = "Id of the project", example = "1", required = true)
            @PathVariable("projectId") int projectId,

            @Parameter(description = "Id of the task", example = "1", required = true)
            @PathVariable("taskId") int taskId) {
        projectService.editTask(userId, projectId, taskId, taskDtoObject);
    }

    @GetMapping("/{projectId}/task/{taskId}")
    @ResponseStatus(code = HttpStatus.OK)
    public GetTaskDto getTask(@PathVariable("userId") int userId, @PathVariable("projectId") int projectId, @PathVariable("taskId") int taskId) {
        return projectService.getTask(userId, projectId, taskId);
    }

    @GetMapping("/{projectId}/task")
    @ResponseStatus(code = HttpStatus.OK)
    public List<GetTaskDto> getTasks(@PathVariable("userId") int userId, @PathVariable("projectId") int projectId) {
        return projectService.getTasks(userId, projectId);
    }

    @PutMapping("/{projectId}/changeUserRole/{userToChangeRoleId}")
    @ResponseStatus(code = HttpStatus.OK)
    public void changeUserRoleInProject(@RequestBody String newRole, @PathVariable("userId") int userId, @PathVariable("projectId") int projectId, @PathVariable("userToChangeRoleId") int userToChangeRoleId) {
        projectService.changeUserRole(projectId, userId, userToChangeRoleId, newRole);
    }

    @GetMapping("/{projectId}")
    @ResponseStatus(code = HttpStatus.OK)
    public GetProjectDto getProject(@PathVariable("userId") int userId, @PathVariable("projectId") int projectId) {
        return projectService.getProject(userId, projectId);
    }

    @GetMapping("")
    @ResponseStatus(code = HttpStatus.OK)
    public List<GetProjectDto> getProjects(@PathVariable("userId") int userId) {
        return projectService.getProjects(userId);
    }
}
