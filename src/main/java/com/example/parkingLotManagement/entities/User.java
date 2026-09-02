package com.example.parkingLotManagement.entities;

import com.example.parkingLotManagement.enums.Role;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true,nullable = false)
    private String name;
    private String pass;
    @Enumerated(EnumType.STRING)
    private Role role;
}
