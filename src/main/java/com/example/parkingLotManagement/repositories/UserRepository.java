package com.example.parkingLotManagement.repositories;

import com.example.parkingLotManagement.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
