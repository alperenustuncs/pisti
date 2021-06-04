package com.example.server.service;

import com.example.server.model.Player;
import com.example.server.model.Score;
import com.example.server.repository.PlayerRepository;
import com.example.server.repository.ScoreRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 * This class is the implementation of the ScoreService interface. This class is a wrapper class
 * for crud operations of the Score entity.
 * @author Nureddin Alperen Ustun & Mustafa Ali Akcay
 */
@Service
public class ScoreServiceImpl implements ScoreService{

    private final long DAY_IN_MS = 1000 * 60 * 60 * 24;//Total mili seconds in one day
    @Autowired
    private ScoreRepository scoreRepository;
    @Autowired
    private PlayerRepository playerRepository;

    /**
     *
     * @param score score to be added
     * @return added score
     */
    @Override
    @Transactional
    public Score addScore(Score score) {
        return scoreRepository.save(score);
    }

    /**
     *
     * @return all the scores
     */
    @Override
    @Transactional
    public List<Score> getAllScores() {
        return scoreRepository.findAll();
    }

    /**
     *
     * @return weekly high scores by descending order
     */
    @Transactional
    public List<Score> getWeeklyHighScores(){

        return scoreRepository.findAllByScoreTimeBetweenOrderByScoreDesc(new Date(System.currentTimeMillis() - (7 * DAY_IN_MS)),new Date());
    }

    /**
     *
     * @return monthly high scores by descending order
     */
    @Transactional
    public List<Score> getMonthlyHighScores(){
        return scoreRepository.findAllByScoreTimeBetweenOrderByScoreDesc(new Date(System.currentTimeMillis() - (30 * DAY_IN_MS)),new Date());
    }

    /**
     *
     * @return all time high scores by descending order
     */
    @Transactional
    public List<Score> getAllTimeHighScores(){
        return scoreRepository.findAllByOrderByScoreDesc();
    }

    /**
     * Updates given score
     * @param score score to be updated
     * @return updated score
     */
    @Override
    @Transactional
    public Score updateScore(Score score) {
        Score scoreToUpdate = scoreRepository.getOne(score.getId());
        scoreToUpdate.setPlayer(score.getPlayer());
        scoreToUpdate.setScoreTime(score.getScoreTime());
        scoreRepository.save(scoreToUpdate);
        return scoreToUpdate;
    }

    /**
     * Deletes scores with given ids
     * @param idList score id list
     */
    @Override
    @Transactional
    public void deleteScore(List<Integer> idList) {
        for (int id : idList) {
            scoreRepository.deleteById(id);
        }
    }


    /**
     * From json with id, it returns score object.
     * @param jsonScore json which has id in it
     * @return desired score object
     */
    @Override
    @Transactional
    public Score findScore(JSONObject jsonScore) {
        int id = jsonScore.getInt("id");
        return scoreRepository.getOne(id);
    }
}

