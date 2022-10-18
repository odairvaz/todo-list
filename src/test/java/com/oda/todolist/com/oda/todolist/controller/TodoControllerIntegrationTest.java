package com.oda.todolist.com.oda.todolist.controller;

import com.oda.todolist.Application;
import com.oda.todolist.com.oda.todolist.model.Todo;
import com.oda.todolist.com.oda.todolist.repository.TodoRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.IOException;
import java.util.List;

import static java.util.Optional.empty;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TodoControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private TodoRepository repository;

    @After
    public void resetDb() {
        repository.deleteAll();
    }

    @Test
    public void whenValidInput_thenCreateTodo() throws Exception {
        Todo todo = new Todo("Todo 1", "Description 1", false);
        mvc.perform(post("/api/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TodoControllerTest.asJsonString(todo)));

        List<Todo> found = repository.findAll();
        assertThat(found).extracting(Todo::getName).containsOnly("Todo 1");
    }

    @Test
    public void givenTodos_whenGetTodos_thenStatus200() throws Exception {
        createTestTodo("todo1", "description1", true);
        createTestTodo("todo2", "description2", false);

        mvc.perform(get("/api/todos").contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(2))))
                .andExpect(jsonPath("$[0].name", is("todo1")))
                .andExpect(jsonPath("$[0].description", is("description1")))
                .andExpect(jsonPath("$[1].name", is("todo2")))
                .andExpect(jsonPath("$[1].description", is("description2")));
    }

    private void createTestTodo(String name, String desc, boolean done) {
        Todo todo = new Todo(name, desc, done);
        repository.saveAndFlush(todo);
    }


}
