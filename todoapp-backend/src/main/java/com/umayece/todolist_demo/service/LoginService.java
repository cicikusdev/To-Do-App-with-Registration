package com.umayece.todolist_demo.service;

import com.umayece.todolist_demo.dto.LoginRequest;
import com.umayece.todolist_demo.dto.SignupRequest;
import com.umayece.todolist_demo.dto.SignupResponse;
import com.umayece.todolist_demo.model.User;
import com.umayece.todolist_demo.repository.LoginRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Optional;
@Service
public class LoginService {

    @Autowired
    private LoginRepository loginRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    public String doLogin(LoginRequest request) {
        Optional<User> users = loginRepository.findByEmail(request.getEmail());

        if (users.isPresent()) {
            return "User details found";
        }
        return "User details not found";
    }

    public SignupResponse doRegister(SignupRequest request) {

        Optional<User> users = loginRepository.findByEmail(request.getEmail());

        SignupResponse response = new SignupResponse();

        if (users.isPresent()) {
            response.setResponse("User details Already found");
            return response;
        }

        User user = new User();
        user.setAddress(request.getAddress());
        user.setMobileNo(request.getMobileNo());
        user.setEmail(request.getEmail());
        user.setName(request.getName());
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole("USER");
        loginRepository.save(user);

        response.setResponse("User created with id " + user.getId());

        return response;
    }
}
