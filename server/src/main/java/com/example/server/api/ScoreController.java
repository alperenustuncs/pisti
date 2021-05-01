package com.example.server.api;

import com.example.server.model.Player;
import com.example.server.model.Score;
import com.example.server.repository.PlayerRepository;
import com.example.server.repository.ScoreRepository;
import com.example.server.service.PlayerService;
import com.example.server.service.ScoreService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is the Rest api for score model
 * @author Nureddin Alperen Ustun & Mustafa Ali Akcay
 *
 */
@RestController
public class ScoreController {
    @Autowired
    private ScoreService scoreService;

    @Autowired
    private PlayerService playerService;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private ScoreRepository scoreRepository;

    /**
     *
     * @return a string
     */
    @GetMapping("/gtest")
    public String gTest() {
        return "gTest";
    }

    /**
     * It is to get scores of certain player
     * @param playerId player whose scores are asked
     * @param score score object to be filled later
     * @return the information of the player which a score is added to
     * @exception throws Exception if wanted player is not found
     */
    @PostMapping("/api/players/{playerId}/scores")
    public Score createComment(@PathVariable (value = "playerId") int playerId,
                                 @RequestBody Score score) {
        try {
            return playerRepository.findById(playerId).map(post -> {
                score.setPlayer(post);
                return scoreRepository.save(score);
            }).orElseThrow(() -> new Exception());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @param score score to be updated
     * @return updated score information
     */
    @PostMapping("/update")
    public ResponseEntity<Score> updateScore(@RequestBody Score score) {
        return ResponseEntity.ok().body(this.scoreService.updateScore(score));
    }

    /**
     * Gives information about the score related to json object which is sent.
     * @param jsonScore related information about score
     * @return desired score information
     */
    @PostMapping("/find")
    public ResponseEntity<Score> findScore(@RequestBody String jsonScore) {
        return ResponseEntity.ok().body(this.scoreService.findScore(new JSONObject(jsonScore)));
    }

    /**
     *
     * @return WEEKly highly scores
     */
    @GetMapping("/getWeeklyHigh")
    public ResponseEntity<List<Score>> getWeeklyHighScores() {
        return ResponseEntity.ok().body(this.scoreService.getWeeklyHighScores());
    }

    /**
     *
     * @return monthly highly scores
     */
    @GetMapping("/getMonthlyHigh")
    public ResponseEntity<List<Score>> getMonthlyHighScores() {
        return ResponseEntity.ok().body(this.scoreService.getMonthlyHighScores());
    }

    /**
     *
     * @return all time high scores.
     */
    @GetMapping("/getAllTimeHigh")
    public ResponseEntity<List<Score>> getAllTimeHighScores() {
        return ResponseEntity.ok().body(this.scoreService.getAllTimeHighScores());
    }

    /**
     *
     * @return all the scores
     */
    @GetMapping("/getAll")
    public ResponseEntity<List<Score>> getAllScores() {
        return ResponseEntity.ok().body(this.scoreService.getAllScores());
    }

    /**
     *
     * @param ids score ids to be deleted
     * @return an empty response that shows all the players are deleted
     */
    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteScore(@RequestBody String ids) {
        List<Integer> idList = new ArrayList<>();
        for (Object object : new JSONArray(ids)) idList.add(Integer.valueOf(String.valueOf(object)));
        this.scoreService.deleteScore(idList);
        return ResponseEntity.noContent().build();
    }


    /**
     * Deletes all the scores.
     * @return an empty response that shows all the players are deleted
     */
    /// for test purposes
    @DeleteMapping("/deleteAll")
    public ResponseEntity<Void> deleteAllScores() {
        List<Score> scores = this.scoreService.getAllScores();
        List<Integer> ids = new ArrayList<Integer>();
        for (Score score : scores)
            ids.add(score.getId());
        this.scoreService.deleteScore(ids);
        return ResponseEntity.noContent().build();
    }
}
