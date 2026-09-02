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
@RequestMapping("/api/admin")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService){
        this.userService = userService;
    }
    @PostMapping("/create")
    public ResponseEntity<UserResponse> createUser(@RequestBody User user){
        return ResponseEntity.ok(userService.createUser(user));
    }
}
