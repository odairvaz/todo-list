package com.oda.todolist.com.oda.todolist.model;


import javax.persistence.*;

@Entity(name = "todos")
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "todo_name", nullable = false)
    private String name;

    @Column(name = "todo_description")
    private String description;

    private boolean done;

    public Todo() {}

    public Todo(String name, String description, boolean done) {
        this.name = name;
        this.description = description;
        this.done = done;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}
