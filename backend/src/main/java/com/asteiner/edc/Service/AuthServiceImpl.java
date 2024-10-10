package com.asteiner.edc.Service;

import com.asteiner.edc.Entity.User;
import com.asteiner.edc.Exception.NotFoundException;
import com.asteiner.edc.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public int login(String email, String password) {
        User user = userRepository.findByEmailAndPassword(email, password).orElseThrow(() -> new NotFoundException("Invalid credentials"));

        return user.getId();
    }
}
