package com.example.be.controller;

import com.example.be.model.User;
import com.example.be.payload.request.LoginRequest;
import com.example.be.payload.request.RegisterRequest;
import com.example.be.repository.UserRepository;
import com.example.be.service.UserService;
import com.example.be.service.impl.UserServiceImplement;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/v1")
public class RegisterController {
  @Autowired
  UserService userService;

  @Autowired
  UserRepository userRepository;

  @GetMapping("/get-all")
  public ResponseEntity<Object> getUsers() {
    return userService.getAll();
  }


  @PostMapping("/register")
  public ResponseEntity<Object> registerUser(
          @RequestBody RegisterRequest registerRequest) {
    return userService.createUser(registerRequest);
  }

  @PostMapping("/login")
  public ResponseEntity<Object> validateUser(
          @RequestBody LoginRequest loginRequest) {
    return userService.validateUser(loginRequest);
  }

  @DeleteMapping("/delete/{userid}")
  public ResponseEntity<String> deleteUser(@PathVariable("userid") long userid) {
    return userService.deleteUserById(userid);
  }
}
