package com.example.userdemo.controller;

import com.example.userdemo.dto.ResponseDTO;
import com.example.userdemo.model.User;
import com.example.userdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;

@RestController
@RequestMapping("api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    @RolesAllowed("ADMIN")
    public ResponseDTO getUser(@PathVariable("id") Long id){
        return userService.getUser(id);
    }
    @PostMapping
    public String save(@RequestBody User user){
         userService.save(user);
         return "Successful";
    }
    @GetMapping("/getByName")
    public User getByName(@RequestParam("name") String name){
        return userService.getUserByName(name);
    }



}
