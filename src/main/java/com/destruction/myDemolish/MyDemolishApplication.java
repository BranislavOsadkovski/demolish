package com.destruction.myDemolish;

import com.destruction.myDemolish.domain.AppUser;
import com.destruction.myDemolish.domain.Role;
import com.destruction.myDemolish.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
public class MyDemolishApplication {

    public static void main(String[] args) {

        SpringApplication.run(MyDemolishApplication.class, args);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CommandLineRunner run(UserService userService) {
        return args -> {

            userService.saveRole(new Role(null, "ROLE_USER"));
            userService.saveRole(new Role(null, "ROLE_MANAGER"));
            userService.saveRole(new Role(null, "ROLE_ADMIN"));
            userService.saveRole(new Role(null, "ROLE_SUPER_ADMIN"));

            userService.saveUser(new AppUser(null, "Bane1", "username12", "1234", new ArrayList<>()));
            userService.saveUser(new AppUser(null, "Bane2", "username2", "1234", new ArrayList<>()));
            userService.saveUser(new AppUser(null, "Bane3", "username3", "1234", new ArrayList<>()));
            userService.saveUser(new AppUser(null, "Bane4", "username4", "1234", new ArrayList<>()));

            userService.addRoleToUser("username2", "ROLE_ADMIN");

        };
    }
}
