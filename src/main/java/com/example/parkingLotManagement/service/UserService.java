package com.example.parkingLotManagement.service;


import com.example.parkingLotManagement.entities.User;
import com.example.parkingLotManagement.dtos.UserResponse;
import com.example.parkingLotManagement.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponse createUser(User user){
        user.setPass(passwordEncoder.encode(user.getPass()));
        userRepository.save(user);
        return new UserResponse(user.getId(), user.getName(),user.getRole());
    }



}
