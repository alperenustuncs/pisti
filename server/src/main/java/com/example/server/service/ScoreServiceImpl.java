package com.example.server.service;

import com.example.server.model.Player;
import com.example.server.model.Score;
import com.example.server.repository.PlayerRepository;
import com.example.server.repository.ScoreRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ScoreServiceImpl implements ScoreService{

    private final long DAY_IN_MS = 1000 * 60 * 60 * 24;
    @Autowired
    private ScoreRepository scoreRepository;
    @Autowired
    private PlayerRepository playerRepository;

    @Override
    public Score addScore(Score score) {
        return scoreRepository.save(score);
    }

    @Override
    public List<Score> getAllScores() {
        return scoreRepository.findAll();
    }

    public List<Score> getWeeklyHighScores(){

        return scoreRepository.findAllByScoreTimeBetweenOrderByScoreDesc(new Date(System.currentTimeMillis() - (7 * DAY_IN_MS)),new Date());
    }

    public List<Score> getMonthlyHighScores(){
        return scoreRepository.findAllByScoreTimeBetweenOrderByScoreDesc(new Date(System.currentTimeMillis() - (30 * DAY_IN_MS)),new Date());
    }

    public List<Score> getAllTimeHighScores(){
        return scoreRepository.findAllByOrderByScoreDesc();
    }

    @Override
    public Score updateScore(Score score) {
        Score scoreToUpdate = scoreRepository.getOne(score.getId());
        scoreToUpdate.setPlayer(score.getPlayer());
        scoreToUpdate.setScoreTime(score.getScoreTime());
        scoreRepository.save(scoreToUpdate);
        return scoreToUpdate;
    }

    @Override
    public void deleteScore(List<Integer> idList) {
        for (int id : idList) {
            scoreRepository.deleteById(id);
        }
    }

//    username unique olmaliymis
//    @Override
//    public List<Score> findScore(JSONObject jsonScore) {
////        int id = jsonScore.getInt("id");
////        return scoreRepository.getOne(id);
//        String username = jsonScore.getString("username");
//        return scoreRepository.findScoreByUsername(username);
//        //return scoreRepository.findAll(username);
//    }



    @Override
    public Score findScore(JSONObject jsonScore) {
        int id = jsonScore.getInt("id");
        return scoreRepository.getOne(id);
    }
}

