package com.umayece.todolist_demo.controller;

import com.umayece.todolist_demo.config.JWTService;
import com.umayece.todolist_demo.model.Todo;
import com.umayece.todolist_demo.model.User;
import com.umayece.todolist_demo.repository.LoginRepository;
import com.umayece.todolist_demo.service.TodoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/todos")
public class TodoController {

    private final TodoService todoService;
    private final JWTService jwtService;
    private final LoginRepository loginRepository;

    public TodoController(TodoService todoService, JWTService jwtService, LoginRepository loginRepository) {
        this.todoService = todoService;
        this.jwtService = jwtService;
        this.loginRepository = loginRepository;
    }

    //Kullanıcıya özel get'ler
    @GetMapping("/myTodos")
    public List<Todo> getUserTodos(){
        return todoService.getUserTodos();
    }

    // Kullanıcı Todo eklerken kendi hesabına kaydedilsin
    @PostMapping("/addTodo")
    public Todo createTodo(@RequestHeader("Authorization") String token, @RequestBody Todo todo) {
        String email = jwtService.extractUsername(token.substring(7)); // Token’dan çek
        Optional<User> optionalUser = loginRepository.findByEmail(email);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();  // Optional içerisindeki User'ı al
            todo.setUser(user);  // Kullanıcıyı todo'ya set et
            return todoService.createTodo(todo);  // Todo'yu kaydet ve JSON olarak döndür
        } else {
            throw new RuntimeException("User not found");  // Eğer kullanıcı bulunamazsa hata fırlat
        }
    }




    @GetMapping("/{id}")
    public Todo getTodoById(@PathVariable Long id) {
        return todoService.getTodoById(id);
    }

    @PostMapping()
    public Todo createTodo(@RequestBody Todo todo) {
        return todoService.createTodo(todo);
    }

    @PutMapping("/{id}")
    public Todo updateTodo(@PathVariable Long id, @RequestBody Todo todo) {
        return todoService.updateTodo(id, todo);
    }

    @DeleteMapping("/{id}")
    public void deleteTodoById(@PathVariable Long id) {
        todoService.deleteTodoById(id);
    }
}
