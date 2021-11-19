package com.destruction.myDemolish.controllers;

import com.destruction.myDemolish.domain.AppUser;
import com.destruction.myDemolish.services.UserService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/")
public class AppUserController {

    private final UserService userService;

    @GetMapping("/all")
    public List<AppUser> getAll() {
        return userService.getAllAppUsers();
    }

    @PostMapping("/user/save")
    public AppUser createAppUser(@RequestBody AppUser appUser) {
        return userService.saveUser(appUser);
    }

    @PostMapping("/role/save")
    public ResponseEntity createRole(@RequestBody RoleToUser roleToUser) {
        userService.addRoleToUser(roleToUser.username, roleToUser.roleName);
        return ResponseEntity.ok().build();
    }


    @Data
    class RoleToUser {
        private String username;
        private String roleName;
    }

}
