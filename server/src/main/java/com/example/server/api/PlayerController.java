package com.example.server.api;

import com.example.server.model.Player;
import com.example.server.service.PlayerService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("api/player")
@RestController
public class PlayerController {
    @Autowired
    private PlayerService playerService;

    @PostMapping("/add")
    public ResponseEntity<Player> addPlayer(@RequestBody Player player) {
        return ResponseEntity.ok().body(this.playerService.addPlayer(player));
    }

    @PostMapping("/update")
    public ResponseEntity<Player> updatePlayer(@RequestBody Player player) {
        return ResponseEntity.ok().body(this.playerService.updatePlayer(player));
    }

    @PostMapping("/find")
    public ResponseEntity<List<Player>> findPlayer(@RequestBody String jsonPlayer) {
        return ResponseEntity.ok().body(this.playerService.findPlayer(new JSONObject(jsonPlayer)));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Player>> getAllPlayers() {
        return ResponseEntity.ok().body(this.playerService.getAllPlayers());
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deletePlayer(@RequestBody String ids) {
        List<Integer> idList = new ArrayList<>();
        for (Object object : new JSONArray(ids)) idList.add(Integer.valueOf(String.valueOf(object)));
        this.playerService.deletePlayer(idList);
        return ResponseEntity.noContent().build();
    }
}

