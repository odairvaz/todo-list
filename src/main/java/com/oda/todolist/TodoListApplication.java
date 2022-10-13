package com.oda.todolist;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class TodoListApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(TodoListApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(TodoListApplication.class, args);
    }

    /*
    @Bean
    public CommandLineRunner demo(TodoRepository repository) {
        return (args) -> {
            repository.save(new Todo("JS","Learn js fundamentals"));
            repository.save(new Todo("React","Learn React"));
            repository.save(new Todo("UCL","Watch sporting vs Marseille"));

            for (Todo todo : repository.findAll()) {
                LOGGER.info("TODO {} was added successfully " + todo.na);
            }
        };
    }*/


}
