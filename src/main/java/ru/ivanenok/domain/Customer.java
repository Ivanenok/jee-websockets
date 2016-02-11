package ru.ivanenok.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ivanenok on 2/10/16.
 */
@Entity
public class Customer implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;

    //should be removed for huge count of tokens
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer")
    private List<AuthToken> tokens = new ArrayList<>();

    public Customer() {
    }

    public Customer(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<AuthToken> getTokens() {
        return tokens;
    }

    public void setTokens(List<AuthToken> tokens) {
        this.tokens = tokens;
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
}
