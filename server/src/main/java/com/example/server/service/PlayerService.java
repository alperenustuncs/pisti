package com.example.server.service;

import com.example.server.model.Player;
import com.example.server.model.Score;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PlayerService {
    String encodeTest(String pass);
    Player addPlayer(Player player);
    List<Player> getAllPlayers();
    Player updatePlayer(Player player);
    void deletePlayer(List<Integer> idList);
    //List<Player> findPlayer(JSONObject jsonPlayer);
    Player findPlayer(String username);
    Player findPlayer(JSONObject jsonPlayer);
    List<Score> findScoresOfPlayer(JSONObject jsonPlayer);
}
