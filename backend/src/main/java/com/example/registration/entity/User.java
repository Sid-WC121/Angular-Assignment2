package com.example.registration.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private int loginAttempts = 0;

    @Column
    private boolean locked = false;

    @Column
    private LocalDateTime lockTime;

    // Getters
    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getLoginAttempts() {
        return loginAttempts;
    }

    public boolean isLocked() {
        return locked;
    }

    public LocalDateTime getLockTime() {
        return lockTime;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setLoginAttempts(int loginAttempts) {
        this.loginAttempts = loginAttempts;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public void setLockTime(LocalDateTime lockTime) {
        this.lockTime = lockTime;
    }

    // Method to increment login attempts
    public void incrementLoginAttempts() {
        this.loginAttempts++;
        if (this.loginAttempts >= 3) {
            this.locked = true;
            this.lockTime = LocalDateTime.now();
        }
    }

    // Method to reset login attempts
    public void resetLoginAttempts() {
        this.loginAttempts = 0;
        this.locked = false;
        this.lockTime = null;
    }
}