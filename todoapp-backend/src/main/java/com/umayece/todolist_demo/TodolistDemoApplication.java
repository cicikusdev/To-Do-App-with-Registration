package com.umayece.todolist_demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.umayece.todolist_demo")
public class TodolistDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(TodolistDemoApplication.class, args);
	}

}
