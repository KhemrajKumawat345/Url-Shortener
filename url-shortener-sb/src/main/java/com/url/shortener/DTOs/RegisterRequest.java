package com.url.shortener.DTOs;

import lombok.Data;

import java.util.List;

@Data
public class RegisterRequest {
    private String username;
    private String email;
    private List<String> roles;
    private String password;
}
