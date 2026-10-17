package com.url.shortener.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class UrlMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String originalUrl;
    private String shortUrl;
    private int clickCount = 0;
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "urlMapping") // this indicates that urlMapping field of clickEvent class owns this relationship.
    private List<ClickEvent> clickEvents;

    // use mappedBy in one side of relationship and use joincolumn in many side. JoinColumn use for foreign key.
}
