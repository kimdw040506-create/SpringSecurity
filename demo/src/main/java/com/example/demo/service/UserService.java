package com.example.demo.service;

import com.example.demo.model.User;

import java.util.List;

public interface UserService {

    List<User> findAll();

    User findById(long id);

    void save(User user);

    void update(User user);

    void delete(long id);
}