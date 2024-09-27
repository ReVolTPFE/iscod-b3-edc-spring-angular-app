package com.asteiner.edc.Service;

import com.asteiner.edc.Entity.User;
import com.asteiner.edc.Exception.NotFoundException;
import com.asteiner.edc.Others.Token;
import com.asteiner.edc.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public Token login(String email, String password) {
        Optional<User> user = userRepository.findByEmailAndPassword(email, password);

        if (user.isPresent()) {
            Token token = new Token();
            token.setToken(UUID.randomUUID().toString());

            return token;
        }

        throw new NotFoundException();
    }
}
