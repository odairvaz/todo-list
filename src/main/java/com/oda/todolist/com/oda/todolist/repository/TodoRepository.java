package com.oda.todolist.com.oda.todolist.repository;

import com.oda.todolist.com.oda.todolist.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Long> {
}
