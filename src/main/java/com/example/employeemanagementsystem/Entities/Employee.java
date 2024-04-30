package com.example.employeemanagementsystem.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Helper;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Employee { //stores user personal info

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String phoneNumber;

    @JsonIgnore //prevents infinite recursion
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @JsonIgnore //prevents infinite recursion
    @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL)
    private User user;

}
