package com.oda.todolist;

import com.oda.todolist.com.oda.todolist.model.Todo;
import com.oda.todolist.com.oda.todolist.repository.TodoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class Application {

    private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


    @Bean
    public CommandLineRunner demo(TodoRepository repository) {
        return (args) -> {
            repository.save(new Todo("JS","Learn js fundamentals", false));
            repository.save(new Todo("React","Learn React", false));
            repository.save(new Todo("Java Spring","Spring - advance in learning spring", false));
            repository.save(new Todo("UCL","Watch sporting vs Marseille", true));

            for (Todo todo : repository.findAll()) {
                LOGGER.info("TODO {} was added successfully ", todo.getName());
            }
        };
    }


}
