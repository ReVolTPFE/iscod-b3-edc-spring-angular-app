package com.asteiner.edc.Repository;

import com.asteiner.edc.Entity.Project;
import com.asteiner.edc.Entity.User;
import com.asteiner.edc.Entity.UserProjectRole;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserProjectRoleRepository extends CrudRepository<UserProjectRole, Integer> {
    Optional<UserProjectRole> findByUserAndProject(User user, Project project);
}
