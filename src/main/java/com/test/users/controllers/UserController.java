package com.test.users.controllers;

import com.test.users.dtos.request.LoginRequest;
import com.test.users.dtos.request.UserRequest;
import com.test.users.dtos.response.ErrorResponse;
import com.test.users.dtos.response.MessageResponse;
import com.test.users.dtos.response.SuccessResponse;
import com.test.users.services.UserService;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/users")
public class UserController {


    @Autowired
    private UserService service;


    /**
     * Create new user
     * @param request data to save new user
     * @return
     */
    @PostMapping
    public ResponseEntity<MessageResponse> create(@Valid @RequestBody UserRequest request){
        try {
            return ResponseEntity.ok().body(
                    new SuccessResponse("Usuario creado exitosamente", service.createUser(request)));
        }catch (ValidationException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("error: " + e.getMessage()));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("error: " + e.getMessage()));
        }
    }

    /**
     * Update user
     * @param request data to update existing user
     * @return
     */
    @PutMapping("/{id}")
    public ResponseEntity<MessageResponse> update(@PathVariable String id, @Valid @RequestBody UserRequest request){
        try {
            return ResponseEntity.ok().body(
                    new SuccessResponse("Usuario actualizado exitosamente", service.updateUser(id, request)));
        }catch (ValidationException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("error: " + e.getMessage()));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("error: " + e.getMessage()));
        }
    }

    /**
     * Login user
     * @param request data for login user
     * @return
     */
    @PostMapping("/login")
    public ResponseEntity<MessageResponse> login(@Valid @RequestBody LoginRequest request){
        try {
            return ResponseEntity.ok().body(
                    new SuccessResponse("Usuario creado exitosamente", service.loginUser(request)));
        }catch (ValidationException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("error: " + e.getMessage()));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("error: " + e.getMessage()));
        }
    }
}