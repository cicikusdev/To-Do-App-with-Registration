package com.umayece.todolist_demo.repository;

import com.umayece.todolist_demo.model.Todo;

import com.umayece.todolist_demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {
    List<Todo> findByUser(User user);
}
