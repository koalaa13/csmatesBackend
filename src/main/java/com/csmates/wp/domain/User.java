package com.csmates.wp.domain;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Date;

@SuppressWarnings({"unused", "WeakerAccess"})
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "login"))
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // should have IDENTITY here but it does not work
    private Long id;

    @NotNull
    @NotEmpty
    @Size(min = 2, max = 24)
    private String login;

    @CreationTimestamp
    private Date creationTime;

    // aka nickname
    @NotNull
    @NotEmpty
    @Size(min = 2, max = 24)
    private String username;

    @NotNull
    @NotEmpty
    @Size(min = 2, max = 24)
    private String password;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    public User(String login, String username, String password) {
        this.login = login;
        this.username = username;
        this.password = password;
    }

    public User() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
