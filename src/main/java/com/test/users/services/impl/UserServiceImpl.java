package com.test.users.services.impl;

import com.test.users.configs.security.JwtTokenUtil;
import com.test.users.exceptions.DuplicateException;
import com.test.users.exceptions.NotFoundException;
import com.test.users.dtos.request.LoginRequest;
import com.test.users.dtos.request.PhoneRequest;
import com.test.users.dtos.request.UserRequest;
import com.test.users.dtos.response.UserResponse;
import com.test.users.exceptions.UserInactiveException;
import com.test.users.models.Phone;
import com.test.users.models.User;
import com.test.users.repositories.PhoneRepository;
import com.test.users.repositories.UserRepository;
import com.test.users.services.UserService;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("UserService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PhoneRepository phoneRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private JwtTokenUtil tokenUtil;

    @Override
    public Map<String, Object> listAllUsers(Integer page, Integer size, String sortBy, String sortDirection, String filter){

        Pageable paging = PageRequest.of(page, size,
                sortDirection.equals("asc") ? Sort.by(sortBy) : Sort.by(sortBy).descending());

        if (filter == null) filter = "";

        Page<User> userList;
        userList = userRepo.findFiltering(filter.toUpperCase(), paging);

        Map<String, Object> response = new HashMap<>();
        if (userList.hasContent()){
            response.put("users", toResponseList(userList.getContent()));
            response.put("page",userList.getNumber());
            response.put("size", userList.getSize());
            response.put("totalElem", userList.getTotalElements());
            response.put("totalPage", userList.getTotalPages());
        }

        return response;
    }

    @Override
    public UserResponse createUser(UserRequest request) throws DuplicateException {

        if (userRepo.existsByEmail(request.getEmail())) throw new DuplicateException("Correo ya se encuentra registrado");

        User newUser = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .isactive(true)
                .build();

        newUser.setPhones(preparePhoneData(request.getPhones(), newUser));

        userRepo.save(newUser);

        return UserResponse.toResponse(newUser, true);
    }

    @Override
    public UserResponse updateUser(String id, UserRequest request) throws NotFoundException, DuplicateException {

        User currUser = userRepo.findById(id)
                .orElseThrow(() -> (new NotFoundException("Usuario no encontrado")));

        if (!currUser.getEmail().equals(request.getEmail()) && userRepo.existsByEmail(request.getEmail()))
            throw new DuplicateException("Correo ya se encuentra registrado");

        currUser.setEmail(request.getEmail());
        currUser.setPassword(passwordEncoder.encode(request.getPassword()));
        currUser.setName(request.getName());
        currUser.setIsactive(request.getIsactive());

        userRepo.save(currUser);

        return UserResponse.toResponse(currUser, true);
    }

    @Override
    public UserResponse loginUser(LoginRequest loginUser) throws NotFoundException, UserInactiveException {

        User user = userRepo.findByEmail(loginUser.getEmail())
                .orElseThrow(() -> (new NotFoundException("Credenciales incorrectas")));

        if (!user.getIsactive())
            throw new UserInactiveException("Usuario se encuentra desactivado");

        if (!bCryptPasswordEncoder.matches(loginUser.getPassword(), user.getPassword()))
            throw new ValidationException("Credenciales incorrectas");

        user.setLastlogin(new Date());
        user.setToken(tokenUtil.generateJwtToken(user));

        userRepo.save(user);

        return UserResponse.toResponse(user, true);
    }

    private List<Phone> preparePhoneData(List<PhoneRequest> phoneRequests, User user){
        List<Phone> phoneList = new ArrayList<>();
        for (PhoneRequest phoneRequest : phoneRequests) {
            phoneList.add(Phone.builder()
                    .citycode(phoneRequest.getCitycode())
                    .countrycode(phoneRequest.getCountrycode())
                    .number(phoneRequest.getNumber())
                    .user(user)
                    .build());
        }
        return phoneList;
    }

    private List<UserResponse> toResponseList(List<User> userList){
        List<UserResponse> responseList = new ArrayList<>();
        for (User user: userList) {
            responseList.add(UserResponse.toResponse(user,false));
        }
        return responseList;
    }

}
