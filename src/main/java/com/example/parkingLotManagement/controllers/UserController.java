package com.example.parkingLotManagement.controllers;

import com.example.parkingLotManagement.entities.User;
import com.example.parkingLotManagement.dtos.UserResponse;
import com.example.parkingLotManagement.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController (UserService userService){
        this.userService = userService;
    }
    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody User user){
        return ResponseEntity.ok(userService.createUser(user));
    }
}
