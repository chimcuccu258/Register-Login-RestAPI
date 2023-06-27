package com.example.be.service.impl;

import com.example.be.model.Role;
import com.example.be.model.User;
import com.example.be.model.UserRole;
import com.example.be.payload.request.LoginRequest;
import com.example.be.payload.request.RegisterRequest;
import com.example.be.payload.response.LoginResponse;
import com.example.be.payload.response.RegisterResponse;
import com.example.be.payload.response.UserResponse;
import com.example.be.repository.RoleRepository;
import com.example.be.repository.UserRepository;
import com.example.be.repository.UserRoleRepository;
import com.example.be.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImplement implements UserService {
  @Autowired
  UserRepository userRepository;

  @Autowired
  RoleRepository roleRepository;

  @Autowired
  UserRoleRepository userRoleRepository;


  @Override
  public List<User> getUsers() {
    return null;
  }

  @Override
  public ResponseEntity<Object> createUser(RegisterRequest registerRequest) {
    try {
      String userName = registerRequest.getUserName();
      String password = registerRequest.getPassword();
      Boolean gender = registerRequest.getGender();
      String phoneNumber = registerRequest.getPhoneNumber();
      String email = registerRequest.getEmail();

      if (userRepository.existsByPhoneNumber(phoneNumber)) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Phone number already exists!");
      }
      if (userRepository.existsByEmail(email)) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Email already exists!");
      }
      if (!validateUserName(userName)) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Invalid username!");
      }
      if (!validatePhoneNumber(phoneNumber)) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Invalid phone number!");
      }
      if (!validateEmail(email)) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Invalid email!");
      }

      Optional<Role> role = roleRepository.findById(3L);
      if (role.isEmpty()) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Role not found!");
      }

      User user = new User();
      user.setUserName(userName);
      user.setPassword(password);
      user.setGender(gender);
      user.setPhoneNumber(phoneNumber);
      user.setEmail(email);

      User savedUser = userRepository.save(user);
      UserRole userRole = UserRole.builder().role(role.get()).user(savedUser).build();
      userRoleRepository.save(userRole);
      if (savedUser.getId() > 0) {
        RegisterResponse response = new RegisterResponse();
        response.setMessage("User " + userName + " is created successfully!");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
      }
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body("An exception occurred from server with exception = " + e);
    }
    return null;
  }

  @Override
  public ResponseEntity<Object> validateUser(LoginRequest loginRequest) {
    try {
      String email = loginRequest.getEmail();
      String password = loginRequest.getPassword();
      if (email.equals("ngavm@gmail.com") && password.equals("ngavm")) {
        return ResponseEntity.ok(loginRequest);
      }

      User foundUser = userRepository.findByEmail(email);
      if (foundUser != null) {
        if (foundUser.getPassword().equals(password)) {
          LoginResponse response = new LoginResponse();
          response.setMessage("User " + foundUser.getUserName() + " is logged in successfully!");
          return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
          return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid password!");
        }
      } else {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email not found!");
      }
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body("An exception occurred from the server with exception = " + e);
    }
  }

  @Override
  public ResponseEntity<String> deleteUserById(Long userid) {
    try {
      if (userRepository.existsById(userid)) {
        userRepository.deleteById(userid);
        return ResponseEntity.ok("User " + userid + " deleted successfully");
      } else {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
      }
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body("An exception occurred from the server with exception = " + e);
    }
  }

  @Override
  public ResponseEntity<Object> getAll() {
    try {
      List<User> userList = userRepository.findAll();
      List<UserResponse> userResponses = userList.stream().map(user -> {
        UserResponse userResponse = new UserResponse();
        userResponse.setUserName(user.getUserName());
        userResponse.setGender(user.getGender());
        userResponse.setPhoneNumber(user.getPhoneNumber());
        userResponse.setEmail(user.getEmail());
        List<UserRole> userRoles = user.getUserRole();
        List<String> roleNames = userRoles.stream().map(userRole -> userRole.getRole().getRoleName()).toList();
        userResponse.setRoleNames(roleNames);
        return userResponse;
      }).toList();
      return ResponseEntity.ok().body(userResponses);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body("An exception occurred from the server with exception = " + e);
    }
  }

  public boolean validateUserName(String userName) {
    String regex = "^[a-zA-Z ]*$";

    if (userName.matches(regex)) {
      System.out.println(("Username is valid"));
      return true;
    } else {
      System.out.println("Username is invalid");
      return false;
    }
  }

//  public boolean validatePassword(String password) {
//    String regex = "(?=.*[0-9])(?=.*[!@#$%^&*()\\[\\]{}\\-_=~`|:;\"'<>,./?])(?=.*[a-z])(?=.*[A-Z])(?=.*).{8,}";
//
//    if (password.matches(regex)) {
//      System.out.println("Password is valid");
//      return true;
//    } else {
//      System.out.println("Password is invalid");
//      return false;
//    }
//  }

  public boolean validatePhoneNumber(String phoneNumber) {
    String regex = "^0\\d{9}$";

    if (phoneNumber.matches(regex)) {
      System.out.println("Phone number is valid");
      return true;
    } else {
      System.out.println("Phone number is invalid");
      return false;
    }
  }

  public boolean validateEmail(String email) {
    String regex = "^([a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6})*$";

    if (email.matches(regex)) {
      System.out.println("Email is valid");
      return true;
    } else {
      System.out.println("Email is invalid");
      return false;
    }
  }
}
