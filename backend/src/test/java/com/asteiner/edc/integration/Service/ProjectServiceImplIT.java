package com.asteiner.edc.integration.Service;

import com.asteiner.edc.Entity.Project;
import com.asteiner.edc.Entity.User;
import com.asteiner.edc.Entity.UserProjectRole;
import com.asteiner.edc.Others.GetProjectDto;
import com.asteiner.edc.Others.GetUserDto;
import com.asteiner.edc.Others.GetUserWithProjectRoleDto;
import com.asteiner.edc.Repository.UserProjectRoleRepository;
import com.asteiner.edc.Service.ProjectServiceImpl;
import com.asteiner.edc.Service.UserServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;

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
}
