package com.oda.todolist.com.oda.todolist.controller;

import com.oda.todolist.com.oda.todolist.model.Todo;
import com.oda.todolist.com.oda.todolist.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class TodoController {

    @Autowired
    private TodoRepository repository;

    /**
     * Get all todos
     * @return ResponseEntity all the todo in the api and the response status
     */
    @GetMapping("/todos")
    public ResponseEntity<List<Todo>> getAllTodos() {
        return new ResponseEntity<>(repository.findAll(), HttpStatus.OK);
    }

    /**
     * Find a todo by its ID
     * @param id if of the todo to be found
     * @return the todo
     */
    @GetMapping("/todos/{id}")
    public ResponseEntity<Todo> getTodo(@PathVariable("id") long id) {
        try {
            return new ResponseEntity<>(repository.findById(id).get(), HttpStatus.OK);
        } catch (Exception exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Add a new todo to the list
     * @param todo todo to be added
     * @return the newly added todo
     */
    @PostMapping("/todos")
    public Todo addTodo(@RequestBody Todo todo) {
        return repository.save(todo);
    }

    /**
     * Update an existing todo
     * @param id todo id
     * @param todo New info to update the todo
     * @return ResponseEntity
     */
    @PutMapping("/todos/{id}")
    public ResponseEntity<Todo> updateTodo(@PathVariable("id") long id, @RequestBody Todo todo) {
        Optional<Todo> tobeUpdated = repository.findById(id);

        if (tobeUpdated.isPresent()) {
            Todo updated = tobeUpdated.get();
            updated.setName(todo.getName());
            updated.setDescription(todo.getDescription());
            return new ResponseEntity<>(repository.save(updated), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Remove a todo
     * @param id id of the todo to be removed
     * @throws Exception NOT_FOUND it the id doesn't exist
     */
    @DeleteMapping("/todos/{id}")
    public ResponseEntity<Todo> deleteTodo(@PathVariable("id") long id) throws IllegalArgumentException {
        try {
            repository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

}
