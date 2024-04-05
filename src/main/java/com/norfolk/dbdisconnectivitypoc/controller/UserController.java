package com.norfolk.dbdisconnectivitypoc.controller;

import com.norfolk.dbdisconnectivitypoc.model.User;
import com.norfolk.dbdisconnectivitypoc.service.IUserService;
import com.norfolk.dbdisconnectivitypoc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {


    @Autowired
    IUserService userService;

    @GetMapping("/hello")
    public String hello() {
        return "Hello from Spring Boot";
    }

    @GetMapping("/user/create")
    public ResponseEntity<User> createUser(){
        User user = userService.createUser();
        if (user != null ) {
            return ResponseEntity.ok()
                    .body(user);
        }else {
            return null;
        }
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers(){
        List<User> usersList= userService.getUsers();
        if (usersList != null ) {
            return ResponseEntity.ok()
                    .body(usersList);
        }else {
            return null;
        }
    }

    @GetMapping("/users/db")
    public ResponseEntity<List<User>> getUsersDirectlyDB(){
        List<User> usersList= userService.getUserFromDB();
        if (usersList != null ) {
            return ResponseEntity.ok()
                    .body(usersList);
        }else {
            return null;
        }
    }


}
