package com.cinema.security;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GenerationType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Table(name = "users_roles")
@Data @AllArgsConstructor @NoArgsConstructor 
public class UserRole {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String role;
    private String username;
    
}
