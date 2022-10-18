package com.oda.todolist.com.oda.todolist.controller;

import com.oda.todolist.com.oda.todolist.model.Todo;
import com.oda.todolist.com.oda.todolist.repository.TodoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/todos")
public class TodoController {

    private TodoRepository repository;

    public TodoController(TodoRepository repository) {
        this.repository = repository;
    }

    /**
     * Get all todos
     * @return ResponseEntity all the todo in the api and the response status
     */
    @GetMapping
    public List<Todo> getAllTodos() {
        return repository.findAll();
    }

    /**
     * Find a todo by its ID
     * @param id if of the todo to be found
     * @return the todo
     */
    @GetMapping("/{id}")
    public Todo getTodo(@PathVariable("id") long id) {
        return repository.findById(id).orElseThrow();
    }

    /**
     * Add a new todo to the list
     * @param todo todo to be added
     */
    @PostMapping
    public Todo addTodo(@RequestBody Todo todo) {
        return repository.save(todo);
    }

    /**
     * Update an existing todo
     * @param id todo id
     * @param todo New info to update the todo
     * @return ResponseEntity
     */
    @PutMapping("/{id}")
    public ResponseEntity<Todo> updateTodo(@PathVariable("id") long id, @RequestBody Todo todo) {
        //Update use .map
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
    @DeleteMapping("/{id}")
    public void deleteTodo(@PathVariable("id") long id) throws IllegalArgumentException {
        repository.deleteById(id);
    }

}
