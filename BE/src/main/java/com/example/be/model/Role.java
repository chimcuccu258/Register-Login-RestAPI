package com.example.be.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Role{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  private String roleName;

  @OneToMany(
          mappedBy = "role",
          cascade = CascadeType.ALL,
          orphanRemoval = true
  )
  @JsonManagedReference
  private List<UserRole> userRole = new ArrayList<>();
}
