package com.asteiner.edc.Repository;

import com.asteiner.edc.Entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {

}
