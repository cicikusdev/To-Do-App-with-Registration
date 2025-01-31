package com.umayece.todolist_demo.service;

import com.umayece.todolist_demo.model.Todo;
import com.umayece.todolist_demo.model.User;
import com.umayece.todolist_demo.repository.LoginRepository;
import com.umayece.todolist_demo.repository.TodoRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService {

    private final TodoRepository todoRepository;
    private final LoginRepository loginRepository;

    public TodoService(TodoRepository todoRepository, LoginRepository loginRepository) {
        this.todoRepository = todoRepository;
        this.loginRepository = loginRepository;
    }

    public List<Todo> getUserTodos(){
        return todoRepository.findByUser(getCurrentUser());
    }

    public User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (User) auth.getPrincipal();
    }

    public List<Todo> getAllTodos() {
        return todoRepository.findAll();
    }

    public Todo getTodoById(Long id) {
        return todoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Todo not found"));
    }

    public Todo createTodo(Todo todo) {
        return todoRepository.save(todo);
    }

    public Todo updateTodo(Long id, Todo updatedTodo) {
        Todo todo = getTodoById(id);
        todo.setTitle(updatedTodo.getTitle());
        todo.setDescription(updatedTodo.getDescription());
        todo.setCompleted(updatedTodo.isCompleted());
        return todoRepository.save(todo);
    }

    public void deleteTodoById(Long id) {
        todoRepository.deleteById(id);
    }

}
