package com.example.be.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
  private String userName;
  private Boolean gender;
  private String phoneNumber;
  private String email;
  private List<String> roleNames;
}
