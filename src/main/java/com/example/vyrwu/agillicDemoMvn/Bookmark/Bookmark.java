package com.example.vyrwu.agillicDemoMvn.Bookmark;

import com.example.vyrwu.agillicDemoMvn.Account.Account;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

// Has a relation (N Bookmarks : 1 Account)
@Entity
public class Bookmark {

    @Id
    @GeneratedValue
    private Long id;

    @JsonIgnore(value = false)
    @ManyToOne
    private Account account;

    private String uri;

    private String description;

    public Bookmark(Account account, String uri, String description) {
        this.account = account;
        this.uri = uri;
        this.description = description;
    }

    public Bookmark() {

    }

    public Account getAccount() {
        return account;
    }

    public Long getId() {
        return id;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
