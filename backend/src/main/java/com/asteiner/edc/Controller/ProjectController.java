package com.asteiner.edc.Controller;

import com.asteiner.edc.Entity.Project;
import com.asteiner.edc.Service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/{projectId}/addUser")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void addUserToProject(@RequestBody String email, @PathVariable("userId") int userId, @PathVariable("projectId") int projectId) {
        projectService.addUser(projectId, userId, email);
    }

    @PutMapping("/{projectId}/changeUserRole/{userToChangeRoleId}")
    @ResponseStatus(code = HttpStatus.OK)
    public void changeUserRoleInProject(@RequestBody String newRole, @PathVariable("userId") int userId, @PathVariable("projectId") int projectId, @PathVariable("userToChangeRoleId") int userToChangeRoleId) {
        projectService.changeUserRole(projectId, userId, userToChangeRoleId, newRole);
    }
}
