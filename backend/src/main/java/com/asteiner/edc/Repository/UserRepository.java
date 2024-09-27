package com.asteiner.edc.Repository;

import com.asteiner.edc.Entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Integer> {
    Optional<User> findByEmailAndPassword(String email, String password);
}
