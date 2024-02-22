package com.test.users.controllers;

import com.test.users.dtos.request.LoginRequest;
import com.test.users.dtos.request.UserRequest;
import com.test.users.dtos.response.ErrorResponse;
import com.test.users.dtos.response.MessageResponse;
import com.test.users.dtos.response.SuccessResponse;
import com.test.users.exceptions.DuplicateException;
import com.test.users.exceptions.NotFoundException;
import com.test.users.exceptions.UserInactiveException;
import com.test.users.services.UserService;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
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
        try {
            return ResponseEntity.ok().body(new SuccessResponse("success",
                    service.listAllUsers(page, size, sortBy, sortDirection, filter)));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Ocurrió un error: " + e.getMessage()));
        }
    }

    /**
     * Create new user
     * @param request data to save new user
     * @return
     */
    @PostMapping(produces = "application/json")
    public ResponseEntity<MessageResponse> create(@Valid @RequestBody UserRequest request){
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(
                    new SuccessResponse("Usuario creado exitosamente", service.createUser(request)));
        }catch (ValidationException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("error: " + e.getMessage()));
        }catch (DuplicateException e){
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ErrorResponse("error: " + e.getMessage()));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("error: " + e.getMessage()));
        }
    }

    /**
     * Update user
     * @param id path variable user id
     * @param request data to update existing user
     * @return
     */
    @PutMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<MessageResponse> update(@PathVariable String id, @Valid @RequestBody UserRequest request){
        try {
            return ResponseEntity.ok().body(
                    new SuccessResponse("Usuario actualizado exitosamente", service.updateUser(id, request)));
        }catch (ValidationException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("error: " + e.getMessage()));
        }catch (NotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("error: " + e.getMessage()));
        }catch (DuplicateException e){
            return ResponseEntity.status(HttpStatus.CONFLICT)
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
    @PostMapping(value = "/login", produces = "application/json")
    public ResponseEntity<MessageResponse> login(@Valid @RequestBody LoginRequest request){
        try {
            return ResponseEntity.ok().body(
                    new SuccessResponse("Usuario creado exitosamente", service.loginUser(request)));
        }catch (ValidationException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("error: " + e.getMessage()));
        }catch (UserInactiveException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse("error: " + e.getMessage()));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("error: " + e.getMessage()));
        }
    }
}
