package com.test.users.controllers;

import com.test.users.dtos.request.LoginRequest;
import com.test.users.dtos.request.UserRequest;
import com.test.users.dtos.response.MessageResponse;
import com.test.users.dtos.response.SuccessResponse;
import com.test.users.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/users")
public class UserController implements UserApi{


    @Autowired
    private UserService service;

    /**
     * List all users
     * @param page
     * @param size
     * @param sortBy
     * @param sortDirection
     * @param filter
     * @return
     */
    @GetMapping(produces = "application/json")
    public ResponseEntity<MessageResponse> listAll(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "20") Integer size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection,
            @RequestParam(required = false) String filter
    ){
        return ResponseEntity.ok().body(new SuccessResponse("success",
                service.listAllUsers(page, size, sortBy, sortDirection, filter)));
    }

    /**
     * Create new user
     * @param request data to save new user
     * @return
     */
    @PostMapping(produces = "application/json")
    public ResponseEntity<MessageResponse> create(@Valid @RequestBody UserRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new SuccessResponse("Usuario creado exitosamente", service.createUser(request)));

    }

    /**
     * Update user
     * @param id path variable user id
     * @param request data to update existing user
     * @return
     */
    @PutMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<MessageResponse> update(@PathVariable String id, @Valid @RequestBody UserRequest request){
        return ResponseEntity.ok().body(
                new SuccessResponse("Usuario actualizado exitosamente", service.updateUser(id, request)));

    }

    /**
     * Login user
     * @param request data for login user
     * @return
     */
    @PostMapping(value = "/login", produces = "application/json")
    public ResponseEntity<MessageResponse> login(@Valid @RequestBody LoginRequest request){
        return ResponseEntity.ok().body(
                new SuccessResponse("Usuario creado exitosamente", service.loginUser(request)));
    }
}
