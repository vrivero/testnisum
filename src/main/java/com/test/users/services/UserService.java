package com.test.users.services;

import com.test.users.exceptions.DuplicateException;
import com.test.users.exceptions.NotFoundException;
import com.test.users.dtos.request.LoginRequest;
import com.test.users.dtos.request.UserRequest;
import com.test.users.dtos.response.UserResponse;
import com.test.users.exceptions.UserInactiveException;

import java.util.Map;

public interface UserService {

    Map<String, Object> listAllUsers(Integer page, Integer size, String sortBy, String sortDirection, String filter);

    UserResponse createUser(UserRequest newUser) throws DuplicateException;

    UserResponse updateUser(String id, UserRequest updateUser) throws NotFoundException, DuplicateException;

    UserResponse loginUser(LoginRequest loginUser) throws NotFoundException, UserInactiveException;


}
