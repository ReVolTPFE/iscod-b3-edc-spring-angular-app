package com.asteiner.edc.Service;

import com.asteiner.edc.Entity.Project;
import com.asteiner.edc.Entity.User;
import com.asteiner.edc.Entity.UserProjectRole;
import com.asteiner.edc.Exception.NotFoundException;
import com.asteiner.edc.Repository.ProjectRepository;
import com.asteiner.edc.Repository.UserProjectRoleRepository;
import com.asteiner.edc.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProjectServiceImpl implements ProjectService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserProjectRoleRepository userProjectRoleRepository;

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
    public void addUser(int projectId, int userId, String email) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new NotFoundException("Project not found"));

        //if the user isn't in the project, we don't allow him to do anything
        userProjectRoleRepository.findByUserAndProject(user, project).orElseThrow(() -> new NotFoundException("This user isn't in this project"));

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
}
