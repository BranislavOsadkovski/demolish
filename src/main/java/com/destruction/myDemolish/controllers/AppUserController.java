package com.destruction.myDemolish.controllers;

import com.destruction.myDemolish.domain.AppUser;
import com.destruction.myDemolish.domain.UserAccount;
import com.destruction.myDemolish.services.UserService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class AppUserController {

    private final UserService userService;

    @GetMapping("/all")
    public List<AppUser> getAll() {
        return userService.getAllAppUsers();
    }

    @PostMapping("/create")
    public ResponseEntity<AppUser> createAppUser(@RequestBody AppUser appUser) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.saveAppUser(appUser));
    }

    @GetMapping("/auth")
    public Collection<? extends GrantedAuthority> permissions(@AuthenticationPrincipal UserAccount userAccount) {
        return userAccount.getAuthorities();
    }

    @PostMapping("/role/save")
    public ResponseEntity createRole(@RequestBody RoleToUser roleToUser) {
//        userService.addRoleToUser(roleToUser.username, roleToUser.roleName);
        return ResponseEntity.ok().build();
    }


    @Data
    class RoleToUser {
        private String username;
        private String roleName;
    }

}
