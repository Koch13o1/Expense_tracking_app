package com.sharedexp.user_service.controller;

import com.sharedexp.user_service.model.User;
import com.sharedexp.user_service.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserRepository userRepo;

    public UserController(UserRepository userRepo){
        this.userRepo = userRepo;
    }

    @PostMapping
    public User createUser(@RequestBody User user){
        return userRepo.save(user);
    }

    @GetMapping
    public List<User> getAllUsers(){
        return userRepo.findAll();
    }


}
