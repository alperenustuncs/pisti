package com.example.server.service;

import com.example.server.model.Player;
import com.example.server.model.Score;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

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