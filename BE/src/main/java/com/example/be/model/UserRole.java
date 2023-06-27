package com.example.be.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Data
@Table(name = "USER_ROLE")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRole{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  //  @Id
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  @JsonIgnore
  private User user;

  //  @Id
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "role_id")
  private Role role;
}
