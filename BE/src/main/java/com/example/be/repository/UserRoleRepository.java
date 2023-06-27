package com.example.be.repository;

import com.example.be.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

}
