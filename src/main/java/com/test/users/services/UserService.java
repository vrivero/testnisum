package com.test.users.services;

import com.test.users.exceptions.NotFoundException;
import com.test.users.dtos.request.LoginRequest;
import com.test.users.dtos.request.UserRequest;
import com.test.users.dtos.response.UserResponse;

public interface UserService {

    UserResponse createUser(UserRequest newUser);

    UserResponse updateUser(String id, UserRequest updateUser) throws NotFoundException;

    UserResponse loginUser(LoginRequest loginUser) throws NotFoundException;


}
