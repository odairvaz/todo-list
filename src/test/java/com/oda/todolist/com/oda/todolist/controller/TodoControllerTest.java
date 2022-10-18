package com.oda.todolist.com.oda.todolist.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oda.todolist.com.oda.todolist.model.Todo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(TodoController.class)
public class TodoControllerTest {

    @MockBean
    private TodoController controller;

    @Autowired
    private MockMvc mvc;

    @Test
    public void whenPostTodo_thenCreateTodo() throws Exception {
        Todo todo = new Todo("Todo", "Todo Description", false);
        given(controller.addTodo(Mockito.any())).willReturn(todo);

        mvc.perform(post("/api/todos")
                        .content(asJsonString(todo))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(todo.getName())))
                .andExpect(jsonPath("$.description", is(todo.getDescription())));
    }

    @Test
    public void givenTodos_whenGetTodos_thenReturnJsonArray() throws Exception {
        List<Todo> allTodos = new ArrayList<>(
                Arrays.asList(new Todo("Todo 1", "Description 1", true),
                        new Todo("Todo 2", "Description 2", true)));

        given(controller.getAllTodos()).willReturn(allTodos);

        mvc.perform(get("/api/todos")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is(allTodos.get(0).getName())))
                .andExpect(jsonPath("$[0].description", is(allTodos.get(0).getDescription())))
                .andExpect(jsonPath("$[1].name", is(allTodos.get(1).getName())))
                .andExpect(jsonPath("$[1].description", is(allTodos.get(1).getDescription())));

    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}