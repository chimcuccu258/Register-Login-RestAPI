package com.example.be.service;

import com.example.be.model.User;
import com.example.be.payload.request.LoginRequest;
import com.example.be.payload.request.RegisterRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
  List<User> getUsers();
  ResponseEntity<Object> createUser(RegisterRequest registerRequest);
  ResponseEntity<Object> validateUser(LoginRequest loginRequest);
  ResponseEntity<String> deleteUserById(Long userid);

  ResponseEntity<Object> getAll();
}
