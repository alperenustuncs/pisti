package com.example.server.model;


import javax.persistence.*;
import java.util.Date;

/**
 * This is a Score model class which represents Scores.
 *
 * @author Nureddin Alperen Ustun & Mustafa Ali Akcay
 *
 */
@Entity
@Table(name = "SCORE")
public class Score {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private int id;

    @Column(name = "score")
    private int score;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "player_id", nullable = false)
    private Player player;

    //farkli temporal type olabilir.
    //farkli date librarysi olabilir.
    @Column(name="score_time")
    @Temporal(TemporalType.TIMESTAMP)
    public Date scoreTime;

    public Score() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Date getScoreTime() {
        return scoreTime;
    }

    public void setScoreTime(Date scoreTime) {
        this.scoreTime = scoreTime;
    }

    public Score(int score){
        this.score = score;
        this.scoreTime = new Date();
    }
}
