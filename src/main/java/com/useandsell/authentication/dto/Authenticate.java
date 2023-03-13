package com.useandsell.authentication.dto;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table
public class Authenticate {
    @Id
    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_sequence"
    )

    private Long userid;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private boolean isSeller;
    @Column(nullable = false)
    private boolean isLoggedIn;

    public Authenticate() {
    }

    public Authenticate(Long userid,
                        String email,
                        String password,
                        Boolean isSeller,
                        Boolean isLoggedIn) {
        this.userid = userid;
        this.email = email;
        this.password = password;
        this.isSeller = isSeller;
        this.isLoggedIn = isLoggedIn;
    }

    public Authenticate(String email,
                        String password,
                        Boolean isSeller,
                        Boolean isLoggedIn) {
        this.email = email;
        this.password = password;
        this.isSeller = isSeller;
        this.isLoggedIn = isLoggedIn;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
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

    public boolean isSeller() {
        return isSeller;
    }

    public void setSeller(boolean seller) {
        isSeller = seller;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        isLoggedIn = loggedIn;
    }
}
