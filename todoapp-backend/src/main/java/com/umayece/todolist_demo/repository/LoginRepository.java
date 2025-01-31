package com.umayece.todolist_demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.umayece.todolist_demo.model.User;

@Repository
public interface LoginRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
}
