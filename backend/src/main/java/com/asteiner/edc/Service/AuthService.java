package com.asteiner.edc.Service;

import com.asteiner.edc.Others.Token;

public interface AuthService {
    Token login(String email, String password);
}
