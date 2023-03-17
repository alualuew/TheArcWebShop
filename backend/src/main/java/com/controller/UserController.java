package com.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.model.User;

import service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
    
    @Autowired
    private final UserService userService;

    public UserController(UserService userService) { //konstruktor
        this.userService = userService;}

//GetMapping Annotation für alle Get Requests
@GetMapping
public List<User> getAllUsers() {
    return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public Optional<User> getUserById(@PathVariable Long id){
        return userService.getUsersById(id);
    }
    @DeleteMapping("/{id}")
        public void deleteUser(@PathVariable Long id){
            userService.deleteUser(id);}
}

