package com.example.gamestore.domain.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "users")
public class User extends BaseEntity {
    @Column(nullable = false,unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(name = "full_name",nullable = false)
    private String fullName;
    @ManyToMany
    private Set<Game> games;
    @Column(nullable = false)
    private boolean isAdministrator;


    public User() {
        games = new HashSet<>();
    }

    public User(String email, String password, String fullName, boolean isAdministrator) {
        this();
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.isAdministrator = isAdministrator;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Set<Game> getGames() {
        return games;
    }

    public void setGames(Set<Game> games) {
        this.games = games;
    }

    public boolean isAdministrator() {
        return isAdministrator;
    }

    public void setAdministrator(boolean administrator) {
        isAdministrator = administrator;
    }
}
