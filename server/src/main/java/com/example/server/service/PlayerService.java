package com.example.server.service;

import com.example.server.model.Player;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * This interface includes functions regarding crud operations on Player entity.
 * @author Nureddin Alperen Ustun & Mustafa Ali Akcay
 */
@Service
public interface PlayerService {
    String encodeTest(String pass);
    Player addPlayer(Player player);
    List<Player> getAllPlayers();
    Player updatePlayer(Player player);
    void deletePlayer(List<Integer> idList);
    void deletePlayer(int id);
    Player findPlayer(String username);
    Player findPlayer(int id);
    Player findPlayer(String email, String password);
    void forgottenPassword(String username);
    void updatePassword(String password, String token);//exceptions are yet to be thrown

}
