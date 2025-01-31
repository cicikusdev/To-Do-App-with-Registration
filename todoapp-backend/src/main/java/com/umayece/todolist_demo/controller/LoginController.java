package com.umayece.todolist_demo.controller;

import java.util.Optional;

import com.umayece.todolist_demo.config.JWTService;
import com.umayece.todolist_demo.dto.*;
import com.umayece.todolist_demo.repository.LoginRepository;
import com.umayece.todolist_demo.model.User;
import com.umayece.todolist_demo.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.umayece.todolist_demo.dto.LoginRequest;
import com.umayece.todolist_demo.dto.SignupRequest;
import com.umayece.todolist_demo.dto.SignupResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
//@CrossOrigin(origins = "http://localhost:4200")
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private LoginService loginService;

//    @PostMapping("/doLogin")
//    public ResponseEntity<LoginResponse> doLogin(@RequestBody LoginRequest request) {
//        LoginResponse response = new LoginResponse();
//
//        Authentication authentication = authenticationManager
//                .authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
//
//        if (authentication.isAuthenticated()) {
//            response.setToken(jwtService.generateToken(request.getEmail()));
//        }
//
//        return new ResponseEntity<>(response, HttpStatus.CREATED);
//    }

    @PostMapping("/doLogin")
    public ResponseEntity<LoginResponse> doLogin(@RequestBody LoginRequest request) {
        LoginResponse response = new LoginResponse();

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        if (authentication.isAuthenticated()) {
            // Kullanıcının rollerini al (Örnek: "ROLE_USER")
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String role = userDetails.getAuthorities().stream()
                    .findFirst()
                    .map(GrantedAuthority::getAuthority)
                    .orElse("USER"); // Fallback

            // Token'a rolü ekleyerek oluştur
            response.setToken(jwtService.generateToken(request.getEmail(), role));
        }

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/dashboard")
    public ResponseEntity<DashboardResponse> dashboard() {
        DashboardResponse response = new DashboardResponse();
        response.setResponse("Success");

        System.out.println("Dashboard Response");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/doRegister")
    public ResponseEntity<SignupResponse> doRegister(@RequestBody SignupRequest request) {
        return new ResponseEntity<>(loginService.doRegister(request), HttpStatus.CREATED);
    }

}
