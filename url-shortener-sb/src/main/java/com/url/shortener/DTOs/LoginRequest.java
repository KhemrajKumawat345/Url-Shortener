package com.url.shortener.DTOs;

import lombok.Data;

@Data
public class LoginRequest {
    String username;
    String password;
}
