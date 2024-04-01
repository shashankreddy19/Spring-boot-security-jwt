package com.shanks.springbootsecurityjwt.controllers;

import com.shanks.springbootsecurityjwt.model.User;
import com.shanks.springbootsecurityjwt.repository.UserRepo;
import com.shanks.springbootsecurityjwt.security.jwt.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class Dummy {
    @Autowired
    UserRepo repo;
    @Autowired
    Service s;

//    @DeleteMapping("/delete/{email}")
//    public List<User> del(@PathVariable("email") String e)
//    {
//        return s.del(e);
//    }
//
    @GetMapping("/show")
    public List<User> getUsers()
    {
        return s.showAll();
    }
//
//    @PostMapping("/add")
//    private List<User> add(@RequestBody User user)
//    {
//        return s.add(user);
//    }

 }
