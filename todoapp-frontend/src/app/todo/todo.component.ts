import { Component, OnInit } from '@angular/core';
import { TodoService } from '../services/todo.service';
import { Todo } from '../models/todo';
import {LocalStorageService} from '../services/local-storage.service';
import {Router} from '@angular/router';
import {ActivatedRoute} from '@angular/router';

@Component({
  selector: 'app-todo',
  standalone: false,

  templateUrl: './todo.component.html',
  styleUrls: ['./todo.component.css'],
})
export class TodoComponent implements OnInit {
  todos: Todo[] = [];
  chosenTodo: Todo = new Todo();

  constructor(private todoService: TodoService, private storage : LocalStorageService, private router: Router) {}


  ngOnInit(): void {
    this.getTodos();
  }

  getTodos() {
    this.todoService.getTodos().subscribe((todos) => {
      this.todos = todos;
    });
  }
  addTodo(): void {
    if(this.chosenTodo.title !== null && this.chosenTodo.description !== null){
      this.chosenTodo.user = null;
      this.todoService.createTodo(this.chosenTodo).subscribe((createdTodo) => {
        this.getTodos();
        this.chosenTodo = new Todo();
      });
    }
  }

  deleteTodo(id: number): void {
    this.todoService.deleteTodoById(id).subscribe(() => {
      this.todos = this.todos.filter((todo) => todo.id !== id);
    });
  }

  editTodo(todo: Todo): void {
    this.chosenTodo = todo;
  }

  // updateTodo(): void {
  //   if (this.todoToEdit) {
  //     this.todoService.updateTodo(this.todoToEdit.id!, this.todoToEdit).subscribe((updatedTodo) => {
  //       this.getTodos();
  //       this.todoToEdit = null;
  //     });
  //   }
  // }

  cancelEdit(): void {
    // this.todoToEdit = null;
  }

  updateTodoStatus(todo: Todo): void {
    todo.completed = !todo.completed;
    this.todoService.updateTodo(todo.id!, todo).subscribe((updatedTodo) => {
      const index = this.todos.findIndex(t => t.id === updatedTodo.id);
      if (index !== -1) {
        this.todos[index] = updatedTodo;
      }
    });
  }

  isSidebarOpen = false;

  toggleSidebar() {
    this.isSidebarOpen = !this.isSidebarOpen;
  }

  markAsCompleted(todo: any) {
    todo.completed = !todo.completed; // Tamamlama durumunu değiştir
  }

  // toggleExit(){
  // this.storage.remove('auth-key');
  // }
  logout(){
    this.storage.remove('auth-key');
    this.router.navigate(['/login']);
  }
}
