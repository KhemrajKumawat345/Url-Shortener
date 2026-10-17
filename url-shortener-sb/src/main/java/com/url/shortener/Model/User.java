package com.url.shortener.Model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "users")
public class User {
    @Id     // make primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)     // for auto generated.
    private long id;
    private String email;
    private String username;
    private String password;
    private String role = "ROLE_USER";
}
