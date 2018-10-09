package com.michalowicz.inzynierka;

import com.michalowicz.inzynierka.entity.User;
import com.michalowicz.inzynierka.repository.UserDao;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class InzynierkaApplication {

    public static void main(String[] args) {
        SpringApplication.run(InzynierkaApplication.class, args);
    }


    @Bean
    CommandLineRunner init(final UserDao userDao){
        return args -> userDao.save(new User("dawid","dawid"));
    }
}
