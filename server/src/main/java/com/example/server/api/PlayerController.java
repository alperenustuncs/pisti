package com.example.server.api;

import com.example.server.model.Player;
import com.example.server.model.Score;
import com.example.server.service.PlayerService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/player")
public class PlayerController {
    @Autowired
    private PlayerService playerService;

    @PostMapping("/gtest")
    public String gTest(@RequestParam String pass) {
    return playerService.encodeTest(pass);
}

    @PostMapping("/add")
    public ResponseEntity<Player> addPlayer(@RequestBody Player player)  {
        //if (playerService.findPlayer())
        return ResponseEntity.ok().body(this.playerService.addPlayer(player));
    }

    @PostMapping("/update")
    public ResponseEntity<Player> updatePlayer(@RequestBody Player player) {
        return ResponseEntity.ok().body(this.playerService.updatePlayer(player));
    }

    @PostMapping("/find")
    public ResponseEntity<Player> findPlayer(@RequestBody String jsonPlayer) {
        return ResponseEntity.ok().body(this.playerService.findPlayer(new JSONObject(jsonPlayer)));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Player>> getAllPlayers() {
        return ResponseEntity.ok().body(this.playerService.getAllPlayers());
    }

    @PostMapping("/score")
    public ResponseEntity<List<Score>> findScoresOfPlayer(@RequestBody String jsonPlayer) {
        return ResponseEntity.ok().body(this.playerService.findScoresOfPlayer(new JSONObject(jsonPlayer)));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deletePlayer(@RequestBody String ids) {
        List<Integer> idList = new ArrayList<>();
        for (Object object : new JSONArray(ids)) idList.add(Integer.valueOf(String.valueOf(object)));
        this.playerService.deletePlayer(idList);
        return ResponseEntity.noContent().build();
    }

    /// for test purposes
    @DeleteMapping("/deleteAll")
    public ResponseEntity<Void> deleteAllPlayers() {
        List<Player> players = this.playerService.getAllPlayers();
        List<Integer> ids = new ArrayList<Integer>();
        for (Player player : players)
            ids.add(player.getId());
        this.playerService.deletePlayer(ids);
        return ResponseEntity.noContent().build();
    }
}

