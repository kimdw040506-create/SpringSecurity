package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Pattern(
            regexp = "^[\\p{L}]+$",
            message = "Name must contain only letters")
    @Column(name = "name")
    @NotBlank(message = "Name must not be blank")
    @Size(max = 30, message = "Name must contain at most 30 characters")
    private String name;

    @Pattern(
            regexp = "^[\\p{L}]+$",
            message = "Surname must contain only letters")
    @Column(name = "surname")
    @NotBlank(message = "Surname must not be blank")
    @Size(max = 30, message = "Surname must contain at most 30 characters")
    private String surname;


    @Column(name = "email")
    @Email
    @NotBlank(message = "Email must not be blank")
    @Size(max = 250, message = "Email must contain at most 250 characters")
    private String email;

    public User() {
    }

    public User(String name, String surname, String email) {
        this.name = name;
        this.surname = surname;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}