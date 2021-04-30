package com.example.server.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "PLAYER")
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private int id;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "USERNAME", unique = true)
    private String username;

    @Column(name = "PASSWORD")
    private String password;

    @OneToMany(mappedBy = "player")
    private List<Score> scores;

    public Player(){

    }

    public Player(int id, String email, String username, String password) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Score> getScores(){
        return scores;
    }

    public void setScores(List<Score> scores){
        this.scores = scores;
    }
}
