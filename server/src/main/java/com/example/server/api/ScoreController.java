package com.example.server.api;

import com.example.server.model.Score;
import com.example.server.service.ScoreService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/score")
public class ScoreController {
    @Autowired
    private ScoreService scoreService;

    @GetMapping("/gtest")
    public String gTest() {
        return "gTest";
    }

    @PostMapping("/add")
    public ResponseEntity<Score> addScore(@RequestBody Score score)  {
        //if (scoreService.findScore())
        return ResponseEntity.ok().body(this.scoreService.addScore(score));
    }

    @PostMapping("/update")
    public ResponseEntity<Score> updateScore(@RequestBody Score score) {
        return ResponseEntity.ok().body(this.scoreService.updateScore(score));
    }

    @PostMapping("/find")
    public ResponseEntity<Score> findScore(@RequestBody String jsonScore) {
        return ResponseEntity.ok().body(this.scoreService.findScore(new JSONObject(jsonScore)));
    }

    @GetMapping("/getWeeklyHigh")
    public ResponseEntity<List<Score>> getWeeklyHighScores() {
        return ResponseEntity.ok().body(this.scoreService.getWeeklyHighScores());
    }

    @GetMapping("/getMonthlyHigh")
    public ResponseEntity<List<Score>> getMonthlyHighScores() {
        return ResponseEntity.ok().body(this.scoreService.getMonthlyHighScores());
    }

    @GetMapping("/getAllTimeHigh")
    public ResponseEntity<List<Score>> getAllTimeHighScores() {
        return ResponseEntity.ok().body(this.scoreService.getAllTimeHighScores());
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Score>> getAllScores() {
        return ResponseEntity.ok().body(this.scoreService.getAllScores());
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteScore(@RequestBody String ids) {
        List<Integer> idList = new ArrayList<>();
        for (Object object : new JSONArray(ids)) idList.add(Integer.valueOf(String.valueOf(object)));
        this.scoreService.deleteScore(idList);
        return ResponseEntity.noContent().build();
    }


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
