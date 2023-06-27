package com.example.be.payload.request;

import lombok.Data;

@Data
public class RegisterRequest {
  private String userName;
  private String password;
  private Boolean gender;
  private String phoneNumber;
  private String email;
}
