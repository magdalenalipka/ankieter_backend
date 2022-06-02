package com.example.ankieter.model;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User extends AuditModel {

    @Column(name = "password", columnDefinition = "text")
    private int password;

    public boolean checkPassword(String password) {
        return this.password == password.hashCode();
    }

    public void setPassword(String password) {
        this.password = password.hashCode();
    }

}