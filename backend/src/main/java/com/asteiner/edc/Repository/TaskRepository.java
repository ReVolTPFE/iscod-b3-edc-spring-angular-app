package com.asteiner.edc.Repository;

import com.asteiner.edc.Entity.Task;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaskRepository extends CrudRepository<Task, Integer> {
    @Query("SELECT t FROM Task t " +
            "JOIN t.taskHistories th " +
            "WHERE th.id = (SELECT MAX(th2.id) FROM TaskHistory th2 WHERE th2.task = t) " +
            "AND th.status = :status")
    List<Task> findTasksByStatus(@Param("status") String status);
}
