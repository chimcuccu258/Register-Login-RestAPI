package com.example.be.repository;

import com.example.be.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  User findByEmail(String email);
  boolean existsByPhoneNumber(String phoneNumber);
  boolean existsByEmail(String email);

}
