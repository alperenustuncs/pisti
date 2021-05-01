package com.example.server.service;

import com.example.server.model.Player;
import com.example.server.repository.PlayerRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is the implementation of the PlayerService interface. This class is a wrapper class
 * for crud operations of Player entity.
 * @author Nureddin Alperen Ustun & Mustafa Ali Akcay
 */
@Service
public class PlayerServiceImpl implements PlayerService{

    /**
     *
     * @return PasswordEncoder object
     */
    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    /**
     *
     * @param player Player to be added
     * @return if the given informations has unique email and username it returns newly added
     * Player otherwise it returns null.
     */
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
        return playerRepository.save(playerToAdd);
    }

    /**
     *
     * @return all the players
     */
    @Override
    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }

    /**
     *
     * @param player player to be updated
     * @return updated player
     */
    @Override
    public Player updatePlayer(Player player) {
        Player playerToUpdate = playerRepository.findPlayerByUsername(player.getUsername());
        playerToUpdate.setUsername(player.getUsername());
        playerToUpdate.setEmail(player.getEmail());
        playerToUpdate.setPassword(passwordEncoder.encode(player.getPassword()));
        playerRepository.save(playerToUpdate);
        return playerToUpdate;
    }

    /**
    * Deletes the player with given id
    * @param id of the player to be deleted
    * */
    @Override
    public void deletePlayer(int id) {
        playerRepository.deleteById(id);
    }

    /**
     * Deletes players with given ids
     * @param idList player ids to be deleted.
     */
    @Override
    public void deletePlayer(List<Integer> idList) {
        for (int id : idList) {
            playerRepository.deleteById(id);
        }
    }

    /**
     * Returns a player using given username
     * @param username username of the player
     * @return desired player
     */
    @Override
    public Player findPlayer(String username) {
        return playerRepository.findPlayerByUsername(username);
    }


    /**
     * Return a player using player's id
     * @param id player's id
     * @return queried player
     */
    @Override
    public Player findPlayer(int id) {
        return playerRepository.getOne(id);
    }
}
