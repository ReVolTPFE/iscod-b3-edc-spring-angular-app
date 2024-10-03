package com.asteiner.edc.Repository;

import com.asteiner.edc.Entity.TaskHistory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface TaskHistoryRepository extends CrudRepository<TaskHistory, Integer> {
    @Query("SELECT th FROM TaskHistory th WHERE th.task.id = :taskId ORDER BY th.id DESC")
    TaskHistory findLatestTaskHistoryByTaskId(@Param("taskId") int taskId);
}
