package com.umayece.todolist_demo.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.umayece.todolist_demo.model.User;
import com.umayece.todolist_demo.repository.LoginRepository;

@Component
public class UserService implements UserDetailsService {

    @Autowired
    private LoginRepository repository;

    @Override
    public User loadUserByUsername(String email) {
        Optional<User> userInfo = repository.findByEmail(email);
        return userInfo.orElseThrow(() -> new UsernameNotFoundException("Kullanıcı bulunamadı"));
    }
}
