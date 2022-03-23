package com.wellbeignatwork.backend.controller;

import com.wellbeignatwork.backend.entity.User;
import com.wellbeignatwork.backend.repository.UserRepository;
import com.wellbeignatwork.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
public class UserController {
    @Autowired
    UserRepository repository;
    @Autowired
    UserService service;

    @GetMapping("/all")
    public List<User> getALlUsers(){
        return repository.findAll();
    }

    @PostMapping("/add")
    public User addUser(@RequestBody User user ){
        return repository.save(user);
    }

    @PostMapping("/token/{userId}/{t}")
    public void setToken(@PathVariable Long userId,@PathVariable String t){
        service.saveFirebaseToken(userId,t);
    }

}
