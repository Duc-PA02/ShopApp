package com.example.shopappbackend.services.user;

import com.example.shopappbackend.dtos.UpdateUserDTO;
import com.example.shopappbackend.dtos.UserDTO;
import com.example.shopappbackend.exceptions.DataNotFoundException;
import com.example.shopappbackend.exceptions.InvalidPasswordException;
import com.example.shopappbackend.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IUserService {
    User createUser(UserDTO userDTO) throws Exception;
    String login(String phoneNumber, String password, Integer roleId) throws Exception;
    User getUserDetailsFromToken(String token) throws Exception;
    User getUserDetailsFromRefreshToken(String refreshToken) throws Exception;
    User updateUser(Integer userId, UpdateUserDTO updatedUserDTO) throws Exception;
    Page<User> findAll(String keyword, Pageable pageable) throws Exception;
    void resetPassword(Integer userId, String newPassword)
            throws InvalidPasswordException, DataNotFoundException ;
    void blockOrEnable(Integer userId, Boolean active) throws DataNotFoundException;
}
