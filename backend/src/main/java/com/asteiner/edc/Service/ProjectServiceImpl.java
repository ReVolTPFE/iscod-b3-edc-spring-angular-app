package com.asteiner.edc.Service;

import com.asteiner.edc.Entity.Project;
import com.asteiner.edc.Entity.User;
import com.asteiner.edc.Entity.UserProjectRole;
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
        Optional<User> user = userRepository.findById(userId);

        if (user.isPresent()) {
            User actualUser = user.get();

            projectRepository.save(project);

            UserProjectRole userProjectRole = new UserProjectRole();
            userProjectRole.setUser(actualUser);
            userProjectRole.setProject(project);
            userProjectRole.setRole("ADMIN");

            userProjectRoleRepository.save(userProjectRole);
        }
    }
}
