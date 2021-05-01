package com.example.server.service;

import com.example.server.model.Player;
import com.example.server.model.Score;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * This interface includes functions regarding crud operations on Score entity.
 * It has functions which helps to create weekly, monthly and all time high score tables.
 * @author Nureddin Alperen Ustun & Mustafa Ali Akcay
 */
@Service
public interface ScoreService {
    Score addScore(Score score);
    List<Score> getAllScores();
    List<Score> getWeeklyHighScores();
    List<Score> getMonthlyHighScores();
    List<Score> getAllTimeHighScores();
    Score updateScore(Score score);
    void deleteScore(List<Integer> idList);
    Score findScore(JSONObject jsonScore);
}