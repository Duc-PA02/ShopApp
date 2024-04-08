package com.example.shopappbackend.services;

import com.example.shopappbackend.dtos.UpdateUserDTO;
import com.example.shopappbackend.dtos.UserDTO;
import com.example.shopappbackend.exceptions.DataNotFoundException;
import com.example.shopappbackend.models.User;

public interface IUserService {
    User createUser(UserDTO userDTO) throws Exception;
    String login(String phoneNumber, String password, Integer roleId) throws Exception;
    User getUserDetailsFromToken(String token) throws Exception;
    User updateUser(Integer userId, UpdateUserDTO updatedUserDTO) throws Exception;
}
