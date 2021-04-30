package com.example.server.service;

import com.example.server.model.Player;
import com.example.server.model.Score;
import com.example.server.repository.PlayerRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PlayerServiceImpl implements PlayerService{

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public String encodeTest(String pass) {
        return passwordEncoder.encode(pass);
    }


    @Override
    public Player addPlayer(Player player) {
        if (playerRepository.findPlayerByEmail(player.getEmail()) != null
                ||
            playerRepository.findPlayerByUsername(player.getUsername()) != null)
            return null;
        Player playerToAdd = new Player();
        playerToAdd.setUsername(player.getUsername());
        playerToAdd.setEmail(player.getEmail());
        playerToAdd.setPassword(passwordEncoder.encode(player.getPassword()));
        return playerRepository.save(player);
    }

    @Override
    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }

    @Override
    public Player updatePlayer(Player player) {
        Player playerToUpdate = playerRepository.getOne(player.getId());
        playerToUpdate.setUsername(player.getUsername());
        playerToUpdate.setEmail(player.getEmail());
        playerToUpdate.setPassword(player.getPassword());
        playerRepository.save(playerToUpdate);
        return playerToUpdate;
    }

    @Override
    public void deletePlayer(List<Integer> idList) {
        for (int id : idList) {
            playerRepository.deleteById(id);
        }
    }

//    username unique olmaliymis
//    @Override
//    public List<Player> findPlayer(JSONObject jsonPlayer) {
////        int id = jsonPlayer.getInt("id");
////        return playerRepository.getOne(id);
//        String username = jsonPlayer.getString("username");
//        return playerRepository.findPlayerByUsername(username);
//        //return playerRepository.findAll(username);
//    }

    // why override
    @Override
    public Player findPlayer(String username) {
        return playerRepository.findPlayerByUsername(username);
    }

    @Override
    public Player findPlayer(JSONObject jsonPlayer) {
        int id = jsonPlayer.getInt("id");
        return playerRepository.getOne(id);
    }

    @Override
    public List<Score> findScoresOfPlayer(JSONObject jsonPlayer) {
        int id = jsonPlayer.getInt("id");
        Player player = playerRepository.getOne(id);
        return player.getScores();
    }
}
