package com.example.parkingLotManagement.service;


import com.example.parkingLotManagement.dtos.RegisterRequest;
import com.example.parkingLotManagement.entities.User;
import com.example.parkingLotManagement.dtos.UserResponse;
import com.example.parkingLotManagement.enums.Role;
import com.example.parkingLotManagement.exceptions.DuplicateResourceException;
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
        if(userRepository.findByName(user.getName()).isPresent()){
            throw new DuplicateResourceException("User with name "+user.getName()+" is already present");
        }
        user.setPass(passwordEncoder.encode(user.getPass()));
        userRepository.save(user);
        return new UserResponse(user.getId(), user.getName(),user.getRole());
    }


    public UserResponse registerUser(RegisterRequest request){

        if(userRepository.findByName(request.getName()).isPresent()){
            throw new DuplicateResourceException("User with name "+request.getName() + " already exists");
        }
        User user = new User();

        user.setName(request.getName());
        user.setPass(passwordEncoder.encode(request.getPass()));
        user.setRole(Role.USER);
        User saved = userRepository.save(user);
        return new UserResponse(saved.getId(),saved.getName(),saved.getRole());

    }



}
