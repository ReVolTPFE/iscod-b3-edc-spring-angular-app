package com.asteiner.edc.Repository;

import com.asteiner.edc.Entity.Project;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjectRepository extends CrudRepository<Project, Integer> {
    @Query("SELECT p FROM Project p JOIN p.userProjectRoles upr WHERE upr.user.id = :userId")
    List<Project> findAllProjectsByUserId(@Param("userId") int userId);
}
