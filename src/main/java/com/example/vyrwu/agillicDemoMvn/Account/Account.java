package com.example.vyrwu.agillicDemoMvn.Account;

import com.example.vyrwu.agillicDemoMvn.Bookmark.Bookmark;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

// Each acc stores user bookmarks (1 acc : N bookmarks)
@Entity
public class Account {

    @Id
    @GeneratedValue
    private Long id;

    private String username;
    @JsonIgnore
    private String password;

    @JsonIgnore
    @OneToMany(mappedBy = "account")
    private Set<Bookmark> bookmarks = new HashSet<>();

    public Account() { // for JPA

    }

    public Account(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Set<Bookmark> getBookmarks() {
        return bookmarks;
    }

    public Long getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
