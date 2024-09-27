package com.asteiner.edc.Repository;

import com.asteiner.edc.Entity.Task;
import org.springframework.data.repository.CrudRepository;

public interface TaskRepository extends CrudRepository<Task, Integer> {

}
