package com.example.shopappbackend.services.token;

import com.example.shopappbackend.models.Token;
import com.example.shopappbackend.models.User;

public interface ITokenService {
    Token addToken(User user, String token, boolean isMobileDevice);
    Token refreshToken(String refreshToken, User user) throws Exception;
}
