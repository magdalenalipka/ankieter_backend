package com.example.ankieter.model;

import javax.persistence.*;

@Entity
@Table(name = "users")
public abstract class User extends AuditModel {
    @Id
    private String nick;

    @Column(name = "password", columnDefinition = "text")
    private int password;

    public String getNick() {
        return this.nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public boolean checkPassword(String password) {
        return this.password == password.hashCode();
    }

    public void setPassword(String password) {
        this.password = password.hashCode();
    }
}